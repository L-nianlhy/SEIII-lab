<template>
  <!--  众包工人任务广场界面-->
  <div style="height: 100%">
    <div style="height: 75px"></div>
    <div class = "task-home-title">任务广场</div>
    <div style="height: 340px">
      <div class = "task-home-subtitle" >推荐任务</div>
      <el-card class="task-card" v-for="task in recommendTaskListShow" :key="task.id">
        <div slot="header" class="clearfix">
          <span v-if = "task.taskName.length > 10">{{ task.taskName.substring(0,9) + "..." }}</span>
          <span v-else>{{ task.taskName }}</span>
          <el-button type="text" style="float: right;color:#2376b7;" @click = "handleDetail(task.taskId)">查看详情</el-button>
        </div>
        <div class="text item">
          测试截止日期：{{task.taskTime}}<br>
        </div>
        <div class="text item">
          测试类型：{{task.taskType}}<br>
        </div>
        <div class="text item">
          现有工人：{{task.numOfWorker}}<br>
        </div>
        <div class="text item">
          招募工人：{{task.numOfNeedWorker}}<br>
        </div>
      </el-card>
    </div>
    <el-button style="margin-left: 2%;background-color: #2376b7;color: white" @click = "changeRecommend()">换一批</el-button>
    <div>
      <div class = "task-home-subtitle">正在招募任务</div>
      <el-card class="task-card" v-for="task in taskList" :key="task.id">
        <div slot="header" class="clearfix">
          <span v-if = "task.taskName.length > 10">{{ task.taskName.substring(0,9) + "..." }}</span>
          <span v-else>{{ task.taskName }}</span>
          <el-button type="text" style="float: right;color:#2376b7;" @click = "handleDetail(task.taskId)">查看详情</el-button>
        </div>
        <div class="text item">
          测试截止日期：{{task.taskTime}}<br>
        </div>
        <div class="text item">
          测试类型：{{task.taskType}}<br>
        </div>
        <div class="text item">
          现有工人：{{task.numOfWorker}}<br>
        </div>
        <div class="text item">
          招募工人：{{task.numOfNeedWorker}}<br>
        </div>
      </el-card>
    </div>
  </div>
</template>

<script>
import {getRecommendTask, getUnfinishedTask} from "../../api/task";

export default {
  name: "WorkerHome",
  data() {
    return {
      taskList:[],
      recommendTaskList:[],
      recommendTaskListShow:[],
    };
  },

  methods :{
    handleDetail(taskId){
      this.$router.push({
        path: `/worker/taskDetail/${taskId}`
      });
    },
    changeRecommend(){
      if(this.recommendTaskList.length < 4){
        this.$message.warning("暂无更多推荐任务");
        return;
      }
      this.recommendTaskListShow = [];
      let i = Math.round(Math.random()*(this.recommendTaskList.length - 4));
      for(let j = i; j < i + 4;j++){
        this.recommendTaskListShow.push(this.recommendTaskList[j]);
      }
    }
  },
  mounted() {
    getUnfinishedTask().then(res=>{
      this.taskList = res;
      getRecommendTask(window.localStorage.getItem("uid")).then(res=>{
        this.recommendTaskList = res;
        if(this.recommendTaskList.length < 4){
          this.recommendTaskListShow = this.recommendTaskList;
        }
        else{
          for(let i = 0; i < 4;i++){
            this.recommendTaskListShow.push(this.recommendTaskList[i]);
          }
        }
      }).catch(res=>{
        this.$message.error("推荐任务获取失败："+res);
      })
    }).catch(res=>{
      this.$message.error("招募任务获取失败："+res);
    })
  }
}
</script>

<style scoped>
.task-home-title{
  color: #2376b7;
  margin-left: 3%;
  margin-top: 2%;
  font-size: 200%;
}
.task-home-subtitle{
  color: #2376b7;
  margin-left: 3%;
  margin-top: 2%;
  font-size: 125%;
}
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

.task-card{
  margin-top: 2%;
  float: left;
  width: 21.5%;
  margin-left: 2%;
  margin-right: 1%;
}
</style>
