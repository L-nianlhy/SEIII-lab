package com.example.collect.comment;

import com.example.collect.MockObject;
import com.example.collect.dao.comment.CommentMapper;
import com.example.collect.dao.report.ReportMapper;
import com.example.collect.dao.task.TaskMapper;
import com.example.collect.dao.user.UserMapper;
import com.example.collect.service.comment.CommentService;
import com.example.collect.serviceImpl.report.ReportServiceImpl;
import com.example.collect.serviceImpl.task.TaskServiceImpl;
import com.example.collect.serviceImpl.user.UserServiceImpl;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import com.example.collect.vo.ResultVO;
import javax.annotation.Resource;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest
public class CommentServiceImplTest {
    @Autowired
    private CommentService commentService;

    @Resource
    CommentMapper commentMapper;

    @Autowired
    private TaskServiceImpl taskServiceImpl;

    @Resource
    TaskMapper taskMapper;

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Resource
    UserMapper userMapper;

    @Autowired
    private ReportServiceImpl reportServiceImpl;

    @Resource
    ReportMapper reportMapper;

    private MockObject mock = new MockObject();

    /**
     * 创建评论
     */
    //成功流程
    @Test
    public void createComment() {
        ResultVO<Integer> res = reportServiceImpl.createReport(mock.MockReportVO1());
        Assertions.assertEquals("成功发表评论",commentService.createComment(mock.MockCommentVO(res.getData())).getMsg());
        commentMapper.deleteByPrimaryKey(commentMapper.selectCommentsByReportId(res.getData()).get(0).getCommentId());
        reportMapper.deleteByPrimaryKey(res.getData());
    }

    /**
     * 获取评论列表
     */
    @Test
    public void getCommentList() {
        ResultVO<Integer> res = reportServiceImpl.createReport(mock.MockReportVO1());
        for (int i = 0; i <5;i++){
            commentService.createComment(mock.MockCommentVO(res.getData()));
        }
        Assertions.assertEquals(5,commentService.getCommentList(res.getData()).size());
        for (int i = 0; i <5;i++){
            commentMapper.deleteByPrimaryKey(commentMapper.selectCommentsByReportId(res.getData()).get(0).getCommentId());
        }
        reportMapper.deleteByPrimaryKey(res.getData());

    }
}