package com.example.collect.vo.order;

import com.example.collect.po.order.Order;

public class OrderVO {
    private Integer orderId;

    private Integer taskId;

    private Integer uid;

    public OrderVO(Integer taskId, Integer uid){
        this.taskId = taskId;
        this.uid = uid;
    }

    public OrderVO(Order order){
        this.orderId = order.getOrderId();
        this.taskId = order.getTaskId();
        this.uid = order.getUid();
    }
    public OrderVO(){}
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
