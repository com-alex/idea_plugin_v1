package com.fromLab.action.KeywordPopupList;


import com.fromLab.Handler.UpdateJavaDocActionHandler;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.popup.JBPopup;
import com.intellij.openapi.ui.popup.PopupChooserBuilder;
import com.intellij.ui.components.JBList;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @Auther: JIN KE
 * @Date: 2020/3/6 19:01
 */
public class PopupList {

    private UpdateJavaDocActionHandler updateJavaDocActionHandler = new UpdateJavaDocActionHandler();

    public void createListPopup(String keyword, Project project, int lineStartOffset, int lineEndOffset, Document document, Editor editor, HashMap task) {

        JBList<String> list = new JBList<>();
        String[] remainTaskFields = new String[9];
        remainTaskFields[0] = "taskType";
        remainTaskFields[1] = "startTime";
        remainTaskFields[2] = "progress";
        remainTaskFields[3] = "taskName";
        remainTaskFields[4] = "projectName";
        remainTaskFields[5] = "status";
        remainTaskFields[6] = "taskPriority";
        remainTaskFields[7] = "dueTime";
        remainTaskFields[8] = "taskDetail";
        HashMap<String, String> taskHashMap = new HashMap<>();
        taskHashMap.put(remainTaskFields[0], (String) task.get("taskType"));
        taskHashMap.put(remainTaskFields[1], (String) task.get("startTime"));
        taskHashMap.put(remainTaskFields[2], (String) task.get("progress"));
        taskHashMap.put(remainTaskFields[3], (String) task.get("taskName"));
        taskHashMap.put(remainTaskFields[4], (String) task.get("projectName"));
        taskHashMap.put(remainTaskFields[5], (String) task.get("status"));
        taskHashMap.put(remainTaskFields[6], (String) task.get("taskPriority"));
        taskHashMap.put(remainTaskFields[7], (String) task.get("dueTime"));
        taskHashMap.put(remainTaskFields[8], (String) task.get("taskDetail"));


        if (keyword.contains("*")) {
            String[] content = new String[9];
            //Enter "@" at this time ,display all keywords
            System.arraycopy(remainTaskFields, 0, content, 0, remainTaskFields.length);
            list.setListData(content);
        } else {
            //keyword is not empty, complete automatically
            ArrayList<String> content = new ArrayList<>();
            char[] chars = keyword.toLowerCase().toCharArray();
            for (String remainTaskField : remainTaskFields) {
                int count = 0;
                for (char aChar : chars) {
                    if (remainTaskField.toLowerCase().contains(aChar + "")) {
                        count++;
                    } else {
                        break;
                    }
                    if (count == chars.length) {
                        content.add(remainTaskField);
                    }
                }
            }
            int size = content.size();
            String[] strings = content.toArray(new String[size]);
            list.setListData(strings);
        }

        JBPopup popup = new PopupChooserBuilder(list).setItemChoosenCallback(new Runnable() {

            // Add click event monitoring
            @Override
            public void run() {
                WriteCommandAction.runWriteCommandAction(project, new Runnable() {

                    @Override
                    public void run() {
                        String selectedValue = list.getSelectedValue();
                        String inputContent = "     * @" + selectedValue + " " + taskHashMap.get(selectedValue);
                        //Replace content, complete comments
                        document.replaceString(lineStartOffset, lineEndOffset, updateJavaDocActionHandler.format(inputContent));
                    }
                });
            }
        }).createPopup();

// Set size
        Dimension dimension = popup.getContent().getPreferredSize();
        popup.setSize(new Dimension(150, dimension.height));
// display
        popup.showInBestPositionFor(editor);


    }


}
