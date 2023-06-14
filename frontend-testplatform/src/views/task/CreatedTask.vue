<template>
<!--发包方已发布任务界面-->
  <div>
    <div style="height: 75px"></div>
    <div id = “title” style="color: #2b4b6b;margin-left: 2%;margin-top: 2%;font-size: 150%;">已发布测试任务</div>
    <el-card class="task-card" v-for="task in createdTasks" :key="task.taskId">
      <div slot="header" class="clearfix">
        <span>{{ task.taskName }}</span>
        <el-button type="text"  style="float: right;color:#2b4b6b;margin-right: 10px;margin-left: 10px" @click = "goToReportList(task.taskId)">查看报告</el-button>
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
  </div>
</template>

<script>
import {getTaskByUser} from "../../api/task";

export default {
  name: "CreatedTask",
  data(){
    return{
      createdTasks:[
        {
          id:1,
          taskName:"1",
          taskIntro:"21312341141324125dfasdf",
          taskTime:"2022-02-27",
          numOfWorker:1,
          numOfNeedWorker:3,
          taskType:"213",
          taskApp:null,
          taskDoc:null
        },
        {
          id:2,
          taskName:"2",
          taskIntro:"213123411",
          taskTime:"2022-02-27",
          numOfWorker:1,
          numOfNeedWorker:3,
          taskType:"213",
          taskApp:null,
          taskDoc:null
        },
        {
          id:3,
          taskName:"3",
          taskIntro:"213123411",
          taskTime:"2022-02-27",
          numOfWorker:1,
          numOfNeedWorker:3,
          taskType:"213",
          taskApp:null,
          taskDoc:null
        },
        {
          id:4,
          taskName:"4",
          taskIntro:"213123411",
          taskTime:"2022-02-27",
          numOfWorker:1,
          numOfNeedWorker:3,
          taskType:"213",
          taskApp:null,
          taskDoc:null
        },
        {
          id:5,
          taskName:"5",
          taskIntro:"213123411",
          taskTime:"2022-02-27",
          numOfWorker:1,
          numOfNeedWorker:3,
          taskType:"213",
          taskApp:null,
          taskDoc:null
        }
      ],
    };
  },
  methods:{
    goToReportList(taskId){
      this.$router.push({
        path:`/employer/reportList/${taskId}`
      })
    }
  },
  mounted() {
    const uid = window.localStorage.getItem("uid");
    getTaskByUser(uid).then(res=>{
      this.createdTasks = res;
      console.log(this.createdTasks)
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
