package com.fromLab.GUI.component;

import com.fromLab.VO.TaskVO;
import com.fromLab.entity.Task;
import com.fromLab.utils.ReflectionUtils;

import javax.swing.table.AbstractTableModel;
import java.lang.reflect.Field;
import java.util.List;

/**
 * @author wsh
 * @date 2019-12-17
 * 任务主界面的任务表格列表
 */
public class TaskTableModel extends AbstractTableModel {
    /*
     * 这里和刚才一样，定义列名和每个数据的值
     */
    String[] columnNames =
            {"taskId", "task_name", "project_name", "task_priority", "task_type", "start_time", "end_time", "due_time", "status", "progress", "time_spent"};
    Object[][] data;

    /**
     * 构造方法，初始化二维数组data对应的数据
     */
    public TaskTableModel(List<TaskVO> list)
    {
        this.data = new Object[list.size()][this.columnNames.length];
        for(int i = 0; i < list.size(); i++){
            TaskVO task = list.get(i);
            List<Object> params = ReflectionUtils.getObjectParams(task);
            for(int j = 0; j < params.size(); j++){
                if(params.get(j) != null){
                    data[i][j] = params.get(j);
                }
                else {
                    data[i][j] = " ";
                }
            }

        }
    }

    // 以下为继承自AbstractTableModle的方法，可以自定义
    /**
     * 得到列名
     */
    @Override
    public String getColumnName(int column)
    {
        return columnNames[column];
    }

    /**
     * 重写方法，得到表格列数
     */
    @Override
    public int getColumnCount()
    {
        return columnNames.length;
    }

    /**
     * 得到表格行数
     */
    @Override
    public int getRowCount()
    {
        return data.length;
    }

    /**
     * 得到数据所对应对象
     */
    @Override
    public Object getValueAt(int rowIndex, int columnIndex)
    {
        return data[rowIndex][columnIndex];
    }

    /**
     * 得到指定列的数据类型
     */
    @Override
    public Class<?> getColumnClass(int columnIndex)
    {
        return data[0][columnIndex].getClass();
    }


    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex)
    {
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
     * 如果数据单元为可编辑，则将编辑后的值替换原来的值
     */
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex)
    {
        data[rowIndex][columnIndex] = aValue;
        /*通知监听器数据单元数据已经改变*/
        fireTableCellUpdated(rowIndex, columnIndex);
    }

}
