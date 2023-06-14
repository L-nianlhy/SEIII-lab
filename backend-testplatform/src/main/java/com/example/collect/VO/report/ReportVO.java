package com.example.collect.vo.report;

import com.example.collect.po.report.Report;

public class ReportVO {
    private Integer reportId;

    private Integer uid;

    private Integer taskId;

    private Integer cooperationId;

    private String bugPhoto;

    private String bugIntro;

    private String bugStep;

    private String deviceInformation;

    private String timeStamp;

    public ReportVO(){}

    public ReportVO(Integer uid, Integer taskId, Integer cooperationId, String bugPhoto,
                    String bugIntro, String bugStep, String deviceInformation, String timeStamp){
        this.uid = uid;
        this.taskId = taskId;
        this.cooperationId = cooperationId;
        this.bugPhoto = bugPhoto;
        this.bugIntro = bugIntro;
        this.bugStep = bugStep;
        this.deviceInformation = deviceInformation;
        this.timeStamp = timeStamp;
    }

    public ReportVO(Report report){
        this.reportId = report.getReportId();
        this.uid = report.getUid();
        this.taskId = report.getTaskId();
        this.cooperationId = report.getCooperationId();
        this.bugPhoto = report.getBugPhoto();
        this.bugIntro = report.getBugIntro();
        this.bugStep = report.getBugStep();
        this.deviceInformation = report.getDeviceInformation();
        this.timeStamp = report.getTimeStamp();
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
