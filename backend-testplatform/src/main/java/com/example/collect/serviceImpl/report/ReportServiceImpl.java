package com.example.collect.serviceImpl.report;

import com.example.collect.dao.comment.CommentMapper;
import com.example.collect.dao.report.ReportMapper;
import com.example.collect.dao.user.UserMapper;
import com.example.collect.enums.UserRole;
import com.example.collect.po.report.Report;
import com.example.collect.po.user.User;
import com.example.collect.service.file.FileService;
import com.example.collect.service.report.ReportService;
import com.example.collect.serviceImpl.async.AsyncTask;
import com.example.collect.util.Constant;
import com.example.collect.util.SimilarHelper;
import com.example.collect.vo.ResultVO;
import com.example.collect.vo.report.ReportVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *  任务报告相关service方法实现
 */

@Service
public class ReportServiceImpl implements ReportService {
    @Resource
    private ReportMapper reportMapper;

    @Resource
    private FileService fileService;

    @Autowired
    private AsyncTask asyncTask;

//    @Resource
//    private UserMapper userMapper;
//
//    @Resource
//    private CommentMapper commentMapper;

    /**
     * 创建测试报告
     */
    @Override
    public ResultVO<Integer> createReport(ReportVO reportVO) {
        if(reportMapper.selectReport(reportVO.getUid(), reportVO.getTaskId(),reportVO.getCooperationId()) != null){
            fileService.removeFile(reportVO.getBugPhoto());
            return new ResultVO<>(Constant.REQUEST_FAIL, "您已提交过测试报告");
        }
        reportMapper.insert(new Report(reportVO));
        Report report = reportMapper.selectReport(reportVO.getUid(), reportVO.getTaskId(),reportVO.getCooperationId());
        // 更新用户评分
        asyncTask.updateScore(reportVO.getUid());
        return new ResultVO<>(Constant.REQUEST_SUCCESS, "成功提交测试报告", report.getReportId());
    }

    /**
     * 删除测试报告
     */
    @Override
    public ResultVO<ReportVO> removeReport(Integer reportId) {
        Report report = reportMapper.selectByPrimaryKey(reportId);
        if(report == null){
            return new ResultVO<>(Constant.REQUEST_FAIL, "测试报告不存在");
        }
        fileService.removeFile(report.getBugPhoto());
        reportMapper.deleteByPrimaryKey(reportId);
        // 更新用户评分
        asyncTask.updateScore(report.getUid());
        return new ResultVO<>(Constant.REQUEST_SUCCESS, "成功删除测试报告");
    }

    /**
     * 获取某一任务的测试报告
     */
    @Cacheable(value = "reports", key = "#taskId")
    @Override
    public List<ReportVO> getTaskReports(Integer taskId) {
        List<ReportVO> res = new ArrayList<>();
        List<Report> reports = reportMapper.selectReportsByTaskId(taskId);
        for(Report report: reports){
            ReportVO reportVO = new ReportVO(report);
            reportVO.setBugPhoto(fileService.download(reportVO.getBugPhoto()));
            res.add(reportVO);
        }
        return res;
    }

    /**
     * 获取某一测试报告的信息
     */
    @CachePut(value = "reportInfo", key = "#reportId")
    @Override
    public ReportVO getReportInfo(Integer reportId) {
        ReportVO reportVO = new ReportVO(reportMapper.selectByPrimaryKey(reportId));
        reportVO.setBugPhoto(fileService.download(reportVO.getBugPhoto()));
        return reportVO;
    }

    /**
     * 获取相似报告
     */
    @CachePut(value = "similarReports", key = "#reportId")
    @Override
    public List<ReportVO> getSimilarReports(Integer reportId) {
        Report target = reportMapper.selectByPrimaryKey(reportId);
        List<Report> reports = reportMapper.selectReportsByTaskId(target.getTaskId());
        List<ReportVO> result = new ArrayList<>();
        for(Report report: reports){
            if(!Objects.equals(report.getReportId(), reportId)){
                double param = SimilarHelper.calculateTextSimilar(target.getBugIntro(), report.getBugIntro());
                // double param2 = SimilarHelper.calculateTextSimilar(target.getBugStep(), report.getBugStep());
                if(param >= 0.4){
                    result.add(new ReportVO(report));
                }
            }
        }
        return result;
    }

    /**
     * 获取协作报告
     */
    @CachePut(value = "coopReports", key = "#reportId")
    @Override
    public List<ReportVO> getCooperationReports(Integer reportId) {
        List<Report> reports = reportMapper.selectCooperationReports(reportId);
        List<ReportVO> res = new ArrayList<>();
        for(Report report: reports){
            res.add(new ReportVO(report));
        }
        return res;
    }
}
