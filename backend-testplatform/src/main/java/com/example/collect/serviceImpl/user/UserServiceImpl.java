package com.example.collect.serviceImpl.user;

import com.example.collect.dao.comment.CommentMapper;
import com.example.collect.dao.report.ReportMapper;
import com.example.collect.dao.score.ScoreMapper;
import com.example.collect.dao.user.UserMapper;
import com.example.collect.po.score.Score;
import com.example.collect.po.user.User;
import com.example.collect.service.user.UserService;
import com.example.collect.serviceImpl.async.AsyncTask;
import com.example.collect.serviceImpl.score.ScoreServiceImpl;
import com.example.collect.util.Constant;
import com.example.collect.vo.ResultVO;
import com.example.collect.vo.score.ScoreVO;
import com.example.collect.vo.user.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 *  用户相关service方法实现
 */

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private ReportMapper reportMapper;

    @Resource
    private ScoreMapper scoreMapper;

    @Resource
    private ScoreServiceImpl scoreService;

    @Autowired
    private AsyncTask asyncTask;

    /**
     * 用户注册
     */
    @Override
    public ResultVO<UserVO> userRegister(UserVO userVO) {
        String phone = userVO.getPhone();
        String password = userVO.getPassword();
        String uname = userVO.getUname();
        if(userMapper.selectByPhone(phone) == null){
            if(StringUtils.hasText(uname) && StringUtils.hasText(password)){
                userMapper.insert(new User(userVO));
                // 添加用户评分
                asyncTask.createUserScore(phone);
                return new ResultVO<>(Constant.REQUEST_SUCCESS, "用户注册成功");
            }
            return new ResultVO<>(Constant.REQUEST_FAIL, "用户名或手机号码不得为空");
        }
        return new ResultVO<>(Constant.REQUEST_FAIL, "该手机号码已注册过账号");
    }

    /**
     * 用户登录
     */
    @Override
    public ResultVO<UserVO> userLogin(String phone, String password) {
        User user = userMapper.selectByPhone(phone);
        if(user == null){
            return new ResultVO<>(Constant.REQUEST_FAIL, "手机号码尚未注册账号");
        }
        else{
            if(!user.getPassword().equals(password)){
                return new ResultVO<>(Constant.REQUEST_FAIL, "账号或密码错误");
            }
        }
        return new ResultVO<>(Constant.REQUEST_SUCCESS, "登陆成功", new UserVO(user));
    }

    /**
     * 获取用户信息
     */
    @Cacheable(value = "userInfo", key = "#uid")
    @Override
    public UserVO getUserInfo(Integer uid) {
        User user = userMapper.selectByPrimaryKey(uid);
        if(user == null){
            return new UserVO();
        }
        else{
            return new UserVO(user);
        }
    }

    /**
     * 更新用户信息
     */
    @CacheEvict(value = "userInfo", key = "#userVO.uid")
    @Override
    public ResultVO<UserVO> updateUserInfo(UserVO userVO) {
        if(userMapper.selectByPrimaryKey(userVO.getUid()) == null){
            return new ResultVO<>(Constant.REQUEST_FAIL, "该用户不存在");
        }
        userMapper.updateByPrimaryKey(new User(userVO));
        return new ResultVO<>(Constant.REQUEST_SUCCESS, "更新用户信息成功");
    }

    /**
     * 获取用户活跃度
     */
    @CachePut(value = "userActivity", key = "#uid")
    @Override
    public String getUserActivity(Integer uid) {
        int level = getActivityLevel(uid);
        return Constant.LEVEL[level];
    }

    /**
     * 获取评分
     */
    @Cacheable(value = "score", key = "#uid")
    @Override
    public List<Integer> getScore(Integer uid) {
        List<Integer> scores = scoreService.getUserScores(uid);
        // 更新用户评分
        asyncTask.updateScore(uid);
        return scores;
    }

    /**
     * 获取评分排行榜
     */
    @Override
    public List<ScoreVO> getScoreList() {
        List<Score> scores = scoreMapper.selectScoreList();
        List<ScoreVO> result = new ArrayList<>();
        for(Score score: scores){
            User user = userMapper.selectByPrimaryKey(score.getUid());
            result.add(new ScoreVO(user, score.getTotalScore()));
        }
        return result;
    }

    /**
     * 获取用户活跃度等级
     */
    private Integer getActivityLevel(Integer uid){
        int num = reportMapper.selectNumOfReports(uid);
        int level = 0;
        while(level < Constant.activity.length - 1){
            if(num < Constant.activity[level + 1]) break;
            level++;
        }
        return level;
    }

}
