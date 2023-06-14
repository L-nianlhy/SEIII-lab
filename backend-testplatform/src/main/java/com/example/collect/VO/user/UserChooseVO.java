package com.example.collect.vo.user;

import lombok.Data;

/**
 * 用户选择测试任务
 */

@Data
public class UserChooseVO {
    public UserChooseVO(){}
    private Integer uid;
    private Integer taskId;

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
}
