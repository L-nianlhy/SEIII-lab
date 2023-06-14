package com.example.collect.task;

import com.example.collect.dao.order.OrderMapper;
import com.example.collect.dao.task.TaskMapper;
import com.example.collect.dao.user.UserMapper;
import com.example.collect.serviceImpl.order.OrderServiceImpl;
import com.example.collect.serviceImpl.task.TaskServiceImpl;
import com.example.collect.serviceImpl.user.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import javax.annotation.Resource;
import org.junit.runner.RunWith;
import com.example.collect.MockObject;
import com.example.collect.vo.ResultVO;
import com.example.collect.vo.task.TaskVO;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.List;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest
public class TaskServiceImplTest {
    @Autowired
    private TaskServiceImpl taskServiceImpl;

    @Resource
    TaskMapper taskMapper;

    @Autowired
    private OrderServiceImpl orderServiceImpl;

    @Resource
    OrderMapper orderMapper;

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Resource
    UserMapper userMapper;

    private MockObject mock = new MockObject();
    /**
     * 创建测试任务(不包含文件上传
     *      去除 处理task httpRequest
     */
    @Test      //测试名重复
    public void createTask1() {
        String msg1 ="";
        Integer task1;
        String msg2 ="";
        ResultVO<Integer> res1 = taskServiceImpl.createTask(mock.MockTaskVO1());
        msg1=res1.getMsg();
        task1=res1.getData();
        Assertions.assertEquals("成功创建测试任务",msg1);
        //task1:taskMapper.selectTaskId(taskVO.getTaskName()).getTaskId()
        Assertions.assertEquals(taskMapper.selectTaskId(mock.MockTaskVO1().getTaskName()).getTaskId()
                ,task1);
        ResultVO<Integer> res2= taskServiceImpl.createTask(mock.MockTaskVO2());
        msg2=res2.getMsg();
        Assertions.assertEquals("测试名重复",msg2);
        taskMapper.deleteByPrimaryKey(task1);
        taskMapper.deleteByPrimaryKey(res2.getData());
    }

    /**
     * 删除测试任务
     */
    @Test      //正常流程删去
    public void removeTask1() {
        String msg ="";
        Integer taskId;
        ResultVO<Integer> res = taskServiceImpl.createTask(mock.MockTaskVO1());
        msg=res.getMsg();
        Assertions.assertEquals("成功创建测试任务",msg);
        taskId=res.getData();
        ResultVO<TaskVO> resRemove = taskServiceImpl.removeTask(taskId);
        Assertions.assertEquals("成功删除测试任务",resRemove.getMsg());
        Assertions.assertEquals(null,taskMapper.selectByPrimaryKey(taskId));
        taskMapper.deleteByPrimaryKey(taskId);
    }
    @Test      //测试任务不存在 断言前提taskId1000不存在
    public void removeTask2() {
        String msg ="";
        ResultVO<TaskVO> res = taskServiceImpl.removeTask(1000);
        msg=res.getMsg();
        Assertions.assertEquals("测试任务不存在",msg);
    }

