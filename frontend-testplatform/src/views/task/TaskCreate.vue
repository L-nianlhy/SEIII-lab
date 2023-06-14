<template>
<!--发布任务界面-->
  <div>
    <div style="height: 75px"></div>
    <div class = "create-dask-title">发布任务</div>
    <el-form ref="form" :model="task" label-width="100px" style="margin-left: 15%;margin-right: 15%">
      <el-form-item label="任务标题">
        <el-input v-model="task.taskName"></el-input>
      </el-form-item>
      <el-form-item label="任务简介">
        <el-input type="textarea" v-model="task.taskIntro"></el-input>
      </el-form-item>
      <el-form-item label="截止日期">
        <el-col :span="11">
          <el-date-picker type="date" placeholder="选择日期" v-model="time" style="width: 100%;"></el-date-picker>
        </el-col>
      </el-form-item>
      <el-form-item label="测试类型">
        <el-select v-model="task.taskType" placeholder="请选择测试类型">
          <el-option label="功能测试" value="功能测试"></el-option>
          <el-option label="性能测试" value="性能测试"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="测试难度">
        <el-select v-model="task.taskDifficulty" placeholder="请选择测试难度">
          <el-option label="业余" value="amateur"></el-option>
          <el-option label="普通" value="normal"></el-option>
          <el-option label="专业" value="professional "></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="测试设备">
        <el-select v-model="task.taskDevice" placeholder="请选择测试设备">
          <el-option label="Android" value="Android"></el-option>
          <el-option label="Linux" value="Linux"></el-option>
          <el-option label="IOS" value="IOS"></el-option>
          <el-option label="Windows" value="Windows"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="工人数量">
        <el-input-number v-model="task.numOfNeedWorker"  :min="1" label="工人数量"></el-input-number>
      </el-form-item>
      <el-form-item label="可执行文件">
        <input type="file" name="file" id="file1"/>
        <div class="el-upload__tip">若有多个待测应用可执行文件请以压缩包形式上传</div>
      </el-form-item>
      <el-form-item label="需求描述文件">
        <input type="file" name="file" id="file2"/>
        <div class="el-upload__tip">只能上传.pdf,.PDF文件</div>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="createTask" style="background-color: #2b4b6b">立即发布</el-button>
        <el-button @click="jumpToHome">取消</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import {createTask} from "../../api/task";
import {uploadApp, uploadDoc} from "../../api/file";

export default {
  name: "TaskCreate",
  data() {
    return {
      time:null,
      task: {
        uid:-1,
        taskName:"",
        taskIntro:"",
        taskTime:"",
        numOfWorker:0,
        numOfNeedWorker:0,
        taskType:"",
        taskDifficulty:"",
        taskDevice:"",
        taskApp:null,
        taskDoc:null,
      }
    }
  },
  methods: {
    createTask() {
      console.log(this.task)
      if(this.task.taskName === ""){
        this.$message.error("请输入任务标题");
        return;
      }
      if(this.task.taskIntro === ""){
        this.$message.error("请输入任务简介");
        return;
      }
      if(this.time === null){
        this.$message.error("请选择任务截止时间");
        return;
      }
      if(this.task.taskType === ""){
        this.$message.error("请选择测试类型");
        return;
      }
      if(this.task.taskDifficulty === ""){
        this.$message.error("请选择测试难度");
        return;
      }
      if(this.task.taskDevice === ""){
        this.$message.error("请选择测试设备");
        return;
      }
      if(this.task.numOfNeedWorker < 1 ){
        this.$message.error("任务至少需要一名工人");
        return;
      }
      if(document.getElementById("file1").files[0] === undefined){
        this.$message.error("请上传可执行文件");
        return;
      }
      if(document.getElementById("file2").files[0] === undefined){
        this.$message.error("请上传需求描述文件");
        return;
      }
      if(!(document.getElementById("file2").files[0].name.endsWith(".pdf") || document.getElementById("file2").files[0].name.endsWith(".PDF"))){
        this.$message.error("请上传pdf形式的需求描述文件");
        return;
      }
      this.task.taskApp = document.getElementById("file1").files[0];
      this.task.taskDoc = document.getElementById("file2").files[0];
      this.task.taskTime = this.time.getFullYear() + '-' + ((this.time.getMonth()+1 < 10)?('0'+(this.time.getMonth()+1)):this.time.getMonth()+1) + '-' + ((this.time.getDate() < 10)?('0'+this.time.getDate()):this.time.getDate());
      const date = new Date();
      const nowTime = date.getFullYear() + '-' + ((date.getMonth()+1 < 10)?('0'+(date.getMonth()+1)):date.getMonth()+1) + '-' + ((date.getDate() < 10)?('0'+date.getDate()):date.getDate());
      if(this.task.taskTime <= nowTime){
        this.$message.error("任务至少明天截止")
      }
      createTask({
        uid:window.localStorage.getItem("uid"),              //发包方id
        taskName:this.task.taskName,         //测试任务标题
        taskIntro:this.task.taskIntro,        //测试任务简介
        taskTime:this.task.taskTime,         //测试时间段
        numOfWorker:this.task.numOfWorker,      //现有工人
        numOfNeedWorker:this.task.numOfNeedWorker,  //需要工人
        taskType:this.task.taskType,         //测试类型
        taskDifficulty: this.task.taskDifficulty,
        taskDevice: this.task.taskDevice
      }).then(res=>{
        if(res.code === 1){
          const taskId = res.data;
          uploadApp(taskId,this.task.taskApp).then(res=>{
            if(res.code === 1){
              uploadDoc(taskId,this.task.taskDoc).then(res=>{
                if(res.code === 1){
                  this.$message.success("任务发布成功");
                  this.$router.push("/employer/createdTask");
                }
                else{
                  this.$message.error("需求文档上传失败：" + res.msg);
                }
              })
            }
            else{
              this.$message.error("待测文件上传失败：" + res.msg);
            }
          })
        }
        else{
          this.$message.error("任务发布失败：" + res.msg);
        }
      }).catch(res=>{
        this.$message.error("任务发布失败："+res);
      })
    },
    jumpToHome(){
      this.$router.push("/employer");
    }
  }
}
</script>

<style scoped>
.create-dask-title{
  color: #2b4b6b;
  margin-left: 15%;
  margin-top: 2%;
  margin-bottom: 2%;
  font-size: 200%;
}
</style>
