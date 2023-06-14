package com.example.collect.vo.score;

import com.example.collect.po.user.User;
import lombok.Data;

@Data
public class ScoreVO {
    private User user;
    private Integer totalScore;

    public ScoreVO(User user, Integer totalScore){
        this.user = user;
        this.totalScore = totalScore;
    }
}
