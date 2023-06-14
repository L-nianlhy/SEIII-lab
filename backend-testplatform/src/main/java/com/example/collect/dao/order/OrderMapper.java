package com.example.collect.dao.order;

import com.example.collect.po.order.Order;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderMapper {
    int deleteByPrimaryKey(Integer orderId);

    int insert(Order record);

    int insertSelective(Order record);

    Order selectByPrimaryKey(Integer orderId);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);

    // 根据uid和taskId查找order记录
    Integer selectOrder(@Param("uid") Integer uid, @Param("taskId") Integer taskId);

    // 删除某测试任务的所有order
    int deleteTaskOrder(Integer taskId);

    // 返回某个用户选择测试任务的所有order
    List<Order> selectAllByUid(Integer uid);

}