<template>
<!--查看测试报告界面-->
  <div>
    <div style="height: 75px"></div>
    <el-tabs v-model="activeName" class="Tab" stretch @tab-click="handleClick">
      <el-tab-pane label="报告详情" name="report_detail">
        <el-container>
          <el-main class="reportDetail_box" >
            <el-row>
              <el-col :span="12"> <div class="reportTable">
                <el-descriptions :model="reportInformation" :column="1" border direction="vertical" style="font-size: 150%" class="reportDec">
                  <el-descriptions-item label="测试项目名称">{{task.taskName}}</el-descriptions-item>
                  <el-descriptions-item label="测试项目简介">{{task.taskIntro}}</el-descriptions-item>
                  <el-descriptions-item label="测试人员">{{user.uname}}</el-descriptions-item>
                  <el-descriptions-item label="缺陷情况说明" ><textarea readonly>{{reportInformation.bugIntro}}</textarea></el-descriptions-item>
                  <el-descriptions-item label="缺陷复现步骤"><textarea readonly>{{reportInformation.bugStep}}</textarea></el-descriptions-item>
                  <el-descriptions-item label="设备信息"><textarea readonly>{{reportInformation.deviceInformation}}</textarea></el-descriptions-item>
                </el-descriptions>
              </div></el-col>
              <el-col :span="12"><div class="photo">
                <el-descriptions border  style="font-size: 150%" direction="vertical" class="photo_table" :column="1">
                  <el-descriptions-item label="测试截图"  >
                    <el-image style="width: 100%;height: 100%;" :src="reportInformation.bugPhoto" :preview-src-list="srcList" class="reportImg"></el-image>
                  </el-descriptions-item>
                  <el-descriptions-item label="报告综合评分" >
                    <el-rate v-model="avgRate" :colors="colors" disabled> </el-rate>
                  </el-descriptions-item>
                </el-descriptions>
              </div>
                <el-button type="success" style="float: right; margin:20px" @click="dialogVisible=true" class="btns">添加协作报告</el-button>
                <el-dialog
                  title="添加协作报告"
                  :visible.sync="dialogVisible"
                  width="30%"
                  :before-close="handleClose">
                  <el-form :model="cooperation">
                    <el-form-item label="缺陷截图">
                      <input type="file" name="file" id="file1"/>
                    </el-form-item>
                    <!--            缺陷情况说明-->
                    <el-form-item label="缺陷情况说明">
                      <el-input v-model="cooperation.bugIntro" type="textarea" :autosize="{minRows:4,maxRows:6}">
                      </el-input>
                    </el-form-item>
                    <!--              缺陷复现步骤-->
                    <el-form-item label="缺陷复现步骤">
                      <el-input v-model="cooperation.bugStep" type="textarea" :autosize="{minRows:4,maxRows:6}">
                      </el-input>
                    </el-form-item>
                    <!--            设备相关信息-->
                    <el-form-item label="测试设备信息">
                      <el-select v-model="cooperation.deviceInformation" placeholder="测试设备">
                        <el-option label="Linux" value="Linux"></el-option>
                        <el-option label="Android" value="Android"></el-option>
                        <el-option label="Windows" value="Windows"></el-option>
                        <el-option label="IOS" value="IOS"></el-option>
                      </el-select>
                    </el-form-item>
                  </el-form>
                  <span>
    <el-button @click="dialogVisible = false">取 消</el-button>
    <el-button type="primary" @click="dialogVisible = false; submitCooperation()">确 定</el-button></span>
                </el-dialog>
                <el-button type="primary" style="float: right; margin:20px" @click="jumpLast()" class="btns">返回</el-button>
              </el-col>
            </el-row>
          </el-main>

        </el-container>
      </el-tab-pane>
      <el-tab-pane label="相似报告" name="similar">
        <el-card class="report-card" v-for="(report,index) in similarReports" :key="index">
          <div slot="header" class="clearfix" v-if = "report.bugIntro.length >= 10">
            <span>{{ report.bugIntro.substring(0,9)+"..." }}</span>
            <el-button type="text" class = detail-button1 style="float: right;color:#2376b7;margin-right: 10px;margin-left: 10px" @click = "goToReportDetail(report.reportId)">查看报告</el-button>
          </div>
          <div slot="header" class="clearfix" v-else>
            <span>{{ report.bugIntro}}</span>
            <el-button type="text" class = detail-button2 style="float: right;color:#2376b7;margin-right: 10px;margin-left: 10px" @click = "goToReportDetail(report.reportId)">查看报告</el-button>
          </div>
          <div class="text item">
            报告作者：{{similarReportWorkers[index].uname}}<br>
          </div>
          <div class="text item">
            测试设备：{{report.deviceInformation}}<br>
          </div>
        </el-card>
      </el-tab-pane>
      <el-tab-pane label="协作报告" name="cooperation">
        <el-card class="report-card" v-for="(report,index) in coopReports" :key="index">
          <div slot="header" class="clearfix" v-if = "report.bugIntro.length >= 10">
            <span>{{ report.bugIntro.substring(0,9)+"..." }}</span>
            <el-button type="text" class = detail-button1 style="float: right;color:#2376b7;margin-right: 10px;margin-left: 10px" @click = "goToReportDetail(report.reportId)">查看报告</el-button>
          </div>
          <div slot="header" class="clearfix" v-else>
            <span>{{ report.bugIntro}}</span>
            <el-button type="text" class = detail-button2 style="float: right;color:#2376b7;margin-right: 10px;margin-left: 10px" @click = "goToReportDetail(report.reportId)">查看报告</el-button>
          </div>
          <div class="text item">
            报告作者：{{coopReportWorkers[index].uname}}<br>
          </div>
          <div class="text item">
            测试设备：{{report.deviceInformation}}<br>
          </div>
        </el-card>
      </el-tab-pane>
      <el-tab-pane label="评论区" name="comment">
        <el-container>
          <div class="commentArea">
            <el-divider></el-divider>
            <div class="commentTitle">{{this.comments.length}}条评论</div>
            <el-card class="card">
              <div class="box-card ">
                <div>
                  <el-avatar :size="80" :src="require('../../../static/photos/10.jpg')"></el-avatar>
                </div>
                <div class="myComment"><el-input :rows="2" resize="none" type="textarea" v-model="content" placeholder="请输入内容" style="font-size: 150%"></el-input>
                  <el-rate v-model="score" :colors="colors" class="reportRate"> </el-rate>
                </div>
                <div>
                </div>
                <div class="commentCreate"><button @click="submitComment">发表<br>评论</button></div>
              </div>
            </el-card>
            <el-card class="card " v-for="(comment,index) in comments" :key="index">
              <div class="box-card commentCard">
                <div class="commentAvator">
                  <el-avatar :size="80" :src="photos[index%photos.length].url"></el-avatar>
                </div>
                <div class="myComment" style="margin-left: 30px;">
                  <span style="color: #00eeee">{{comment.uname}}</span>  说:
                  {{comment.content}}
                </div>
                <div class="commentRight">
                  <el-rate class="score" v-model="comment.score" :colors="colors" disabled> </el-rate>
                  <div class="timeStamp">{{comment.timeStamp}}</div>
                </div>
              </div>
            </el-card>
          </div>
        </el-container>
      </el-tab-pane>
    </el-tabs>

  </div>
