package com.example.collect.controller.comment;

import com.example.collect.serviceImpl.comment.CommentServiceImpl;
import com.example.collect.vo.ResultVO;
import com.example.collect.vo.comment.CommentVO;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 评论相关功能controller层接口
 */

@RestController
@RequestMapping("/api/comment")
public class CommentController {

    @Resource
    private CommentServiceImpl commentService;

    /**
     * 创建评论
     */
    @PostMapping("/create")
    public ResultVO<CommentVO> createComment(@RequestBody CommentVO commentVO){
        System.out.println(commentVO.toString());
        return commentService.createComment(commentVO);
    }

    /**
     * 获取评论列表
     */
    @GetMapping("/getComment/{reportId}")
    public List<CommentVO> getCommentList(@PathVariable Integer reportId){
        return commentService.getCommentList(reportId);
    }
}
