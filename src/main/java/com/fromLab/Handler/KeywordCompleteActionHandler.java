package com.fromLab.Handler;

import com.fromLab.action.KeywordPopupList.PopupList;
import com.fromLab.utils.SocketUtil;
import com.fromLab.utils.StringUtil;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.SelectionModel;
import com.intellij.openapi.editor.actionSystem.TypedActionHandler;
import com.intellij.openapi.project.Project;
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
 * @Date: 2020/4/7 08:49
 */
public class KeywordCompleteActionHandler implements TypedActionHandler {

    PopupList popupList = new PopupList();
    private SocketUtil socketUtil = new SocketUtil();
    private TypedActionHandler oldHandler;

    @Override
    public void execute(@NotNull Editor editor, char charTyped, @NotNull DataContext dataContext) {
        if (oldHandler != null) {
            oldHandler.execute(editor, charTyped, dataContext);
        }
        Document document = editor.getDocument();
        Project project = editor.getProject();
        PsiFile psiFile = CommonDataKeys.PSI_FILE.getData(dataContext);
        int offset = editor.getCaretModel().getOffset();
        PsiElement element = null;
        if (psiFile != null) {
            element = psiFile.findElementAt(offset);
        }
        if (element != null) {
            PsiMethod containingMethod = PsiTreeUtil.getParentOfType(element, PsiMethod.class);
            PsiDocComment docComment = null;
            if (containingMethod != null) {
                docComment = containingMethod.getDocComment();
            }
            if (docComment != null) {
                TextRange docCommentTextRange = docComment.getTextRange();
                PsiDocTag taskIdDocTag = docComment.findTagByName("taskId");
                if (taskIdDocTag != null) {
                    int startOffset = taskIdDocTag.getValueElement().getTextOffset();
                    int endOffset = document.getLineEndOffset(document.getLineNumber(startOffset));
                    TextRange textRange = new TextRange(startOffset, endOffset);
                    String oldValue = document.getText(textRange).trim();
                    HashMap taskHashMap = socketUtil.getTaskMap();
                    String curTaskId = "";
                    if (taskHashMap.size() > 0) {
                        curTaskId = (String) taskHashMap.get("taskId");
                    }
                    StringUtil stringUtil = new StringUtil();
                    ArrayList<String> oldTasks = stringUtil.minusPlus(oldValue);
                    String lastOldTask = oldTasks.get(oldTasks.size() - 1);
                    if (curTaskId.equals(lastOldTask) && docCommentTextRange.contains(offset)) {
                        SelectionModel selectionModel = editor.getSelectionModel();
                        selectionModel.selectWordAtCaret(false);
                        int selectionStart = selectionModel.getSelectionStart();
                        int selectionEnd = selectionModel.getSelectionEnd();
                        String selectedText = selectionModel.getSelectedText();
                        selectionModel.removeSelection();
                        HashMap task = socketUtil.getTaskMap();
                        int lineStartOffset = document.getLineStartOffset(document.getLineNumber(selectionStart));
                        int lineEndOffset = document.getLineEndOffset(document.getLineNumber(selectionEnd));
                        //When only @ is entered, WordAtCaret will select two entire lines ...
                        if (selectedText.contains("*")) {
                            lineStartOffset = document.getLineStartOffset(document.getLineNumber(editor.getCaretModel().getOffset()));
                            lineEndOffset = document.getLineEndOffset(document.getLineNumber(lineStartOffset));
                        } else {
                            editor.getCaretModel().moveToOffset(selectionEnd);
                        }
                        popupList.createListPopup(selectedText, project, lineStartOffset, lineEndOffset, document, editor, task);
                    }
                }
            }
        }
    }
    public void setOldHandler (TypedActionHandler oldHandler){
        this.oldHandler = oldHandler;
    }
}


