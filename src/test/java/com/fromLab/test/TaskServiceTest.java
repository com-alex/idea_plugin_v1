package com.fromLab.test;

import com.fromLab.entity.Status;
import com.fromLab.entity.Task;
import com.fromLab.service.impl.TaskServiceImpl;
import com.fromLab.utils.DateUtils;
import com.fromLab.utils.GetCustomFieldNumUtil;
import com.fromLab.utils.OpenprojectURL;
import org.apache.commons.collections.CollectionUtils;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.List;

/**
 * @author wsh
 * @date 2020-02-18
 */
public class TaskServiceTest {

    private OpenprojectURL openprojectURL;
    private TaskServiceImpl taskService = new TaskServiceImpl();

    @Before
    public void before(){
        openprojectURL = new OpenprojectURL(
                "http://projects.plugininide.com/openproject/api/v3/work_packages/",
                "e66517369652fea76049f9c3e1094230ad45fb5b723da5b392d86248c6472123");
    }

    @Test
    public void getTaskById(){

        Task task = taskService.getTaskById(openprojectURL,8);
        System.out.println(task);
    }



    @Test
    public void updateStatus(){
        Task task = taskService.getTaskById(openprojectURL, 8);
        System.out.println("before:" + task);

//        taskService.updateStatus(OPENPROJECT_URL, API_KEY, task.getTaskId(), Status.InProgress, task.getLockVersion());

        task = taskService.getTaskById(openprojectURL, 8);
        System.out.println("before:" + task);

    }

    @Test
    public void updateProgress(){
        Task task = taskService.getTaskById(openprojectURL, 8);
        System.out.println("before:" + task);
//        taskService.updateProgress(OPENPROJECT_URL,API_KEY,task.getTaskId(), task.getLockVersion(),15);
        task = taskService.getTaskById(openprojectURL, 8);
        System.out.println("update: " + task);
    }


    @Test
    public void update(){

        Task task = taskService.getTaskById(openprojectURL, 8);
        taskService.updateStatusAndProgress(openprojectURL, task.getTaskId(), task.getLockVersion(), Status.OnHold, 50);
    }

    @Test
    public void updateSpentTime(){
        Task task = taskService.getTaskById(openprojectURL, 8);
        String custom = GetCustomFieldNumUtil.getCustomFieldNum("Time spent", openprojectURL);
        taskService.updateSpentTime(openprojectURL, task.getTaskId(), task.getLockVersion(), 10, custom);
    }

    @Test
    public void testUpdateStartDate(){
        Task task = taskService.getTaskById(openprojectURL, 15);
        String startDate = DateUtils.date2String(new Date());
        taskService.updateStartDate(openprojectURL, task.getTaskId(), task.getLockVersion(), startDate);
    }


    @Test
    public void testUpdateEndDate(){
        Task task = taskService.getTaskById(openprojectURL, 15);
        String custom = GetCustomFieldNumUtil.getCustomFieldNum("End date", openprojectURL);
        String endDate = DateUtils.date2String(new Date());
        System.out.println(endDate);
        taskService.updateEndDate(openprojectURL, task.getTaskId(), task.getLockVersion(), endDate, custom);
    }
    
    @Test
    public void testGetTasksByConditon(){
        List<Task> tasksByConditon = taskService.getTasksByConditions(openprojectURL,
                7, 7, "2020-02-12", "2020-02-29", 2, "1");
        if (CollectionUtils.isNotEmpty(tasksByConditon)){
            tasksByConditon.forEach(task -> {
                System.out.println(task);
            });
        }

    }
}
