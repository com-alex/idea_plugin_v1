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
 * ������������������б�
 */
public class TaskTableModel extends AbstractTableModel {
    /*
     * ����͸ղ�һ��������������ÿ�����ݵ�ֵ
     */
    String[] columnNames =
            {"", "taskId", "subject", "project_name", "task_priority", "task_type", "start_date", "end_date", "due_date", "status", "progress", "time_spent"};
    Object[][] data;

    /**
     * ���췽������ʼ����ά����data��Ӧ������
     */
    public TaskTableModel(List<TaskVO> list) {
        this.data = new Object[list.size()][this.columnNames.length];
        for (int i = 0; i < list.size(); i++) {
            data[i][0] = "";
            TaskVO task = list.get(i);
            List<Object> params = ReflectionUtils.getObjectParams(task);
            for (int j = 0; j < params.size(); j++) {

                if (params.get(j) != null) {
                    if(StringUtils.equals(params.get(j) + "", "null")){
                        data[i][j + 1] = "";
                    }
                    else{
                        if(j == 3){
                            data[i][j + 1] = this.taskPriorityFilter(params.get(j));
                        }else{
                            data[i][j + 1] = params.get(j);
                        }
                    }
                } else {
                    data[i][j + 1] = "";
                }


            }

        }
    }

    private String taskPriorityFilter(Object input){
        Integer param = (Integer) input;
        if(param.equals(7)){
            return "Low";
        }else if(param.equals(8)){
            return "Normal";
        }else if(param.equals(9)){
            return "High";
        }else if (param.equals(10)){
            return "Immediate";
        }
        return " ";
    }

    // ����Ϊ�̳���AbstractTableModle�ķ����������Զ���

    /**
     * �õ�����
     */
    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    /**
     * ��д�������õ��������
     */
    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    /**
     * �õ��������
     */
    @Override
    public int getRowCount() {
        return data.length;
    }

    /**
     * �õ���������Ӧ����
     */
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return data[rowIndex][columnIndex];
    }

    /**
     * �õ�ָ���е���������
     */
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return data[0][columnIndex].getClass();
    }


    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
//        if (columnIndex < 2){
//            return false;
//        }
//
//        else{
//            return true;
//        }
        return false;

    }

    /**
     * ������ݵ�ԪΪ�ɱ༭���򽫱༭���ֵ�滻ԭ����ֵ
     */
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        data[rowIndex][columnIndex] = aValue;
        /*֪ͨ���������ݵ�Ԫ�����Ѿ��ı�*/
        fireTableCellUpdated(rowIndex, columnIndex);
    }

}
