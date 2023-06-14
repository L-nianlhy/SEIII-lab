<template>
<!--填写测试报告界面-->
  <div>
    <div style="height: 75px"></div>
    <div class="reportContainer">
        <div class="reportBox">
          <el-form ref="reportFormRef" :model="reportForm" class="report_Form" label-width="200px" >
<!--            测试任务标题-->
            <el-form-item label="当前测试任务标题">
              <el-input v-model="taskName" disabled>
              </el-input>
            </el-form-item>
<!--            测试任务简介-->
            <el-form-item label="当前测试任务简介">
              <el-input v-model="taskIntro" disabled>
              </el-input>
            </el-form-item>
            <!--            待测可执行文件下载-->
            <el-form-item label="待测可执行文件下载">
              <el-button type="primary" @click="AppDownload">下载</el-button>
            </el-form-item>
            <!--            测试需求文件下载-->
            <el-form-item label="测试需求文件下载">
              <el-button type="primary" @click="DocDownload">下载</el-button>
            </el-form-item>
<!--            缺陷类型截图-->
            <el-form-item label="缺陷截图（限制10MB）">
              <input type="file" name="file" id="file1"/>
            </el-form-item>
<!--            缺陷情况说明-->
            <el-form-item label="缺陷情况说明">
              <el-input v-model="reportForm.bugIntro" type="textarea" :autosize="{minRows:4,maxRows:6}">
              </el-input>
            </el-form-item>
            <!--              缺陷复现步骤-->
            <el-form-item label="缺陷复现步骤">
              <el-input v-model="reportForm.bugStep" type="textarea" :autosize="{minRows:4,maxRows:6}">
              </el-input>
            </el-form-item>
<!--            设备相关信息-->
              <el-form-item label="测试设备信息">
                <el-select v-model="reportForm.deviceInformation" placeholder="测试设备">
                  <el-option label="Linux" value="Linux"></el-option>
                  <el-option label="Android" value="Android"></el-option>
                  <el-option label="Windows" value="Windows"></el-option>
                  <el-option label="IOS" value="IOS"></el-option>
                </el-select>
              </el-form-item>
            <div class="btns">
              <el-button  type="success" style="float: right;background-color: #2376b7" @click="submit()">提交
              </el-button>
              <el-button type="info" style="float: right;margin-right: 50px;" @click="moveBack()">返回</el-button>
            </div>
          </el-form>
        </div>
    </div>
    <a id="AppDown" target="_blank"></a>
    <a id="DocDown" target="_blank"></a>
  </div>
</template>

<script>
import {createReport} from "../../api/testReport";
import {downloadApp, downloadDoc, uploadPhoto} from "../../api/file";
import {getTaskById} from "../../api/task";

export default {
  name: "ReportCreate",
  data(){
    return{
      taskName:"",
      taskIntro:"",
      reportForm:{
        uid:0,
        taskId:3,
        bugPhoto:null,
        bugIntro:"",
        bugStep:"",
        deviceInformation:"",
        cooperationId: -1,
      },
    };
  },
  methods:{
    moveBack(){
      this.$router.go(-1);
    },
    AppDownload(){
      downloadApp(this.reportForm.taskId).then(res=>{
        if(res!=null){
          const downloadLink = document.querySelector("#AppDown");
          downloadLink.setAttribute("href",res);
          downloadLink.click();
        }
      })
    },
    DocDownload(){
      downloadDoc(this.reportForm.taskId).then(res=>{
        if(res!=null){
          const downloadLink = document.querySelector("#DocDown");
          downloadLink.setAttribute("href",res);
          downloadLink.click();
        }
      })
    },
    submit(){
      if(document.getElementById("file1").files[0] === undefined){
        this.$message.error("请上传缺陷截图");
        return;
      }
      if(document.getElementById("file1").files[0].size > 10485759){
        this.$message.error("缺陷截图大小超过10MB");
        return;
      }
      if(this.reportForm.bugIntro === ""){
        this.$message.error("请输入缺陷情况说明");
        return;
      }
      if(this.reportForm.bugStep === ""){
        this.$message.error("请输入缺陷复现步骤");
        return;
      }
      if(this.reportForm.deviceInformation === ""){
        this.$message.error("请输入测试设备信息");
        return;
      }
      createReport({
        uid : this.reportForm.uid,
        taskId: this.reportForm.taskId,
        bugStep: this.reportForm.bugStep,
        bugIntro: this.reportForm.bugIntro,
        cooperationId:-1,
        deviceInformation: this.reportForm.deviceInformation,
        }).then(res=>{
          if(res.code === 1){
            uploadPhoto(res.data,document.getElementById("file1").files[0]).then(res=>{
              if(res.code === 1){
                this.$message.success("报告提交成功！");
                this.$router.go(-1);
              }
              else{
                this.$message.error("截图上传失败:" + res.msg);
              }
            })
          }
          else{
            this.$message.error("报告提交失败:" +res.msg);
          }
      }).catch(res=>{
        this.$message.error("报告提交失败："+res);
      })
    },
  },
  mounted() {
    console.log(this.$route.params);
    const {taskId} = this.$route.params;
    this.reportForm.taskId = taskId;
    getTaskById(taskId).then(res=>{
      this.taskName = res.taskName;
      this.taskIntro = res.taskIntro;
    }).catch(res=>{
      this.$message.error("任务信息获取失败："+res);
    })
    this.reportForm.uid = window.localStorage.getItem("uid");
  }
}
</script>

<style lang="less" scoped>
.reportContainer{
  height: 100%;
}
.reportBox{
  width: 600px;
  height: 600px;
  background-color: #fff;
  position: absolute;
  left: 50%;
  top: 50%;
  transform: translate(-50%,-50%);
}

</style>
