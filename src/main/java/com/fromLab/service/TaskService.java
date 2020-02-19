package com.fromLab.service;

import com.fromLab.VO.TaskDetailVO;
import com.fromLab.VO.TaskVO;
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
     * 通过用户id查找到所有的的任务
     * @param uid
     * @return
     */
    List<Task> displayAllTask(Integer uid);

    /**
     * 通过taskId查询某个任务
     * @param taskId
     * @return
     */
    Task queryTaskByTaskId(Integer taskId);

    /**
     * 通过用户id查找所有显示的任务
     * @param uid
     * @return
     */
    List<TaskVO> queryAllShowTask(Integer uid);

    /**
     * 通过用户id与task的名字模糊查询任务
     * @param uid
     * @param taskName
     * @return
     */
    List<TaskVO> queryAllShowTaskByTaskName(Integer uid, String taskName);

    /**
     * 通过用户id与task的状态模糊查询任务
     * @param uid
     * @param status
     * @return
     */
    List<TaskVO> queryAllShowTaskByStatus(Integer uid, String status);

    /**
     * 通过查询条件查询任务
     * @param uid
     * @param status
     * @param fromDueTime
     * @param toDueTime
     * @return
     */
    List<TaskVO> queryShowTaskByCondition(Integer uid,
                                          String status,
                                          String fromDueTime,
                                          String toDueTime);

    /**
     * 通过用户id与主题查找任务
     * @param uid
     * @param porojectName
     * @return
     */
    List<Task> queryTaskByProjectName(Integer uid, String porojectName);

    /**
     * 通过用户id与任务的种类查找任务
     * @param uid
     * @param TaskType
     * @return
     */
    List<Task> queryTaskByTaskType(Integer uid, String TaskType);

    /**
     * 通过用户id与任务的优先级查找任务
     * @param uid
     * @param TaskPriority
     * @return
     */
    List<Task> queryTaskByTaskPriority(Integer uid, Integer TaskPriority);

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

    /**
     * 保存或者更新Task
     * @param object
     * @return
     */
    Boolean saveOrUpdateTask(Object object);

    /**
     * 根据taskId获取task的detail
     * @param taskId
     * @return
     */
    TaskDetailVO queryTaskDetailByTaskId(Integer taskId);



    List<Task> getTasks(String openprojectURL, String apikey, List<Filter> filters);

    Task getTaskById(String openprojectURL, String apiKey, int id);

    void updateStatus(String openprojectURL, String apiKey, int id, Status status, int lock_version);

    void updateProgress(String openprojectURL, String apiKey, int id, int lock_version, int percentage);

    void updateStautsAndProgress(String openprojectURL, String apiKey, int id, int lock_version, Status status, int percentage);
}
