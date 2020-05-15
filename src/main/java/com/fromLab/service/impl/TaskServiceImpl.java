package com.fromLab.service.impl;


import com.fromLab.exception.BusinessException;
import com.fromLab.utils.OpenprojectURL;
import com.fromLab.entity.*;
import com.fromLab.service.TaskService;
import com.fromLab.utils.GetCustomFieldNumUtil;
import com.fromLab.utils.SortUtils;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.sf.json.JSONObject;

import java.math.BigDecimal;
import java.util.List;

import static com.fromLab.utils.JsonToObjectUtil.JsonToTask;
import static com.fromLab.utils.JsonToObjectUtil.JsonToTaskList;

/**
 * @Auther: JIN KE
 * @Date: 2019/12/11 20:42
 */
public class TaskServiceImpl implements TaskService {



    @Override
    public Task getTaskById(OpenprojectURL openprojectURL, int id) throws BusinessException {
        String originalUrl = openprojectURL.getOpenProjectURL();
        openprojectURL.setOpenProjectURL(originalUrl + id);
        String json= openprojectURL.getJson();
        JSONObject jsonObject = JSONObject.fromObject(json);
        openprojectURL.setOpenProjectURL(originalUrl.substring(0,originalUrl.length()-1));

        return JsonToTask(jsonObject, openprojectURL);
    }

    @Override
    public String updateStartDate(OpenprojectURL openprojectURL, int id, int lockVersion, String startDate) {
        JsonObject jsonObject=new JsonObject();
        jsonObject.addProperty("lockVersion",lockVersion);
        jsonObject.addProperty("startDate",startDate);
        openprojectURL.setOpenProjectURL(openprojectURL.getOpenProjectURL() + id);
        String result=openprojectURL.patch(jsonObject.toString());
        System.out.println(returnUpdateStatus(result));
        return returnUpdateStatus(result);
    }

    @Override
    public String updateEndDate(OpenprojectURL openprojectURL, int id, int lockVersion, String endDate, String customField) {
        JsonObject jsonObject=new JsonObject();
        jsonObject.addProperty("lockVersion",lockVersion);
        jsonObject.addProperty(customField,endDate);
        openprojectURL.setOpenProjectURL(openprojectURL.getOpenProjectURL() + id);
        String result = openprojectURL.patch(jsonObject.toString());
        System.out.println(returnUpdateStatus(result));
        return returnUpdateStatus(result);
    }

    @Override
    public String updateSpentTime(OpenprojectURL openprojectURL, int id, int lockVersion, BigDecimal time, String customField) {
        JsonObject jsonObject=new JsonObject();
        jsonObject.addProperty("lockVersion",lockVersion);
        jsonObject.addProperty(customField,time);
        openprojectURL.setOpenProjectURL(openprojectURL.getOpenProjectURL() + id);
        String result = openprojectURL.patch(jsonObject.toString());
        System.out.println(returnUpdateStatus(result));
        return returnUpdateStatus(result);
    }

    @Override
    public String updateStatusAndProgress(OpenprojectURL openprojectURL, int id, int lockVersion, Status status, int percentage) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("lockVersion", lockVersion);
        JsonObject links = new JsonObject();
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
        openprojectURL.setOpenProjectURL(openprojectURL.getOpenProjectURL() + id);
        String result = openprojectURL.patch(jsonObject.toString());
        System.out.println(returnUpdateStatus(result));
        return returnUpdateStatus(result);

    }


    @Override
    public List<Task> getTasksByConditions(OpenprojectURL openprojectURL,
                                           Integer statusNum,
                                           Integer priorityNum,
                                           String fromDueDate,
                                           String toDueDate,
                                           Integer taskTypeNum,
                                           String subject) throws BusinessException {


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
            String customName = GetCustomFieldNumUtil.getCustomFieldNum("Task type", openprojectURL);
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
        String originalUrl = openprojectURL.getOpenProjectURL();
        openprojectURL.setOpenProjectURL(openprojectURL.getOpenProjectURL() + "?filters="+filterJson);
        String json= openprojectURL.getJson();
        JSONObject jsonObject = JSONObject.fromObject(json);
        openprojectURL.setOpenProjectURL(originalUrl);
        return JsonToTaskList(jsonObject, openprojectURL);

    }
    public String returnUpdateStatus(String returnJson){
        JSONObject jsonObject = JSONObject.fromObject(returnJson);
        String type = jsonObject.getString("_type");
        if (type.equals("Error")){
            return "error";
        }
        return "success";
    }



}
