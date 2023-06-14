package com.example.collect.serviceImpl.async;

import com.example.collect.dao.score.ScoreMapper;
import com.example.collect.dao.user.UserMapper;
import com.example.collect.po.score.Score;
import com.example.collect.po.user.User;
import com.example.collect.serviceImpl.score.ScoreServiceImpl;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 异步操作
 */

@Component
public class AsyncTask {

    @Resource
    private ScoreMapper scoreMapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    private ScoreServiceImpl scoreService;


    /**
     * 异步创建用户评分字段
     */
    @Async
    public void createUserScore(String phone){
        User user = userMapper.selectByPhone(phone);
        try{
            scoreMapper.insert(new Score(user.getUid(), 0));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 异步更新用户评分
     */
    @CacheEvict(value = "score", key = "#uid")
    @Async
    public void updateScore(Integer uid){
        int totalScore = scoreService.getUserTotalScore(uid);
        try{
            scoreMapper.updateScore(uid, totalScore);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
