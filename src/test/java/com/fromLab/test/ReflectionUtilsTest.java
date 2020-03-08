package com.fromLab.test;

import com.fromLab.VO.TaskDetailVO;
import com.fromLab.entity.Task;
import com.fromLab.service.TaskService;
import com.fromLab.service.impl.TaskServiceImpl;
import com.fromLab.utils.ReflectionUtils;
import org.junit.Test;

/**
 * @author wsh
 * @date 2020-02-20
 */
public class ReflectionUtilsTest {

    private final static String OPENPROJECT_URL="http://projects.plugininide.com/openproject";
    private final static String API_KEY="e66517369652fea76049f9c3e1094230ad45fb5b723da5b392d86248c6472123";

    private TaskService taskService = new TaskServiceImpl();


}
