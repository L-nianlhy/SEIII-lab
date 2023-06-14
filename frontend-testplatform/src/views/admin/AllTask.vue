<template>
<!--管理员查看所有任务界面-->
  <div>
    <div style="height: 75px"></div>
    <div style="display:flex;align-items:center">
      <div class = "task-home-title">任务广场</div>
    </div>
    <div>
      <el-card class="task-card" v-for="task in taskList" :key="task.id">
        <div slot="header" class="clearfix">
          <span v-if = "task.taskName.length > 10">{{ task.taskName.substring(0,9) + "..." }}</span>
          <span v-else>{{ task.taskName }}</span>
          <el-button type="text" round style="float: right;color: #61649f" @click = "handleDetail(task.taskId)">查看详情</el-button>
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
import {getAllTask} from "../../api/task";

export default {
  name: "AllTask",
  data() {
    return {
      taskList:[
        {
          id:1,
          taskName:"测试任务1",
          taskIntro:"测试任务",
          taskTime:"2022-04-01",
          numOfWorker:1,
          numOfNeedWorker:3,
          taskType:"213",
          taskApp:null,
          taskDoc:null
        },
        {
          id:2,
          taskName:"测试任务2",
          taskIntro:"测试任务",
          taskTime:"2022-04-01",
          numOfWorker:1,
          numOfNeedWorker:3,
          taskType:"213",
          taskApp:null,
          taskDoc:null
        },
        {
          id:3,
          taskName:"测试任务3",
          taskIntro:"测试任务",
          taskTime:"2022-04-01",
          numOfWorker:1,
          numOfNeedWorker:3,
          taskType:"213",
          taskApp:null,
          taskDoc:null
        },
        {
          id:4,
          taskName:"测试任务4",
          taskIntro:"测试任务",
          taskTime:"2022-04-01",
          numOfWorker:1,
          numOfNeedWorker:3,
          taskType:"213",
          taskApp:null,
          taskDoc:null
        },
        {
          id:5,
          taskName:"测试任务5",
          taskIntro:"测试任务",
          taskTime:"2022-04-01",
          numOfWorker:1,
          numOfNeedWorker:3,
          taskType:"213",
          taskApp:null,
          taskDoc:null
        }
      ]
    };
  },
  methods :{
    handleDetail(taskId){
      this.$router.push({
        path: `/admin/taskDetail/${taskId}`
      });
    }
  },
  mounted() {
    getAllTask().then(res=>{
      this.taskList = res;
    }).catch(res=>{
      this.$message.error("任务获取失败："+res);
    })
  }
}
</script>

<style scoped>
.task-home-title{
  color: #2b4b6b;
  margin-left: 3%;
  margin-top: 2%;
  margin-right: 10%;
  display: inline;
  font-size: 200%;
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

.task-card {
  margin-top: 2%;
  float: left;
  width: 21%;
  margin-left: 2%;
  margin-right: 1%;
}
</style>
