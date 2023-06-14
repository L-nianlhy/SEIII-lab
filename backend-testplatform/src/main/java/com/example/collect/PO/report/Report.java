package com.example.collect.po.report;

import com.example.collect.vo.report.ReportVO;

public class Report {
    // report表主键
    private Integer reportId;

    // 用户id
    private Integer uid;

    // 测试任务id
    private Integer taskId;

    // 协作报告id
    private Integer cooperationId;

    // 缺陷应用截图(对象存储的api)
    private String bugPhoto;

    // 缺陷情况说明
    private String bugIntro;

    // 缺陷复先步骤
    private String bugStep;

    // 测试设备信息
    private String deviceInformation;

    // 报告提交时间
    private String timeStamp;

    public Report(){}

    public Report(ReportVO reportVO){
        this.reportId = reportVO.getReportId();
        this.uid = reportVO.getUid();
        this.taskId = reportVO.getTaskId();
        this.cooperationId = reportVO.getCooperationId();
        this.bugPhoto = reportVO.getBugPhoto();
        this.bugIntro = reportVO.getBugIntro();
        this.bugStep = reportVO.getBugStep();
        this.deviceInformation = reportVO.getDeviceInformation();
        this.timeStamp = reportVO.getTimeStamp();
    }

    public Integer getReportId() {
        return reportId;
    }

    public void setReportId(Integer reportId) {
        this.reportId = reportId;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public Integer getCooperationId() {
        return cooperationId;
    }

    public void setCooperationId(Integer cooperationId) {
        this.cooperationId = cooperationId;
    }

    public String getBugPhoto() {
        return bugPhoto;
    }

    public void setBugPhoto(String bugPhoto) {
        this.bugPhoto = bugPhoto == null ? null : bugPhoto.trim();
    }

    public String getBugIntro() {
        return bugIntro;
    }

    public void setBugIntro(String bugIntro) {
        this.bugIntro = bugIntro == null ? null : bugIntro.trim();
    }

    public String getBugStep() {
        return bugStep;
    }

    public void setBugStep(String bugStep) {
        this.bugStep = bugStep == null ? null : bugStep.trim();
    }

    public String getDeviceInformation() {
        return deviceInformation;
    }

    public void setDeviceInformation(String deviceInformation) {
        this.deviceInformation = deviceInformation == null ? null : deviceInformation.trim();
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }
}