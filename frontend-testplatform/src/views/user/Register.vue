<template>
    <!-- 注册页面 -->
    <div class="register_container">
        <div class="register_box">
          <el-form ref="registerFormRef" :rules="registerRules" :model="registerForm"  class="register_form" label-width="100px" >
            <!-- 用户名 -->
            <el-form-item label="用户名" prop="uname">
              <el-input v-model="registerForm.uname"></el-input>
            </el-form-item>
            <!-- 密码 -->
            <el-form-item label="密码" prop="password">
              <el-input v-model="registerForm.password" type="password"></el-input>
            </el-form-item>
            <!-- 再次输入密码 -->
            <el-form-item label="再次输入" prop="pwd">
              <el-input v-model="registerForm.pwd" type="password"></el-input>
            </el-form-item>
            <!-- 邮箱 -->
            <el-form-item label="手机号" prop="phone">
              <el-input v-model="registerForm.phone"></el-input>
            </el-form-item>
            <el-form-item label="请选择身份" prop="userRole">
              <el-radio-group v-model="registerForm.userRole">
                <el-radio label="众包工人" @change="workerClick()"></el-radio>
                <el-radio label="发包方"  @change="bossClick()"></el-radio>
              </el-radio-group>
            </el-form-item>
            <!--专业能力-->
            <el-form-item label="专业能力" class="none" >
              <el-select v-model="registerForm.ability" placeholder="编程能力">
                <el-option label="业余" value="amateur"></el-option>
                <el-option label="普通" value="normal"></el-option>
                <el-option label="专业" value="professional"></el-option>
              </el-select>
            </el-form-item>
            <!--任务偏好-->
            <el-form-item class="none" label="擅长测试方向" >
              <el-select v-model="registerForm.preference" placeholder="任务偏好">
                <el-option label="性能测试" value="性能测试"></el-option>
                <el-option label="功能测试" value="功能测试"></el-option>
              </el-select>
            </el-form-item>
            <!--测试设备-->
            <el-form-item label="测试设备" class="none" >
              <el-select v-model="registerForm.device" placeholder="常用测试设备">
                <el-option label="Linux" value="Linux"></el-option>
                <el-option label="Android" value="Android"></el-option>
                <el-option label="Windows" value="Windows"></el-option>
                <el-option label="IOS" value="IOS"></el-option>
              </el-select>
            </el-form-item>
            <!-- 按钮 -->
            <el-form-item class="btns">
              <el-button type="primary" @click="submit">注册</el-button>
              <el-button type="info" @click="jumpLogin">返回登录</el-button>
            </el-form-item>
          </el-form>
          <!-- 表单区域 -->
            <div class="avatar_box">
                <img src="../../../static/photos/register.jpg" alt="">
            </div>
        </div>
    </div>
</template>
<script>
import {register} from "../../api/user";

export default{
    data(){
        return{
            //表单数据
            registerForm:{
                uname:"",
                password:"",
                pwd:"",
                phone:"",
                userRole:"",
                ability:"",
              preference:"",
              device:"",
            },
            registerRules:{
                uname:[
                    {required:true,message:"请输入用户名",trigger:"blur"},
                    {min:1,max:12,message:"长度在5~12个字符",trigger:'blur'}
                ],
                password:[
                    {required:true,message:"请输入密码",trigger:"blur"},
                    {min:1,max:15,message:"长度在6~15个字符",trigger:'blur'}
                ],
                pwd:[
                    {required:true,message:"请输入密码",trigger:"blur"},
                    {min:1,max:15,message:"长度在6~15个字符",trigger:'blur'}
                ],
                phone:[
                    {required:true,message:"请输入手机号",trigger:"blur"},
                    {min:1,max:11,message:"号码格式错误",trigger:'blur'}
                ],
                userRole:[
                    {required:true,message:'请选择身份',trigger:'blur'}
                ],
            }
        }
    },
    methods:{
      workerClick(){
        const block = document.querySelectorAll(".none");
        for(let i=0;i<block.length;i++){
            block[i].className= "block";
        }
        const form = document.querySelector(".register_box");
        form.style.height = "75%";
      },
      bossClick(){
        const block = document.querySelectorAll(".block");
        for(let i=0;i<block.length;i++){
          block[i].className= "none";
        }
        const form = document.querySelector(".register_box")
        this.registerForm.ability=null;
        this.registerForm.device=null;
        this.registerForm.preference=null;
        form.style.height = "50%";
      },
        submit(){
          if(this.registerForm.uname === ""){
            this.$message.error("请输入用户名");
            return;
          }
          if(this.registerForm.password === ""){
            this.$message.error("请输入密码");
            return;
          }
          if(this.registerForm.pwd === ""){
            this.$message.error("请再次输入密码");
            return;
          }
          if(this.registerForm.phone === ""){
            this.$message.error("请输入手机号");
            return;
          }
          if(this.registerForm.userRole === ""){
            this.$message.error("请选择身份");
            return;
          }
          if(this.registerForm.password !== this.registerForm.pwd){
            this.$message.error("两次密码输入不一致");
            return;
          }
          console.log(this.registerForm.preference)
          register({
            uname:this.registerForm.uname,
            phone:this.registerForm.phone,
            password:this.registerForm.password,
            userRole:(this.registerForm.userRole === "众包工人")?"worker" : "employer",
            ability:this.registerForm.ability,
            preference:this.registerForm.preference,
            device:this.registerForm.device,
            intro: "该用户暂未设置个性签名"
          }).then(res =>{
            if(res.code === 1){
              this.$message.success("注册成功");
              setTimeout(()=>{
                this.$router.push("/login");
              })
            } else {
              this.$message.error("注册失败："+res.msg);
            }
          }).catch(res=>{
            this.$message.error("注册失败："+res);
          })
        },
        jumpLogin(){
            console.log('返回登录');
            this.$router.push('/login');
        }
    },
}
</script>
<style lang="less" scoped>
.block{
  margin: 6% 0;
  display: block;
}
.none{
  display: none;
}
.register_container{
    background-color: #2b4b6b;
    height: 100%;
}
.register_box{
    width: 600px;
    height: 50%;
    background-color: #fff;
    position: absolute;
    left:50%;
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
.register_form{
    position:absolute;
    top:15%;
    width: 100%;
    padding: 0  40px;
    box-sizing: border-box;
}
</style>
