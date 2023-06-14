package com.example.collect.service.task;

import com.example.collect.vo.ResultVO;
import com.example.collect.vo.task.TaskVO;

import java.text.ParseException;
import java.util.List;

/**
 *  测试任务相关service方法
 */

public interface TaskService {
    // 创建测试任务
    ResultVO<Integer> createTask(TaskVO taskVO) throws ParseException;

    // 删除测试任务
    ResultVO<TaskVO> removeTask(Integer taskId);

    // 获取所有测试任务
    List<TaskVO> getAll();

    // 获取正在招募的测试任务
    List<TaskVO> getUnfinishedTasks() throws ParseException;

    // 获取某一用户发布或者选取的测试任务
    List<TaskVO> getTasksByUid(Integer uid);

    // 获取某一测试的信息
    TaskVO getTaskInfo(Integer taskId);

    // 获取推荐任务
    List<TaskVO> getRecommendTasks(Integer uid);

    // 设置任务推荐规则
    ResultVO<TaskVO> setRecommendRule(String rule);
}
