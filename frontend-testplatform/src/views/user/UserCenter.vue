<template>
<!--个人中心-->
  <div class="123">
    <div style="height: 75px"></div>
    <el-row>
      <el-col :span="12"><div class="grid-content bg-purple">
        <el-card class="avatar center">
          <div slot="header" class="clearfix">
            <span>个人名片</span>
          </div>
          <div v-if="this.userRole==='worker'">
            <el-avatar fit="fill" :size="200" :src="require('../../../static/photos/employer.jpg')" alt="picture"></el-avatar>
            <div style="color: #0077aa">{{this.UserInformation.intro}}</div>
          </div>
          <div v-if="this.userRole!=='worker'">
            <el-avatar fit="fill" :size="200" :src="require('../../../static/photos/boss.jpg')" alt="picture"></el-avatar>
          </div>
        </el-card>
        <el-card class="detail center">
          <div slot="header" class="clearfix">
            <span>详细资料</span>
            <el-button v-if="this.userRole!=='worker'" type="primary" style="float: right" @click="goToScoreList()">排行榜</el-button>
          </div>
          <el-descriptions :model="UserInformation"  :column="1" style="margin-left: 15%;margin-right: 15%;font-size: 100%">
            <el-descriptions-item label="用户名" >{{UserInformation.uname}}</el-descriptions-item>
            <el-descriptions-item label="手机号" >{{UserInformation.phone}}</el-descriptions-item>
            <el-descriptions-item label="用户身份" >{{UserInformation.userRoleShow}}</el-descriptions-item>
            <el-descriptions-item label="个性签名" v-if = "this.userRole === 'worker'">{{UserInformation.intro}}</el-descriptions-item>
            <el-descriptions-item label="专业能力" v-if = "this.userRole === 'worker'">{{UserInformation.abilityShow}}</el-descriptions-item>
            <el-descriptions-item label="任务偏好" v-if = "this.userRole === 'worker'">{{UserInformation.preference}}</el-descriptions-item>
            <el-descriptions-item label="测试设备" v-if = "this.userRole === 'worker'">{{UserInformation.device}}</el-descriptions-item>
            <el-descriptions-item label="用户活跃度" v-if = "this.userRole === 'worker'">
              <el-rate
                v-model="active"
                disabled
                :icon-classes="iconClasses"
                void-icon-class="icon-rate-face-off"
                show-text
                :texts = this.rateText
                :colors="['#99A9BF', '#F7BA2A', '#FF9900']">
              </el-rate>
            </el-descriptions-item>
          </el-descriptions>
          <el-button id = "updateButton" type= "primary" style="width: 100px; transform: translate(100%,0)" @click="dialogVisible = true" v-if = "this.userRole === 'worker'">修改</el-button>
        </el-card>
        <el-dialog title="修改用户信息" :visible.sync="dialogVisible" width="60%">
          <el-form ref="form" :model="UserInformation" label-width="80px">
            <el-form-item label="用户名">
              <el-input v-model="UserInformation.uname" disabled></el-input>
            </el-form-item>
            <el-form-item label="手机号">
              <el-input v-model="UserInformation.phone" disabled></el-input>
            </el-form-item>
            <el-form-item label="个性签名">
              <el-input v-model="UserInformation.intro"></el-input>
            </el-form-item>
            <el-form-item label="专业能力">
              <el-select v-model="ability" placeholder="专业能力">
                <el-option label="业余" value="amateur"></el-option>
                <el-option label="普通" value="normal"></el-option>
                <el-option label="专业" value="professional"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="任务偏好">
              <el-select v-model="UserInformation.preference" placeholder="任务偏好">
                <el-option label="功能测试" value="功能测试"></el-option>
                <el-option label="性能测试" value="性能测试"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="测试设备">
              <el-select v-model="UserInformation.device" placeholder="测试设备">
                <el-option label="Android" value="Android"></el-option>
                <el-option label="Linux" value="Linux"></el-option>
                <el-option label="IOS" value="IOS"></el-option>
                <el-option label="Windows" value="Windows"></el-option>
              </el-select>
            </el-form-item>
          </el-form>
          <span slot="footer" class="dialog-footer">
  <el-button @click="dialogVisible = false">取 消</el-button>
  <el-button id = "updateButton2" type="primary" @click="update" style="background-color: #2376b7">确 定</el-button>
