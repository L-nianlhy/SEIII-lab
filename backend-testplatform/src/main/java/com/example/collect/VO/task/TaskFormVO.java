package com.example.collect.vo.task;

import lombok.Data;

/**
 * 传输Task数据结构
 */
@Data
public class TaskFormVO {
    private Integer uid;
    private String taskName;
    private String taskIntro;
    private String taskTime;
    private String taskType;
    private Integer numOfWorker;
    private Integer numOfNeedWorker;
    private String taskDifficulty;
    private String taskDevice;

    public TaskFormVO(){}

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
        this.taskName = taskName == null ? null : taskName.trim();
    }

    public String getTaskTime() {
        return taskTime;
    }

    public void setTaskTime(String taskTime) {
        this.taskTime = taskTime == null ? null : taskTime.trim();
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType == null ? null : taskType.trim();
    }

    public Integer getNumOfWorker() {
        return numOfWorker;
    }

    public void setNumOfWorker(Integer numOfWorker) {
        this.numOfWorker = numOfWorker;
    }

    public Integer getNumOfNeedWorker() {
        return numOfNeedWorker;
    }

    public void setNumOfNeedWorker(Integer numOfNeedWorker) {
        this.numOfNeedWorker = numOfNeedWorker;
    }

    public String getTaskDifficulty() {
        return taskDifficulty;
    }

    public void setTaskDifficulty(String taskDifficulty) {
        this.taskDifficulty = taskDifficulty;
    }

    public String getTaskDevice() {
        return taskDevice;
    }

    public void setTaskDevice(String taskDevice) {
        this.taskDevice = taskDevice;
    }

    public String getTaskIntro() {
        return taskIntro;
    }

    public void setTaskIntro(String taskIntro) {
        this.taskIntro = taskIntro == null ? null : taskIntro.trim();
    }
}
