package com.example.collect.po.comment;

import com.example.collect.vo.comment.CommentVO;

public class Comment {
    // comment表主键
    private Integer commentId;

    // 用户id
    private Integer uid;

    // 用户名
    private String uname;

    // 报告id
    private Integer reportId;

    // 评分
    private Integer score;

    // 评论内容
    private String content;
    
    // 评论时间
    private String timeStamp;

    public Comment(){}

    public Comment(CommentVO commentVO){
        this.uid = commentVO.getUid();
        this.uname = commentVO.getUname();
        this.reportId = commentVO.getReportId();
        this.score = commentVO.getScore();
        this.content = commentVO.getContent();
        this.timeStamp = commentVO.getTimeStamp();
    }

    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public Integer getReportId() {
        return reportId;
    }

    public void setReportId(Integer reportId) {
        this.reportId = reportId;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }
}