</span>
        </el-dialog>
      </div></el-col>
      <el-col :span="12" v-if="this.userRole==='worker'"><div class="grid-content bg-purple-light">
        <el-card class="half center">
          <div slot="header" class="clearfix">
            <div style="float: left;line-height: 190%">能力测评</div>
            <div style="float: left;margin-left: 10%; line-height: 190%">总分：{{this.score[0]}}</div>
            <el-button type="primary" style="float: right" @click="goToScoreList()">排行榜</el-button>
          </div>
          <div id="painting">
            <canvas id="canvas"></canvas>
          </div>
          <div>
              <div>能力提升tips</div>
            <el-divider></el-divider>
              <div>
                <el-tooltip class="item" effect="dark" content="偷偷告诉你，一周内完成更多任务活跃度会更高哦" placement="bottom-start">
                <el-button>活跃度</el-button>
              </el-tooltip>
                <el-tooltip class="item" effect="dark" content="偷偷告诉你，完成更多的协作任务，协作能力会上升哦" placement="bottom">
                  <el-button>协作能力</el-button>
                </el-tooltip>
                <el-tooltip class="item" effect="dark" content="偷偷告诉你，为更多的报告打分，审查能力会上升哦" placement="bottom">
                  <el-button>审查能力</el-button>
                </el-tooltip>
                <el-tooltip class="item" effect="dark" content="偷偷告诉你，高质量的报告会让报告平均得分更高哦" placement="bottom">
                  <el-button>报告平均得分</el-button>
                </el-tooltip>
                <el-tooltip class="item" effect="dark" content="偷偷告诉你，自己完成报告会让查重率更低哦" placement="bottom-end">
                  <el-button>查重率</el-button>
                </el-tooltip></div>
          </div>
        </el-card>
        <el-card class="half center" v-if="this.abilityScore>this.score[0]">
<!--         用户填写的能力大于用户的总分-->
          <el-avatar  fit="fill" :size="50" :src="require('../../../static/photos/huangdou.jpg')"></el-avatar>
          <p>您近期的表现与资料中填写的能力描述不符<br>建议您前往详细资料进行修改</p>
        </el-card>
        <el-card class="half center" v-if="this.abilityScore<this.score[0]">
<!--          用户填写的能力小于用户的总分-->
          <el-avatar  fit="fill" :size="50" :src="require('../../../static/photos/haobang.jpg')"></el-avatar>
          <p>欢迎大神加入网站，期待您更高水平的创作</p>
        </el-card>
      </div></el-col>
      <el-col :span="12" v-if="this.userRole==='employer'">
        <div class="grid-content bg-purple-light">
          <el-card class="half center">
            欢迎老板！
          </el-card>
        </div>
      </el-col>
    </el-row>

  </div>
</template>

<script>
import {judgeAdmin, judgeEmployer, judgeWorker} from "../../util/auth";
import {getActive, getScore, getUser, updateUser} from "../../api/user";

