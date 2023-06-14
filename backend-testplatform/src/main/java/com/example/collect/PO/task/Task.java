package com.example.collect.po.task;

import com.example.collect.vo.task.TaskVO;

public class Task {
    // 测试任务id, task表主键
    private Integer taskId;

    // 发包方id
    private Integer uid;

    // 测试任务名
    private String taskName;

    // 测试任务简介
    private String taskIntro;

    // 测试时间段
    private String taskTime;

    // 测试类型
    private String taskType;

    // 已参加测试工人数量
    private Integer numOfWorker;

    // 测试所需工人数量
    private Integer numOfNeedWorker;

    // 任务难度
    private String taskDifficulty;

    // 测试设备需求
    private String taskDevice;

    // 待测应用可执行文件(对象存储api)
    private String taskApp;

    // 测试需求描述文件(对象存储api)
    private String taskDoc;

    public Task(){}

    public Task(TaskVO taskVO){
        this.taskId = taskVO.getTaskId();
        this.uid = taskVO.getUid();
        this.taskName = taskVO.getTaskName();
        this.taskIntro = taskVO.getTaskIntro();
        this.taskTime = taskVO.getTaskTime();
        this.taskType = taskVO.getTaskType();
        this.numOfWorker = taskVO.getNumOfWorker();
        this.numOfNeedWorker = taskVO.getNumOfNeedWorker();
        this.taskDifficulty = taskVO.getTaskDifficulty();
        this.taskDevice = taskVO.getTaskDevice();
        this.taskApp = taskVO.getTaskApp();
        this.taskDoc = taskVO.getTaskDoc();
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