package com.example.collect.serviceImpl.comment;

import com.example.collect.dao.comment.CommentMapper;
import com.example.collect.po.comment.Comment;
import com.example.collect.service.comment.CommentService;
import com.example.collect.serviceImpl.async.AsyncTask;
import com.example.collect.util.Constant;
import com.example.collect.vo.ResultVO;
import com.example.collect.vo.comment.CommentVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 评论相关service方法实现
 */

@Service
public class CommentServiceImpl implements CommentService {

    @Resource
    private CommentMapper commentMapper;

    @Autowired
    private AsyncTask asyncTask;

    /**
     * 创建评论
     */
    @CacheEvict(value = "commentList", key = "#commentVO.reportId")
    @Override
    public ResultVO<CommentVO> createComment(CommentVO commentVO) {
        commentMapper.insert(new Comment(commentVO));
        // 更新用户评分
        asyncTask.updateScore(commentVO.getUid());
        return new ResultVO<>(Constant.REQUEST_SUCCESS, "成功发表评论");
    }

    /**
     * 获取评论列表
     */
    @Cacheable(value = "commentList", key = "#reportId")
    @Override
    public List<CommentVO> getCommentList(Integer reportId) {
        List<Comment> comments = commentMapper.selectCommentsByReportId(reportId);
        List<CommentVO> res = new ArrayList<>();
        for(Comment comment: comments){
            res.add(new CommentVO(comment));
        }
        return res;
    }
}
