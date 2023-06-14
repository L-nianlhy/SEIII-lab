package com.example.collect.service.comment;

import com.example.collect.vo.ResultVO;
import com.example.collect.vo.comment.CommentVO;

import java.util.List;

/**
 * 评论相关service方法
 */

public interface CommentService {
    // 创建评论
    ResultVO<CommentVO> createComment(CommentVO commentVO);

    // 获取评论列表
    List<CommentVO> getCommentList(Integer reportId);
}
