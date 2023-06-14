package com.example.collect.vo.task;

import com.example.collect.po.task.Task;

public class TaskVO {
    private Integer taskId;

    private Integer uid;

    private String taskName;

    private String taskIntro;

    private String taskTime;

    private String taskType;

    private Integer numOfWorker;

    private Integer numOfNeedWorker;

    private String taskDifficulty;

    private String taskDevice;

    private String taskApp;

    private String taskDoc;

    public TaskVO(){}

    public TaskVO(Integer uid, String taskName, String taskIntro, String taskTime, String taskType, Integer numOfWorker, Integer numOfNeedWorker,
                  String taskDifficulty, String taskDevice, String taskApp, String taskDoc){
        this.uid = uid;
        this.taskName = taskName;
        this.taskIntro = taskIntro;
        this.taskTime = taskTime;
        this.taskType = taskType;
        this.numOfWorker = numOfWorker;
        this.numOfNeedWorker = numOfNeedWorker;
        this.taskDifficulty = taskDifficulty;
        this.taskDevice = taskDevice;
        this.taskApp = taskApp;
        this.taskDoc = taskDoc;
    }

    public TaskVO(Task task){
        this.taskId = task.getTaskId();
        this.uid = task.getUid();
        this.taskName = task.getTaskName();
        this.taskTime = task.getTaskTime();
        this.taskType = task.getTaskType();
        this.numOfWorker = task.getNumOfWorker();
        this.numOfNeedWorker = task.getNumOfNeedWorker();
        this.taskDifficulty = task.getTaskDifficulty();
        this.taskDevice = task.getTaskDevice();
        this.taskApp = task.getTaskApp();
        this.taskDoc = task.getTaskDoc();
        this.taskIntro = task.getTaskIntro();
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

    public String getTaskApp() {
        return taskApp;
    }

    public void setTaskApp(String taskApp) {
        this.taskApp = taskApp == null ? null : taskApp.trim();
    }

    public String getTaskDoc() {
        return taskDoc;
    }

    public void setTaskDoc(String taskDoc) {
        this.taskDoc = taskDoc == null ? null : taskDoc.trim();
    }

    public String getTaskIntro() {
        return taskIntro;
    }

    public void setTaskIntro(String taskIntro) {
        this.taskIntro = taskIntro == null ? null : taskIntro.trim();
    }
}
