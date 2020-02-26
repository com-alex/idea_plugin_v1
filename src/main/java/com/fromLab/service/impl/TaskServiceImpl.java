package com.fromLab.service.impl;


import com.fromLab.utils.OpenprojectURL;
import com.fromLab.entity.*;
import com.fromLab.service.TaskService;
import com.fromLab.utils.GetCustomFieldNumUtil;
import com.fromLab.utils.SortUtils;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.sf.json.JSONObject;

import java.util.List;

import static com.fromLab.utils.JsonToObjectUtil.JsonToTask;
import static com.fromLab.utils.JsonToObjectUtil.JsonToTaskList;

/**
 * @Auther: JIN KE
 * @Date: 2019/12/11 20:42
 */
public class TaskServiceImpl implements TaskService {

    private static final String OPENPROJECT_URL="http://projects.plugininide.com/openproject";
    private static final String API_KEY ="e66517369652fea76049f9c3e1094230ad45fb5b723da5b392d86248c6472123";
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
    public List<Task> getTasks(String openprojectURL, String apikey, List<Filter> filters) {
        JsonArray jsonArray=new JsonArray();
        for(int i=0;i<filters.size();i++){
            jsonArray.add(filters.get(i).toJsonObj());
        }
        String filterJson=jsonArray.toString();
        OpenprojectURL o=new OpenprojectURL(openprojectURL,apikey,OpenprojectURL.WORKPAGES_URL);

        String json= o.getJson(openprojectURL+OpenprojectURL.WORKPAGES_URL+
                "?filters="+filterJson
        );
//        System.out.println(json);
        JSONObject jsonObject = JSONObject.fromObject(json);
        List<Task> taskList = JsonToTaskList(jsonObject);

        return taskList;
    }

    @Override
    public String updateStartDate(String openprojectURL, String apiKey, int id, int lock_version, String start_date) {
        JsonObject jsonObject=new JsonObject();
        jsonObject.addProperty("lockVersion",lock_version);
        jsonObject.addProperty("startDate",start_date);
        OpenprojectURL o=new OpenprojectURL(openprojectURL,apiKey,OpenprojectURL.WORKPAGES_URL);
        String result=o.patch(openprojectURL+OpenprojectURL.WORKPAGES_URL+id,jsonObject.toString());
        //System.out.println(result);
        TaskServiceImpl taskService=new TaskServiceImpl();
        System.out.println(taskService.ReturnUpdateStatus(result));
        return taskService.ReturnUpdateStatus(result);
    }

    @Override
    public String updateEndDate(String openprojectURL, String apiKey, int id, int lock_version, String end_date, String customField) {
        JsonObject jsonObject=new JsonObject();
        jsonObject.addProperty("lockVersion",lock_version);
        jsonObject.addProperty(customField,end_date);
        OpenprojectURL o=new OpenprojectURL(openprojectURL,apiKey,OpenprojectURL.WORKPAGES_URL);
        String result=o.patch(openprojectURL+OpenprojectURL.WORKPAGES_URL+id,jsonObject.toString());
        //System.out.println(result);
        TaskServiceImpl taskService=new TaskServiceImpl();
        System.out.println(taskService.ReturnUpdateStatus(result));
        return taskService.ReturnUpdateStatus(result);
    }

    @Override
    public String updateSpentTime(String openprojectURL, String apiKey, int id, int lock_version, int time, String customField) {
        JsonObject jsonObject=new JsonObject();
        jsonObject.addProperty("lockVersion",lock_version);
        jsonObject.addProperty(customField,time);
        OpenprojectURL o=new OpenprojectURL(openprojectURL,apiKey,OpenprojectURL.WORKPAGES_URL);
        String result=o.patch(openprojectURL+OpenprojectURL.WORKPAGES_URL+id,jsonObject.toString());
        //System.out.println(result);
        TaskServiceImpl taskService=new TaskServiceImpl();
        System.out.println(taskService.ReturnUpdateStatus(result));
        return taskService.ReturnUpdateStatus(result);
    }

    @Override
    public String updateStatusAndProgress(String openprojectURL, String apiKey, int id, int lock_version, Status status, int percentage) {
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
        //System.out.println(result);
        TaskServiceImpl taskService=new TaskServiceImpl();
        System.out.println(taskService.ReturnUpdateStatus(result));
        return taskService.ReturnUpdateStatus(result);

    }


