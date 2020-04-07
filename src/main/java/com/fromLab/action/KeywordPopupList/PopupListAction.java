package com.fromLab.action.KeywordPopupList;

import com.fromLab.entity.Task;
import com.fromLab.utils.SocketUtil;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.SelectionModel;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.util.PsiTreeUtil;

import java.util.HashMap;

public class PopupListAction extends AnAction {

    PopupList popupList=new PopupList();
    private SocketUtil socketUtil=new SocketUtil();

    @Override
    public void actionPerformed(AnActionEvent e) {
        this.generatePopupList(this.getPsiMethodFromContext(e),e);
    }



    private void generatePopupList(final PsiClass psiMethod, AnActionEvent e) {
        (new WriteCommandAction.Simple(psiMethod.getProject(), new PsiFile[]{psiMethod.getContainingFile()}) {
            @Override
            protected void run() throws Throwable {
                PopupListAction.this.addPopupList(psiMethod,e);

            }
        }).execute();
    }
    private void addPopupList(PsiClass psiClass, AnActionEvent e) {
        Editor editor = e.getData(PlatformDataKeys.EDITOR);
        Project project = e.getProject();
        Document document = editor.getDocument();
        SelectionModel selectionModel = editor.getSelectionModel();
        selectionModel.selectWordAtCaret(false);
        HashMap task = socketUtil.getTaskMap();
        String selectedText = selectionModel.getSelectedText();
        //当只输入了@的时候，WordAtCaret会选中两个整行...,改为使用LineAtCaret
        int lineEndOffset;
        int lineStartOffset;
        if (selectedText.contains("*")){
            selectionModel.removeSelection();
            lineStartOffset= editor.getCaretModel().getOffset();
            lineEndOffset=document.getLineEndOffset(document.getLineNumber(lineStartOffset));
        }
        else {
            lineStartOffset = selectionModel.getSelectionStart();
            lineEndOffset = document.getLineEndOffset(document.getLineNumber(selectionModel.getSelectionEnd()));
        }
        popupList.createListPopup(selectedText,project,lineStartOffset,lineEndOffset,document,editor,task);

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
