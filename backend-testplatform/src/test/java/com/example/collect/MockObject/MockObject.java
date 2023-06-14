package com.example.collect.MockObject;

import com.example.collect.enums.UserRole;
import com.example.collect.vo.report.ReportVO;
import com.example.collect.vo.report.ReportFormVO;
import com.example.collect.vo.order.OrderVO;
import com.example.collect.vo.user.UserVO;
import com.example.collect.vo.user.UserChooseVO;
import com.example.collect.vo.user.UserFormVO;
import com.example.collect.vo.user.UserInfoVO;
import com.example.collect.vo.task.TaskFormVO;
import com.example.collect.vo.task.TaskVO;
import com.example.collect.vo.file.FileVO;
import com.example.collect.util.Constant;
import com.example.collect.enums.UserRole;
import com.example.collect.vo.comment.CommentVO;

public class MockObject {
    /**  测试前置条件 sql脚本
     *  uid:1  worker  /  2 employer
     *  taskId 上限可供worker加入测试
     *
     */

    /**
     *  ReportService
     */

    public ReportVO MockReportVO1(){
        ReportVO po=new ReportVO();
        po.setUid(1);
        po.setTaskId(1);
        po.setBugPhoto("缺陷应用截图(对象存储的api)");
        po.setBugIntro("缺陷情况说明");
        po.setBugStep("缺陷复先步骤");
        po.setDeviceInformation("测试设备信息");
        po.setTimeStamp("2023-03-11");
        po.setCooperationId(0);
        return po;
    }
    public ReportVO MockReportVO2(){  //再次创建 已提交过测试报告的测试
        ReportVO po=new ReportVO();
        po.setUid(1);
        po.setTaskId(1);
        po.setBugPhoto("22缺陷应用截图(对象存储的api)");
        po.setBugIntro("22缺陷情况说明");
        po.setBugStep("22缺陷复先步骤");
        po.setDeviceInformation("22测试设备信息");
        po.setTimeStamp("2023-03-11");
        po.setCooperationId(0);
        return po;
    }

    public ReportVO MockReportVO3(Integer uid,Integer taskId,Integer cooperationId,String bugIntro){  //再次创建 已提交过测试报告的测试
        ReportVO po=new ReportVO();
        po.setUid(uid);
        po.setTaskId(taskId);
        po.setBugPhoto("33缺陷应用截图(对象存储的api)");
        po.setBugIntro(bugIntro);
        po.setBugStep("33缺陷复先步骤");
        po.setDeviceInformation("33测试设备信息");
        po.setTimeStamp("2023-03-11");
        po.setCooperationId(cooperationId);
        return po;
    }

    public ReportVO MockReportVO4(Integer uid,Integer taskId,String time){
        ReportVO po=new ReportVO();
        po.setUid(uid);
        po.setTaskId(taskId);
        po.setBugPhoto("33缺陷应用截图(对象存储的api)");
        po.setBugIntro("123456");
        po.setBugStep("33缺陷复先步骤");
        po.setDeviceInformation("33测试设备信息");
        po.setTimeStamp(time);
        po.setCooperationId(0);
        return po;
    }

    //获取某一任务的测试报告

    /**
     *  TaskService
     */

    //create测试名重复
    public TaskVO MockTaskVO1(){
        TaskVO po= new TaskVO();
        po.setUid(2);
        po.setTaskName("collect测试task");
        po.setTaskIntro("Intro");
        po.setTaskTime("2023-03-11");
        po.setTaskType("performanceTest");
        po.setNumOfNeedWorker(1);
        po.setNumOfWorker(0);
        po.setTaskDoc(Constant.FILE_NOT_UPLOAD);
        po.setTaskApp(Constant.FILE_NOT_UPLOAD);
        po.setTaskDifficulty("amateur");
        po.setTaskDevice("Android");
        return po;
    }
    public TaskVO MockTaskVO2(){
        TaskVO po= new TaskVO();
        po.setUid(2);
        po.setTaskName("collect测试task");
        po.setTaskIntro("Intro2");
        po.setTaskTime("2023-03-12");
        po.setTaskType("functionalTest");
        po.setNumOfNeedWorker(5);
        po.setNumOfWorker(0);
        po.setTaskDoc(Constant.FILE_NOT_UPLOAD);
        po.setTaskApp(Constant.FILE_NOT_UPLOAD);
        po.setTaskDifficulty("amateur");
        po.setTaskDevice("Android");
        return po;
    }

