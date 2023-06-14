package com.example.collect.dao.task;

import com.example.collect.po.task.Task;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TaskMapper {
    int deleteByPrimaryKey(Integer taskId);

    int insert(Task record);

    int insertSelective(Task record);

    Task selectByPrimaryKey(Integer taskId);

    int updateByPrimaryKeySelective(Task record);

    int updateByPrimaryKeyWithBLOBs(Task record);

    int updateByPrimaryKey(Task record);

    // 根据taskId获取对应测试App存储的objectName
    String selectTaskApp(Integer taskId);

    // 根据taskId获取对应测试文档存储的objectName
    String selectTaskDoc(Integer taskId);

    // 获取当前测试任务众包工人数量
    Integer selectNumOfWorker(Integer taskId);

    // 获取测试任务所需众包工人数量
    Integer selectNumOfNeedWorker(Integer taskId);

    // 添加测试任务众包工人数量
    int updateNumOfWorker(@Param("taskId") Integer taskId, @Param("numOfWorker") Integer numOfWorker);

    // 获取测试任务截止日期
    String selectTaskTime(Integer taskId);

    // 获取所有测试任务
    List<Task> selectAll();

    // 获取发包方发布的所有测试任务
    List<Task> selectAllReleasedTasks(@Param("uid") Integer uid);

    // 更新测试app
    int updateAppByPrimaryKey(@Param(("taskId")) Integer taskId, @Param("taskApp") String taskApp);

    // 更新测试doc
    int updateDocByPrimaryKey(@Param("taskId") Integer taskId, @Param("taskDoc") String taskDoc);

    // 获取taskId
    Task selectTaskId(String taskName);

    // 根据ability获取任务
    List<Task> selectTasksByAbility(String value);

    // 根据preference获取任务
    List<Task> selectTaskByPreference(String value);

    // 根据device获取任务
    List<Task> selectTasksByDevice(String value);

}