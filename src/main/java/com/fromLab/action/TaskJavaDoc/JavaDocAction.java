package com.fromLab.action.TaskJavaDoc;

import com.fromLab.entity.Task;
import com.fromLab.utils.SocketUtil;
import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.CaretModel;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.util.PsiTreeUtil;

import javax.swing.*;


public class JavaDocAction extends AnAction {

    private JavaDocSetting javaDocSetting;
    private SocketUtil socketUtil=new SocketUtil();


    @Override
    public void actionPerformed(AnActionEvent e) {

        this.javaDocSetting = JavaDocSetting.getInstance();
        this.generateJavaDoc(this.getPsiMethodFromContext(e),e);

    }
    private void generateJavaDoc(final PsiClass psiMethod, AnActionEvent e) {
        try {
            (new WriteCommandAction.Simple(psiMethod.getProject(), new PsiFile[]{psiMethod.getContainingFile()}) {
                @Override
                protected void run() throws Throwable {
                    JavaDocAction.this.addJavaDoc(psiMethod, e);

                }
            }).execute();
        }catch (NullPointerException exception){
            JOptionPane.showMessageDialog(null,
                    "The JavaDoc Annotation must be generated in a Java Class!",
                    "Tips", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void addJavaDoc(PsiClass psiClass, AnActionEvent e) {
        Editor editor = e.getData(CommonDataKeys.EDITOR);
        Document document = editor.getDocument();
        CaretModel caretModel = editor.getCaretModel();
        int caretOffset = caretModel.getOffset();
        int line = document.getLineNumber(caretOffset);
        Task task = socketUtil.getTask();

        if(task==null) {
            JOptionPane.showMessageDialog(null,
                    "The JavaDoc Annotation must be generated in a Java Class!",
                    "Tips", JOptionPane.ERROR_MESSAGE);
            return;
        }
        document.insertString(document.getLineStartOffset(line),TaskJavaDocString(task));



    }
    private String TaskJavaDocString(Task task){
        String taskJavaDocString =
                "    /**\n" +
                "     * @taskId "+task.getTaskId() + "\n" +
                "     * @taskName "+task.getTaskName() + "\n"+
                "     * @projectName "+task.getProjectName() + "\n"+
                "     * @status "+task.getStatus()+"\n" +
                "     * @taskPriority "+task.getTaskPriority()+"\n" +
                "     * @dueTime "+task.getDueTime()+"\n" +
                "     */ ";
        return taskJavaDocString;
    }

    private PsiClass getPsiMethodFromContext(AnActionEvent e) {
        PsiElement elementAt = this.getPsiElement(e);
        return elementAt == null?null:(PsiClass) PsiTreeUtil.getParentOfType(elementAt, PsiClass.class);
    }

    private PsiElement getPsiElement(AnActionEvent e) {
        PsiFile psiFile = (PsiFile)e.getData(LangDataKeys.PSI_FILE);
        Editor editor = (Editor)e.getData(PlatformDataKeys.EDITOR);
        if(psiFile != null && editor != null) {
            int offset = editor.getCaretModel().getOffset();
            return psiFile.findElementAt(offset);
        } else {
            e.getPresentation().setEnabled(false);
            return null;
        }
    }
}