</template>

<script>
import {getTaskById} from "../../api/task";
import {createReport, getCooperationReport, getReportById, getSimilarReport} from "../../api/testReport";
import {getUser} from "../../api/user";
import {createComment, getComment} from "../../api/comment";
import {uploadPhoto} from "../../api/file";
import {judgeEmployer, judgeWorker} from "../../util/auth";

export default {
  name: "ReportDetail",
  data(){
    return{
      dialogVisible:false,
      activeName: 'report_detail',
      reportId:"",
      reportInformation:{},
      task:{},
      user:{},
      localUser:{},
      srcList:[],
      comments:[
        {}
      ],
      photos:[{url: require("../../../static/photos/1.jpg")},
        {url: require("../../../static/photos/2.jpg")},
        {url: require("../../../static/photos/3.jpg")},
        {url: require("../../../static/photos/4.jpg")}
      ],
      avgRate:0,
      score:null,
      colors:['red','blue','#FF9900'],
      countOfComment:'',
      content:"",
      cooperation:{
        uid:"",
        taskId:"",
        cooperationId:"1",
        bugPhoto:"",
        bugIntro: "",
        bugStep:"",
        deviceInformation:"",
      },
      similarReports:[],
      similarReportWorkers:[],
      coopReports:[],
      coopReportWorkers:[]
    };
  },
  methods:{
    handleClick(tab){
      switch(tab.index){
        case "1":
          this.loadSimilar();
          break;
        case "2":
          this.loadCooperation();
          break;
        case "3":
          this.loadComment();
          break;
        default:break;
      }
    },

    goToReportDetail(reportId){
      if(judgeEmployer()){
        const{href} = this.$router.resolve({
          path:`/employer/reportDetail/${reportId}`
        });
        window.open(href,'_blank');
      }
      else if(judgeWorker()){
        const{href} = this.$router.resolve({
          path:`/Worker/reportDetail/${reportId}`
        });
        window.open(href,'_blank');
      }
    },

    handleClose(done) {
      this.$confirm('确认关闭？')
        .then(_ => {
          done();
        })
        .catch(_ => {})},

    submitCooperation(){
      if(document.getElementById("file1").files[0] === undefined){
        this.$message.error("请上传缺陷截图");
        return;
      }
      if(this.cooperation.bugIntro === ""){
        this.$message.error("请输入缺陷情况说明");
        return;
      }
      if(this.cooperation.bugStep === ""){
        this.$message.error("请输入缺陷复现步骤");
        return;
      }
      if(this.cooperation.deviceInformation === ""){
        this.$message.error("请输入测试设备信息");
        return;
      }
      createReport({
        uid : this.localUser.uid,
        taskId: this.task.taskId,
        bugStep: this.cooperation.bugStep,
        bugIntro: this.cooperation.bugIntro,
        cooperationId:this.reportId,
        deviceInformation: this.cooperation.deviceInformation,
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
          }).catch(res=>{
            this.$message.error("截图上传失败:" + res);
          })
        }
        else{
          this.$message.error("报告提交失败:" +res.msg);
        }
      }).catch(res=>{
        this.$message.error("报告提交失败："+res);
      })
    },
    jumpLast(){
      if(judgeEmployer()){
        this.$router.push({
          path:'/employer'
        });
      }
      if(judgeWorker()){
        this.$router.push({
          path:'/worker'
        });
      }
    },

    loadLocalUser(){
      getUser(window.localStorage.getItem("uid")).then(res=>{
        this.localUser.uid = res.uid
        this.localUser.uname=res.uname
      })
    },

    submitComment(){
      let Stamp =new Date().toLocaleString();
      Stamp = Stamp.substr(0,Stamp.length-3);
      if(this.score===0){
        this.$message.error("请评分后再评论");
      }else {
        createComment({
          uid:this.localUser.uid,
          uname: this.localUser.uname,
          reportId: this.reportId,
          score: this.score,
          content: this.content,
          timeStamp: Stamp
        }).then(res => {
          if (res.code === 1) {
            this.$message.success("评论成功");
            setTimeout(() => {
              this.$router.go(0);
            })
          } else {
            this.$message.error("评论失败" + res.msg);
          }
        }).catch(res => {
          this.$message.error("评论失败" + res);
        })
      }
    },

    loadReport(){
    const{reportId} = this.$route.params;
    this.reportId = reportId;
    getReportById(reportId).then(res=>{
      this.reportInformation = res;
      this.srcList.push(this.reportInformation.bugPhoto);
      getTaskById(this.reportInformation.taskId).then(res=>{
        this.task = res;
        getUser(this.reportInformation.uid).then(res=>{
          this.user = res;
          this.loadComment();
        })
      })
    });
    },

    loadComment(){
      this.avgRate = 0;
      getComment(this.reportId).then(res=>{
        this.comments = res;
      }).catch(res=>{
        this.$message.error("评论获取失败:" +res);
      });
      if(this.comments.length===0){
        this.avgRate=0;
      }else {
        for (let i = 0; i < this.comments.length; i++) {
          this.avgRate += this.comments[i].score;
        }
        this.avgRate = this.avgRate / this.comments.length;
      }
    },

    loadCooperation(){
      getCooperationReport(this.reportId).then(res=>{
        this.coopReports = res;
        for(let i = 0;i < this.coopReports.length;i++){
          getUser(this.coopReports[i].uid).then(res=>{
            this.coopReportWorkers.push(res)
          }).catch(res=>{
            this.$message.error("众包工人信息获取错误：" + res);
          })
        }
      }).catch(res=>{
        this.$message.error("协作报告获取失败:" +res);
      })
    },

    loadSimilar(){
      console.log(1)
      getSimilarReport(this.reportId).then(res=>{
        console.log(res)
        this.similarReports = res;
        for(let i = 0;i < this.similarReports.length;i++){
          getUser(this.similarReports[i].uid).then(res=>{
            this.similarReportWorkers.push(res)
          }).catch(res=>{
            this.$message.error("众包工人信息获取错误：" + res);
          })
        }
      }).catch(res=>{
        this.$message.error("相似报告获取失败:" +res);
      })
    }
  },
  mounted() {
    this.loadLocalUser();
    this.loadReport();
    this.loadComment();
  }
}
</script>

