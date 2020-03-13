package com.fromLab.action.KeywordPopupList;


import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.ui.popup.JBPopup;
import com.intellij.openapi.ui.popup.PopupChooserBuilder;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiFile;
import com.intellij.ui.components.JBList;

import java.awt.*;

/**
 * @Auther: JIN KE
 * @Date: 2020/3/6 19:01
 */
public class PopupList {

    PopupList (){}

    public void createListPopup(String keyword, PsiClass psiClass, int lineStartOffset, int lineEndOffset, Document document, Editor editor){
        JBList<String> list = new JBList<>();
        String[] content = new String[3];
        for (int i = 0; i <content.length ; i++) {
            if (keyword.contains("*"))
                //此时输入为@，显示所有keyword
                content[i]="default"+i;
            else
                //keyword不为空，自动补全
               content[i]=keyword+i;
        }
        list.setListData(content);
        JBPopup popup = new PopupChooserBuilder(list).setItemChoosenCallback(new Runnable() { // 添加点击项的监听事件
            @Override
            public void run() {
                (new WriteCommandAction.Simple(psiClass.getProject(), new PsiFile[]{psiClass.getContainingFile()}) {

                    protected void run() throws Throwable {
                        String selectedValue = list.getSelectedValue();
                        String inputContent= selectedValue + " " + selectedValue + "Value";
                            //替换内容，补全注释
                            //只有@的情况
                            if(keyword.contains("*")) {

                                document.replaceString(lineStartOffset, lineEndOffset, "     * @"+inputContent);
                            }
                            //写了部分keyword
                            else
                                document.replaceString(lineStartOffset,lineEndOffset,inputContent);


                    }
                }).execute();
                }
            }).createPopup();

// 设置大小
        Dimension dimension = popup.getContent().getPreferredSize();
        popup.setSize(new Dimension(150, dimension.height));
// 显示
        popup.showInBestPositionFor(editor);


    }



}
