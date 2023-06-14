package com.example.collect.po.score;

public class Score {
    private Integer uid;

    private Integer totalScore;

    public Score(Integer uid, Integer totalScore){
        this.uid = uid;
        this.totalScore = totalScore;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(Integer totalScore) {
        this.totalScore = totalScore;
    }
}