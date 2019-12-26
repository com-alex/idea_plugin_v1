package com.fromLab.test;

import com.fromLab.VO.TaskDetailVO;
import com.fromLab.VO.TaskVO;
import com.fromLab.entity.Task;
import com.fromLab.service.TaskService;
import com.fromLab.service.impl.TaskServiceImpl;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * @author wsh
 * @date 2019-12-25
 */
public class TaskTest {

    private TaskService taskService = new TaskServiceImpl();

    @Test
    public void testTaskVO(){
        List<TaskVO> dataSource = taskService.queryAllShowTask(1);
        System.out.println(dataSource);
    }

    @Test
    public void testQueryTaskDetailByTaskId(){
        TaskDetailVO taskDetailVO = taskService.queryTaskDetailByTaskId(4);
        System.out.println(taskDetailVO);
    }


    @Test
    public void testQueryTaskByTaskId(){
        Task task = taskService.queryTaskByTaskId(4);
        System.out.println(task);
    }

}
