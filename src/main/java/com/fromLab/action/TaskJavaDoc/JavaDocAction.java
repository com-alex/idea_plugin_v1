package com.fromLab.action.TaskJavaDoc;

import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.CaretModel;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.actionSystem.EditorActionManager;
import com.intellij.openapi.editor.actionSystem.TypedAction;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.util.PsiTreeUtil;


public class JavaDocAction extends AnAction {

    private JavaDocSetting javaDocSetting;



    @Override
    public void actionPerformed(AnActionEvent e) {


        this.javaDocSetting = JavaDocSetting.getInstance();
        this.generateJavaDoc(this.getPsiMethodFromContext(e),e);

    }
    private void generateJavaDoc(final PsiClass psiMethod, AnActionEvent e) {
        (new WriteCommandAction.Simple(psiMethod.getProject(), new PsiFile[]{psiMethod.getContainingFile()}) {
            protected void run() throws Throwable {
                JavaDocAction.this.addJavaDoc(psiMethod,e);

            }
        }).execute();
    }


    private void addJavaDoc(PsiClass psiClass, AnActionEvent e) {

        Editor editor = e.getData(CommonDataKeys.EDITOR);
        Document document = editor.getDocument();
        CaretModel caretModel = editor.getCaretModel();
        int caretOffset = caretModel.getOffset();
        int line = document.getLineNumber(caretOffset);
        String[] JavaDocStringArr= StringToStringArray(this.javaDocSetting.getJavaDocFormat());
        for (int i = 0; i < JavaDocStringArr.length; i++) {
            if(JavaDocStringArr[i]!=null){
                line++;
                document.insertString(document.getLineStartOffset(line),"    "+JavaDocStringArr[i]);
            }
            else
                break;
        }



        }
    private  String[]  StringToStringArray(String defaultFormat){
        boolean flag=true;
        String[] arr=new String[20];
        int lastindex=3;
        arr[0]="/**";
        int count=1;
        while (flag){
            int i = defaultFormat.indexOf("\n", lastindex+1);
            if (i==-1) {
                arr[count] = " */";
                flag = false;
            }
            else {
                String s=defaultFormat.substring(lastindex+1,i);
                arr[count]=s;
                count++;
                lastindex=i;
            }
        }
        return arr;
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
