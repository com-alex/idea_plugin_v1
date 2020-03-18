package com.fromLab.action.KeywordPopupList;


import com.fromLab.entity.Task;
import com.fromLab.utils.SocketUtil;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.ui.popup.JBPopup;
import com.intellij.openapi.ui.popup.PopupChooserBuilder;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiFile;
import com.intellij.ui.components.JBList;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @Auther: JIN KE
 * @Date: 2020/3/6 19:01
 */
public class PopupList {

    PopupList (){}


    public void createListPopup(String keyword, PsiClass psiClass, int lineStartOffset, int lineEndOffset, Document document, Editor editor, Task task){

        JBList<String> list = new JBList<>();
        String[] remainTaskFields = new String[7];
        remainTaskFields[0]="taskType";
        remainTaskFields[1]="startTime";
        remainTaskFields[2]="endTime";
        remainTaskFields[3]="progress";
        remainTaskFields[4]="timeSpent";
        remainTaskFields[5]="lockVersion";
        remainTaskFields[6]="taskDetail";
        HashMap<String, String> taskHashMap = new HashMap<>();
        taskHashMap.put(remainTaskFields[0],task.getTaskType());
        taskHashMap.put(remainTaskFields[1],task.getStartTime());
        taskHashMap.put(remainTaskFields[2],task.getEndTime());
        taskHashMap.put(remainTaskFields[3],task.getProgress());
        taskHashMap.put(remainTaskFields[4],task.getTimeSpent().toString());
        taskHashMap.put(remainTaskFields[5],task.getLockVersion().toString());
        taskHashMap.put(remainTaskFields[6],task.getTaskDetail());

        if (keyword.contains("*")) {
            String[] content = new String[7];
            //此时输入为@，显示所有keyword
            for (int i = 0; i < remainTaskFields.length; i++) {
                content[i] = remainTaskFields[i];
            }
            list.setListData(content);
        }
        else {
            //keyword不为空，自动补全
            ArrayList<String> content = new ArrayList<>();
            for (int i = 0; i < remainTaskFields.length; i++) {
                if(remainTaskFields[i].toLowerCase().contains(keyword.toLowerCase()))
                    content.add(remainTaskFields[i]);
            }
            int size=content.size();
            String[] strings = (String[]) content.toArray(new String[size]);
            list.setListData(strings);
        }

        JBPopup popup = new PopupChooserBuilder(list).setItemChoosenCallback(new Runnable() { // 添加点击项的监听事件
            @Override
            public void run() {
                (new WriteCommandAction.Simple(psiClass.getProject(), new PsiFile[]{psiClass.getContainingFile()}) {

                    protected void run() throws Throwable {
                        String selectedValue = list.getSelectedValue();
                        String inputContent= selectedValue + " " + taskHashMap.get(selectedValue);
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
