package com.fromLab.service;


import com.fromLab.entity.Filter;
import com.fromLab.entity.Status;
import com.fromLab.entity.Task;

import java.util.List;

/**
 *
 *@Auther: JIN KE
 *@Date: 2019/12/11 17:47
 */
public interface TaskService {

    /**
     * 通过检索条件获取所有的task，此方法可以被弃用，可以直接使用getTasksByConditons
     * @param openprojectURL
     * @param apikey
     * @param filters
     * @return
     */
    List<Task> getTasks(String openprojectURL, String apikey, List<Filter> filters);

    /**
     * 通过taskId 获取task
     * @param openprojectURL
     * @param apiKey
     * @param id
     * @return
     */
    Task getTaskById(String openprojectURL, String apiKey, int id);

    /**
     * 根据task的id更新task的status和progress
     * @param openprojectURL
     * @param apiKey
     * @param id
     * @param lock_version
     * @param status
     * @param percentage
     */
    String updateStatusAndProgress(String openprojectURL, String apiKey, int id, int lock_version, Status status, int percentage);

    /**
     * 根据task的id更新task的startDate
     * @param openprojectURL
     * @param apiKey
     * @param id
     * @param lock_version
     * @param start_date
     */
    String updateStartDate(String openprojectURL, String apiKey, int id, int lock_version, String start_date);

    /**
     * 根据task的id更新自定义字段endDate
     * @param openprojectURL
     * @param apiKey
     * @param id
     * @param lock_version
     * @param end_date
     * @param customField
     */
    String updateEndDate(String openprojectURL, String apiKey, int id, int lock_version, String end_date, String customField);

    /**
     * 根据task的id更新自定义字段time spent
     * @param openprojectURL
     * @param apiKey
     * @param id
     * @param lock_version
     * @param time
     * @param customField
     */
    String updateSpentTime(String openprojectURL, String apiKey, int id, int lock_version, int time, String customField);

    /**
     * 根据各种条件查询task
     * @param openprojectURL
     * @param apikey
     * @param statusNum
     * @param priorityNum
     * @param fromDueDate
     * @param toDueDate
     * @param taskTypeNum
     * @param subject
     * @return
     */
    List<Task> getTasksByConditons(String openprojectURL, String apikey,
                                   Integer statusNum,
                                   Integer priorityNum,
                                   String fromDueDate,
                                   String toDueDate,
                                   Integer taskTypeNum,
                                   String subject);


    /**
     * 根据项目名称排序查询任务
     * @param taskList
     * @return
     */
    List<Task> sortTaskWithProjectName(List<Task> taskList);

    /**
     * 通过项目种类排序查询任务
     * @param taskList
     * @return
     */
    List<Task> sortTaskWithTaskType(List<Task> taskList);

    /**
     * 通过项目的优先级排序查询任务
     * @param taskList
     * @return
     */
    List<Task> sortTaskWithTaskPriority(List<Task> taskList);

    /**
     * 通过截止日期排序查询任务
     * @param taskList
     * @return
     */
    List<Task> sortTaskWithDueTime(List<Task> taskList);



}
