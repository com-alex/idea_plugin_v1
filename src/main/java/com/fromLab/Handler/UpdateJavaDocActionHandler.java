package com.fromLab.Handler;

import com.fromLab.GUI.Modal.ChooseDialog;
import com.fromLab.utils.SocketUtil;
import com.fromLab.utils.StringUtil;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.actionSystem.TypedActionHandler;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.javadoc.PsiDocComment;
import com.intellij.psi.javadoc.PsiDocTag;
import com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @Auther: JIN KE
 * @Date: 2020/3/5 15:54
 */
public class UpdateJavaDocActionHandler implements TypedActionHandler {
    //use dots to complete each line of comments to make each line has a length of 100
    public String format(String s) {
        int length = 100;
        StringBuilder sb = new StringBuilder();
        sb.append(s);
        int size = s.length();
        for (int i = size; i < length; i++) {
            sb.append(".");
        }
        return sb.toString();
    }

    private TypedActionHandler oldHandler;
    SocketUtil socketUtil = new SocketUtil();
    @Override
    public void execute(@NotNull Editor editor, char charTyped, @NotNull DataContext dataContext) {
        if (oldHandler != null) {
            oldHandler.execute(editor, charTyped, dataContext);
        }
        ArrayList<String> docTagStringList=new ArrayList<>();
        docTagStringList.add("taskName");
        docTagStringList.add("projectName");
        docTagStringList.add("status");
        docTagStringList.add("taskType");
        docTagStringList.add("progress");
        docTagStringList.add("taskPriority");
        docTagStringList.add("dueTime");
        docTagStringList.add("taskDetail");

        PsiFile psiFile = CommonDataKeys.PSI_FILE.getData(dataContext);
        final Document document = editor.getDocument();
        int offset = editor.getCaretModel().getOffset();
        PsiElement element = psiFile.findElementAt(offset);
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
                        StringUtil stringUtil = new StringUtil();
                        ArrayList<String> oldTasks = stringUtil.minusPlus(oldValue);
                        System.out.println(oldTasks);
                        if (curTaskId != null && socketUtil.getTask() != null) {
                            if (!stringUtil.contains(oldTasks, curTaskId)) {
                                ChooseDialog chooseDialog = new ChooseDialog(oldTasks, curTaskId);
                                chooseDialog.setOld(oldTasks);
                                chooseDialog.setCur(curTaskId);
                                if (chooseDialog.showAndGet()) {
                                    oldTasks.add(curTaskId);
                                    String cur = stringUtil.addPlus(oldTasks);
                                    document.replaceString(startOffset, endOffset, format(cur));
                                    for (String docTag: docTagStringList) {
                                        updateJavaDoc(docTag,docComment,document,taskHashMap);
                                    }
                                }
                            }
                        }


                    }
                }
            }

        }
    }
     public void updateJavaDoc(String TagName, PsiDocComment docComment, Document document,  HashMap taskHashMap){
         PsiDocTag DocTag = docComment.findTagByName(TagName);
         if (DocTag != null) {
             int startOffset = document.getLineStartOffset(document.getLineNumber(DocTag.getValueElement().getTextOffset()));
             int endOffset = document.getLineEndOffset(document.getLineNumber(startOffset));
             String curTaskDetail = (String) taskHashMap.get(TagName);
             document.replaceString(startOffset, endOffset, format("     * @"+TagName+" " + curTaskDetail));
         }
     }

            public void setOldHandler (TypedActionHandler oldHandler){
                this.oldHandler = oldHandler;
            }
        }

