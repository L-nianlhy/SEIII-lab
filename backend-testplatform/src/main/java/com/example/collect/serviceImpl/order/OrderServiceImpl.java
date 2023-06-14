package com.example.collect.serviceImpl.order;

import com.example.collect.dao.order.OrderMapper;
import com.example.collect.dao.task.TaskMapper;
import com.example.collect.po.order.Order;
import com.example.collect.service.order.OrderService;
import com.example.collect.util.Constant;
import com.example.collect.util.DateUtil;
import com.example.collect.vo.ResultVO;
import com.example.collect.vo.order.OrderVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;

/**
 *  任务订单相关service方法实现
 */

@Service
public class OrderServiceImpl implements OrderService {
    @Resource
    private OrderMapper orderMapper;

    @Resource
    private TaskMapper taskMapper;

    @Override
    public ResultVO<OrderVO> chooseTask(Integer uid, Integer taskId) throws ParseException {
        if(orderMapper.selectOrder(uid, taskId) != 0){
            return new ResultVO<>(Constant.REQUEST_FAIL, "您已选择过该测试任务");
        }
        int currentWorkerNum = taskMapper.selectNumOfWorker(taskId);
        // 测试所需工人是否已满
        if(currentWorkerNum >= taskMapper.selectNumOfNeedWorker(taskId)){
            return new ResultVO<>(Constant.REQUEST_FAIL, "测试任务所需众包工人数量已满");
        }
        // 测试任务是否过期
        DateUtil dateUtil = new DateUtil();
        if(dateUtil.isOverDue(taskMapper.selectTaskTime(taskId))){
            return new ResultVO<>(Constant.REQUEST_FAIL, "该测试任务已过期");
        }
        Order order = new Order();
        order.setUid(uid);
        order.setTaskId(taskId);
        // 插入order记录
        orderMapper.insert(order);
        // 测试任务众包工人数量加一
        taskMapper.updateNumOfWorker(taskId, currentWorkerNum+1);
        return new ResultVO<>(Constant.REQUEST_SUCCESS, "成功选择该测试任务");
    }
}
