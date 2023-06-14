package com.example.collect.dao.report;

import com.example.collect.po.report.Report;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ReportMapper {
    int deleteByPrimaryKey(Integer reportId);

    int insert(Report record);

    int insertSelective(Report record);

    Report selectByPrimaryKey(Integer reportId);

    int updateByPrimaryKeySelective(Report record);

    int updateByPrimaryKey(Report record);

    // 根据reportId获取缺陷截图存储的objectName
    String selectPhoto(Integer reportId);

    // 根据uid和taskId获取测试报告
    Report selectReport(@Param("uid") Integer uid, @Param("taskId") Integer taskId,@Param("cooperationId") Integer cooperationId);

    // 根据taskId返回所有测试报告
    List<Report> selectReportsByTaskId(Integer taskId);

    // 更新缺陷截图
    int updateBugPhoto(@Param("reportId") Integer reportId, @Param("bugPhoto") String bugPhoto);

    // 获取协作报告
    List<Report> selectCooperationReports(Integer reportId);

    // 获取7日内用户发布的报告数量
    Integer selectNumOfReports(Integer uid);

    // 获取7日内用户完成的协作报告数量
    Integer selectNumOfCoopReports(Integer uid);

    // 获取用户所写报告的相似报告数量
    Integer selectNumOfDupReports(Integer uid);

    // 获取用户所写所写报告总数
    Integer selectNumOfTotalReports(Integer uid);
}