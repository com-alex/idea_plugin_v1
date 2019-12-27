package com.fromLab.DAO;

import com.fromLab.VO.TaskDetailVO;
import com.fromLab.VO.TaskVO;
import com.fromLab.entity.Task;

import java.util.List;

/**
 *
 * 使用uid来确定具体获得哪一个用户的task
 *
 */
public interface TaskDao {

    Task queryTaskByTaskId(Integer taskId);
    List<Task> queryAllTask(Integer uid);
    List<TaskVO> queryAllShowTask(Integer uid);
    List<TaskVO> queryAllShowTaskByTaskName(Integer uid, String taskName);
    List<TaskVO> queryAllShowTaskByStatus(Integer uid, String status);
    List<TaskVO> queryShowTaskByCondition(Integer uid,
                                          String status,
                                          String fromDueTime,
                                          String toDueTime);
    List<Task> queryTaskByProjectName(Integer uid, String project);
    List<Task> queryTaskByTaskType(Integer uid, String TaskType);
    List<Task> queryTaskByTaskPriority(Integer uid, Integer TaskPriority);
    Boolean saveOrUpdateTask(Object object);
    TaskDetailVO queryTaskDetailByTaskId(Integer taskId);



}