export default {
  name: "UserCenter",
  data(){
    return{
      UserInformation:{
        uname: "admin",
        phone:"12345678999",
        userRoleShow:"",
        abilityShow:"",
        preference:"",
        device:"",
        intro:""
      },
      password:"",
      userRole:"worker",
      ability:"",
      abilityScore: 20,
      dialogVisible: false,
      active:2,
      rateText:['极不活跃','较不活跃','普通','较活跃','极活跃'],
      iconClasses: ['icon-rate-face-1', 'icon-rate-face-2', 'icon-rate-face-3'],
      score:[],//顺序：总分、活跃度得分、报告协作得分、报告审查得分、报告平均得分、报告查重得分
    }
  },
  methods:{
    drawCanvas(){
      const canvas = document.querySelector("#canvas");
      const paint = document.querySelector("#painting");
      const paintWidth = paint.offsetWidth;
      const context = canvas.getContext('2d');
      canvas.width = paintWidth;
      canvas.height = paintWidth;
      const ORIGIN_X = paintWidth/2; // 原点横坐标
      const ORIGIN_Y = paintWidth/2; // 原点纵坐标
      const BG_STROKE_COLOR = "rgb(121,121,121)"; // 背景填充的颜色连线的颜色
      const BG_FILL_COLOR = "rgb(242,242,242)"; // 背景
      const RADIUS = paintWidth/2*0.7; // 图形的最外层半径
      const RING_NUMS = 5; // 图形的环数
      const FULL_VALUE = 100; // 数据的满值
      const FONT_COLOR = "rgb(121,121,121)"; // 文字颜色
      const POWER_BG_COLOR = "rgba(0,121,121,.4)"; // 能力图背景填充颜色
      const POWER_POINT_COLOR = "rgb(0,121,121)"; // 能力图点的颜色
      const POWER_STROKE_COLOR = "rgb(0,121,121)"; // 能力图连线的颜色
      let data = [{
        name: "活跃度",
        value: this.score[1]
      }, {
        name: "协作能力",
        value: this.score[2]
      }, {
        name: "审查能力",
        value: this.score[3]
      }, {
        name: "报告平均得分",
        value: this.score[4]
      }, {
        name: "查重率",
        value: this.score[5]
      }]
      // 绘制环形背景  参数为环形中心横坐标，纵坐标，环形最外层半径，环数，数据，线条颜色，填充颜色
      CanvasRenderingContext2D.prototype.drawRing = function(x, y, r, ringNum, data, strokeColor = "rgba(0,0,0)", fillColor = "rgb(242,242,242)") {
        const PER_RADIUS = r / ringNum;
        for (let i = ringNum; i > 0; i--) {
          this.beginPath();
          this.strokeStyle = strokeColor;
          if (i % 2 === 0) {
            this.arc(x, y, PER_RADIUS * i, 0, 2 * Math.PI);
            this.fillStyle = "rgb(255,255,255)";
            this.stroke();
            this.fill();
          } else {
            this.arc(x, y, PER_RADIUS * i, 0, 2 * Math.PI);
            this.fillStyle = fillColor;
            this.stroke();
            this.fill();
          }
          this.closePath();
        }
        const PER_ANGLE = 2 * Math.PI / data.length;
        for (let i = 0; i < data.length; i++) {
          this.beginPath();
          this.strokeStyle = strokeColor;
          this.moveTo(x, y);
          this.lineTo(x + r * Math.sin(PER_ANGLE * i), y - r * Math.cos(PER_ANGLE * i));
          this.stroke();
          this.closePath();
        }
      }
      // 绘制能力形状  参数为环形中心横坐标，纵坐标，环形最外层半径，数据，能力图线条颜色，能力图填充颜色，能力图点颜色，满值
      CanvasRenderingContext2D.prototype.drawPowerShape = function(x, y, r, data, strokeColor, fillColor, pointColor, fullValue = FULL_VALUE) {
        if (!data || data.length < 3) {
          throw new Error("An array with a length of more than 3 should be passed as the parameter")
        }
        this.beginPath();
        const PER_ANGLE = 2 * Math.PI / data.length;
        for (let i = 0; i < data.length; i++) {
          const DATA_RADIUS = data[i].value / fullValue * r;
          new Promise(resolve => {
            this.lineTo(x + DATA_RADIUS * Math.sin(PER_ANGLE * i), y - DATA_RADIUS * Math.cos(PER_ANGLE * i))
            resolve(DATA_RADIUS);
          }).then(radius => { // 通过promise异步保证点的绘制在能力图背景的绘制之后
            this.beginPath();
            this.arc(x + radius * Math.sin(PER_ANGLE * i), y - radius * Math.cos(PER_ANGLE * i), 3, 0, Math.PI * 2);
            this.fillStyle = pointColor;
            this.fill();
            this.closePath();
          })
        }
        this.lineTo(x, y - data[0].value / fullValue * r)
        this.fillStyle = fillColor;
        this.strokeColor = strokeColor;
        this.fill();
        this.stroke();
        this.closePath();
      }
      // 绘制文字  参数为环形中心横坐标，纵坐标，文字绘制起点所在圆半径，数据，文字颜色
      CanvasRenderingContext2D.prototype.drawText = function(x, y, r, data, fontColor = "rgb(121,121,121)") {
        if (!data || data.length < 3) {
          throw new Error("An array with a length of more than 3 should be passed as the parameter")
        }
        const PER_ANGLE = 2 * Math.PI / data.length;
        this.fillStyle = fontColor
        for (let i = 0; i < data.length; i++) {
          this.beginPath();
          this.textAlign = "center";
          this.textBaseline = "middle";
          this.font = "15px Adobe Ming Std";
          this.fillText(data[i].name, x + r * Math.sin(PER_ANGLE * i), y - r * Math.cos(PER_ANGLE * i));
          this.fill();
          this.closePath();
        }
      }
      context.drawRing(ORIGIN_X, ORIGIN_Y, RADIUS, RING_NUMS, data, BG_STROKE_COLOR, BG_FILL_COLOR);
      context.drawPowerShape(ORIGIN_X, ORIGIN_Y, RADIUS, data, POWER_STROKE_COLOR, POWER_BG_COLOR, POWER_POINT_COLOR);
      context.drawText(ORIGIN_X, ORIGIN_Y, RADIUS + 25, data, FONT_COLOR)
    },
    returnBack(){
      this.$router.go(-1);
    },
    update(){
      this.dialogVisible = false;
      updateUser({
        uid:window.localStorage.getItem("uid"),
        uname:this.UserInformation.uname,
        phone:this.UserInformation.phone,
        password:this.password,
        userRole:this.userRole,
        ability:this.ability,
        preference:this.UserInformation.preference,
        device:this.UserInformation.device,
        intro:this.UserInformation.intro,
      }).then(res=>{
        if(res.code === 1){
          this.$message.success("修改成功");
          location.reload();
        }
        else{
          this.$message.error("修改失败：" + res.msg);
        }
      }).catch(res=>{
        this.$message.error("修改失败："+res);
      });
    },
    goToScoreList(){
      if(this.userRole==="worker"){
        const{href} = this.$router.resolve({
          path:`/worker/scoreList`
        });
        window.open(href,'_blank');
      }else if(this.userRole==="employer"){
        const{href} = this.$router.resolve({
          path:`/employer/scoreList`
        });
        window.open(href,'_blank');
      }
      else{
        const{href} = this.$router.resolve({
          path:`/admin/scoreList`
        });
        window.open(href,'_blank');
      }
    }
  },
  mounted() {
    getUser(window.localStorage.getItem("uid")).then(res=>{
      this.UserInformation.uname = res.uname;
      this.UserInformation.phone = res.phone;
      this.userRole = res.userRole;
      this.ability = res.ability;
      this.UserInformation.preference = res.preference;
      this.UserInformation.device = res.device;
      this.password = res.password;
      this.UserInformation.intro = res.intro;
      switch (this.userRole){
        case "employer":
          this.UserInformation.userRoleShow = "发包方";
          break;
        case "worker":
          this.UserInformation.userRoleShow = "众包工人";
          break;
        default:break;
      }
      switch (this.ability){
        case "amateur":
          this.UserInformation.abilityShow = "业余";
          this.abilityScore=30;
          break;
        case "normal":
          this.UserInformation.abilityShow = "普通";
          this.abilityScore=60;
          break;
        case "professional":
          this.UserInformation.abilityShow = "专业";
          this.abilityScore=85;
          break;
        default:
          break;
      }
      if(judgeWorker()){
        getActive(window.localStorage.getItem("uid")).then(res => {
          switch (res) {
            case 'least':
              this.active = 1;
              break;
            case 'less':
              this.active = 2;
              break;
            case 'normal':
              this.active = 3;
              break;
            case 'more':
              this.active = 4;
              break;
            case 'most':
              this.active = 5;
              break;
            default:
              break;
          }
        }).catch(res => {
          this.$message.error("用户活跃度获取失败：" + res);
        })
        getScore(window.localStorage.getItem("uid")).then(res=>{
          this.score = res;
          this.drawCanvas();
        }).catch(res=>{
          this.$message.error("用户评分获取失败："+res);
        })
      }
    }).catch(res=>{
      this.$message.error("用户信息获取失败："+res);
    })
  },
}
</script>
<style lang="less" scoped>
.item{
  margin: 10px;
}
#user-center-title{
  margin: 2% 10% 2% 15%;
  font-size: 200%;
}
.centerTable{
  margin-top: 5%;
  width: 100%;
}
.center{
  margin:10% 10%;
}
#updateButton{
  margin-left: 40%;
}
.half{
  height: 100%;
}
.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}
.clearfix:after {
  clear: both
}

</style>
