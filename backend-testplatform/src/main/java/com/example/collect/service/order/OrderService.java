package com.example.collect.service.order;

import com.example.collect.vo.ResultVO;
import com.example.collect.vo.order.OrderVO;

import java.text.ParseException;

/**
 *  任务订单相关service方法
 */

public interface OrderService {
    // 工人选择测试任务
    ResultVO<OrderVO> chooseTask(Integer uid, Integer taskId) throws ParseException;
}
