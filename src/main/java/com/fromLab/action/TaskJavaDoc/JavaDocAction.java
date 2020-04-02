package com.fromLab.action.TaskJavaDoc;

import com.fromLab.action.MyTypedActionHandler;
import com.fromLab.entity.Task;
import com.fromLab.utils.SocketUtil;
import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.CaretModel;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.actionSystem.EditorActionManager;
import com.intellij.openapi.editor.actionSystem.TypedAction;
import com.intellij.openapi.editor.actionSystem.TypedActionHandler;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.util.PsiTreeUtil;

import javax.swing.*;
import java.util.HashMap;


public class JavaDocAction extends AnAction {
    private JavaDocSetting javaDocSetting;
    private SocketUtil socketUtil=new SocketUtil();
    static {
        final EditorActionManager actionManager = EditorActionManager.getInstance();
        final TypedAction typedAction = actionManager.getTypedAction();

        MyTypedActionHandler handler = new MyTypedActionHandler();
        //将自定义的TypedActionHandler设置进去后，
        //返回旧的TypedActionHandler，即IDEA自身的TypedActionHandler
        TypedActionHandler oldHandler = typedAction.setupHandler(handler);
        handler.setOldHandler(oldHandler);
    }

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
        HashMap task = socketUtil.getTaskMap();

        if(task==null) {
            JOptionPane.showMessageDialog(null,
                    "The JavaDoc Annotation must be generated in a Java Class!",
                    "Tips", JOptionPane.ERROR_MESSAGE);
            return;
        }
        document.insertString(document.getLineStartOffset(line),TaskJavaDocString(task));



    }
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
    private String TaskJavaDocString(HashMap task){
        String taskJavaDocString =
                "    /**\n" +
                        "     * @taskId "+format(task.get("taskId").toString()) + "\n" +
                        "     * @taskName "+format(task.get("taskName").toString()) + "\n"+
                        "     * @projectName "+format(task.get("projectName").toString()) + "\n"+
                        "     * @status "+format(task.get("status").toString())+"\n" +
                        "     * @taskPriority "+format(task.get("taskPriority").toString())+"\n" +
                        "     * @dueTime "+format(task.get("dueTime").toString())+"\n" +
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
