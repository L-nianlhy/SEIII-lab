package com.example.collect.serviceImpl.score;

import com.example.collect.dao.comment.CommentMapper;
import com.example.collect.dao.report.ReportMapper;
import com.example.collect.service.score.ScoreService;
import com.example.collect.util.Constant;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class ScoreServiceImpl implements ScoreService {

    @Resource
    private ReportMapper reportMapper;

    @Resource
    private CommentMapper commentMapper;

    @Override
    public Integer getUserTotalScore(Integer uid) {
        return getScores(uid).get(0);
    }

    @Override
    public List<Integer> getUserScores(Integer uid) {
        return getScores(uid);
    }

    /**
     * 获取用户详细评分
     */
    private List<Integer> getScores(Integer uid){
        int activityScore = (getActivityLevel(uid) + 1) * 20;
        int cooperationScore = (getReportCoopLevel(uid) + 1) * 20;
        int censorScore = (getReportCensorLevel(uid) + 1) * 20;
        int averageScore = getReportAverageScore(uid);
        int dupScore = getReportDupLevel(uid) * 20;
        int totalScore = (int) (activityScore * 0.1 + cooperationScore * 0.2 +
                censorScore * 0.2 + averageScore * 0.3 + dupScore * 0.2);

        List<Integer> scores = new ArrayList<Integer>(){{
            add(totalScore);
            add(activityScore);
            add(cooperationScore);
            add(censorScore);
            add(averageScore);
            add(dupScore);
        }};

        return scores;
    }

    /**
     * 获取用户活跃度等级
     */
    private Integer getActivityLevel(Integer uid){
        int num = reportMapper.selectNumOfReports(uid);
        int level = 0;
        while(level < Constant.activity.length - 1){
            if(num < Constant.activity[level + 1]) break;
            level++;
        }
        return level;
    }

    /**
     * 获取用户报告协作得分等级
     */
    private Integer getReportCoopLevel(Integer uid){
        int num = reportMapper.selectNumOfCoopReports(uid);
        int level = 0;
        while(level < Constant.cooperation.length - 1){
            if(num < Constant.cooperation[level + 1]) break;
            level++;
        }
        return level;
    }

    /**
     * 获取用户报告审查得分等级
     */
    private Integer getReportCensorLevel(Integer uid){
        int num = commentMapper.selectNumOfComments(uid);
        int level = 0;
        while(level < Constant.censor.length - 1){
            if(num < Constant.censor[level + 1]) break;
            level++;
        }
        return level;
    }

    /**
     * 获取报告平均得分
     */
    private Integer getReportAverageScore(Integer uid){
        Double averageScore = commentMapper.selectAverageScore(uid);
        if(averageScore == null) averageScore = 0.0;
        return (int) (averageScore * 20);
    }

    /**
     * 获取用户报告查重得分
     */
    private Integer getReportDupLevel(Integer uid){
        int totalNum = reportMapper.selectNumOfTotalReports(uid);
        if(totalNum == 0) return 0;

        int dupNum = reportMapper.selectNumOfDupReports(uid);
        int level = 0;
        while(level < Constant.dup.length - 1){
            if(dupNum < Constant.dup[level + 1]) break;
            level++;
        }
        return 5 - level;
    }
}
