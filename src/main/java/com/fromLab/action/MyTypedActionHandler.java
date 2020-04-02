package com.fromLab.action;

import com.fromLab.GUI.Modal.ChooseDialog;
import com.fromLab.utils.SocketUtil;
import com.fromLab.utils.StringUtil;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.actionSystem.TypedActionHandler;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.javadoc.PsiDocComment;
import com.intellij.psi.javadoc.PsiDocTag;
import com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * @Auther: JIN KE
 * @Date: 2020/3/5 15:54
 */
public class MyTypedActionHandler implements TypedActionHandler {
    public String format(String s){
        int length=100;
        StringBuilder sb=new StringBuilder();
        sb.append(s);
        int size=s.length();
        for(int i=size;i<length;i++){
            sb.append(".");
        }
        return sb.toString();
    }

    private TypedActionHandler oldHandler;
    SocketUtil socketUtil=new SocketUtil();
    @Override
    public void execute(@NotNull Editor editor, char charTyped, @NotNull DataContext dataContext) {
        if (oldHandler != null)
            oldHandler.execute(editor, charTyped, dataContext);
        PsiFile psiFile = CommonDataKeys.PSI_FILE.getData(dataContext);
        final Project project = editor.getProject();
        final Document document = editor.getDocument();
        int offset = editor.getCaretModel().getOffset();
        final StringBuilder infoBuilder = new StringBuilder();
        PsiElement element = psiFile.findElementAt(offset);
        infoBuilder.append("Element at caret: ").append(element).append("\n");
        if (element != null) {
            PsiMethod containingMethod = PsiTreeUtil.getParentOfType(element, PsiMethod.class);
            if (containingMethod != null) {
                PsiDocComment docComment = containingMethod.getDocComment();
                if (docComment != null) {
                    PsiDocTag taskIdDocTag = docComment.findTagByName("taskId");
                    if (taskIdDocTag != null) {
                        int startOffset = taskIdDocTag.getValueElement().getTextOffset();
                        int endOffset = document.getLineEndOffset(document.getLineNumber(startOffset));
                        TextRange textRange = new TextRange(startOffset, endOffset);
                        String oldValue = document.getText(textRange).trim();
                        HashMap taskHashMap = socketUtil.getTaskMap();
                        String curTaskId = (String) taskHashMap.get("taskId");
                        StringUtil stringUtil=new StringUtil();
                        ArrayList<String> oldTasks=stringUtil.minusPlus(oldValue);
                        System.out.println(oldTasks);
                        String after=format(curTaskId);
                        infoBuilder.append("oldValue: " + oldValue + " \n");
                        String[] s = oldValue.split(" ");
                        System.out.println(oldValue);
                        System.out.println(after);
                        if(oldValue!=null&&curTaskId!=null) {
                            if (!stringUtil.contains(oldTasks,curTaskId)) {
                                ChooseDialog chooseDialog = new ChooseDialog(oldTasks,curTaskId);
                                chooseDialog.setOld(oldTasks);
                                chooseDialog.setCur(curTaskId);
                                if (chooseDialog.showAndGet()) {
                                    int result = chooseDialog.getStatus();
                                    switch (result) {
                                        case 0:
                                            System.out.println("do nothing");
                                            break;
                                        case 1:
                                            oldTasks.add(curTaskId);
                                            String cur=stringUtil.addPlus(oldTasks);
                                            document.replaceString(startOffset, endOffset, format(cur));
                                            PsiDocTag taskNameDocTag = docComment.findTagByName("taskName");
                                            if (taskNameDocTag != null) {
                                                startOffset = taskNameDocTag.getValueElement().getTextOffset();
                                                endOffset = document.getLineEndOffset(document.getLineNumber(startOffset));
                                                String curTaskName = (String) taskHashMap.get("taskName");
                                                System.out.println(curTaskName);
                                                document.replaceString(startOffset, endOffset, format(curTaskName));
                                            }
                                            PsiDocTag projectNameDocTag = docComment.findTagByName("projectName");
                                            if (projectNameDocTag != null) {
                                                startOffset = projectNameDocTag.getValueElement().getTextOffset();
                                                endOffset = document.getLineEndOffset(document.getLineNumber(startOffset));
                                                String curProjectName = (String) taskHashMap.get("projectName");
                                                document.replaceString(startOffset, endOffset, format(curProjectName));
                                            }
                                            PsiDocTag statusDocTag = docComment.findTagByName("status");
                                            if (statusDocTag != null) {
                                                startOffset = statusDocTag.getValueElement().getTextOffset();
                                                endOffset = document.getLineEndOffset(document.getLineNumber(startOffset));
                                                String curStatus = (String) taskHashMap.get("status");
                                                document.replaceString(startOffset, endOffset, format(curStatus));
                                            }
                                            PsiDocTag taskPriorityDocTag = docComment.findTagByName("taskPriority");
                                            if (taskPriorityDocTag != null) {
                                                startOffset = taskPriorityDocTag.getValueElement().getTextOffset();
                                                endOffset = document.getLineEndOffset(document.getLineNumber(startOffset));
                                                String curTaskPriority = (String) taskHashMap.get("taskPriority");
                                                document.replaceString(startOffset, endOffset, format(curTaskPriority));
                                            }
                                            PsiDocTag dueTimeTag = docComment.findTagByName("dueTime");
                                            if (dueTimeTag != null) {
                                                startOffset = dueTimeTag.getValueElement().getTextOffset();
                                                endOffset = document.getLineEndOffset(document.getLineNumber(startOffset));
                                                String curTime = (String) taskHashMap.get("dueTime");
                                                document.replaceString(startOffset, endOffset, format(curTime));
                                            }
                                            break;
                                        case 2:
                                            System.out.println("choose task");
                                            break;
                                    }
                                }
                            }
                        }
                        int count=0;
                        for (int i = 0; i <s.length ; i++) {
                            if(s[i].equals(curTaskId))
                                return;
                            else
                                count++;
                            if(count==s.length&&taskHashMap.get("taskId")!=null){
                                TextRange textRangeInParent = docComment.getTextRangeInParent();
                                //textRangeInParent.replace(docComment.getText(),NewJavaDoc);
                                int endOffset2 = document.getLineEndOffset((document.getLineNumber(containingMethod.getStartOffsetInParent()) - 1));
                                int startOffset1 = document.getLineStartOffset(document.getLineNumber(textRange.getStartOffset()));
                                int endOffset1 = document.getLineEndOffset(document.getLineNumber(textRange.getEndOffset())+2);
                                //document.deleteString(textRangeInParent.getStartOffset(),textRangeInParent.getEndOffset());
                                int textOffset = docComment.getTextOffset();
                                //document.replaceString(startOffset1,endOffset1,NewJavaDoc);
                                //updateJavaDoc(docComment,document);
                                infoBuilder.append(updateJavaDoc(docComment.getText(),taskHashMap));
                            }
                        }
                    }

                }
            }
        //    Messages.showMessageDialog(project, infoBuilder.toString(), "PSI Info", null);

        }
    }
    private void updateJavaDoc(PsiDocComment docComment, Document document){

        PsiDocTag taskNameDocTag = docComment.findTagByName("taskName");
        if (taskNameDocTag != null) {
            int startOffset = taskNameDocTag.getValueElement().getTextOffset();
            int endOffset = document.getLineEndOffset(document.getLineNumber(startOffset));
            document.replaceString(startOffset+2, endOffset,"newTaskName");
        }
        PsiDocTag projectNameDocTag = docComment.findTagByName("projectName");
        if (projectNameDocTag != null) {
            int startOffset = projectNameDocTag.getValueElement().getTextOffset();
            int endOffset = document.getLineEndOffset(document.getLineNumber(startOffset));
            document.replaceString(startOffset+"newTaskName".length(), endOffset, "newProjectName");
        }
        PsiDocTag statusDocTag = docComment.findTagByName("status");
        if (statusDocTag != null) {
            int startOffset = statusDocTag.getValueElement().getTextOffset();
            int endOffset = document.getLineEndOffset(document.getLineNumber(startOffset));
            document.replaceString(startOffset, endOffset, "newStatus");
        }


    }
    public String updateJavaDoc (String s,HashMap map){
        String newString="/**\n";
        String[] split = s.split("\n");
        String[] strings = Arrays.copyOfRange(split, 1, split.length - 1);
        for (int i = 0; i <strings.length ; i++) {
            String[] split1 = strings[i].trim().split(" ");
            for (int j = 0; j < split1.length; j++) {
                if (split1.length > 1) {
                    String keyword = split1[1].substring(1);
                    if (split1[1].equals("@taskId")) {
                        String taskId = strings[i].toString() + " " + map.get("taskId") + "\n";
                        newString += taskId;
                        break;
                    }
                    if (map.get(keyword) != null && !keyword.equals("taskId")) {
                        split1[2] = (String) map.get(keyword);
                        String string = "     " + split1[0] + " " + split1[1] + " " + split1[2] + "\n";
                        newString += string;
                        break;
                    }
                }
            }
        }
        newString+="     */";
        return newString;
    }
    public void setOldHandler(TypedActionHandler oldHandler) {
        this.oldHandler = oldHandler;
    }
}
