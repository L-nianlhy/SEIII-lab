package com.example.collect.controller.task;

import com.example.collect.serviceImpl.task.TaskServiceImpl;
import com.example.collect.util.Constant;
import com.example.collect.vo.ResultVO;
import com.example.collect.vo.task.TaskFormVO;
import com.example.collect.vo.task.TaskVO;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.List;

/**
 *  测试任务功能相关controller层接口
 */

@RestController
@RequestMapping("/api/task")
public class TaskController {

    @Resource
    private TaskServiceImpl taskService;

    /**
     * 创建测试任务
     */
    @PostMapping("/create")
    public ResultVO<Integer> createTestTask(@RequestBody TaskFormVO taskFormVO){
        return taskService.createTask(taskRequestParse(taskFormVO));
    }

    /**
     * 删除测试任务
     */
    @PostMapping("/remove")
    public ResultVO<TaskVO> removeTestTask(@RequestBody Integer taskId){
        return taskService.removeTask(taskId);
    }

    /**
     * 获取所有测试任务(管理员)
     */
    @GetMapping("/getAll")
    public List<TaskVO> getAllTasks(){
        return taskService.getAll();
    }

    /**
     * 获取正在招募的测试任务
     */
    @GetMapping("/getUnfinished")
    public List<TaskVO> getUnfinishedTasks() throws ParseException {
        return taskService.getUnfinishedTasks();
    }

    /**
     * 获取某一用户发布或者选取的测试任务
     */
    @GetMapping("/uid/{uid}")
    public List<TaskVO> getTaskList(@PathVariable Integer uid){
        return taskService.getTasksByUid(uid);
    }

    /**
     * 获取某一测试任务信息
     */
    @GetMapping("/taskId/{taskId}")
    public TaskVO getTaskInfo(@PathVariable Integer taskId){
        return taskService.getTaskInfo(taskId);
    }

    /**
     * 获取推荐任务
     */
    @GetMapping("/recommend/{uid}")
    public List<TaskVO> getRecommendTasks(@PathVariable Integer uid){
        return taskService.getRecommendTasks(uid);
    }

    /**
     * 设置推荐规则
     */
    @PostMapping("/rule/{rule}")
    public ResultVO<TaskVO> setRecommendRule(@PathVariable String rule){
        return taskService.setRecommendRule(rule);
    }

    /**
     * 处理task httpRequest
     */
    private TaskVO taskRequestParse(TaskFormVO taskFormVO){
        Integer uid = taskFormVO.getUid();
        String taskName = taskFormVO.getTaskName();
        String taskIntro = taskFormVO.getTaskIntro();
        String taskTime = taskFormVO.getTaskTime();
        String taskType = taskFormVO.getTaskType();
        Integer numOfWorker = taskFormVO.getNumOfWorker();
        Integer numOfNeedWorker = taskFormVO.getNumOfNeedWorker();
        String taskDifficulty = taskFormVO.getTaskDifficulty();
        String taskDevice = taskFormVO.getTaskDevice();
        // 设置app默认值
        String taskApp = Constant.FILE_NOT_UPLOAD;
        // 设置doc默认值
        String taskDoc = Constant.FILE_NOT_UPLOAD;
        return new TaskVO(uid, taskName, taskIntro, taskTime, taskType, numOfWorker, numOfNeedWorker,
                taskDifficulty, taskDevice,taskApp, taskDoc);
    }
}
