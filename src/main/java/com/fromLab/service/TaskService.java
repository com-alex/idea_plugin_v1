package com.fromLab.service;


import com.fromLab.entity.Filter;
import com.fromLab.entity.Status;
import com.fromLab.entity.Task;
import com.fromLab.exception.BusinessException;
import com.fromLab.utils.OpenprojectURL;

import java.math.BigDecimal;
import java.util.List;

/**
 *
 *@Auther: JIN KE
 *@Date: 2019/12/11 17:47
 */
public interface TaskService {


    /**
     * Get task by taskId
     * @param openprojectURL
     * @param id
     * @return
     */
    Task getTaskById(OpenprojectURL openprojectURL, int id) throws BusinessException;

    /**
     * Update task status and progress according to task id
     * @param openprojectURL
     * @param id
     * @param lock_version
     * @param status
     * @param percentage
     */
    String updateStatusAndProgress(OpenprojectURL openprojectURL, int id, int lock_version, Status status, int percentage);

    /**
     * Update task's startDate according to task id
     * @param openprojectURL
     * @param id
     * @param lock_version
     * @param start_date
     */
    String updateStartDate(OpenprojectURL openprojectURL, int id, int lock_version, String start_date);

    /**
     * Update custom field endDate according to task id
     * @param openprojectURL
     * @param id
     * @param lock_version
     * @param end_date
     * @param customField
     */
    String updateEndDate(OpenprojectURL openprojectURL, int id, int lock_version, String end_date, String customField);

    /**
     * Update custom field endDate according to task id
     * @param openprojectURL
     * @param id
     * @param lock_version
     * @param time
     * @param customField
     */
    String updateSpentTime(OpenprojectURL openprojectURL, int id, int lock_version, BigDecimal time, String customField);

    /**
     * Query task according to various conditions
     * @param openprojectURL
     * @param statusNum
     * @param priorityNum
     * @param fromDueDate
     * @param toDueDate
     * @param taskTypeNum
     * @param subject
     * @return
     */
    List<Task> getTasksByConditions(OpenprojectURL openprojectURL,
                                    Integer statusNum,
                                    Integer priorityNum,
                                    String fromDueDate,
                                    String toDueDate,
                                    Integer taskTypeNum,
                                    String subject) throws BusinessException;





}
