package com.example.collect.vo.comment;


import com.example.collect.po.comment.Comment;

public class CommentVO {
    private Integer commentId;

    private Integer uid;

    private String uname;

    private Integer reportId;

    private Integer score;

    private String content;

    private String timeStamp;

    public CommentVO(){}

    public CommentVO(Comment comment){
        this.commentId = comment.getCommentId();
        this.uid = comment.getUid();
        this.uname = comment.getUname();
        this.reportId = comment.getReportId();
        this.score = comment.getScore();
        this.content = comment.getContent();
        this.timeStamp = comment.getTimeStamp();
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
        this.content = content;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }
}
