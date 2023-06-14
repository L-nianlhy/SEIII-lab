package com.example.collect.serviceImpl.task;

import com.example.collect.dao.order.OrderMapper;
import com.example.collect.dao.task.TaskMapper;
import com.example.collect.dao.user.UserMapper;
import com.example.collect.enums.RecommendRule;
import com.example.collect.enums.UserRole;
import com.example.collect.po.order.Order;
import com.example.collect.po.task.Task;
import com.example.collect.po.user.User;
import com.example.collect.service.file.FileService;
import com.example.collect.service.task.TaskService;
import com.example.collect.util.Constant;
import com.example.collect.util.DateUtil;
import com.example.collect.vo.ResultVO;
import com.example.collect.vo.task.TaskVO;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 *  测试任务相关service方法实现
 */

@Service
public class TaskServiceImpl implements TaskService {
    @Resource
    private TaskMapper taskMapper;

    @Resource
    private FileService fileService;

    @Resource
    private OrderMapper orderMapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    private DateUtil dateUtil;

    private RecommendRule rule = RecommendRule.preference;

    /**
     * 创建测试任务(不含文件上传)
     */
    @Override
    public ResultVO<Integer> createTask(TaskVO taskVO){
        Task task = taskMapper.selectTaskId(taskVO.getTaskName());
        if(task != null) {
            return new ResultVO<>(Constant.REQUEST_FAIL, "测试名重复");
        }
        taskMapper.insert(new Task(taskVO));
        return new ResultVO<>(Constant.REQUEST_SUCCESS, "成功创建测试任务", taskMapper.selectTaskId(taskVO.getTaskName()).getTaskId());
    }

    /**
     * 删除测试任务
     */
    @Override
    public ResultVO<TaskVO> removeTask(Integer taskId) {
        Task task = taskMapper.selectByPrimaryKey(taskId);
        if(task == null){
            return new ResultVO<>(Constant.REQUEST_FAIL, "测试任务不存在");
        }
        // 删除测试App
        fileService.removeFile(task.getTaskApp());
        // 删除测试记录
        fileService.removeFile(task.getTaskDoc());
        // 删除order记录
        if(task.getNumOfWorker() != 0) orderMapper.deleteTaskOrder(taskId);

        taskMapper.deleteByPrimaryKey(taskId);
        return new ResultVO<>(Constant.REQUEST_SUCCESS, "成功删除测试任务");
    }

    /**
     * 获取所有测试任务
     */
    @Cacheable("allTasks")
    @Override
    public List<TaskVO> getAll() {
        List<Task> tasks = taskMapper.selectAll();
        return taskToTaskVO(tasks);
    }

    /**
     * 获取正在招募的任务
     */
    @Cacheable("unfinishedTasks")
    @Override
    public List<TaskVO> getUnfinishedTasks() throws ParseException {
        List<Task> tasks = taskMapper.selectAll();
        List<Task> temp = new ArrayList<>();
        for(Task task: tasks){
            if(task.getNumOfWorker()<task.getNumOfNeedWorker()
                    && !dateUtil.isOverDue(task.getTaskTime()))
                temp.add(task);
        }
        return taskToTaskVO(temp);
    }

    /**
     * 获取某一用户发布或者选取的测试任务
     */
    @Cacheable(value = "tasks", key = "#uid")
    @Override
    public List<TaskVO> getTasksByUid(Integer uid) {
        User user = userMapper.selectByPrimaryKey(uid);
        if(UserRole.valueOf(user.getUserRole()) == UserRole.employer){
            // 发包方，返回发包方发布的所有测试
            List<Task> tasks = taskMapper.selectAllReleasedTasks(uid);
            return taskToTaskVO(tasks);
        }
        else if(UserRole.valueOf(user.getUserRole()) == UserRole.worker){
            // 众包工人，返回选择的所有测试
            List<Order> orders = orderMapper.selectAllByUid(uid);
            List<TaskVO> res = new ArrayList<>();
            for(Order order: orders){
                TaskVO taskVO = new TaskVO(taskMapper.selectByPrimaryKey(order.getTaskId()));
                // 设置app的下载url
                taskVO.setTaskApp(fileService.download(taskVO.getTaskApp()));
                // 设置doc的下载url
                taskVO.setTaskDoc(fileService.download(taskVO.getTaskDoc()));
                taskVO.setTaskType(taskVO.getTaskType());
                res.add(taskVO);
            }
            return res;
        }
        return null;
    }

    /**
     * 获取某一测试的信息
     */
    @Cacheable(value = "taskInfo", key = "#taskId")
    @Override
    public TaskVO getTaskInfo(Integer taskId) {
        TaskVO taskVO = new TaskVO(taskMapper.selectByPrimaryKey(taskId));
        if(!taskVO.getTaskApp().equals(Constant.FILE_NOT_UPLOAD)) {
            // 设置app的下载url
            taskVO.setTaskApp(fileService.download(taskVO.getTaskApp()));
            // 设置doc的下载url
            taskVO.setTaskDoc(fileService.download(taskVO.getTaskDoc()));
            taskVO.setTaskType(taskVO.getTaskType());
            return taskVO;
        }
        return null;
    }

    /**
     * 获取推荐任务
     */
    @Override
    public List<TaskVO> getRecommendTasks(Integer uid) {
        List<Task> tasks;
        User user = userMapper.selectByPrimaryKey(uid);
        String value;
        if(rule == RecommendRule.ability){
            value = user.getAbility();
            tasks = taskMapper.selectTasksByAbility(value);
        }
        else if(rule == RecommendRule.preference){
            value = user.getPreference();
            tasks = taskMapper.selectTaskByPreference(value);
        }
        else{
            value = user.getDevice();
            tasks = taskMapper.selectTasksByDevice(value);
        }
        return taskToTaskVO(tasks);
    }

    /**
     * 设置推荐规则
     */
    @Override
    public ResultVO<TaskVO> setRecommendRule(String rule) {
        this.rule = RecommendRule.valueOf(rule);
        return new ResultVO<>(Constant.REQUEST_SUCCESS, "成功设置任务推荐规则");
    }

    /**
     *TaskList转换成TaskVOList
     */
    private List<TaskVO> taskToTaskVO(List<Task> tasks){
        List<TaskVO> res = new ArrayList<>();
        for(Task task: tasks){
            TaskVO taskVO = new TaskVO(task);
            if(!taskVO.getTaskApp().equals(Constant.FILE_NOT_UPLOAD)){
                // 设置app的下载url
                taskVO.setTaskApp(fileService.download(task.getTaskApp()));
                // 设置doc的下载url
                taskVO.setTaskDoc(fileService.download(task.getTaskDoc()));
                taskVO.setTaskType(task.getTaskType());
                res.add(taskVO);
            }
        }
        return res;
    }
}
