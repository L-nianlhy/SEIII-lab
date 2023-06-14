<template>
  <div class = "adminLayout-container">
    <div>
      <i class = "el-icon-s-home"></i>
      <div class = "admin-msg">COLLECT ADMIN</div>
      <el-dropdown @command="handleCommand" style="float: right;margin-top: 15px;margin-right: 10px">
        <span class="el-dropdown-link">
          <i class="el-icon-more"></i>
        </span>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item command="changeRule">修改任务推荐规则</el-dropdown-item>
          <el-dropdown-item command="loginOut">登出</el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
      <el-dialog title="修改任务推荐规则" :visible.sync="dialogVisible" width="40%">
        <el-form ref="form" :model="rule" label-width="80px">
          <el-form-item label="推荐规则">
            <el-select v-model="rule" placeholder="推荐规则">
              <el-option label="专业能力与任务难度匹配" value="ability"></el-option>
              <el-option label="任务偏好与测试类型匹配" value="preference"></el-option>
              <el-option label="测试设备与测试设备需求匹配" value="device"></el-option>
            </el-select>
          </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer">
    <el-button @click="dialogVisible = false">取 消</el-button>
    <el-button id = "updateButton2" type="primary" @click="changeRule" style="background-color: #2376b7">确 定</el-button>
  </span>
      </el-dialog>
    </div>
    <router-view/>
  </div>
</template>

<script>
import {changeRule} from "../api/task";

export default {
  name: "adminLayout",
  data(){
    return{
      dialogVisible:false,
      rule:"",
    }
  },
  methods: {
    handleCommand(command){
      switch (command){
        case "loginOut":
         localStorage.clear();
          this.$router.push("/login");
          break;
        case "changeRule":
          this.dialogVisible = true;
          break;
        default:break;
      }
    },
    changeRule(){
      console.log(this.rule)
      if(this.rule === ""){
        this.$message.error("请选择推荐规则");
        return;
      }
      changeRule(this.rule).then(res=>{
        if(res.code === 1)
          this.$message.success("修改成功");
        else
          this.$message.error("修改失败：" + res.msg);
      }).catch(res=>{
        this.$message.error("修改失败：" + res);
      })
    }
  }
}
</script>

<style scoped>
.el-icon-s-home{
  margin-top: 15px;
  margin-left: 10px;
  float: left;
  font-size: 250%;
  color: white;
}
.admin-msg{
  margin-top: 15px;
  margin-left: 100px;
  float: left;
  color: white;
  font-size: 200%;
}
.el-dropdown-link {
  font-size: 150%;
  cursor: pointer;
  color: white;
}
.el-icon-more {
  font-size: 200%;
}
.adminLayout-container{
  height: 75px;
  background-color: #61649f;
}
</style>
