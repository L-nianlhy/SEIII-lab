package com.example.collect.vo.report;

import lombok.Data;

@Data
public class ReportFormVO {
    private Integer uid;
    private Integer taskId;
    private Integer cooperationId;
    private String bugIntro;
    private String bugStep;
    private String deviceInformation;
    private String timeStamp;
}
