package com.example.collect.service.user;

import com.example.collect.vo.ResultVO;
import com.example.collect.vo.score.ScoreVO;
import com.example.collect.vo.user.UserInfoVO;
import com.example.collect.vo.user.UserVO;

import java.util.List;

/**
 *  用户相关service方法
 */

public interface UserService {
    // 用户注册
    ResultVO<UserVO> userRegister(UserVO userVO);

    // 用户登录
    ResultVO<UserVO> userLogin(String phone, String password);

    // 获取用户信息
    UserVO getUserInfo(Integer uid);

    // 更新用户信息
    ResultVO<UserVO> updateUserInfo(UserVO userVO);

    // 获取用户活跃度
    String getUserActivity(Integer uid);

    // 获取评分
    List<Integer> getScore(Integer uid);

    // 获取评分排行榜
    List<ScoreVO> getScoreList();
}
