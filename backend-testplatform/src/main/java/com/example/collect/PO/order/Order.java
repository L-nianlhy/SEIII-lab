package com.example.collect.po.order;

import com.example.collect.vo.order.OrderVO;

public class Order {
    // order表主键
    private Integer orderId;

    // 对应的task的id
    private Integer taskId;

    // 用户id
    private Integer uid;

    public Order(){}

    public Order(OrderVO orderVO){
        this.orderId = orderVO.getOrderId();
        this.taskId = orderVO.getTaskId();
        this.uid = orderVO.getUid();
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
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
}