package com.example.collect.vo.user;

import com.example.collect.po.user.User;
import com.example.collect.enums.UserRole;

public class UserVO {
    private Integer uid;

    private String uname;

    private String password;

    private UserRole userRole;

    private String phone;

    private String ability;

    private String preference;

    private String device;

    private String intro;

    public UserVO(){}

    public UserVO(String uname, String password, String userRole, String phone,
                  String ability, String preference, String device, String intro){
        this.uname = uname;
        this.password = password;
        this.userRole = UserRole.valueOf(userRole);
        this.phone = phone;
        this.ability = ability;
        this.preference = preference;
        this.device = device;
        this.intro = intro;
    }

    public UserVO(User user){
        this.uid = user.getUid();
        this.uname = user.getUname();
        this.password = user.getPassword();
        this.userRole = UserRole.valueOf(user.getUserRole());
        this.phone = user.getPhone();
        this.ability = user.getAbility();
        this.preference = user.getPreference();
        this.device = user.getDevice();
        this.intro = user.getIntro();
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

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole == null ? null : UserRole.valueOf(userRole.trim());
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
        this.preference = preference == null ? null : preference.trim();
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
