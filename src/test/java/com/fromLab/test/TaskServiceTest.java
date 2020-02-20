package com.fromLab.test;

import com.fromLab.VO.TaskVO;
import com.fromLab.entity.Filter;
import com.fromLab.entity.Status;
import com.fromLab.entity.Task;
import com.fromLab.service.impl.TaskServiceImpl;
import com.fromLab.utils.DateUtils;
import com.fromLab.utils.GetCustomFieldNumUtil;
import com.fromLab.utils.ReflectionUtils;
import gherkin.lexer.Da;
import org.apache.commons.collections.CollectionUtils;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author wsh
 * @date 2020-02-18
 */
public class TaskServiceTest {

    private final static String OPENPROJECT_URL="http://projects.plugininide.com/openproject";
    private final static String API_KEY="e66517369652fea76049f9c3e1094230ad45fb5b723da5b392d86248c6472123";

    private TaskServiceImpl taskService = new TaskServiceImpl();


    @Test
    public void getTaskById(){
        Task task = taskService.getTaskById(OPENPROJECT_URL,API_KEY,8);
        System.out.println(task);
    }


    @Test
    public void getTasks(){
        Filter filter=new Filter("type_id","1");
        ArrayList<Filter> filters=new ArrayList<>();
        filters.add(filter);
        List<Task> taskList = taskService.getTasks(OPENPROJECT_URL, API_KEY, filters);
        for (int i = 0; i < taskList.size(); i++) {
            System.out.println(taskList.get(i));
        }
    }

    @Test
    public void updateStatus(){
        Task task = taskService.getTaskById(OPENPROJECT_URL,API_KEY, 8);
        System.out.println("before:" + task);

//        taskService.updateStatus(OPENPROJECT_URL, API_KEY, task.getTaskId(), Status.InProgress, task.getLockVersion());

        task = taskService.getTaskById(OPENPROJECT_URL,API_KEY, 8);
        System.out.println("before:" + task);

    }

    @Test
    public void updateProgress(){
        Task task = taskService.getTaskById(OPENPROJECT_URL,API_KEY, 8);
        System.out.println("before:" + task);
//        taskService.updateProgress(OPENPROJECT_URL,API_KEY,task.getTaskId(), task.getLockVersion(),15);
        task = taskService.getTaskById(OPENPROJECT_URL, API_KEY, 8);
        System.out.println("update: " + task);
    }


    @Test
    public void setDatasource(){
        List<TaskVO> taskVOS = new ArrayList<>();
        Filter filter=new Filter("type_id","1");
        ArrayList<Filter> filters=new ArrayList<>();
        filters.add(filter);
        List<Task> taskList = taskService.getTasks(OPENPROJECT_URL, API_KEY, filters);

        taskList.forEach(task -> {
            TaskVO taskVO = new TaskVO();
            taskVO = (TaskVO) ReflectionUtils.copyProperties(task, taskVO);
            taskVOS.add(taskVO);
        });

        for (int i = 0; i < taskList.size(); i++) {
            System.out.println(taskList.get(i));
        }
    }

    @Test
    public void update(){

        Task task = taskService.getTaskById(OPENPROJECT_URL,API_KEY, 8);
        taskService.updateStautsAndProgress(OPENPROJECT_URL, API_KEY, task.getTaskId(), task.getLockVersion(), Status.OnHold, 50);


    }





    @Test
    public void updateSpentTime(){
        Task task = taskService.getTaskById(OPENPROJECT_URL, API_KEY, 8);
        String custom = GetCustomFieldNumUtil.getCustomfiledNum("Time spent", OPENPROJECT_URL, API_KEY);
        taskService.updateSpentTime(OPENPROJECT_URL, API_KEY, task.getTaskId(), task.getLockVersion(), 10, custom);
    }

    @Test
    public void testUpdateStartDate(){
        Task task = taskService.getTaskById(OPENPROJECT_URL, API_KEY, 8);
        String startDate = DateUtils.date2String(new Date());
        taskService.updateStartDate(OPENPROJECT_URL, API_KEY, task.getTaskId(), task.getLockVersion(), startDate);
    }


    @Test
    public void testUpdateEndDate(){
        Task task = taskService.getTaskById(OPENPROJECT_URL, API_KEY, 8);
        String custom = GetCustomFieldNumUtil.getCustomfiledNum("End date", OPENPROJECT_URL, API_KEY);
        String endDate = DateUtils.date2String(new Date());
        System.out.println(endDate);
        taskService.updateEndDate(OPENPROJECT_URL, API_KEY, task.getTaskId(), task.getLockVersion(), endDate, custom);
    }
    
    @Test
    public void testGetTasksByConditon(){
        List<Task> tasksByConditon = taskService.getTasksByConditons(OPENPROJECT_URL, API_KEY,
                7, 7, "2020-02-12", "2020-02-29", 2, "1");
        if (CollectionUtils.isNotEmpty(tasksByConditon)){
            tasksByConditon.forEach(task -> {
                System.out.println(task);
            });
        }

    }
}
