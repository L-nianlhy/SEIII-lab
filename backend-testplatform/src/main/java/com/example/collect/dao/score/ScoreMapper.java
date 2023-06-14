package com.example.collect.dao.score;

import com.example.collect.po.score.Score;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ScoreMapper {
    int insert(Score record);

    int insertSelective(Score record);

    // 更新用户总分
    int updateScore(@Param("uid") Integer uid, @Param("totalScore") Integer totalScore);

    // 获取用户评分排行榜
    List<Score> selectScoreList();
}