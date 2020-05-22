package com.fromLab.utils;


import com.fromLab.entity.Task;

import com.fromLab.exception.BusinessException;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.fromLab.utils.GetCustomFieldNumUtil.getCustomFieldNum;

/**
 * @Auther: JIN KE
 * @Date: 2020/1/12 18:50
 * The static utility class for parsing JSON string
 */
public class JsonToObjectUtil {


    /**
     * Convert the returned json to a task object
     *
     * @param json
     * @return
     */
    public static Task JsonToTask(JSONObject json, OpenprojectURL openprojectURL) throws BusinessException {

        String task_type = getCustomFieldNum("Task type", openprojectURL);
        String end_date = getCustomFieldNum("End date", openprojectURL);
        String time_spent = getCustomFieldNum("Time spent", openprojectURL);
        Task task = new Task();
        //id
        int taskId = json.getInt("id");
        task.setTaskId(taskId);
        //lockVersion
        int lockVersion = json.getInt("lockVersion");
        task.setLockVersion(lockVersion);
        //subject
        String taskName = json.getString("subject");
        task.setTaskName(taskName);
        //percentageDone
        int progress = json.getInt("percentageDone");
        task.setProgress(progress + "%");
        //startTime
        String startTime = String.valueOf(json.getOrDefault("startDate", "null"));
        task.setStartTime(startTime);
        //dueTime
        String dueTime = String.valueOf(json.getOrDefault("dueDate", "null"));
        task.setDueTime(dueTime);
        //endTime
        String endTime = String.valueOf(json.getOrDefault(end_date, "null"));
        task.setEndTime(endTime);

        //spentTime
        Object timeSpent = json.getOrDefault(time_spent, "null");
        if ("null".equals(timeSpent.toString())) {
            task.setTimeSpent(BigDecimal.ZERO);
        } else {
            task.setTimeSpent(new BigDecimal(timeSpent.toString()));
        }


        //description
        String descriptionEntity = json.getString("description");
        JSONObject descriptionJsonObject = JSONObject.fromObject(descriptionEntity);
        String description = descriptionJsonObject.getString("raw");
        task.setTaskDetail(description);


        //taskType
        String links = json.getString("_links");
        JSONObject linksJsonObject = JSONObject.fromObject(links);
        Object taskTypeEntity = linksJsonObject.getOrDefault(task_type, "null");
        if (!"null".equals(taskTypeEntity.toString())) {
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
     * Convert the returned json to a task list
     *
     * @param json
     * @return
     */
    public static List<Task> JsonToTaskList(JSONObject json, OpenprojectURL openprojectURL) throws BusinessException {
        String task_type = getCustomFieldNum("Task type", openprojectURL);
        String end_date = getCustomFieldNum("End date", openprojectURL);
        String time_spent = getCustomFieldNum("Time spent", openprojectURL);
        List<Task> taskList = new ArrayList<>();
        String embedded = json.getString("_embedded");
        JSONObject embeddedJsonObject = JSONObject.fromObject(embedded);
        JSONArray elementsArray = embeddedJsonObject.getJSONArray("elements");
        for (int i = 0; i < elementsArray.size(); i++) {
            Task task = new Task();
            //lockVersion
            int lockVersion = elementsArray.getJSONObject(i).getInt("lockVersion");
            task.setLockVersion(lockVersion);

            //taskId
            int taskId = elementsArray.getJSONObject(i).getInt("id");
            task.setTaskId(taskId);

            //taskName
            String taskName = elementsArray.getJSONObject(i).getString("subject");
            task.setTaskName(taskName);

            //progress
            int progress = elementsArray.getJSONObject(i).getInt("percentageDone");
            task.setProgress(progress + "%");

            String startTime = elementsArray.getJSONObject(i).getString("startDate");
            task.setStartTime(startTime);
            String dueTime = elementsArray.getJSONObject(i).getString("dueDate");
            task.setDueTime(dueTime);
            String endTime = String.valueOf(elementsArray.getJSONObject(i).getOrDefault(end_date, "null"));
            task.setEndTime(endTime);

            //timeSpent
            Object timeSpent = elementsArray.getJSONObject(i).getOrDefault(time_spent, "null");
            if ("null".equals(timeSpent.toString())) {
                task.setTimeSpent(BigDecimal.ZERO);
            } else {
                task.setTimeSpent(new BigDecimal(timeSpent.toString()));
            }

            //taskDetail
            String descriptionEntity = elementsArray.getJSONObject(i).getString("description");
            JSONObject descriptionJsonObject = JSONObject.fromObject(descriptionEntity);
            String description = descriptionJsonObject.getString("raw");
            task.setTaskDetail(description);

            //taskType
            String links = elementsArray.getJSONObject(i).getString("_links");
            JSONObject linksJsonObject = JSONObject.fromObject(links);


            Object taskTypeEntity = linksJsonObject.getOrDefault(task_type, null);
            if (!"null".equals(taskTypeEntity.toString())) {
                JSONObject taskTypeJsonObject = JSONObject.fromObject(taskTypeEntity.toString());
                String taskType = taskTypeJsonObject.getString("title");
                task.setTaskType(taskType);
            }


            //priority
            String priorityEntity = linksJsonObject.getString("priority");
            JSONObject priorityJsonObject = JSONObject.fromObject(priorityEntity);
            String priorityHref = priorityJsonObject.getString("href");
            int priorityId = Integer.valueOf(priorityHref.substring(priorityHref.lastIndexOf('/') + 1));
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


            taskList.add(task);
        }

        return taskList;
    }
}
