package com.example.collect.user;

import com.example.collect.dao.report.ReportMapper;
import com.example.collect.dao.task.TaskMapper;
import com.example.collect.dao.user.UserMapper;
import com.example.collect.serviceImpl.report.ReportServiceImpl;
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
import com.example.collect.vo.user.UserVO;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceImplTest {
    @Autowired
    private UserServiceImpl userServiceImpl;

    @Resource
    UserMapper userMapper;

    @Autowired
    private ReportServiceImpl reportServiceImpl;

    @Resource
    ReportMapper reportMapper;

    @Autowired
    private TaskServiceImpl taskServiceImpl;

    @Resource
    TaskMapper taskMapper;

    private MockObject mock = new MockObject();

    /**
     * 用户登录注册
     */
    @Test  //注册密码不正确或输错手机号
    public void userRegisterLogin1() {
        String msgRegister = userServiceImpl.userRegister(mock.MockUserVO1()).getMsg();
        Assertions.assertEquals("用户注册成功",msgRegister);
        String msgLogin1 = userServiceImpl.userLogin(mock.MockUserVO1().getPhone(), "###"+mock.MockUserVO1().getPassword()).getMsg();
        Assertions.assertEquals("账号或密码错误",msgLogin1);
        String msgLogin2 = userServiceImpl.userLogin("159"+mock.MockUserVO1().getPhone(), mock.MockUserVO1().getPassword()).getMsg();
        Assertions.assertEquals("手机号码尚未注册账号",msgLogin2);
        String msgLogin3 = userServiceImpl.userLogin(mock.MockUserVO1().getPhone(), mock.MockUserVO1().getPassword()).getMsg();
        Assertions.assertEquals("登陆成功",msgLogin3);
        userMapper.deleteByPrimaryKey(userMapper.selectByPhone
                (mock.MockUserVO1().getPhone()).getUid());
    }
    @Test  //重复手机号
    public void userRegisterLogin2() {
        String msgRegister1 = userServiceImpl.userRegister(mock.MockUserVO1()).getMsg();
        Assertions.assertEquals("用户注册成功",msgRegister1);
        String msgRegister2 = userServiceImpl.userRegister(mock.MockUserVO5()).getMsg();
        Assertions.assertEquals("该手机号码已注册过账号",msgRegister2);
        userMapper.deleteByPrimaryKey(userMapper.selectByPhone
                (mock.MockUserVO1().getPhone()).getUid());
    }

    /**
     * 用户更新信息
     */
    @Test  //更新用户信息
    public void updateUserInfo() {
        userServiceImpl.userRegister(mock.MockUserVO1());
        Integer workerId = userMapper.selectByPhone(mock.MockUserVO1().getPhone()).getUid();
        UserVO uservo = mock.MockUserVO2();
        uservo.setUid(workerId);
        ResultVO<UserVO> updateInfo= userServiceImpl.updateUserInfo(uservo);
        Assertions.assertEquals("更新用户信息成功",updateInfo.getMsg());
        Assertions.assertEquals(userMapper.selectByPrimaryKey(workerId).getUname(), "workerB");
        userMapper.deleteByPrimaryKey(workerId);
    }

    /**
     * 获取用户活跃度
     */
    @Test
    public void getUserActivity1() {
        userServiceImpl.userRegister(mock.MockUserVO1());
        Integer workerId = userMapper.selectByPhone(mock.MockUserVO1().getPhone()).getUid();
        Assertions.assertEquals("least", userServiceImpl.getUserActivity(workerId));
        userMapper.deleteByPrimaryKey(workerId);
    }
    @Test
    public void getUserActivity2() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Timestamp delayDate = new Timestamp(System.currentTimeMillis());
        String time= sdf.format(delayDate);

        userServiceImpl.userRegister(mock.MockUserVO4());
        userServiceImpl.userRegister(mock.MockUserVO1());
        Integer employerId = userMapper.selectByPhone(mock.MockUserVO4().getPhone()).getUid();
        Integer workerId = userMapper.selectByPhone(mock.MockUserVO1().getPhone()).getUid();
        ResultVO<Integer> res5 = taskServiceImpl.createTask(mock.MockTaskVO5(employerId));
        Integer task5Id= taskMapper.selectByPrimaryKey(res5.getData()).getTaskId();
        ResultVO<Integer> res6 = taskServiceImpl.createTask(mock.MockTaskVO6(employerId));
        Integer task6Id= taskMapper.selectByPrimaryKey(res6.getData()).getTaskId();
        ResultVO<Integer> res7 = taskServiceImpl.createTask(mock.MockTaskVO7(employerId));
        Integer task7Id= taskMapper.selectByPrimaryKey(res7.getData()).getTaskId();

        ResultVO<Integer> re1 = reportServiceImpl.createReport(mock.MockReportVO4(workerId,task5Id,time));
        ResultVO<Integer> re2 = reportServiceImpl.createReport(mock.MockReportVO4(workerId,task6Id,time));
        ResultVO<Integer> re3 = reportServiceImpl.createReport(mock.MockReportVO4(workerId,task7Id,time));
        Assertions.assertEquals("normal", userServiceImpl.getUserActivity(workerId));

        reportMapper.deleteByPrimaryKey(re1.getData());
        reportMapper.deleteByPrimaryKey(re2.getData());
        reportMapper.deleteByPrimaryKey(re3.getData());
        taskMapper.deleteByPrimaryKey(task5Id);
        taskMapper.deleteByPrimaryKey(task6Id);
        taskMapper.deleteByPrimaryKey(task7Id);
        userMapper.deleteByPrimaryKey(workerId);
        userMapper.deleteByPrimaryKey(employerId);
    }
}
