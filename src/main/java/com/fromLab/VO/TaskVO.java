package com.fromLab.VO;

import com.fromLab.annotation.Column;
import com.fromLab.annotation.Ignored;

/**
 * @author wsh
 * @date 2019-12-17
 * 返回数据的实体类
 */
public class TaskVO {

    private Integer taskId;

    @Ignored
    private Integer uid;

    private String taskName;

    private String projectName;

    private Integer taskPriority;

    private String taskType;

    private String startTime;

    private String endTime;

    private String dueTime;

    private String status;

    private String progress;

    private Integer timeSpent;

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Integer getTaskPriority() {
        return taskPriority;
    }

    public void setTaskPriority(Integer taskPriority) {
        this.taskPriority = taskPriority;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getDueTime() {
        return dueTime;
    }

    public void setDueTime(String dueTime) {
        this.dueTime = dueTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    public Integer getTimeSpent() {
        return timeSpent;
    }

    public void setTimeSpent(Integer timeSpent) {
        this.timeSpent = timeSpent;
    }

    @Override
    public String toString() {
        return "TaskVO{" +
                "taskId=" + taskId +
                ", uid=" + uid +
                ", taskName='" + taskName + '\'' +
                ", projectName='" + projectName + '\'' +
                ", taskPriority=" + taskPriority +
                ", taskType='" + taskType + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", dueTime='" + dueTime + '\'' +
                ", status='" + status + '\'' +
                ", progress='" + progress + '\'' +
                ", timeSpent=" + timeSpent +
                '}';
    }
}
