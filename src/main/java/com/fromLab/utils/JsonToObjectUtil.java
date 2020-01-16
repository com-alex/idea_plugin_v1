package com.fromLab.utils;


import com.fromLab.VO.TaskVO;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.jetbrains.uast.values.UBooleanConstant;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: JIN KE
 * @Date: 2020/1/12 18:50
 */
public class JsonToObjectUtil {


    /**
     * 将返回的json转化为一个task对象
     * @param json
     * @return
     */
    public static TaskVO  JsonToTask(JSONObject json){
        TaskVO task=new TaskVO();
        int taskId = json.getInt("id");
        task.setTaskId(taskId);
        int lockVersion = json.getInt("lockVersion");
        task.setLockVersion(lockVersion);
        String taskName= json.getString("subject");
        task.setTaskName(taskName);
        int progress = json.getInt("percentageDone");
        task.setProgress(progress+"%");
        String startTime = json.getString("startDate");
        task.setStartTime(startTime);
        String dueTime = json.getString("dueDate");
        task.setStartTime(dueTime);
        String endTime = String.valueOf(json.getOrDefault("customField2","null"));
        task.setEndTime(endTime);
        Object timeSpent = json.getOrDefault("customField3","null");
        if ("null".equals(timeSpent.toString()))
            task.setTimeSpent(0);
        else
            task.setTimeSpent(Integer.valueOf(timeSpent.toString()));
        String descriptionEntity = json.getString("description");
        JSONObject descriptionJsonObject = JSONObject.fromObject(descriptionEntity);
        String description = descriptionJsonObject.getString("raw");
        task.setTaskDetail(description);
        String links = json.getString("_links");
        JSONObject linksJsonObject = JSONObject.fromObject(links);
        Object taskTypeEntity = linksJsonObject.getOrDefault("customField1",null);
        JSONObject taskTypeJsonObject = JSONObject.fromObject(taskTypeEntity.toString());
        String taskType = taskTypeJsonObject.getString("title");
        task.setTaskType(taskType);
        //String spentTime = json.getString("spentTime");
        //返回的spentTime字段格式为PT3H，PT3H15M，PT30M
        //task.setTimeSpent(timeSpentTransfer(spentTime));
        String embedded = json.getString("_embedded");
        JSONObject embeddedJsonObject = JSONObject.fromObject(embedded);
        String priorityEntity = embeddedJsonObject.getString("priority");
        JSONObject priorityJsonObject = JSONObject.fromObject(priorityEntity);
        int priorityId = priorityJsonObject.getInt("id");
        String statusEntity = embeddedJsonObject.getString("status");
        JSONObject statusJsonObject = JSONObject.fromObject(statusEntity);
        String status = statusJsonObject.getString("name");
        String projectEntity = embeddedJsonObject.getString("project");
        JSONObject projectJsonObject = JSONObject.fromObject(projectEntity);
        String projectName = projectJsonObject.getString("name");



        task.setTaskPriority(priorityId);
        task.setStatus(status);
        task.setProjectName(projectName);

        //System.out.println(task.toString());
        return task;
    }

    /**
     * 将返回的json转化为一个task列表
     * @param json
     * @return
     */
    public static List<TaskVO> JsonToTaskList (JSONObject json){
        List<TaskVO> taskVOList =new ArrayList<>();
        String embedded = json.getString("_embedded");
        JSONObject embeddedJsonObject = JSONObject.fromObject(embedded);
        JSONArray elementsArray = embeddedJsonObject.getJSONArray("elements");
        for (int i = 0; i < elementsArray.size(); i++) {
            TaskVO task=new TaskVO();
            int lockVersion = elementsArray.getJSONObject(i).getInt("lockVersion");
            task.setLockVersion(lockVersion);
            int taskId = elementsArray.getJSONObject(i).getInt("id");
            task.setTaskId(taskId);
            String taskName= elementsArray.getJSONObject(i).getString("subject");
            task.setTaskName(taskName);
            int progress = elementsArray.getJSONObject(i).getInt("percentageDone");
            task.setProgress(progress+"%");
            String startTime = elementsArray.getJSONObject(i).getString("startDate");
            task.setStartTime(startTime);
            String dueTime = elementsArray.getJSONObject(i).getString("dueDate");
            task.setStartTime(dueTime);
            String endTime = String.valueOf(elementsArray.getJSONObject(i).getOrDefault("customField2","null"));
            task.setEndTime(endTime);
            Object timeSpent = elementsArray.getJSONObject(i).getOrDefault("customField3","null");
            if ("null".equals(timeSpent.toString()))
                task.setTimeSpent(0);
            else
                task.setTimeSpent(Integer.valueOf(timeSpent.toString()));
            String descriptionEntity = elementsArray.getJSONObject(i).getString("description");
            JSONObject descriptionJsonObject = JSONObject.fromObject(descriptionEntity);
            String description = descriptionJsonObject.getString("raw");
            task.setTaskDetail(description);
            String links = elementsArray.getJSONObject(i).getString("_links");
            JSONObject linksJsonObject = JSONObject.fromObject(links);
            Object taskTypeEntity = linksJsonObject.getOrDefault("customField1",null);
            JSONObject taskTypeJsonObject = JSONObject.fromObject(taskTypeEntity.toString());
            String taskType = taskTypeJsonObject.getString("title");
            task.setTaskType(taskType);
            //String spentTime = json.getString("spentTime");
            //返回的spentTime字段格式为PT3H，PT3H15M，PT30M
            //task.setTimeSpent(timeSpentTransfer(spentTime));
            String priorityEntity = linksJsonObject.getString("priority");
            JSONObject priorityJsonObject = JSONObject.fromObject(priorityEntity);
            String priorityHref = priorityJsonObject.getString("href");
            int priorityId=Integer.valueOf(priorityHref.substring(priorityHref.lastIndexOf('/')+1));
            String statusEntity = linksJsonObject.getString("status");
            JSONObject statusJsonObject = JSONObject.fromObject(statusEntity);
            String status = statusJsonObject.getString("title");
            String projectEntity = linksJsonObject.getString("project");
            JSONObject projectJsonObject = JSONObject.fromObject(projectEntity);
            String projectName = projectJsonObject.getString("title");



            task.setTaskPriority(priorityId);
            task.setStatus(status);
            task.setProjectName(projectName);

            //System.out.println(task.toString());
            taskVOList.add(task);
        }

        return taskVOList;
    }

    private static double timeSpentTransfer(String s){
        double result=0;
        double hours=0;
        double mins=0;
        if(s.contains("H")){
            if (s.contains("M")){
                int index=s.indexOf("H");
                hours=Integer.valueOf(s.substring(2,index));
                mins=Integer.valueOf(s.substring(index+1,s.length()-1));
                result=Double.valueOf(String.format("%.2f", hours+mins/60));
            }
            else {
                result=Integer.valueOf(s.substring(2,s.length()-1));
            }
        }
        else{
            result=Double.valueOf(String.format("%.2f", Double.valueOf(s.substring(2,s.length()-1))/60));
        }
        return result;
    }

}
