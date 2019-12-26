package com.fromLab.service;

import com.fromLab.VO.TaskDetailVO;
import com.fromLab.VO.TaskVO;
import com.fromLab.entity.Task;

import java.util.List;

/**
 *
 *@Auther: JIN KE
 *@Date: 2019/12/11 17:47
 *需求：
 * 1.Select a project? Or filter by project --
 * this might not be needed as the pending tasks should be listed across the projects.
 * 这点我觉得和第二点中根据project过滤一样的意思，因为我们针对的重点是task而不是project
 * 2.List assigned tasks in a panel.
 * Task list should allow ordering and filtering based on project, task type, task priority etc.
 * 通过下面的这些方法实现，我又增加了根据dueTime排序
 * 我现在设置的默认排序顺序是:"Project","TaskType","TaskPriority","DueTime","TaskId"
 * 3.View task details
 * 这点我觉得都不需要写专门的方法，直接在界面上搞一个按钮（或者鼠标悬停在任务上？），然后显示出该task对象的taskdetail属性就行
 * 4.Select a task to working on
 * 这点我觉得也是需要和界面结合，点击开始按钮后设置当前时间为startTime，点击完成按钮时设置当前时间为endTime
 *
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
}
