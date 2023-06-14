<template>
<!--  任务详情界面-->
  <div>
    <div style="height: 75px"></div>
    <div id = "task-detail-title">任务详情<el-button plain style="float: right;margin-right: 6.5%;" @click="returnBack()">返回</el-button></div>
    <el-descriptions  :column="1" border style="margin-left: 15%;margin-right: 15%;font-size: 100%">
      <el-descriptions-item label="任务标题" class = "taskDetail">{{ task.taskName }}</el-descriptions-item>
      <el-descriptions-item label="任务简介">{{ task.taskIntro }}</el-descriptions-item>
      <el-descriptions-item label="发包方">{{ employer.uname }}</el-descriptions-item>
      <el-descriptions-item label="测试时间段">{{ task.taskTime }}</el-descriptions-item>
      <el-descriptions-item label="现有工人数量">{{ task.numOfWorker }}</el-descriptions-item>
      <el-descriptions-item label="所需工人数量">{{ task.numOfNeedWorker }}</el-descriptions-item>
      <el-descriptions-item label="测试类型">
        <el-tag size="medium" style="font-size: 100%">{{ task.taskType }}</el-tag>
      </el-descriptions-item>
      <el-descriptions-item label="测试难度">
        <el-tag size="medium" style="font-size: 100%" type="success">{{ taskDifficultyShow}}</el-tag>
      </el-descriptions-item>
      <el-descriptions-item label="测试设备">
        <el-tag size="medium" style="font-size: 100%" type="warning">{{ task.taskDevice }}</el-tag>
      </el-descriptions-item>
    </el-descriptions>
    <el-button type="primary" id="join-button" @click="chooseTask">接受测试任务</el-button>
    <el-button type="primary" id="remove-button" @click = "removeTask">删除测试任务</el-button>
  </div>
</template>

<script>

import {judgeAdmin, judgeEmployer, judgeWorker} from "../../util/auth";
import {chooseTask, getUser} from "../../api/user";
import {getTaskById, removeTask} from "../../api/task";

export default {
  name: "TaskDetail",
  data() {
    return{
      taskId: -1,
      taskDifficultyShow:"1",
      employer:{
        id:1,
        name:"test",
        phone:"123123"
      },
      task:{
        id:1,
        uid:1,
        taskName:"1",
        taskIntro:"213123411",
        taskTime:"2022-02-27",
        numOfWorker:1,
        numOfNeedWorker:3,
        taskType:"213",
        taskDifficulty:"",
        taskDevice:"IOS",
        taskApp:null,
        taskDoc:null
      }
    }
  },
  methods:{
    loadTask(){
      const {taskId} = this.$route.params;
      this.taskId = taskId;
      getTaskById(taskId).then(res=>{
        this.task = res;
        switch (this.task.taskDifficulty){
          case "amateur":
            this.taskDifficultyShow = "业余";
            break;
          case "normal":
            this.taskDifficultyShow = "普通";
            break;
          case "professional ":
            this.taskDifficultyShow = "专业";
            break;
          default:
            this.taskDifficultyShow = "出错了！！！";
            break;
        }
        getUser(this.task.uid).then(res=>{
          this.employer = res;
        }).catch(res=>{
          this.$message.error("发包方信息获取失败："+res);
        })
      }).catch(res=>{
        this.$message.error("任务加载失败："+res);
      })
    },
    returnBack(){
      this.$router.go(-1);
    },
    chooseTask(){
      chooseTask({
        uid:window.localStorage.getItem("uid"),
        taskId:this.taskId
      }).then(res=>{
        if(res.code === 1){
          this.$message.success("接受测试任务成功");
          this.$router.push("/worker/selectedTask");
        }
        else{
          this.$message.error("接受测试任务失败：" + res.msg);
        }
      }).catch(res=>{
        this.$message.error("接受测试任务失败："+res);
      })
    },
    removeTask(){
      removeTask(this.taskId).then(res=>{
        if(res.code === 1){
          this.$message.success("删除测试任务成功");
          this.$router.push("/admin");
        }
        else{
          this.$message.error("删除测试任务失败：" + res.msg);
        }
      }).catch(res=>{
        this.$message.error("任务删除失败："+res);
      })
    }
  },
  mounted(){
    if(judgeEmployer()){
      document.getElementById("task-detail-title").style.color = "#2b4b6b";
    }
    else if (judgeWorker()){
      document.getElementById("task-detail-title").style.color = "#2376b7";
      document.getElementById("join-button").style.visibility = "visible";
    }
    else if(judgeAdmin()){
      document.getElementById("task-detail-title").style.color = "#61649f";
      document.getElementById("remove-button").style.visibility = "visible";
    }
    this.loadTask();
  }
}
</script>

<style scoped>
  #task-detail-title{
    margin: 2% 10% 2% 15%;
    font-size: 200%;
  }
  #join-button{
    visibility: hidden;
    background-color: #2376b7;
    margin-left: 15%;
    font-size: 100%;
    margin-top: 2%;
  }
  #remove-button{
    float: right;
    visibility: hidden;
    background-color:#61649f;
    margin-right: 15%;
    font-size: 100%;
    margin-top: 2%;
  }
</style>
