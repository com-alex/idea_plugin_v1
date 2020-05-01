package com.fromLab.test;

import com.fromLab.entity.Status;
import com.fromLab.entity.Task;
import com.fromLab.exception.BusinessException;
import com.fromLab.service.impl.TaskServiceImpl;
import com.fromLab.utils.DateUtils;
import com.fromLab.utils.GetCustomFieldNumUtil;
import com.fromLab.utils.OpenprojectURL;
import org.apache.commons.collections.CollectionUtils;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author wsh
 * @date 2020-02-18
 */
public class TaskServiceTest {

    private OpenprojectURL openprojectURL;
    private TaskServiceImpl taskService = new TaskServiceImpl();
    private String originalUrl = "";

    @Before
    public void before(){
        openprojectURL = new OpenprojectURL(
                "http://projects.plugininide.com/openproject/api/v3/work_packages/",
                "e66517369652fea76049f9c3e1094230ad45fb5b723da5b392d86248c6472123");
        this.originalUrl = openprojectURL.getOpenProjectURL();
    }

    @Test
    public void getTaskById(){

        Task task = null;
        try {
            task = taskService.getTaskById(openprojectURL,8);
        } catch (BusinessException e) {
            e.printStackTrace();
        }
        System.out.println(task);
    }



    @Test
    public void updateStatus(){
        Task task = null;
        try {
            task = taskService.getTaskById(openprojectURL, 8);
        } catch (BusinessException e) {
            e.printStackTrace();
        }
        System.out.println("before:" + task);

//        taskService.updateStatus(OPENPROJECT_URL, API_KEY, task.getTaskId(), Status.InProgress, task.getLockVersion());

        try {
            task = taskService.getTaskById(openprojectURL, 8);
        } catch (BusinessException e) {
            e.printStackTrace();
        }
        System.out.println("before:" + task);

    }

    @Test
    public void updateProgress(){
        Task task = null;
        try {
            task = taskService.getTaskById(openprojectURL, 8);
        } catch (BusinessException e) {
            e.printStackTrace();
        }
        System.out.println("before:" + task);
//        taskService.updateProgress(OPENPROJECT_URL,API_KEY,task.getTaskId(), task.getLockVersion(),15);
        try {
            task = taskService.getTaskById(openprojectURL, 8);
        } catch (BusinessException e) {
            e.printStackTrace();
        }
        System.out.println("update: " + task);
    }


    @Test
    public void update(){

        Task task = null;
        try {
            task = taskService.getTaskById(openprojectURL, 8);
        } catch (BusinessException e) {
            e.printStackTrace();
        }
        taskService.updateStatusAndProgress(openprojectURL, task.getTaskId(), task.getLockVersion(), Status.OnHold, 50);
    }

    @Test
    public void updateSpentTime(){
        Task task = null;
        try {
            task = taskService.getTaskById(openprojectURL, 8);
        } catch (BusinessException e) {
            e.printStackTrace();
        }
        String custom = null;
        try {
            custom = GetCustomFieldNumUtil.getCustomFieldNum("Time spent", openprojectURL);
        } catch (BusinessException e) {
            e.printStackTrace();
        }
        taskService.updateSpentTime(openprojectURL, task.getTaskId(), task.getLockVersion(), BigDecimal.valueOf(10), custom);
    }

    @Test
    public void testUpdateStartDate(){
        Task task = null;
        try {
            task = taskService.getTaskById(openprojectURL, 16);
        } catch (BusinessException e) {
            e.printStackTrace();
        }
        openprojectURL.setOpenProjectURL(originalUrl);
        String startDate = DateUtils.date2String(new Date());
        String s = taskService.updateStartDate(openprojectURL, task.getTaskId(), task.getLockVersion() - 1 , startDate);

    }


    @Test
    public void testUpdateEndDate(){
        Task task = null;
        try {
            task = taskService.getTaskById(openprojectURL, 15);
        } catch (BusinessException e) {
            e.printStackTrace();
        }
        String custom = null;
        try {
            custom = GetCustomFieldNumUtil.getCustomFieldNum("End date", openprojectURL);
        } catch (BusinessException e) {
            e.printStackTrace();
        }
        String endDate = DateUtils.date2String(new Date());
        System.out.println(endDate);
        taskService.updateEndDate(openprojectURL, task.getTaskId(), task.getLockVersion(), endDate, custom);
    }
    
    @Test
    public void testGetTasksByConditon(){
        List<Task> tasksByConditon = null;
        try {
            tasksByConditon = taskService.getTasksByConditions(openprojectURL,
                    null, null, null, null, null, null);
        } catch (BusinessException e) {
            e.printStackTrace();
        }
        if (CollectionUtils.isNotEmpty(tasksByConditon)){
            tasksByConditon.forEach(task -> {
                System.out.println(task);
            });
        }

    }
}
