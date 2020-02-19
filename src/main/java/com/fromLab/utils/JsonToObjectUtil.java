package com.fromLab.utils;


import com.fromLab.VO.TaskVO;
import com.fromLab.entity.Task;
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
    public static Task JsonToTask(JSONObject json){
        Task task = new Task();
        //id
        int taskId = json.getInt("id");
        task.setTaskId(taskId);
        //lockVersion
        int lockVersion = json.getInt("lockVersion");
        task.setLockVersion(lockVersion);
        //subject
        String taskName= json.getString("subject");
        task.setTaskName(taskName);
        //percentageDone
        int progress = json.getInt("percentageDone");
        task.setProgress(progress+"%");
        //startTime
        String startTime = String.valueOf(json.getOrDefault("startDate","null"));
        task.setStartTime(startTime);
        //dueTime
        String dueTime = String.valueOf(json.getOrDefault("dueDate","null"));
        task.setDueTime(dueTime);
        //endTime
        String endTime = String.valueOf(json.getOrDefault("customField2","null"));
        task.setEndTime(endTime);

        //spentTime
        Object timeSpent = json.getOrDefault("customField3","null");
        if ("null".equals(timeSpent.toString())){
            task.setTimeSpent(0);
        }

        else{
            task.setTimeSpent(Integer.valueOf(timeSpent.toString()));
        }


        //description
        String descriptionEntity = json.getString("description");
        JSONObject descriptionJsonObject = JSONObject.fromObject(descriptionEntity);
        String description = descriptionJsonObject.getString("raw");
        task.setTaskDetail(description);



        //taskType
        String links = json.getString("_links");
        JSONObject linksJsonObject = JSONObject.fromObject(links);
        Object taskTypeEntity = linksJsonObject.getOrDefault("customField1","null");
        if(!"null".equals(taskTypeEntity.toString())) {
            JSONObject taskTypeJsonObject = JSONObject.fromObject(taskTypeEntity.toString());
            String taskType = taskTypeJsonObject.getString("title");
            task.setTaskType(taskType);
        }


        //priority
        String embedded = json.getString("_embedded");
        JSONObject embeddedJsonObject = JSONObject.fromObject(embedded);
        String priorityEntity = embeddedJsonObject.getString("priority");
        JSONObject priorityJsonObject = JSONObject.fromObject(priorityEntity);
        int priorityId = priorityJsonObject.getInt("id");
        task.setTaskPriority(priorityId);

        //status
        String statusEntity = embeddedJsonObject.getString("status");
        JSONObject statusJsonObject = JSONObject.fromObject(statusEntity);
        String status = statusJsonObject.getString("name");
        task.setStatus(status);

        //projectName
        String projectEntity = embeddedJsonObject.getString("project");
        JSONObject projectJsonObject = JSONObject.fromObject(projectEntity);
        String projectName = projectJsonObject.getString("name");
        task.setProjectName(projectName);



        return task;
    }

    /**
     * 将返回的json转化为一个task列表
     * @param json
     * @return
     */
    public static List<Task> JsonToTaskList (JSONObject json){
        List<Task> taskList =new ArrayList<>();
        String embedded = json.getString("_embedded");
        JSONObject embeddedJsonObject = JSONObject.fromObject(embedded);
        JSONArray elementsArray = embeddedJsonObject.getJSONArray("elements");
        for (int i = 0; i < elementsArray.size(); i++) {
            Task task=new Task();
            //lockVersion
            int lockVersion = elementsArray.getJSONObject(i).getInt("lockVersion");
            task.setLockVersion(lockVersion);

            //taskId
            int taskId = elementsArray.getJSONObject(i).getInt("id");
            task.setTaskId(taskId);

            //taskName
            String taskName= elementsArray.getJSONObject(i).getString("subject");
            task.setTaskName(taskName);

            //progress
            int progress = elementsArray.getJSONObject(i).getInt("percentageDone");
            task.setProgress(progress+"%");

            String startTime = elementsArray.getJSONObject(i).getString("startDate");
            task.setStartTime(startTime);
            String dueTime = elementsArray.getJSONObject(i).getString("dueDate");
            task.setDueTime(dueTime);
            String endTime = String.valueOf(elementsArray.getJSONObject(i).getOrDefault("customField2","null"));
            task.setEndTime(endTime);

            //timeSpent
            Object timeSpent = elementsArray.getJSONObject(i).getOrDefault("customField3","null");
            if ("null".equals(timeSpent.toString())){
                task.setTimeSpent(0);
            }
            else{
                task.setTimeSpent(Integer.valueOf(timeSpent.toString()));
            }

            //taskDetail
            String descriptionEntity = elementsArray.getJSONObject(i).getString("description");
            JSONObject descriptionJsonObject = JSONObject.fromObject(descriptionEntity);
            String description = descriptionJsonObject.getString("raw");
            task.setTaskDetail(description);

            //taskType
            String links = elementsArray.getJSONObject(i).getString("_links");
            JSONObject linksJsonObject = JSONObject.fromObject(links);


            Object taskTypeEntity = linksJsonObject.getOrDefault("customField1",null);
            if(!"null".equals(taskTypeEntity.toString())) {
                JSONObject taskTypeJsonObject = JSONObject.fromObject(taskTypeEntity.toString());
                String taskType = taskTypeJsonObject.getString("title");
                task.setTaskType(taskType);
            }


            //priority
            String priorityEntity = linksJsonObject.getString("priority");
            JSONObject priorityJsonObject = JSONObject.fromObject(priorityEntity);
            String priorityHref = priorityJsonObject.getString("href");
            int priorityId=Integer.valueOf(priorityHref.substring(priorityHref.lastIndexOf('/')+1));
            task.setTaskPriority(priorityId);

            //status
            String statusEntity = linksJsonObject.getString("status");
            JSONObject statusJsonObject = JSONObject.fromObject(statusEntity);
            String status = statusJsonObject.getString("title");
            task.setStatus(status);


            //projectName
            String projectEntity = linksJsonObject.getString("project");
            JSONObject projectJsonObject = JSONObject.fromObject(projectEntity);
            String projectName = projectJsonObject.getString("title");
            task.setProjectName(projectName);



            //System.out.println(task.toString());
            taskList.add(task);
        }

        return taskList;
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
