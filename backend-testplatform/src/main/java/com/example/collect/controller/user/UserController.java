package com.example.collect.controller.user;

import com.example.collect.serviceImpl.order.OrderServiceImpl;
import com.example.collect.serviceImpl.user.UserServiceImpl;
import com.example.collect.util.Constant;
import com.example.collect.vo.ResultVO;
import com.example.collect.vo.order.OrderVO;
import com.example.collect.vo.score.ScoreVO;
import com.example.collect.vo.task.TaskFormVO;
import com.example.collect.vo.task.TaskVO;
import com.example.collect.vo.user.UserChooseVO;
import com.example.collect.vo.user.UserFormVO;
import com.example.collect.vo.user.UserInfoVO;
import com.example.collect.vo.user.UserVO;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.List;


/**
 *  用户功能相关controller层接口
 */

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Resource
    private UserServiceImpl userService;

    @Resource
    private OrderServiceImpl orderService;

    /**
     * 用户注册
     */
    @PostMapping("/register")
    public ResultVO<UserVO> register(@RequestBody UserInfoVO userInfoVO){
        return userService.userRegister(userRequestParse(userInfoVO));
    }

    /**
     * 用户登录
     */
    @PostMapping("/login")
    public ResultVO<UserVO> login(@RequestBody UserFormVO userFormVO){
        System.out.println("123");
        String phone = userFormVO.getPhone();
        String password = userFormVO.getPassword();
        return userService.userLogin(phone, password);
    }

    /**
     * 获取用户信息
     */
    @GetMapping("/{uid}")
    public UserVO getUserInfo(@PathVariable Integer uid){
        return userService.getUserInfo(uid);
    }

    /**
     * 工人选择某项测试
     */
    @PostMapping("/choose")
    public ResultVO<OrderVO> chooseTask(@RequestBody UserChooseVO userChooseVO) throws ParseException {
        return orderService.chooseTask(userChooseVO.getUid(), userChooseVO.getTaskId());
    }

    /**
     * 更新用户信息
     */
    @PostMapping("/update/{uid}")
    public ResultVO<UserVO> updateUserInfo(@PathVariable Integer uid, @RequestBody UserVO userVO){
        userVO.setUid(uid);
        return userService.updateUserInfo(userVO);
    }

    /**
     * 获取用户活跃度
     */
    @GetMapping("/active/{uid}")
    public String getUserActivity(@PathVariable Integer uid){
        return userService.getUserActivity(uid);
    }

    /**
     * 获取评分
     */
    @GetMapping("/getScore/{uid}")
    public List<Integer> getUserScore(@PathVariable Integer uid){
        return userService.getScore(uid);
    }

    /**
     * 获取评分排行榜
     */
    @GetMapping("/getScoreList")
    public List<ScoreVO> getScoreList(){
        return userService.getScoreList();
    }

    /**
     * 处理user register httpRequest
     */
    private UserVO userRequestParse(UserInfoVO userInfoVO){
        String uname = userInfoVO.getUname();
        String phone = userInfoVO.getPhone();
        String password = userInfoVO.getPassword();
        String userRole = userInfoVO.getUserRole();
        String ability = userInfoVO.getAbility() != null ? userInfoVO.getAbility() : Constant.DEFAULT_VALUE;
        String preference = userInfoVO.getPreference() != null ? userInfoVO.getPreference() : Constant.DEFAULT_VALUE;
        String device = userInfoVO.getDevice() != null ? userInfoVO.getDevice() : Constant.DEFAULT_VALUE;
        String intro = userInfoVO.getIntro();
        return new UserVO(uname,password,userRole,phone,ability,preference,device,intro);
    }
}
