package com.example.collect.po.user;

import com.example.collect.vo.user.UserVO;

public class User {
    // 用户id，user表主键
    private Integer uid;

    // 用户名
    private String uname;

    // 密码
    private String password;

    // 用户身份
    private String userRole;

    // 电话号码
    private String phone;

    // 专业能力
    private String ability;

    // 任务偏好
    private String preference;

    // 测试设备
    private String device;

    // 个人签名
    private String intro;

    public User(){}

    public User(UserVO userVO){
        this.uid = userVO.getUid();
        this.uname = userVO.getUname();
        this.password = userVO.getPassword();
        this.userRole = userVO.getUserRole().name();
        this.phone = userVO.getPhone();
        this.ability = userVO.getAbility();
        this.preference = userVO.getPreference();
        this.device = userVO.getDevice();
        this.intro = userVO.getIntro();
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname == null ? null : uname.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole == null ? null : userRole.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getAbility() {
        return ability;
    }

    public void setAbility(String ability) {
        this.ability = ability;
    }

    public String getPreference() {
        return preference;
    }

    public void setPreference(String preference) {
        this.preference = preference;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }
}