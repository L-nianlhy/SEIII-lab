package com.example.collect.service.score;

import java.util.List;

/**
 * 用户评分相关功能
 */

public interface ScoreService {

    // 获取用户总分
    Integer getUserTotalScore(Integer uid);

    // 获取用户评分详细
    List<Integer> getUserScores(Integer uid);
}
