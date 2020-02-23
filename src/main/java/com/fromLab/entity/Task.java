package com.fromLab.entity;

import com.fromLab.annotation.Ignored;


import java.sql.Timestamp;

/**
 * @Auther: JIN KE
 * @Date: 2019/12/11 09:12
 * 用整数1到5表示任务的优先级，数值越大，优先级越高
 * project我认为和后面提到的topic一个意思，表示任务所属的主题或者说是项目
 * taskype我认为和后面的activity一个意思，比如有design，develop，consultant
 * 用sql.timestamp类型来表示时间，因为比较精确，就是不知道通过PMS api获得的时间类型是什么，是否容易转换？
 */
public class Task {


    private Integer taskId;

    private String taskName;

    private String projectName;

    private Integer taskPriority;

    private String taskType;

    private String startTime;

    //版本号，更新时需要
    private Integer lockVersion;

    private String endTime;

    private String dueTime;

    private String status;

    private String progress;

    private Integer timeSpent;

    private String taskDetail;

    public Task() {

    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
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

    public Integer getLockVersion() {
        return lockVersion;
    }

    public void setLockVersion(Integer lockVersion) {
        this.lockVersion = lockVersion;
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

    public String getTaskDetail() {
        return taskDetail;
    }

    public void setTaskDetail(String taskDetail) {
        this.taskDetail = taskDetail;
    }

    @Override
    public String toString() {
        return "Task{" +
                "taskId=" + taskId +
                ", taskName='" + taskName + '\'' +
                ", projectName='" + projectName + '\'' +
                ", taskPriority=" + taskPriority +
                ", taskType='" + taskType + '\'' +
                ", startTime='" + startTime + '\'' +
                ", lockVersion=" + lockVersion +
                ", endTime='" + endTime + '\'' +
                ", dueTime='" + dueTime + '\'' +
                ", status='" + status + '\'' +
                ", progress='" + progress + '\'' +
                ", timeSpent=" + timeSpent +
                ", taskDetail='" + taskDetail + '\'' +
                '}';
    }
}
