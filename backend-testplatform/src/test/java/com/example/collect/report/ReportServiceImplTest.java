package com.example.collect.report;

import com.example.collect.dao.report.ReportMapper;
import com.example.collect.dao.task.TaskMapper;
import com.example.collect.dao.user.UserMapper;
import com.example.collect.serviceImpl.report.ReportServiceImpl;
import com.example.collect.serviceImpl.task.TaskServiceImpl;
import com.example.collect.serviceImpl.user.UserServiceImpl;
import com.example.collect.util.SimilarHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import javax.annotation.Resource;
import org.junit.runner.RunWith;
import com.example.collect.MockObject;
import com.example.collect.po.report.Report;
import com.example.collect.vo.ResultVO;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest
public class ReportServiceImplTest {
    @Autowired
    private ReportServiceImpl reportServiceImpl;

    @Resource
    ReportMapper reportMapper;

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Resource
    UserMapper userMapper;

    @Autowired
    private TaskServiceImpl taskServiceImpl;

    @Resource
    TaskMapper taskMapper;
    private MockObject mock = new MockObject();

    /**
     * 创建测试报告
     */
    @Test  //正常流程
    public void createReport1() {
         ResultVO<Integer> res = reportServiceImpl.createReport(mock.MockReportVO1());
         Report report = reportMapper.selectReport(mock.MockReportVO1().getUid(),mock.MockReportVO1().getTaskId(),mock.MockReportVO1().getCooperationId());
         Assertions.assertEquals("缺陷情况说明",report.getBugIntro());
         reportMapper.deleteByPrimaryKey(res.getData());
    }
    @Test  //再次创建 已提交过测试报告的测试
    public void createReport2() {
         ResultVO<Integer> res1 = reportServiceImpl.createReport(mock.MockReportVO1());
         Report report = reportMapper.selectReport(mock.MockReportVO1().getUid(),mock.MockReportVO1().getTaskId(),mock.MockReportVO1().getCooperationId());
         Assertions.assertEquals("缺陷情况说明",report.getBugIntro());
         ResultVO<Integer> res2 = reportServiceImpl.createReport(mock.MockReportVO2());
         Assertions.assertEquals("您已提交过测试报告",res2.getMsg());
         reportMapper.deleteByPrimaryKey(res1.getData());
 //        reportMapper.deleteByPrimaryKey(res2.getData());
    }

    /**
     * 删除测试报告
     */
    @Test  //正常流程
    public void removeReport1() {
         ResultVO<Integer> res = reportServiceImpl.createReport(mock.MockReportVO1());
         Report report = reportMapper.selectReport(mock.MockReportVO1().getUid(),mock.MockReportVO1().getTaskId(),mock.MockReportVO1().getCooperationId());
         Assertions.assertEquals("缺陷情况说明",report.getBugIntro());
         Assertions.assertEquals("成功删除测试报告",reportServiceImpl.removeReport(res.getData()).getMsg());
         report = reportMapper.selectReport(mock.MockReportVO1().getUid(),mock.MockReportVO1().getTaskId(),mock.MockReportVO1().getCooperationId());
         Assertions.assertEquals(null,report);
    }

    /**
     * 获取协作报告
     */
    @Test
    public void getCooperationReports() {
        userServiceImpl.userRegister(mock.MockUserVO4());
        userServiceImpl.userRegister(mock.MockUserVO1());
        userServiceImpl.userRegister(mock.MockUserVO2());
        Integer employerId = userMapper.selectByPhone(mock.MockUserVO4().getPhone()).getUid();
        Integer worker1Id = userMapper.selectByPhone(mock.MockUserVO1().getPhone()).getUid();
        Integer worker2Id = userMapper.selectByPhone(mock.MockUserVO2().getPhone()).getUid();
        ResultVO<Integer> res5 = taskServiceImpl.createTask(mock.MockTaskVO5(employerId));
        Integer taskId= taskMapper.selectByPrimaryKey(res5.getData()).getTaskId();

        ResultVO<Integer> resMain = reportServiceImpl.createReport(mock.MockReportVO3(worker1Id,taskId,0,"mainReport"));
        ResultVO<Integer> resCo = reportServiceImpl.createReport(mock.MockReportVO3(worker2Id,taskId,resMain.getData(),"coReport"));
        Assertions.assertEquals("coReport",reportServiceImpl.getCooperationReports(resMain.getData()).get(0).getBugIntro());

        reportMapper.deleteByPrimaryKey(resMain.getData());
        reportMapper.deleteByPrimaryKey(resCo.getData());
        taskMapper.deleteByPrimaryKey(taskId);
        userMapper.deleteByPrimaryKey(worker1Id);
        userMapper.deleteByPrimaryKey(worker2Id);
        userMapper.deleteByPrimaryKey(employerId);

    }

    /**
     * 获取相似报告
     */
    @Test
    public void getSimilarReports() {
        userServiceImpl.userRegister(mock.MockUserVO4());
        userServiceImpl.userRegister(mock.MockUserVO1());
        userServiceImpl.userRegister(mock.MockUserVO2());
        Integer employerId = userMapper.selectByPhone(mock.MockUserVO4().getPhone()).getUid();
        Integer worker1Id = userMapper.selectByPhone(mock.MockUserVO1().getPhone()).getUid();
        Integer worker2Id = userMapper.selectByPhone(mock.MockUserVO2().getPhone()).getUid();
        ResultVO<Integer> res5 = taskServiceImpl.createTask(mock.MockTaskVO5(employerId));
        Integer taskId= taskMapper.selectByPrimaryKey(res5.getData()).getTaskId();

        ResultVO<Integer> resMain = reportServiceImpl.createReport(mock.MockReportVO3(worker1Id,taskId,0,"在评价界面可以反复评价"));
        Integer simNumBefore = reportServiceImpl.getSimilarReports(resMain.getData()).size();
        ResultVO<Integer> resSim = reportServiceImpl.createReport(mock.MockReportVO3(worker2Id,taskId,0,"在评价界面可以一直评价"));
        Assertions.assertEquals(simNumBefore+1,reportServiceImpl.getSimilarReports(resMain.getData()).size());

        reportMapper.deleteByPrimaryKey(resMain.getData());
        reportMapper.deleteByPrimaryKey(resSim.getData());
        taskMapper.deleteByPrimaryKey(taskId);
        userMapper.deleteByPrimaryKey(worker1Id);
        userMapper.deleteByPrimaryKey(worker2Id);
        userMapper.deleteByPrimaryKey(employerId);

    }
}