    //获取某一用户发布或者选取的测试任务
    public TaskVO MockTaskVO5(Integer employerUid){
        TaskVO po= new TaskVO();
        po.setUid(employerUid);
        po.setTaskName("collect测试task获取employerAll1");
        po.setTaskIntro("Intro5");
        po.setTaskTime("2023-03-15");
        po.setTaskType("performanceTest");
        po.setNumOfNeedWorker(11);
        po.setNumOfWorker(0);
        po.setTaskDoc("docApi");
        po.setTaskApp("appApi");
        po.setTaskDifficulty("amateur");
        po.setTaskDevice("Android");
        return po;
    }
    public TaskVO MockTaskVO6(Integer employerUid){
        TaskVO po= new TaskVO();
        po.setUid(employerUid);
        po.setTaskName("collect测试task获取employerAll2");
        po.setTaskIntro("Intro6");
        po.setTaskTime("2023-03-17");
        po.setTaskType("performanceTest");
        po.setNumOfNeedWorker(5);
        po.setNumOfWorker(0);
        po.setTaskDoc("docApi");
        po.setTaskApp("appApi");
        po.setTaskDifficulty("amateur");
        po.setTaskDevice("IOS");
        return po;
    }
    public TaskVO MockTaskVO7(Integer employerUid){
        TaskVO po= new TaskVO();
        po.setUid(employerUid);
        po.setTaskName("collect测试task获取employerAll3");
        po.setTaskIntro("Intro7");
        po.setTaskTime("2023-03-16");
        po.setTaskType("functionalTest");
        po.setNumOfNeedWorker(11);
        po.setNumOfWorker(0);
        po.setTaskDoc("docApi");
        po.setTaskApp("appApi");
        po.setTaskDifficulty("professional");
        po.setTaskDevice("Android");
        return po;
    }
    public UserChooseVO MockUserChooseVO(Integer uid,Integer taskId){
        UserChooseVO po= new UserChooseVO();
        po.setUid(uid);
        po.setTaskId(taskId);
        return po;
    }

    /**
     *  OrderService
     */

    // 测试所需工人是否已满
    public TaskVO MockTaskVO3(){
        TaskVO po= new TaskVO();
        po.setUid(2);
        po.setTaskName("collect测试order工人是否满");
        po.setTaskIntro("Intro3");
        po.setTaskTime("2023-03-13");
        po.setTaskType("functionalTest");
        po.setNumOfNeedWorker(2);
        po.setNumOfWorker(0);
        po.setTaskDoc(Constant.FILE_NOT_UPLOAD);
        po.setTaskApp(Constant.FILE_NOT_UPLOAD);
        po.setTaskDifficulty("amateur");
        po.setTaskDevice("Android");
        return po;
    }

    // 测试任务是否过期
    public TaskVO MockTaskVO4(){
        TaskVO po= new TaskVO();
        po.setUid(2);
        po.setTaskName("collect测试order/是否过期");
        po.setTaskIntro("Intro4");
        po.setTaskTime("2022-01-13");
        po.setTaskType("functionalTest");
        po.setNumOfNeedWorker(3);
        po.setNumOfWorker(0);
        po.setTaskDoc(Constant.FILE_NOT_UPLOAD);
        po.setTaskApp(Constant.FILE_NOT_UPLOAD);
        po.setTaskDifficulty("amateur");
        po.setTaskDevice("Android");
        return po;
    }
    public UserChooseVO MockUserChooseVO4(TaskVO taskVO){
        UserChooseVO po= new UserChooseVO();
        po.setUid(1);
        po.setTaskId(taskVO.getTaskId());
        return po;
    }

    /**
     *  UserService
     */

    public UserVO MockUserVO1(){
        UserVO po= new UserVO();
        po.setPassword("123456A");
        po.setUserRole("worker");
        po.setPhone("131234561");
        po.setUname("workerA");
        po.setAbility("amateur");
        po.setDevice("Android");
        po.setPreference("functionalTest");
        return po;
    }
    public UserVO MockUserVO2(){
        UserVO po= new UserVO();
        po.setPassword("123456B");
        po.setUserRole("worker");
        po.setPhone("131234562");
        po.setUname("workerB");
        po.setAbility("professional");
        po.setDevice("IOS");
        po.setPreference("performanceTest");
        return po;
    }
    public UserVO MockUserVO3(){
        UserVO po= new UserVO();
        po.setPassword("123456C");
        po.setUserRole("worker");
        po.setPhone("131234563");
        po.setUname("workerC");
        po.setAbility("amateur");
        po.setDevice("Android");
        po.setPreference("functionalTest");
        return po;
    }
    public UserVO MockUserVO4(){
        UserVO po= new UserVO();
        po.setPassword("EA123456");
        po.setUserRole("employer");
        po.setPhone("113123456");
        po.setUname("employerA");
        po.setAbility("amateur");
        po.setDevice("Android");
        po.setPreference("functionalTest");
        return po;
    }
    public UserVO MockUserVO5(){
        UserVO po= new UserVO();
        po.setPassword("123456A5");
        po.setUserRole("worker");
        po.setPhone("131234561");
        po.setUname("workerA-5");
        po.setAbility("amateur");
        po.setDevice("Android");
        po.setPreference("functionalTest");
        return po;
    }

    public CommentVO MockCommentVO(Integer reportId) {
        CommentVO po = new CommentVO();
        po.setUid(1);
        po.setUname("worker1");
        po.setReportId(reportId);
        po.setContent("1111");
        po.setScore(1);
        po.setTimeStamp("2023-01-13");
        return po;
    }
}
