package com.fromLab.entity;

import com.fromLab.annotation.Column;
import com.fromLab.annotation.PrimaryKey;
import com.fromLab.annotation.Table;

import java.sql.Timestamp;

/**
 * @Auther: JIN KE
 * @Date: 2019/12/11 09:12
 * 用整数1到5表示任务的优先级，数值越大，优先级越高
 * project我认为和后面提到的topic一个意思，表示任务所属的主题或者说是项目
 * taskype我认为和后面的activity一个意思，比如有design，develop，consultant
 * 用sql.timestamp类型来表示时间，因为比较精确，就是不知道通过PMS api获得的时间类型是什么，是否容易转换？
 */
@Table (tableName = "task_info")
public class Task {

    @PrimaryKey
    @Column(column = "task_id")
    private Integer taskId;
    @Column(column = "uid")
    private Integer uid;
    @Column(column = "task_name")
    private String taskName;
    @Column(column = "project_name")
    private String projectName;
    @Column(column = "task_priority")
    private Integer taskPriority;
    @Column(column = "task_type")
    private String taskType;
    @Column(column = "task_detail")
    private String taskDetail;
    @Column(column = "start_time")
    private String startTime;
    @Column(column = "end_time")
    private String endTime;
    @Column(column = "due_time")
    private String dueTime;
    @Column(column = "status")
    private String status;
    @Column(column = "progress")
    private String progress;
    @Column(column = "time_spent")
    private Integer timeSpent;

    public Task() {
    }

    public Task(Integer taskId, Integer uid, String taskName, String projectName, Integer taskPriority, String taskType, String taskDetail, String startTime, String endTime, String dueTime, String status, String progress, Integer timeSpent) {
        this.taskId = taskId;
        this.uid = uid;
        this.taskName = taskName;
        this.projectName = projectName;
        this.taskPriority = taskPriority;
        this.taskType = taskType;
        this.taskDetail = taskDetail;
        this.startTime = startTime;
        this.endTime = endTime;
        this.dueTime = dueTime;
        this.status = status;
        this.progress = progress;
        this.timeSpent = timeSpent;
    }

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

    public String getTaskDetail() {
        return taskDetail;
    }

    public void setTaskDetail(String taskDetail) {
        this.taskDetail = taskDetail;
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
        return "Task{" +
                "taskId=" + taskId +
                ", uid=" + uid +
                ", taskName='" + taskName + '\'' +
                ", projectName='" + projectName + '\'' +
                ", taskPriority=" + taskPriority +
                ", taskType='" + taskType + '\'' +
                ", taskDetail='" + taskDetail + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", dueTime='" + dueTime + '\'' +
                ", status='" + status + '\'' +
                ", progress='" + progress + '\'' +
                ", timeSpent=" + timeSpent +
                '}';
    }
}
