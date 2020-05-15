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
     * 通过检索条件获取所有的task，此方法可以被弃用，可以直接使用getTasksByConditons
     * @param openprojectURL
     * @param apikey
     * @param filters
     * @return
     */
//    List<Task> getTasks(String openprojectURL, String apikey, List<Filter> filters);

    /**
     * 通过taskId 获取task
     * @param openprojectURL
     * @param id
     * @return
     */
    Task getTaskById(OpenprojectURL openprojectURL, int id) throws BusinessException;

    /**
     * 根据task的id更新task的status和progress
     * @param openprojectURL
     * @param id
     * @param lock_version
     * @param status
     * @param percentage
     */
    String updateStatusAndProgress(OpenprojectURL openprojectURL, int id, int lock_version, Status status, int percentage);

    /**
     * 根据task的id更新task的startDate
     * @param openprojectURL
     * @param id
     * @param lock_version
     * @param start_date
     */
    String updateStartDate(OpenprojectURL openprojectURL, int id, int lock_version, String start_date);

    /**
     * 根据task的id更新自定义字段endDate
     * @param openprojectURL
     * @param id
     * @param lock_version
     * @param end_date
     * @param customField
     */
    String updateEndDate(OpenprojectURL openprojectURL, int id, int lock_version, String end_date, String customField);

    /**
     * 根据task的id更新自定义字段time spent
     * @param openprojectURL
     * @param id
     * @param lock_version
     * @param time
     * @param customField
     */
    String updateSpentTime(OpenprojectURL openprojectURL, int id, int lock_version, BigDecimal time, String customField);

    /**
     * 根据各种条件查询task
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
