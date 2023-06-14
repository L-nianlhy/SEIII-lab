package com.example.collect.service.report;

import com.example.collect.po.report.Report;
import com.example.collect.vo.ResultVO;
import com.example.collect.vo.report.ReportVO;

import java.util.List;

/**
 *  任务报告相关service方法
 */

public interface ReportService {
    // 创建测试报告
    ResultVO<Integer> createReport(ReportVO reportVO);

    // 删除测试报告
    ResultVO<ReportVO> removeReport(Integer reportId);

    // 获取某一任务的测试报告
    List<ReportVO> getTaskReports(Integer taskId);

    // 获取某一测试报告信息
    ReportVO getReportInfo(Integer reportId);

    // 获取相似报告
    List<ReportVO> getSimilarReports(Integer reportId);

    // 获取协作报告
    List<ReportVO> getCooperationReports(Integer reportId);
}