    @Override
    public List<Task> getTasksByConditons(String openprojectURL, String apikey,
                                          Integer statusNum,
                                          Integer priorityNum,
                                          String fromDueDate,
                                          String toDueDate,
                                          Integer taskTypeNum,
                                          String subject) {


        JsonArray jsonArray = new JsonArray();
        JsonObject typeObject = new JsonObject();
        JsonObject typeConditionObject = new JsonObject();
        typeConditionObject.addProperty("operator", "=");
        typeConditionObject.addProperty("values", "1");
        typeObject.add("type_id", typeConditionObject);
        jsonArray.add(typeObject);

        //status条件
        if(statusNum != null){
            JsonObject statusObject = new JsonObject();
            JsonObject statusConditionObject = new JsonObject();
            JsonArray statusValuesArray = new JsonArray();
            statusValuesArray.add(statusNum + "");
            statusConditionObject.addProperty("operator", "=");
            statusConditionObject.add("values", statusValuesArray);
            statusObject.add("status", statusConditionObject);
            jsonArray.add(statusObject);
        }


        //priority条件
        if(priorityNum != null){
            JsonObject priorityObject = new JsonObject();
            JsonObject priorityConditionObject = new JsonObject();
            JsonArray priorityValuesArray = new JsonArray();
            priorityValuesArray.add(priorityNum + "");
            priorityConditionObject.addProperty("operator", "=");
            priorityConditionObject.add("values", priorityValuesArray);
            priorityObject.add("priority", priorityConditionObject);
            jsonArray.add(priorityObject);
        }


        //dueDate条件
        if(fromDueDate != null && toDueDate != null){
            JsonObject dueDateObject = new JsonObject();
            JsonObject dueDateConditionObject = new JsonObject();
            JsonArray dueDateValuesArray = new JsonArray();
            dueDateValuesArray.add(fromDueDate);
            dueDateValuesArray.add(toDueDate);
            dueDateConditionObject.addProperty("operator", "<>d");
            dueDateConditionObject.add("values", dueDateValuesArray);
            dueDateObject.add("dueDate", dueDateConditionObject);
            jsonArray.add(dueDateObject);
        }



        //taskType条件
        if(taskTypeNum != null){
            JsonObject taskTypeObject = new JsonObject();
            JsonObject taskTypeConditonObject = new JsonObject();
            JsonArray taskTypeValuesArray = new JsonArray();
            taskTypeValuesArray.add(taskTypeNum + "");
            taskTypeConditonObject.addProperty("operator", "=");
            taskTypeConditonObject.add("values", taskTypeValuesArray);
            String customName = GetCustomFieldNumUtil.getCustomfiledNum("Task type", OPENPROJECT_URL, API_KEY);
            taskTypeObject.add(customName, taskTypeConditonObject);
            jsonArray.add(taskTypeObject);
        }


        //subject条件
        if(subject != null){
            JsonObject subjectObject = new JsonObject();
            JsonObject subjectConditionObject = new JsonObject();
            JsonArray subjectValuesArray = new JsonArray();
            subjectValuesArray.add(subject);
            subjectConditionObject.addProperty("operator", "~");
            subjectConditionObject.add("values", subjectValuesArray);
            subjectObject.add("subject", subjectConditionObject);
            jsonArray.add(subjectObject);
        }


        String filterJson = jsonArray.toString();

        OpenprojectURL o=new OpenprojectURL(openprojectURL,apikey,OpenprojectURL.WORKPAGES_URL);

        String json= o.getJson(openprojectURL+OpenprojectURL.WORKPAGES_URL+
                "?filters="+filterJson
        );

        JSONObject jsonObject = JSONObject.fromObject(json);
        List<Task> taskList = JsonToTaskList(jsonObject);

        return taskList;
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
        String [] sortNameArr = {"taskType","projectName","taskPriority","dueTime","taskId"};
        boolean [] isAscArr = {true,true,false,true,true};
        SortUtils.sort(taskList,sortNameArr,isAscArr);
        return  taskList;
    }

    @Override
    public List<Task> sortTaskWithTaskPriority(List<Task> taskList) {
        String [] sortNameArr = {"taskPriority","taskType","projectName","dueTime","taskId"};
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


    public String ReturnUpdateStatus(String returnJson){
        JSONObject jsonObject = JSONObject.fromObject(returnJson);
        String type = jsonObject.getString("_type");
        if (type.equals("Error"))
         return "error";
        else
            return "success";
    }



}
