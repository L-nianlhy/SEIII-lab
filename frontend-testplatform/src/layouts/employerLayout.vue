<template>
  <div class = "employerLayout-container">
    <div>
      <i class = "el-icon-s-home" @click="jumpToHome"></i>
      <div class = "employer-msg">COLLECT EMPLOYER</div>
      <el-dropdown @command="handleCommand" style="float: right;margin-top: 15px;margin-right: 10px">
        <span class="el-dropdown-link">
          <i class="el-icon-more"></i>
        </span>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item command="userCenter">个人中心</el-dropdown-item>
          <el-dropdown-item command="createdTask">已发布任务</el-dropdown-item>
          <el-dropdown-item command="loginOut">登出</el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </div>
    <router-view/>
  </div>
</template>

<script>
export default {
  name: "employerLayout",
  methods:{
    handleCommand(command) {
      switch (command){
        case "userCenter":
          if(this.$route.path!=="/employer/userCenter"){
            this.$router.push({
              path:'/employer/userCenter'
            })
          }
          break;
        case "createdTask":
          if(this.$route.path!=="/employer/createdTask") {
            this.$router.push({
              path: "/employer/createdTask"
            })
          }
          break;
        case "loginOut":
          localStorage.clear();
          this.$router.push("/login");
          break;
        default:break;
      }
    },
    jumpToHome(){
      this.$router.push("/employer");
    }
  }
}
</script>

<style lang="less" scoped>
.el-icon-s-home{
  margin-top: 15px;
  margin-left: 10px;
  float: left;
  font-size: 250%;
  color: white;
  cursor:pointer;
}

.employer-msg{
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
.employerLayout-container{
  height: 75px;
  background-color: #2b4b6b;
}
</style>
