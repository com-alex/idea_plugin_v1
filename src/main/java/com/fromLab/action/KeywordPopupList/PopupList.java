package com.fromLab.action.KeywordPopupList;


import com.fromLab.action.MyTypedActionHandler;
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
    MyTypedActionHandler myTypedActionHandler=new MyTypedActionHandler();

    public void createListPopup(String keyword, PsiClass psiClass, int lineStartOffset, int lineEndOffset, Document document, Editor editor, HashMap task){

        JBList<String> list = new JBList<>();
        String[] remainTaskFields = new String[9];
        remainTaskFields[0]="taskType";
        remainTaskFields[1]="startTime";
        remainTaskFields[2]="progress";
        remainTaskFields[3]="taskName";
        remainTaskFields[4]="projectName";
        remainTaskFields[5]="status";
        remainTaskFields[6]="taskPriority";
        remainTaskFields[7]="dueTime";
        remainTaskFields[8]="taskDetail";
        HashMap<String, String> taskHashMap = new HashMap<>();
        taskHashMap.put(remainTaskFields[0],(String)task.get("taskType"));
        taskHashMap.put(remainTaskFields[1],(String)task.get("startTime"));
        taskHashMap.put(remainTaskFields[2],(String)task.get("progress"));
        taskHashMap.put(remainTaskFields[3],(String)task.get("taskName"));
        taskHashMap.put(remainTaskFields[4],(String)task.get("projectName"));
        taskHashMap.put(remainTaskFields[5],(String)task.get("status"));
        taskHashMap.put(remainTaskFields[6],(String)task.get("taskPriority"));
        taskHashMap.put(remainTaskFields[7],(String)task.get("dueTime"));
        taskHashMap.put(remainTaskFields[8],(String)task.get("taskDetail"));




        if (keyword.contains("*")) {
            String[] content = new String[9];
            //此时输入为@，显示所有keyword
            for (int i = 0; i < remainTaskFields.length; i++) {
                content[i] = remainTaskFields[i];
            }
            list.setListData(content);
        }
        else {
            //keyword不为空，自动补全
            ArrayList<String> content = new ArrayList<>();
            char[] chars = keyword.toLowerCase().toCharArray();
            for (int i = 0; i < remainTaskFields.length; i++) {
                int count=0;
                for (int j = 0; j < chars.length; j++) {
                    if(remainTaskFields[i].toLowerCase().contains(chars[j]+""))
                        count++;
                    else
                        break;
                    if(count==chars.length)
                        content.add(remainTaskFields[i]);
                }

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

                                document.replaceString(lineStartOffset, lineEndOffset, myTypedActionHandler.format("     * @"+inputContent));
                            }
                            //写了部分keyword
                            else
                                document.replaceString(lineStartOffset,lineEndOffset,myTypedActionHandler.format(inputContent));


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
