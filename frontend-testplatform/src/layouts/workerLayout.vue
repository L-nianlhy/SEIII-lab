<template>
  <div class = "workerLayout-container">
  <div>
    <i class = "el-icon-s-home" @click="jumpToHome"></i>
    <div class = "worker-msg">COLLECT WORKER</div>
    <el-dropdown @command="handleCommand" style="float: right;margin-top: 15px;margin-right: 10px" >
        <span class="el-dropdown-link">
          <i class="el-icon-more"></i>
        </span>
      <el-dropdown-menu slot="dropdown">
        <el-dropdown-item command="userCenter">个人中心</el-dropdown-item>
        <el-dropdown-item command="selectedTask">已接受任务</el-dropdown-item>
        <el-dropdown-item command="loginOut">登出</el-dropdown-item>
      </el-dropdown-menu>
    </el-dropdown>
  </div>
  <router-view/>
  </div>
</template>

<script>
export default {
  name: "workerLayout",
  methods:{
   handleCommand(command){
     switch (command){
       case "userCenter":
         if(this.$route.path!=="worker/userCenter"){
           this.$router.push({
             path:"/worker/userCenter"
           });
         }
         break;
       case "selectedTask":
         if(this.$route.path!=="worker/selectedTask") {
           this.$router.push("/worker/selectedTask");
         }
         break;
       case "loginOut":
         localStorage.clear();
         this.$router.push("/login");
         break;
       default:break;
     }
   },
    jumpToHome() {
     this.$router.push("/worker");
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
  cursor: pointer;
}
.worker-msg{
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
.workerLayout-container{
  height: 75px;
  background-color: #2376b7;
}
</style>
