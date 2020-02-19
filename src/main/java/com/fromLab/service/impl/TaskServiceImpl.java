package com.fromLab.service.impl;

import com.fromLab.DAO.impl.TaskDaoImpl;
import com.fromLab.OpenprojectURL;
import com.fromLab.VO.TaskDetailVO;
import com.fromLab.VO.TaskVO;
import com.fromLab.entity.Filter;
import com.fromLab.entity.SortBy;
import com.fromLab.entity.Status;
import com.fromLab.entity.Task;
import com.fromLab.service.TaskService;
import com.fromLab.utils.SortUtils;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.fromLab.utils.JsonToObjectUtil.JsonToTask;
import static com.fromLab.utils.JsonToObjectUtil.JsonToTaskList;

/**
 * @Auther: JIN KE
 * @Date: 2019/12/11 20:42
 */
public class TaskServiceImpl implements TaskService {

    static String openprojectURL="http://projects.plugininide.com/openproject";
    static String apiKey="e66517369652fea76049f9c3e1094230ad45fb5b723da5b392d86248c6472123";
    TaskDaoImpl taskDao = new TaskDaoImpl();

    @Override
    public List<Task> displayAllTask(Integer uid) {
        return taskDao.queryAllTask(uid);
    }

    @Override
    public Task queryTaskByTaskId(Integer taskId) {
        return taskDao.queryTaskByTaskId(taskId);
    }


    @Override
    public List<TaskVO> queryAllShowTask(Integer uid) {
        return taskDao.queryAllShowTask(uid);
    }

    @Override
    public List<TaskVO> queryAllShowTaskByTaskName(Integer uid, String taskName) {
        return taskDao.queryAllShowTaskByTaskName(uid, taskName);
    }

    @Override
    public List<TaskVO> queryAllShowTaskByStatus(Integer uid, String status) {
        return taskDao.queryAllShowTaskByStatus(uid, status);
    }

    @Override
    public List<TaskVO> queryShowTaskByCondition(Integer uid,
                                                 String status,
                                                 String fromDueTime,
                                                 String toDueTime) {
        return taskDao.queryShowTaskByCondition(uid, status, fromDueTime, toDueTime);
    }

    @Override
    public List<Task> queryTaskByProjectName(Integer uid, String project) {
        return taskDao.queryTaskByProjectName(uid,project);
    }

    @Override
    public List<Task> queryTaskByTaskType(Integer uid, String TaskType) {
        return taskDao.queryTaskByTaskType(uid, TaskType);
    }

    @Override
    public List<Task> queryTaskByTaskPriority(Integer uid, Integer TaskPriority) {
        return taskDao.queryTaskByTaskPriority(uid, TaskPriority);
    }

    @Override
    public List<Task> sortTaskWithProjectName(List<Task> taskList) {
        String [] sortNameArr = {"projectName","taskType","taskPriority","dueTime","taskId"};
        boolean [] isAscArr = {true,true,false,true,true};
        SortUtils.sort(taskList,sortNameArr,isAscArr);
        return  taskList;

    }

    @Override
    public List<Task> sortTaskWithTaskType(List<Task> taskList) {
        String [] sortNameArr = {"TaskType","Project","TaskPriority","DueTime","TaskId"};
        boolean [] isAscArr = {true,true,false,true,true};
        SortUtils.sort(taskList,sortNameArr,isAscArr);
        return  taskList;
    }

    @Override
    public List<Task> sortTaskWithTaskPriority(List<Task> taskList) {
        String [] sortNameArr = {"TaskPriority","TaskType","Project","DueTime","TaskId"};
        boolean [] isAscArr = {false,true,true,true,true};
        SortUtils.sort(taskList,sortNameArr,isAscArr);
        return  taskList;
    }

