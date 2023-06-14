<template>
<!--某一任务测试报告列表-->
  <div>
    <div style="height: 75px"></div>
    <div v-if = "flag" style="color: #2376b7;margin-left: 2%;margin-top: 2%;font-size: 150%;">测试报告列表</div>
    <div v-else style="color: #2b4b6b;margin-left: 2%;margin-top: 2%;font-size: 150%;">测试报告列表</div>
    <el-card class="task-card" v-for="(report,index) in reports" :key="index">
      <div slot="header" class="clearfix" v-if = "report.bugIntro.length >= 10">
        <span>{{ report.bugIntro.substring(0,9)+"..." }}</span>
        <el-button type="text" v-if = "flag" style="float: right;color:#2376b7;margin-right: 10px;margin-left: 10px" @click = "goToReportDetail(report.reportId)">查看报告</el-button>
        <el-button type="text" v-else style="float: right;color:#2b4b6b;margin-right: 10px;margin-left: 10px" @click = "goToReportDetail(report.reportId)">查看报告</el-button>
      </div>
      <div slot="header" class="clearfix" v-else>
        <span>{{ report.bugIntro}}</span>
        <el-button type="text" v-if = "flag" style="float: right;color:#2376b7;margin-right: 10px;margin-left: 10px" @click = "goToReportDetail(report.reportId)">查看报告</el-button>
        <el-button type="text" v-else style="float: right;color:#2b4b6b;margin-right: 10px;margin-left: 10px" @click = "goToReportDetail(report.reportId)">查看报告</el-button>
      </div>
      <div class="text item" v-if = "reportWorkers.length !== 0">
        报告作者：{{reportWorkers[index].uname}}<br>
      </div>
      <div class="text item">
        测试设备：{{report.deviceInformation}}<br>
      </div>
    </el-card>
  </div>
</template>

<script>
import {judgeEmployer, judgeWorker} from "../../util/auth";
import {getReportByTask} from "../../api/testReport";
import {getUser} from "../../api/user";

export default {
  name: "ReportList",
  data(){
    return{
      taskId:0,
      reports:[],
      reportWorkers:[],
      flag:false
    };
  },
  methods:{
    goToReportDetail(reportId){
      if(judgeEmployer()){
        this.$router.push({
          path:`/employer/reportDetail/${reportId}`
        })
      }
      else if(judgeWorker()){
        this.$router.push({
          path:`/worker/reportDetail/${reportId}`
        })
      }
    }
  },
  mounted() {
    if(judgeWorker()){
      this.flag = true;
    }
    else if(judgeEmployer()){
      this.flag = false;
    }
    const {taskId} = this.$route.params;
    this.taskId = taskId;
    getReportByTask(this.taskId).then(res=>{
      console.log(res);
      this.reports = res;
      for(let i = 0;i < this.reports.length;i++){
        getUser(this.reports[i].uid).then(res=>{
          this.reportWorkers.push(res);
        }).catch(res=>{
          this.$message.error("众包工人信息获取错误：" + res);
        })
      }
    }).catch(res=>{
      this.$message.error("报告获取失败："+res);
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
