package com.fromLab.VO;

/**
 * @author wsh
 * @date 2019-12-26
 * The view object TaskDetail
 */
public class TaskDetailVO {

    private String taskName;

    private String projectName;

    private String dueTime;

    private String taskDetail;

    public TaskDetailVO() {
    }

    public TaskDetailVO(String taskName, String projectName, String dueTime, String taskDetail) {
        this.taskName = taskName;
        this.projectName = projectName;
        this.dueTime = dueTime;
        this.taskDetail = taskDetail;
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

    public String getDueTime() {
        return dueTime;
    }

    public void setDueTime(String dueTime) {
        this.dueTime = dueTime;
    }

    public String getTaskDetail() {
        return taskDetail;
    }

    public void setTaskDetail(String taskDetail) {
        this.taskDetail = taskDetail;
    }

    @Override
    public String toString() {
        return "TaskDetailVO{" +
                "taskName='" + taskName + '\'' +
                ", projectName='" + projectName + '\'' +
                ", dueTime='" + dueTime + '\'' +
                ", taskDetail='" + taskDetail + '\'' +
                '}';
    }
}
