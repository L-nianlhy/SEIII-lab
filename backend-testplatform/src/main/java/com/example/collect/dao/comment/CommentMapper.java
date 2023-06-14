package com.example.collect.dao.comment;

import com.example.collect.po.comment.Comment;

import java.util.List;

public interface CommentMapper {
    int deleteByPrimaryKey(Integer commentId);

    int insert(Comment record);

    int insertSelective(Comment record);

    Comment selectByPrimaryKey(Integer commentId);

    int updateByPrimaryKeySelective(Comment record);

    int updateByPrimaryKey(Comment record);

    // 获取评论列表
    List<Comment> selectCommentsByReportId(Integer reportId);

    // 获取7日内用户评论数量
    Integer selectNumOfComments(Integer uid);

    // 获取用户报告平均得分
    Double selectAverageScore(Integer uid);
}