<style lang="less" scoped>
.btns{
  font-size: 125%;
}
.reportImg{
  width: 100%;
  height: 100%;
}
.reportDetail_box{
  padding: 0 9% ;
}
.commentArea{
  width: 100%;
}
.card{
  margin: 20px 30px;
}
.box-card{
  height: 100px;
  display: flex;
  div{
    padding: 0 2%;
  }
}
.commentCard{
  line-height: 70px;
}
.myComment{
  flex: 1;
  position: relative;
}
.reportRate{
  position: absolute;
  bottom: -5%;
  left: 24%;
}
.commentCreate{
  width: 100px;
  height: 100px;
  line-height: 100px;
  button{
    width: 80%;
    height: 80%;
    border-radius: 15%;
    font-size: 150%;
    border:none;
    color: white;
    background-color: rgb(64,158,255);
    cursor: pointer;
    transform: translate(0,-25%);
  }
}
.commentTitle{
  vertical-align: baseline;
  font-size: 200%;
  font-family: "Cascadia Code",serif;
  margin-left: 5%;
}
.score{
  width: 300%;
  line-height: 120px;
}
.commentRight{
  position: relative;
  .timeStamp{
    position: absolute;
    top: 61%;
    left: 8%;
    color: #A6C4E9;
    font-size: 75%;
  }
}
textarea{
  width: 100%;
  height: 150%;
  overflow-y: scroll;
  overflow-x: hidden;
  border: none;
  font-size: 120%;
  resize: none;
}
//协作报告展示
.coop_box{
  background-color: rgb(103,194,58);
  border-radius: 8%;
  color: white;
  height: 40px;
  width: 70px;
  line-height: 40px;
  margin-right: 30px;
}

.report-card{
    margin-top: 2%;
    margin-left: 2%;
    margin-right: 2%;
}
</style>
