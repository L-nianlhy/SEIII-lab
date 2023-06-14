package com.example.collect.controller.report;

import com.example.collect.serviceImpl.report.ReportServiceImpl;
import com.example.collect.util.Constant;
import com.example.collect.util.DateUtil;
import com.example.collect.vo.ResultVO;
import com.example.collect.vo.report.ReportFormVO;
import com.example.collect.vo.report.ReportVO;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 *  任务报告功能相关controller层接口
 */

@RestController
@RequestMapping("/api/test_report")
public class ReportController {

    @Resource
    private ReportServiceImpl reportService;


    /**
     * 创建测试报告
     */
    @PostMapping("/create")
    public ResultVO<Integer> createTestReport(@RequestBody ReportFormVO reportFormVO) throws Exception {
        return reportService.createReport(reportRequestParse(reportFormVO));
    }

    /**
     * 删除测试报告
     */
    @PostMapping("/remove")
    public ResultVO<ReportVO> removeTestReport(@RequestParam Integer reportId){
        return reportService.removeReport(reportId);
    }

    /**
     * 获取某一任务的测试报告
     */
    @GetMapping("/getReport/{taskId}")
    public List<ReportVO> getReportList(@PathVariable Integer taskId){
        return reportService.getTaskReports(taskId);
    }

    /**
     * 获取某一测试报告信息
     */
    @GetMapping("/reportId/{reportId}")
    public ReportVO getReportInfo(@PathVariable Integer reportId){
        return reportService.getReportInfo(reportId);
    }

    /**
     * 获取相似报告
     */
    @GetMapping("/getSimilar/{reportId}")
    public List<ReportVO> getSimilarReports(@PathVariable Integer reportId){
        return reportService.getSimilarReports(reportId);
    }

    /**
     * 获取协作报告
     */
    @GetMapping("/getCooperation/{reportId}")
    public List<ReportVO> getCooperationReports(@PathVariable Integer reportId){
        return reportService.getCooperationReports(reportId);
    }

    /**
     * 处理report httpRequest
     * @param reportFormVO
     * @return
     */
    private ReportVO reportRequestParse(ReportFormVO reportFormVO){
        Integer uid = reportFormVO.getUid();
        Integer taskId = reportFormVO.getTaskId();
        Integer cooperationId = reportFormVO.getCooperationId() != -1 ? reportFormVO.getCooperationId() : 0;
        String bugIntro = reportFormVO.getBugIntro();
        String bugStep = reportFormVO.getBugStep();
        String deviceInformation = reportFormVO.getDeviceInformation();
        String bugPhoto = Constant.FILE_NOT_UPLOAD;
        String timeStamp = reportFormVO.getTimeStamp() != null ? reportFormVO.getTimeStamp() : DateUtil.getCurrentTime();
        return new ReportVO(uid, taskId, cooperationId, bugPhoto, bugIntro, bugStep, deviceInformation, timeStamp);
    }
}
