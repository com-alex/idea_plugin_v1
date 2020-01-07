package com.fromLab.service.impl;

import com.fromLab.VO.TaskDetailVO;
import com.fromLab.VO.TaskVO;
import com.fromLab.entity.Task;
import com.fromLab.service.TaskService;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.List;

public class TaskServiceImpl1 implements TaskService {
    @Override
    public List<Task> displayAllTask(Integer uid) {
        return null;
    }

    @Override
    public Task queryTaskByTaskId(Integer taskId) {
        return null;
    }

    @Override
    public List<TaskVO> queryAllShowTask(Integer uid) {
        return null;
    }

    @Override
    public List<TaskVO> queryAllShowTaskByTaskName(Integer uid, String taskName) {
        return null;
    }

    @Override
    public List<TaskVO> queryAllShowTaskByStatus(Integer uid, String status) {
        return null;
    }

    @Override
    public List<TaskVO> queryShowTaskByCondition(Integer uid, String status, String fromDueTime, String toDueTime) {
        return null;
    }

    @Override
    public List<Task> queryTaskByProjectName(Integer uid, String porojectName) {
        return null;
    }

    @Override
    public List<Task> queryTaskByTaskType(Integer uid, String TaskType) {
        return null;
    }

    @Override
    public List<Task> queryTaskByTaskPriority(Integer uid, Integer TaskPriority) {
        return null;
    }

    @Override
    public List<Task> sortTaskWithProjectName(List<Task> taskList) {
        return null;
    }

    @Override
    public List<Task> sortTaskWithTaskType(List<Task> taskList) {
        return null;
    }

    @Override
    public List<Task> sortTaskWithTaskPriority(List<Task> taskList) {
        return null;
    }

    @Override
    public List<Task> sortTaskWithDueTime(List<Task> taskList) {
        return null;
    }

    @Override
    public Boolean saveOrUpdateTask(Object object) {
        return null;
    }

    @Override
    public TaskDetailVO queryTaskDetailByTaskId(Integer taskId) {
        return null;
    }

    @Override
    public List<Task> getTasks(String sortby, String groupby, String... filiters) {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url("https://pluginide.openproject.com/api/v3/work_packages")
                .method("GET", null)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .addHeader("Authorization", "Basic YXBpa2V5OmQyODNkZjQwYjQ5Njc0YzQ4MDVhMDg4ZjZiNmYwYjEwOTI3NjYyN2RmMWZjMjQwNTdlOTg1ZWUzYzBmNmJiYzI=")
                .build();
        try {
            Response response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
