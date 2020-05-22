package com.fromLab.GUI.component;

import com.fromLab.VO.TaskVO;
import com.fromLab.entity.Task;
import com.fromLab.utils.ReflectionUtils;
import org.apache.commons.lang3.StringUtils;

import javax.swing.table.AbstractTableModel;
import java.lang.reflect.Field;
import java.util.List;

/**
 * @author wsh
 * @date 2019-12-17
 * Task Table on Select Task Dialog
 */
public class TaskTableModel extends AbstractTableModel {
    /*
     * Define the column of the table
     */
    String[] columnNames =
            {"", "taskId", "subject", "project_name", "task_priority", "task_type", "start_date", "end_date", "due_date", "status", "progress", "time_spent"};
    Object[][] data;

    public TaskTableModel() {
        this.data = new Object[0][0];
    }

    /**
     * Show the task information in table
     */
    public TaskTableModel(List<TaskVO> list, Task selectedTask) {
        this.data = new Object[list.size()][this.columnNames.length];
        for (int i = 0; i < list.size(); i++) {
            data[i][0] = "";
            TaskVO task = list.get(i);
            if (selectedTask != null) {
                //If the task is selected task, add the flag '*'
                if (task.getTaskId().equals(selectedTask.getTaskId())) {
                    data[i][0] = "*";
                }
            }
            List<Object> params = ReflectionUtils.getObjectParams(task);
            for (int j = 0; j < params.size(); j++) {

                if (params.get(j) != null) {
                    if (StringUtils.equals(params.get(j) + "", "null")) {
                        data[i][j + 1] = "";
                    } else {
                        if (j == 3) {
                            //If the field is Priority,
                            // use filter to convert the number to String in order to show what the number means
                            data[i][j + 1] = this.taskPriorityFilter(params.get(j));
                        } else {
                            data[i][j + 1] = params.get(j);
                        }
                    }
                } else {
                    data[i][j + 1] = "";
                }


            }

        }
    }

    /**
     * The filter to convert the number to the String
     *
     * @param input
     * @return
     */
    private String taskPriorityFilter(Object input) {
        Integer param = (Integer) input;
        if (param.equals(7)) {
            return "Low";
        } else if (param.equals(8)) {
            return "Normal";
        } else if (param.equals(9)) {
            return "High";
        } else if (param.equals(10)) {
            return "Immediate";
        }
        return " ";
    }


    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public int getRowCount() {
        return data.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return data[rowIndex][columnIndex];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return data[0][columnIndex].getClass();
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        data[rowIndex][columnIndex] = aValue;
        fireTableCellUpdated(rowIndex, columnIndex);
    }

}