    /**
     * 获取某一用户发布或者选取的测试任务
     */
    @Test  //正常流程  工人
    public void getTasksByUid1() {
        userServiceImpl.userRegister(mock.MockUserVO4());
        userServiceImpl.userRegister(mock.MockUserVO1());
        userServiceImpl.userRegister(mock.MockUserVO2());
        Integer employerId = userMapper.selectByPhone(mock.MockUserVO4().getPhone()).getUid();
        Integer worker1Id = userMapper.selectByPhone(mock.MockUserVO1().getPhone()).getUid();
        Integer worker2Id = userMapper.selectByPhone(mock.MockUserVO2().getPhone()).getUid();
        getTasksByUidCreate(employerId,worker1Id,worker2Id);
        //
        List<TaskVO> list1 = taskServiceImpl.getTasksByUid(worker1Id);
        Assertions.assertEquals(3,list1.size());
        List<TaskVO> list2 = taskServiceImpl.getTasksByUid(worker2Id);
        Assertions.assertEquals(1,list2.size());
        Assertions.assertEquals("collect测试task获取employerAll2",list2.get(0).getTaskName());
        getTasksByUidRemove(employerId,worker1Id,worker2Id);
    }
    @Test  //正常流程  发包方
    public void getTasksByUid2() {
        userServiceImpl.userRegister(mock.MockUserVO4());
        userServiceImpl.userRegister(mock.MockUserVO1());
        userServiceImpl.userRegister(mock.MockUserVO2());
        Integer employerId = userMapper.selectByPhone(mock.MockUserVO4().getPhone()).getUid();
        Integer worker1Id = userMapper.selectByPhone(mock.MockUserVO1().getPhone()).getUid();
        Integer worker2Id = userMapper.selectByPhone(mock.MockUserVO2().getPhone()).getUid();
        getTasksByUidCreate(employerId,worker1Id,worker2Id);
        //
        List<TaskVO> list = taskServiceImpl.getTasksByUid(employerId);
        Assertions.assertEquals(3,list.size());
        Assertions.assertEquals(2,list.get(1).getNumOfWorker());
        getTasksByUidRemove(employerId,worker1Id,worker2Id);
    }
    //employer:5,6,7   w1:5,6,7 w2:6
    public void getTasksByUidCreate(Integer employerId,Integer worker1Id,Integer worker2Id){
        Integer taskId;
        try {
            ResultVO<Integer> res5 = taskServiceImpl.createTask(mock.MockTaskVO5(employerId));
            taskId= taskMapper.selectByPrimaryKey(res5.getData()).getTaskId();
            orderServiceImpl.chooseTask(mock.MockUserChooseVO(worker1Id,taskId).getUid(),
                    mock.MockUserChooseVO(worker1Id,taskId).getTaskId());
            ResultVO<Integer> res6 = taskServiceImpl.createTask(mock.MockTaskVO6(employerId));
            taskId= taskMapper.selectByPrimaryKey(res6.getData()).getTaskId();
            orderServiceImpl.chooseTask(mock.MockUserChooseVO(worker1Id,taskId).getUid(),
                    mock.MockUserChooseVO(worker1Id,taskId).getTaskId());
            taskId= taskMapper.selectByPrimaryKey(res6.getData()).getTaskId();
            orderServiceImpl.chooseTask(mock.MockUserChooseVO(worker2Id,taskId).getUid(),
                    mock.MockUserChooseVO(worker2Id,taskId).getTaskId());
            ResultVO<Integer> res7 = taskServiceImpl.createTask(mock.MockTaskVO7(employerId));
            taskId= taskMapper.selectByPrimaryKey(res7.getData()).getTaskId();
            orderServiceImpl.chooseTask(mock.MockUserChooseVO(worker1Id,taskId).getUid(),
                    mock.MockUserChooseVO(worker1Id,taskId).getTaskId());
        } catch(ParseException ex)
        {
            System.out.println("ParseException");
        }
    }
    public void getTasksByUidRemove(Integer employerId,Integer worker1Id,Integer worker2Id){
        List<TaskVO> list = taskServiceImpl.getTasksByUid(employerId);
        for(int i=0;i<list.size();i++){
            orderMapper.deleteTaskOrder(list.get(i).getTaskId());
            taskMapper.deleteByPrimaryKey(list.get(i).getTaskId());
        }
        userMapper.deleteByPrimaryKey(worker1Id);
        userMapper.deleteByPrimaryKey(worker2Id);
        userMapper.deleteByPrimaryKey(employerId);
    }

    /**
     * 获取推荐任务,设置推荐规则
     */
    //employer:5,6,7   w1:"amateur""Android""functionalTest" w2:"professional""IOS""performanceTest"
    //amateur:5,6 professional:7  normal:
    //Android:5,7 IOS:6
    //functionalTest:7 performanceTest:5,6
    //'功能测试','amateur','Windows'
    @Test
    public void getRecommendTasks() {
        userServiceImpl.userRegister(mock.MockUserVO4());
        userServiceImpl.userRegister(mock.MockUserVO1());
        userServiceImpl.userRegister(mock.MockUserVO2());
        Integer employerId = userMapper.selectByPhone(mock.MockUserVO4().getPhone()).getUid();
        Integer worker1Id = userMapper.selectByPhone(mock.MockUserVO1().getPhone()).getUid();
        Integer worker2Id = userMapper.selectByPhone(mock.MockUserVO2().getPhone()).getUid();

        taskServiceImpl.setRecommendRule("ability");
        List<TaskVO> list = taskServiceImpl.getRecommendTasks(worker1Id);
        Integer ab  = list.size();
        taskServiceImpl.setRecommendRule("preference");
        list = taskServiceImpl.getRecommendTasks(worker2Id);
        Integer pr = list.size();
        taskServiceImpl.setRecommendRule("device");
        list = taskServiceImpl.getRecommendTasks(worker2Id);
        Integer de = list.size();

        ResultVO<Integer> res5 = taskServiceImpl.createTask(mock.MockTaskVO5(employerId));
        ResultVO<Integer> res6 = taskServiceImpl.createTask(mock.MockTaskVO6(employerId));
        ResultVO<Integer> res7 = taskServiceImpl.createTask(mock.MockTaskVO7(employerId));

        //ability //amateur:5,6 professional:7  normal:   //w1:"amateur"
        taskServiceImpl.setRecommendRule("ability");
        list = taskServiceImpl.getRecommendTasks(worker1Id);
        Assertions.assertEquals(ab+2,list.size());

        //preference  //functionalTest:7 performanceTest:5,6  //"performanceTest"
        taskServiceImpl.setRecommendRule("preference");
        list = taskServiceImpl.getRecommendTasks(worker2Id);
        Assertions.assertEquals(pr+2,list.size());

        //device //Android:5,7 IOS:6 // "IOS"
        taskServiceImpl.setRecommendRule("device");
        list = taskServiceImpl.getRecommendTasks(worker2Id);
        Assertions.assertEquals(de+1,list.size());

        getTasksByUidRemove(employerId,worker1Id,worker2Id);
    }


}