    @Override
    public List<Task> sortTaskWithDueTime(List<Task> taskList) {
        String [] sortNameArr = {"dueTime","taskPriority","taskType","projectName","taskId"};
        boolean [] isAscArr = {true,true,true,false,true};
        SortUtils.sort(taskList,sortNameArr,isAscArr);
        return  taskList;
    }

    @Override
    public Boolean saveOrUpdateTask(Object object) {
        return taskDao.saveOrUpdateTask(object);
    }

    @Override
    public Task getTaskById(String openprojectURL, String apiKey, int id) {
        OpenprojectURL o=new OpenprojectURL(openprojectURL,apiKey,OpenprojectURL.WORKPAGES_URL);
//        System.out.println(openprojectURL+OpenprojectURL.WORKPAGES_URL+id);
        String json= o.getJson(openprojectURL+OpenprojectURL.WORKPAGES_URL+id);
        JSONObject jsonObject = JSONObject.fromObject(json);
        Task task =JsonToTask(jsonObject);
        return task;
    }

    @Override
    public TaskDetailVO queryTaskDetailByTaskId(Integer taskId) {
        return taskDao.queryTaskDetailByTaskId(taskId);
    }


    @Override
    /**
     * lock_version 类似于版本号，更新时需要上传当前的版本号，taskVO对象中存储了这个属性
     */
    public void updateStatus(String openprojectURL, String apiKey, int id, Status status, int lock_version) {
        String json=status.toJson(lock_version);
        System.out.println(json);
        OpenprojectURL o=new OpenprojectURL(openprojectURL,apiKey,OpenprojectURL.WORKPAGES_URL);
        String result=o.patch(openprojectURL+OpenprojectURL.WORKPAGES_URL+id,json);
    }

    @Override
    public void updateProgress(String openprojectURL, String apiKey, int id, int lock_version, int percentage) {
        JsonObject jsonObject=new JsonObject();
        jsonObject.addProperty("lockVersion",lock_version);
        jsonObject.addProperty("percentageDone",percentage);
        OpenprojectURL o=new OpenprojectURL(openprojectURL,apiKey,OpenprojectURL.WORKPAGES_URL);
        String result=o.patch(openprojectURL+OpenprojectURL.WORKPAGES_URL+id,jsonObject.toString());
    }

    @Override
    public void updateStautsAndProgress(String openprojectURL, String apiKey, int id, int lock_version, Status status, int percentage) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("lockVersion", lock_version);
        JsonObject links=new JsonObject();
        links.add("status",status.statusJsonObject());
        jsonObject.add("_links",links);
        jsonObject.addProperty("percentageDone",percentage);
        /**
         * {
         *     "lockVersion":10,
         *     "_links":{
         *         "status":{
         *             "href":"/api/v3/statuses/1"
         *         }
         *     },
         *     "percentageDone":15
         * }
         */
        OpenprojectURL o=new OpenprojectURL(openprojectURL,apiKey,OpenprojectURL.WORKPAGES_URL);
        String result=o.patch(openprojectURL+OpenprojectURL.WORKPAGES_URL+id,jsonObject.toString());

    }

    @Override
    public List<Task> getTasks(String openprojectURL, String apikey, List<Filter> filters) {
        JsonArray jsonArray=new JsonArray();
        for(int i=0;i<filters.size();i++){
            jsonArray.add(filters.get(i).toJsonObj());
        }
        String filterJson=jsonArray.toString();
        OpenprojectURL o=new OpenprojectURL(openprojectURL,apikey,OpenprojectURL.WORKPAGES_URL);
        System.out.println(openprojectURL+OpenprojectURL.WORKPAGES_URL+
                "?filters="+filterJson);
        String json= o.getJson(openprojectURL+OpenprojectURL.WORKPAGES_URL+
                "?filters="+filterJson
                );
//        System.out.println(json);
        JSONObject jsonObject = JSONObject.fromObject(json);
        List<Task> taskList = JsonToTaskList(jsonObject);

        return taskList;
    }



}
