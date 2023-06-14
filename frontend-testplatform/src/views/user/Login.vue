<template>
  <!--  登录界面-->
  <div class="login_container">
    <div class="login_box">
      <div class="avatar_box">
        <img src="../../../static/photos/register.jpg" alt/>
      </div>
      <!-- 表单区域 -->
      <el-form ref="loginFormRef" :rules="loginRules" :model="loginForm" class="login_form" label-width="0">
        <!-- 手机号 -->
        <el-form-item prop="phone">
          <el-input v-model="loginForm.phone" prefix-icon="el-icon-user"></el-input>
        </el-form-item>
        <!-- 密码 -->
        <el-form-item prop="password">
          <el-input v-model="loginForm.password" prefix-icon="iconfont icon-mima" type="password"></el-input>
        </el-form-item>
        <!-- 按钮 -->
        <el-form-item class="btns">
          <el-button type="primary" @click="handleLogin">登录</el-button>
          <el-button type="info" @click="jumpRegister">注册</el-button>
        </el-form-item>
      </el-form>
    </div>

  </div>
</template>
<script>
import {judgeAdmin, judgeEmployer, judgeWorker} from "../../util/auth";
import { login } from "../../api/user";
export default {
  data(){
    return{
      //表单数据
      loginForm:{
        phone:"",
        password:""
      },
      //验证对象
      loginRules:{
        //校验手机号
        phone:[
          {required:true,message:"请输入注册手机号",trigger:"blur"},
          {min:1,max:11,message: "长度为11",trigger:"blur"}//必填项验证
        ],
        //校验密码
        password:[
          {required:true,message:"请输入用户密码",trigger:"blur"},//必填项验证
          {min:1,max:15,message:"长度在6~15个字符",trigger:"blur"}
        ],
      },
    };
  },
  methods:{
    jumpRegister(){
      //console.log("跳往注册页面");
      this.$router.push("/register");
    },
    handleLogin(){
      if(this.loginForm.phone === ""){
        this.$message.error("请输入注册手机号");
        return;
      }
      if(this.loginForm.password === ""){
        this.$message.error("请输入密码");
        return;
      }
      login({phone:this.loginForm.phone,password:this.loginForm.password}).then(res=>{
        if(res.code===1){
          this.$message.success("登录成功");
          window.localStorage.setItem("uid",res.data.uid);
          window.localStorage.setItem("userRole",res.data.userRole);
          if(judgeEmployer()){
            setTimeout(()=>{
              this.$router.push("/employer");
            },800);
          }
          if(judgeWorker()){
            setTimeout(()=>{
              this.$router.push("/worker");
            },800);
          }
          if(judgeAdmin()){
            setTimeout(()=>{
              this.$router.push("/admin");
            },800)
          }
        }
        else {
          this.$message.error("登录失败：" + res.msg);
        }
      }).catch(res=>{
        this.$message.error("登陆失败："+res);
      })
    },
  },
}
</script>
<style lang="less" scoped>
//根节点样式
.login_container{
  background-color: #2b4b6b;
  height: 100%;
}
.login_box{
  width: 450px;
  height: 300px;
  background-color: #fff;
  border-radius: 3px;
  position: absolute;
  left: 50%;
  top:50%;
  transform: translate(-50%,-50%);
  .avatar_box{
    width: 130px;
    height: 130px;
    border: 1px solid #eee;
    border-radius: 50%;
    padding: 3px;
    box-shadow: 0 0 0 #ddd;
    position: absolute;
    left: 50%;
    transform: translate(-50%,-50%);
    background-color: #0ee;
    img{
      width: 100%;
      height: 100%;
      border-radius: 50%;
      background-color: #eee;
    }
  }
}
.btns{
  display: flex;
  justify-content: flex-end;
}
.login_form{
  position: absolute;
  bottom: 0;
  width: 100%;
  padding: 0 10px;
  box-sizing: border-box;
}
</style>
