<template>
<!--评分排行界面-->
  <div>
    <div style="height: 75px"></div>
    <h1 style="margin-left: 5%;margin-bottom: 25px">{{year}}年{{mouth}}月{{day}}日排行榜</h1>
    <h2 style="margin-left: 5%;margin-bottom: 25px"><b style="color: #2376b7">{{scoreList[0].user.uname}}</b>占领了榜首，得分为<b style="color: #2376b7">{{scoreList[0].totalScore}}</b></h2>
    <h2 style="margin-left: 5%;margin-bottom: 25px" v-if = "this.rank === 0">&#128517;您当前暂未上榜</h2>
    <h2 style="margin-left: 5%;margin-bottom: 25px" v-else>您当前排名第<b style="color: #2376b7">{{rank}}</b>位</h2>
    <el-card v-for= "(item,index) in scoreList" :key="item.user.uid" class="score-card">
      <div slot="header" class="clearfix">
        <span v-if = "index === 0" style="color: red;font-size: 200%"><b>{{'No. ' + (index + 1) +"&nbsp&nbsp"+ item.user.uname}}</b></span>
        <span v-else-if = "index === 1" style="color: orange;font-size: 150%"><b>{{'No. ' + (index + 1) +"&nbsp&nbsp"+ item.user.uname}}</b></span>
        <span v-else-if = "index === 2" style="color: green;font-size: 125%"><b>{{'No. ' + (index + 1) +"&nbsp&nbsp"+ item.user.uname}}</b></span>
        <span v-else style="color: #2376b7">{{'No. ' + (index + 1) +"&nbsp&nbsp&nbsp&nbsp"+ item.user.uname}}</span>
        <el-button style="float: right; padding: 3px 0;color: #2376b7" type="text" @click="dialog(item.user)">个人信息</el-button>
      </div>
      <div style="margin-bottom: 10px"></div>
      <span v-if = "index === 0 || index === 1 || index === 2" style="font-size: 125%">{{item.user.intro}}</span>
      <span v-if = "index === 0" style="display: inline;float: right; margin-bottom:20px;color: red;font-size: 300%"><b>{{item.totalScore}}</b></span>
      <span v-else-if = "index === 1" style="display: inline;float: right; margin-bottom:20px;color: orange;font-size: 250%"><b>{{item.totalScore}}</b></span>
      <span v-else-if = "index === 2" style="display: inline;float: right; margin-bottom:20px;color: green;font-size: 200%"><b>{{item.totalScore}}</b></span>
      <span v-else style="display: inline;float: right; margin-bottom:20px;color: #2376b7;font-size: 150%">{{item.totalScore}}</span>
    </el-card>
    <el-dialog
      title="个人信息"
      :visible.sync="dialogVisible"
      width="30%">
      <div>{{"用户名称：" + dialogUserShow.uname}}</div>
      <div>{{"手机号码：" + dialogUserShow.phone}}</div>
      <div v-if = "dialogUserShow.ability === 'amateur'">{{"专业能力：业余"}}</div>
      <div v-if = "dialogUserShow.ability === 'normal'">{{"专业能力：普通"}}</div>
      <div v-if = "dialogUserShow.ability === 'professional'">{{"专业能力：专业"}}</div>
      <div>{{"任务偏好：" + dialogUserShow.preference}}</div>
      <div>{{"测试设备：" + dialogUserShow.device}}</div>
      <div>{{"个人简介：" + dialogUserShow.intro}}</div>
      <span slot="footer" class="dialog-footer">
    <el-button type="primary" @click="dialogVisible = false">确 定</el-button>
  </span>
    </el-dialog>
  </div>
</template>

<script>
import {getScoreList} from "../../api/user";

export default {
  name: "ScoreList",
  data(){
    return {
      year:0,
      mouth:0,
      day:0,
      rank:0,
      dialogVisible: false,
      dialogUserShow:{
        uname:"",
        phone:"",
        ability:"",
        preference:"",
        device:"",
        intro:""
      },
      scoreList:[]
    };
  },
  methods:{
    getScoreList(){
      getScoreList().then(res=>{
        console.log(res);
        this.scoreList = res;
        for (let i = 0; i < this.scoreList.length;i++){
          const uid = window.localStorage.getItem("uid");
          if(this.scoreList[i].user.uid === parseInt(uid)){
            this.rank = i + 1;
          }
        }
      }).catch(res=>{
        this.$message.error("排行榜获取失败"+res);
      })
    },
    dialog(user){
      this.dialogVisible = true;
      this.dialogUserShow = user;
    }
  },
  mounted() {
    const date = new Date();
    this.year = date.getFullYear();
    this.mouth = date.getMonth()+1;
    this.day = date.getDate();
    this.getScoreList();

  }
}
</script>

<style scoped>

.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}
.clearfix:after {
  clear: both
}

.score-card {
  margin-top: 2%;
  margin-left: 5%;
  margin-right: 5%;
}
</style>
