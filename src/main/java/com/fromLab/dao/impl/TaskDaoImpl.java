package com.fromLab.DAO.impl;

import com.fromLab.DAO.TaskDao;
import com.fromLab.VO.TaskDetailVO;
import com.fromLab.VO.TaskVO;
import com.fromLab.entity.Task;
import com.fromLab.utils.SqlBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: JIN KE
 * @Date: 2019/12/11 10:19
 */
public class TaskDaoImpl implements TaskDao {

    @Override
    public Task queryTaskByTaskId(Integer taskId) {
        Task task = new Task();
        SqlBuilder sqlBuilder = SqlBuilder.getInstance();
        sqlBuilder.select("*")
                .from("task_info")
                .where()
                .addEqualTo("task_id", taskId);
        try {
            task = ((List<Task>)sqlBuilder.createQuery(sqlBuilder.getSql(), sqlBuilder.getParams(), Task.class)).get(0);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            SqlBuilder.closeAll();
        }
        return task;
    }

    @Override
    public List<Task> queryAllTask(Integer uid) {
        List<Task>  tasks=new ArrayList<>();
        SqlBuilder sqlBuilder = SqlBuilder.getInstance();
        sqlBuilder.select("*")
                .from("task_info")
                .where()
                .addEqualTo("uid",uid);
        try {
            tasks = (List<Task>) sqlBuilder.createQuery(sqlBuilder.getSql(), sqlBuilder.getParams(), Task.class);

        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            SqlBuilder.closeAll();
        }
        return tasks;
    }

    @Override
    public List<TaskVO> queryAllShowTask(Integer uid){
        List<TaskVO> taskList = new ArrayList<>();
        SqlBuilder sqlBuilder = SqlBuilder.getInstance();
        sqlBuilder.select("task_id, uid, task_name, project_name, task_priority, task_type, start_time, end_time, due_time, status, progress, time_spent")
                .from("task_info")
                .where()
                .addEqualTo("uid", uid);
        try {
            taskList = (List<TaskVO>) sqlBuilder.createQuery(sqlBuilder.getSql(), sqlBuilder.getParams(), TaskVO.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            SqlBuilder.closeAll();
        }
        return taskList;
    }

    @Override
    public List<TaskVO> queryAllShowTaskByTaskName(Integer uid, String taskName) {
        List<TaskVO> taskList = new ArrayList<>();
        SqlBuilder sqlBuilder = SqlBuilder.getInstance();
        sqlBuilder.select("task_id, uid, task_name, project_name, task_priority, task_type, start_time, end_time, due_time, status, progress, time_spent")
                .from("task_info")
                .where()
                .addEqualTo("uid", uid)
                .addLike("task_name", '%' + taskName + '%');
        try {
            taskList = (List<TaskVO>) sqlBuilder.createQuery(sqlBuilder.getSql(), sqlBuilder.getParams(), TaskVO.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            SqlBuilder.closeAll();
        }
        return taskList;
    }

    @Override
    public List<TaskVO> queryAllShowTaskByStatus(Integer uid, String status) {
        List<TaskVO> taskList = new ArrayList<>();
        SqlBuilder sqlBuilder = SqlBuilder.getInstance();
        sqlBuilder.select("task_id, uid, task_name, project_name, task_priority, task_type, start_time, end_time, due_time, status, progress, time_spent")
                .from("task_info")
                .where()
                .addEqualTo("uid", uid)
                .addLike("status", '%' + status + '%');
        try {
            taskList = (List<TaskVO>) sqlBuilder.createQuery(sqlBuilder.getSql(), sqlBuilder.getParams(), TaskVO.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            SqlBuilder.closeAll();
        }
        return taskList;
    }

    @Override
    public List<TaskVO> queryShowTaskByCondition(Integer uid, String status, String fromDueTime, String toDueTime) {
        List<TaskVO> taskList = new ArrayList<>();
        SqlBuilder sqlBuilder = SqlBuilder.getInstance();
        sqlBuilder.select("task_id, uid, task_name, project_name, task_priority, task_type, start_time, end_time, due_time, status, progress, time_spent")
                .from("task_info")
                .where()
                .addEqualTo("uid", uid)
                .addLike("status", '%' + status + '%')
                .addGreaterOrEqualTo("due_time", fromDueTime)
                .addLessOrEqualTo("due_time", toDueTime);
        try {
            taskList = (List<TaskVO>) sqlBuilder.createQuery(sqlBuilder.getSql(), sqlBuilder.getParams(), TaskVO.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            SqlBuilder.closeAll();
        }
        return taskList;
    }


    @Override
    public  List<Task> queryTaskByProjectName(Integer uid, String projectName) {
        List<Task> tasks =new ArrayList<>();
        SqlBuilder sqlBuilder = SqlBuilder.getInstance();
        sqlBuilder.select("*")
                .from("task_info")
                .where()
                .addEqualTo("uid",uid)
                .addEqualTo("project_name",projectName);
        try {
            tasks = (List<Task>) sqlBuilder.createQuery(sqlBuilder.getSql(), sqlBuilder.getParams(), Task.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            SqlBuilder.closeAll();
        }
        return tasks;
    }

    @Override
    public List<Task> queryTaskByTaskType(Integer uid, String TaskType) {
        List<Task> tasks =new ArrayList<>();
        SqlBuilder sqlBuilder = SqlBuilder.getInstance();
        sqlBuilder.select("*")
                .from("task_info")
                .where()
                .addEqualTo("uid",uid)
                .addEqualTo("task_type",TaskType);
        try {
            tasks = (List<Task>) sqlBuilder.createQuery(sqlBuilder.getSql(), sqlBuilder.getParams(), Task.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            SqlBuilder.closeAll();
        }
        return tasks;
    }

    @Override
    public List<Task> queryTaskByTaskPriority(Integer uid, Integer TaskPriority) {
        List<Task> tasks =new ArrayList<>();
        SqlBuilder sqlBuilder = SqlBuilder.getInstance();
        sqlBuilder.select("*")
                .from("task_info")
                .where()
                .addEqualTo("uid",uid)
                .addEqualTo("task_priority",TaskPriority);
        try {
            tasks = (List<Task>) sqlBuilder.createQuery(sqlBuilder.getSql(), sqlBuilder.getParams(), Task.class);

        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            SqlBuilder.closeAll();
        }
        return tasks;
    }

    @Override
    public Boolean saveOrUpdateTask(Object object) {
        try {
            return SqlBuilder.saveOrUpdate(object);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            SqlBuilder.closeAll();
        }
        return false;
    }

    @Override
    public TaskDetailVO queryTaskDetailByTaskId(Integer taskId) {
        TaskDetailVO taskDetailVO = new TaskDetailVO();
        SqlBuilder sqlBuilder = SqlBuilder.getInstance();
        sqlBuilder.select("task_name, project_name, due_time, task_detail")
                .from("task_info")
                .where()
                .addEqualTo("task_id", taskId);
        try {
             taskDetailVO = ((List<TaskDetailVO>) sqlBuilder.createQuery(sqlBuilder.getSql(), sqlBuilder.getParams(), TaskDetailVO.class)).get(0);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            SqlBuilder.closeAll();
        }
        return taskDetailVO;
    }
}
