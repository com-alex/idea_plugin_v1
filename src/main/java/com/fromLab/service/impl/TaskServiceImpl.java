package com.fromLab.service.impl;

import com.fromLab.DAO.impl.TaskDaoImpl;
import com.fromLab.VO.TaskVO;
import com.fromLab.entity.Task;
import com.fromLab.service.TaskService;
import com.fromLab.utils.SortUtils;

import java.util.List;

/**
 * @Auther: JIN KE
 * @Date: 2019/12/11 20:42
 */
public class TaskServiceImpl implements TaskService {

    TaskDaoImpl taskDao = new TaskDaoImpl();

    @Override
    public List<Task> displayAllTask(Integer uid) {
        return taskDao.queryAllTask(uid);
    }

    @Override
    public List<TaskVO> queryAllShowTask(Integer uid) {
        return taskDao.queryAllShowTask(uid);
    }

    @Override
    public List<Task> queryTaskByProjectName(Integer uid, String project) {
        return taskDao.queryTaskByProjectName(uid,project);
    }

    @Override
    public List<Task> queryTaskByTaskType(Integer uid, String TaskType) {
        return taskDao.queryTaskByTaskType(uid, TaskType);
    }

    @Override
    public List<Task> queryTaskByTaskPriority(Integer uid, Integer TaskPriority) {
        return taskDao.queryTaskByTaskPriority(uid, TaskPriority);
    }

    @Override
    public List<Task> sortTaskWithProjectName(List<Task> taskList) {
        String [] sortNameArr = {"projectName","taskType","taskPriority","dueTime","taskId"};
        boolean [] isAscArr = {true,true,false,true,true};
        SortUtils.sort(taskList,sortNameArr,isAscArr);
        return  taskList;

    }

    @Override
    public List<Task> sortTaskWithTaskType(List<Task> taskList) {
        String [] sortNameArr = {"TaskType","Project","TaskPriority","DueTime","TaskId"};
        boolean [] isAscArr = {true,true,false,true,true};
        SortUtils.sort(taskList,sortNameArr,isAscArr);
        return  taskList;
    }

    @Override
    public List<Task> sortTaskWithTaskPriority(List<Task> taskList) {
        String [] sortNameArr = {"TaskPriority","TaskType","Project","DueTime","TaskId"};
        boolean [] isAscArr = {false,true,true,true,true};
        SortUtils.sort(taskList,sortNameArr,isAscArr);
        return  taskList;
    }

    @Override
    public List<Task> sortTaskWithDueTime(List<Task> taskList) {
        String [] sortNameArr = {"DueTime","Project","TaskType","TaskPriority","TaskId"};
        boolean [] isAscArr = {true,true,true,false,true};
        SortUtils.sort(taskList,sortNameArr,isAscArr);
        return  taskList;
    }

    @Override
    public Boolean saveOrUpdateTask(Object object) {
        return taskDao.saveOrUpdateTask(object);
    }
}
