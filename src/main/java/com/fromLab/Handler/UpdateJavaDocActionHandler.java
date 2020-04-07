package com.fromLab.Handler;

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
public class UpdateJavaDocActionHandler implements TypedActionHandler {
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
                                    int result = chooseDialog.getStatus();
                                    switch (result) {
                                        case 0:
                                            System.out.println("do nothing");
                                            break;
                                        case 1:
                                            oldTasks.add(curTaskId);
                                            String cur = stringUtil.addPlus(oldTasks);
                                            document.replaceString(startOffset, endOffset, format(cur));
                                            PsiDocTag taskNameDocTag = docComment.findTagByName("taskName");
                                            if (taskNameDocTag != null) {
                                                startOffset = document.getLineStartOffset(document.getLineNumber(taskNameDocTag.getValueElement().getTextOffset()));
                                                endOffset = document.getLineEndOffset(document.getLineNumber(startOffset));
                                                String curTaskName = (String) taskHashMap.get("taskName");
                                                System.out.println(curTaskName);
                                                document.replaceString(startOffset, endOffset, format("     * @taskName " + curTaskName));
                                            }
                                            PsiDocTag projectNameDocTag = docComment.findTagByName("projectName");
                                            if (projectNameDocTag != null) {
                                                startOffset = document.getLineStartOffset(document.getLineNumber(projectNameDocTag.getValueElement().getTextOffset()));
                                                endOffset = document.getLineEndOffset(document.getLineNumber(startOffset));
                                                String curProjectName = (String) taskHashMap.get("projectName");
                                                document.replaceString(startOffset, endOffset, format("     * @projectName " + curProjectName));
                                            }
                                            PsiDocTag statusDocTag = docComment.findTagByName("status");
                                            if (statusDocTag != null) {
                                                startOffset = document.getLineStartOffset(document.getLineNumber(statusDocTag.getValueElement().getTextOffset()));
                                                endOffset = document.getLineEndOffset(document.getLineNumber(startOffset));
                                                String curStatus = (String) taskHashMap.get("status");
                                                document.replaceString(startOffset, endOffset, format("     * @status " + curStatus));
                                            }
                                            PsiDocTag taskTypeDocTag = docComment.findTagByName("taskType");
                                            if (taskTypeDocTag != null) {
                                                startOffset = document.getLineStartOffset(document.getLineNumber(taskTypeDocTag.getValueElement().getTextOffset()));
                                                endOffset = document.getLineEndOffset(document.getLineNumber(startOffset));
                                                String curTaskType = (String) taskHashMap.get("taskType");
                                                document.replaceString(startOffset, endOffset, format("     * @taskType " + curTaskType));
                                            }
                                            PsiDocTag progressDocTag = docComment.findTagByName("progress");
                                            if (progressDocTag != null) {
                                                startOffset = document.getLineStartOffset(document.getLineNumber(progressDocTag.getValueElement().getTextOffset()));
                                                endOffset = document.getLineEndOffset(document.getLineNumber(startOffset));
                                                String curProgress = (String) taskHashMap.get("progress");
                                                document.replaceString(startOffset, endOffset, format("     * @progress " + curProgress));
                                            }
                                            PsiDocTag taskPriorityDocTag = docComment.findTagByName("taskPriority");
                                            if (taskPriorityDocTag != null) {
                                                startOffset = document.getLineStartOffset(document.getLineNumber(taskPriorityDocTag.getValueElement().getTextOffset()));
                                                endOffset = document.getLineEndOffset(document.getLineNumber(startOffset));
                                                String curTaskPriority = (String) taskHashMap.get("taskPriority");
                                                document.replaceString(startOffset, endOffset, format("     * @taskPriority " + curTaskPriority));
                                            }
                                            PsiDocTag dueTimeDocTag = docComment.findTagByName("dueTime");
                                            if (dueTimeDocTag != null) {
                                                startOffset = document.getLineStartOffset(document.getLineNumber(dueTimeDocTag.getValueElement().getTextOffset()));
                                                endOffset = document.getLineEndOffset(document.getLineNumber(startOffset));
                                                String curDueTime = (String) taskHashMap.get("dueTime");
                                                document.replaceString(startOffset, endOffset, format("     * @dueTime " + curDueTime));
                                            }
                                            PsiDocTag taskDetailDocTag = docComment.findTagByName("taskDetail");
                                            if (taskDetailDocTag != null) {
                                                startOffset = document.getLineStartOffset(document.getLineNumber(taskDetailDocTag.getValueElement().getTextOffset()));
                                                endOffset = document.getLineEndOffset(document.getLineNumber(startOffset));
                                                String curTaskDetail = (String) taskHashMap.get("taskDetail");
                                                document.replaceString(startOffset, endOffset, format("     * @taskDetail " + curTaskDetail));
                                            }
                                            break;
                                        case 2:
                                            System.out.println("choose task");
                                            break;
                                    }
                                }
                            }


                        }
                    }
                }
            }
        }
    }

    public void setOldHandler (TypedActionHandler oldHandler){
                        this.oldHandler = oldHandler;
                    }
                }
