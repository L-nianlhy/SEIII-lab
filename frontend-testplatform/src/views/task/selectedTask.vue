<template>
  <!--众包工人已接受任务界面-->
  <div>
    <div style="height: 75px"></div>
    <el-tabs :tab-position = "tabPosition" style="height: 100%;">
      <el-tab-pane label="正在执行任务">
        <el-card class="task-card" v-for="task in unfinishedTasks" :key="task.taskId">
          <div slot="header" class="clearfix">
            <span>{{ task.taskName }}</span>
            <el-button type="text"  style="float: right;color:#2376b7;margin-right: 10px;margin-left: 10px" @click = "goToReportList(task.taskId)">查看报告</el-button>
            <el-button type="text"  style="float: right;color:#2376b7;margin-right: 10px;margin-left: 10px" @click = "handleTest(task.taskId)">前往测试</el-button>
          </div>
          <div class="text item" v-if = "task.taskIntro.length > 20">
            任务简介：{{task.taskIntro.substring(0,19) + "..."}}<br>
          </div>
          <div class="text item" v-else>
            任务简介：{{task.taskIntro}}<br>
          </div>
          <div class="text item">
            测试截止日期：{{task.taskTime}}<br>
          </div>
        </el-card>
      </el-tab-pane>
      <el-tab-pane label="历史完成任务">
        <el-card class="task-card" v-for="task in finishedTasks" :key="task.taskId">
          <div slot="header" class="clearfix">
            <span>{{ task.taskName }}</span>
            <el-button type="text"  style="float: right;color:#2376b7;margin-right: 10px" @click = "goToReportList(task.taskId)">查看报告</el-button>
          </div>
          <div class="text item" v-if = "task.taskIntro.length > 20">
            任务简介：{{task.taskIntro.substring(0,19) + "..."}}<br>
          </div>
          <div class="text item" v-else>
            任务简介：{{task.taskIntro}}<br>
          </div>
          <div class="text item">
            测试截止日期：{{task.taskTime}}<br>
          </div>
        </el-card>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
import {getTaskByUser} from "../../api/task";

export default {
  name: "selectedTasks",
  data() {
    return {
      tabPosition: 'left',
      taskList:[],
      unfinishedTasks:[],
      finishedTasks:[]
    };
  },
  methods:{
    handleTest(taskId){
      this.$router.push({
        path:`/worker/reportCreate/${taskId}`
      })
    },
    goToReportList(taskId){
      this.$router.push({
        path:`/worker/reportList/${taskId}`
      })
    }
  },
  mounted() {
    const date = new Date();
    const nowTime = date.getFullYear() + '-' + ((date.getMonth()+1 < 10)?('0'+(date.getMonth()+1)):date.getMonth()+1) + '-' + ((date.getDate() < 10)?('0'+date.getDate()):date.getDate());
    const uid = window.localStorage.getItem("uid");
    getTaskByUser(uid).then(res=>{
      this.taskList = res;
      for(let i = 0; i < this.taskList.length;i++){
        if(this.taskList[i].taskTime > nowTime){
          this.unfinishedTasks.push(this.taskList[i]);
        }
        else{
          this.finishedTasks.push(this.taskList[i]);
        }
      }
    }).catch(res=>{
      this.$message.error("任务获取失败："+res);
    })
  }
}
</script>

<style scoped>
.text {
  font-size: 14px;
}

.item {
  margin-bottom: 18px;
}

.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}
.clearfix:after {
  clear: both
}

.task-card {
  margin-top: 2%;
  margin-left: 2%;
  margin-right: 2%;
}
</style>
