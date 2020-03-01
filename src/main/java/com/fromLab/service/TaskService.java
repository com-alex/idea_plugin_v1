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
     * ͨ������������ȡ���е�task���˷������Ա����ã�����ֱ��ʹ��getTasksByConditons
     * @param openprojectURL
     * @param apikey
     * @param filters
     * @return
     */
    List<Task> getTasks(String openprojectURL, String apikey, List<Filter> filters);

    /**
     * ͨ��taskId ��ȡtask
     * @param openprojectURL
     * @param apiKey
     * @param id
     * @return
     */
    Task getTaskById(String openprojectURL, String apiKey, int id);

    /**
     * ����task��id����task��status��progress
     * @param openprojectURL
     * @param apiKey
     * @param id
     * @param lock_version
     * @param status
     * @param percentage
     */
    String updateStatusAndProgress(String openprojectURL, String apiKey, int id, int lock_version, Status status, int percentage);

    /**
     * ����task��id����task��startDate
     * @param openprojectURL
     * @param apiKey
     * @param id
     * @param lock_version
     * @param start_date
     */
    String updateStartDate(String openprojectURL, String apiKey, int id, int lock_version, String start_date);

    /**
     * ����task��id�����Զ����ֶ�endDate
     * @param openprojectURL
     * @param apiKey
     * @param id
     * @param lock_version
     * @param end_date
     * @param customField
     */
    String updateEndDate(String openprojectURL, String apiKey, int id, int lock_version, String end_date, String customField);

    /**
     * ����task��id�����Զ����ֶ�time spent
     * @param openprojectURL
     * @param apiKey
     * @param id
     * @param lock_version
     * @param time
     * @param customField
     */
    String updateSpentTime(String openprojectURL, String apiKey, int id, int lock_version, int time, String customField);

    /**
     * ���ݸ���������ѯtask
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
     * ������Ŀ���������ѯ����
     * @param taskList
     * @return
     */
    List<Task> sortTaskWithProjectName(List<Task> taskList);

    /**
     * ͨ����Ŀ���������ѯ����
     * @param taskList
     * @return
     */
    List<Task> sortTaskWithTaskType(List<Task> taskList);

    /**
     * ͨ����Ŀ�����ȼ������ѯ����
     * @param taskList
     * @return
     */
    List<Task> sortTaskWithTaskPriority(List<Task> taskList);

    /**
     * ͨ����ֹ���������ѯ����
     * @param taskList
     * @return
     */
    List<Task> sortTaskWithDueTime(List<Task> taskList);



}
