package com.example.collect.order;

import com.example.collect.dao.order.OrderMapper;
import com.example.collect.dao.user.UserMapper;
import com.example.collect.serviceImpl.order.OrderServiceImpl;
import com.example.collect.serviceImpl.user.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import javax.annotation.Resource;
import org.junit.runner.RunWith;
import com.example.collect.MockObject;
import com.example.collect.dao.task.TaskMapper;
import com.example.collect.serviceImpl.task.TaskServiceImpl;
import com.example.collect.vo.ResultVO;
import com.example.collect.vo.task.TaskVO;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderServiceImplTest {
    @Autowired
    private OrderServiceImpl orderServiceImpl;

    @Resource
    OrderMapper orderMapper;

    @Autowired
    private TaskServiceImpl taskServiceImpl;

    @Resource
    TaskMapper taskMapper;

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Resource
    UserMapper userMapper;

    private MockObject mock = new MockObject();

    /**
     * 工人选择某项测试
     *  UserController chooseTask
     *  OrderServiceImpl chooseTask
     */
    @Test      // 测试所需工人是否已满
    public void chooseTask1() {
        userServiceImpl.userRegister(mock.MockUserVO1());
        userServiceImpl.userRegister(mock.MockUserVO2());
        userServiceImpl.userRegister(mock.MockUserVO3());
        Integer worker1Id = userMapper.selectByPhone(mock.MockUserVO1().getPhone()).getUid();
        Integer worker2Id = userMapper.selectByPhone(mock.MockUserVO2().getPhone()).getUid();
        Integer worker3Id = userMapper.selectByPhone(mock.MockUserVO3().getPhone()).getUid();
        ResultVO<Integer> res = taskServiceImpl.createTask(mock.MockTaskVO3());
        Integer taskId = taskMapper.selectByPrimaryKey(res.getData()).getTaskId();
        String msg ="";
        try {
            msg=orderServiceImpl.chooseTask(mock.MockUserChooseVO(worker1Id,taskId).getUid(),
                    mock.MockUserChooseVO(worker1Id,taskId).getTaskId()).getMsg();
            Assertions.assertEquals("成功选择该测试任务",msg);
            msg=orderServiceImpl.chooseTask(mock.MockUserChooseVO(worker2Id,taskId).getUid(),
                    mock.MockUserChooseVO(worker2Id,taskId).getTaskId()).getMsg();
            Assertions.assertEquals("成功选择该测试任务",msg);
            msg=orderServiceImpl.chooseTask(mock.MockUserChooseVO(worker3Id,taskId).getUid(),
                    mock.MockUserChooseVO(worker3Id,taskId).getTaskId()).getMsg();
            Assertions.assertEquals("测试任务所需众包工人数量已满",msg);
        } catch(ParseException ex)
        {
            System.out.println("ParseException");
        }
        orderMapper.deleteTaskOrder(res.getData());
        taskMapper.deleteByPrimaryKey(res.getData());
        userMapper.deleteByPrimaryKey(worker1Id);
        userMapper.deleteByPrimaryKey(worker2Id);
        userMapper.deleteByPrimaryKey(worker3Id);
    }

    @Test   // 测试任务是否过期
    public void chooseTask2() {
        ResultVO<Integer> res = taskServiceImpl.createTask(mock.MockTaskVO4());
        TaskVO taskVO = new TaskVO(taskMapper.selectByPrimaryKey(res.getData()));
        String msg ="";
        try {
            msg=orderServiceImpl.chooseTask(mock.MockUserChooseVO4(taskVO).getUid(),
                    mock.MockUserChooseVO4(taskVO).getTaskId()).getMsg();
            Assertions.assertEquals("该测试任务已过期",msg);
        } catch(ParseException ex)
        {
            System.out.println("ParseException");
        }
        orderMapper.deleteTaskOrder(res.getData());
        taskMapper.deleteByPrimaryKey(res.getData());
    }
}
