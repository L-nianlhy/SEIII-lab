import Vue from 'vue'
import VueRouter from 'vue-router'
//引入login组件
import Login from "../views/user/Login.vue"
import Register from "../views/user/Register.vue"
import employerLayout from "../layouts/employerLayout";
import workerLayout from "../layouts/workerLayout";
import EmployerHome from "../views/user/EmployerHome";
import WorkerHome from "../views/user/WorkerHome";
import TaskDetail from "../views/task/TaskDetail";
import AllTask from "../views/admin/AllTask";
import TaskCreate from "../views/task/TaskCreate";
import UserCenter from "../views/user/UserCenter";
import CreatedTask from "../views/task/CreatedTask";
import ReportDetail from "../views/testReport/ReportDetail";
import ReportList from "../views/testReport/ReportList";
import ReportCreate from "../views/testReport/ReportCreate";
import adminLayout from "../layouts/adminLayout";
import selectedTask from "../views/task/selectedTask";
import ScoreList from "../views/user/ScoreList";


Vue.use(VueRouter)

const routes = [{
        path: "/",
        redirect: "/login"
    },
    {
        path: "/login",
        name: "Login",
        component: Login
    },
    {
        path: "/register",
        name: "Register",
        component: Register
    },
    {
      path: "/admin",
      name: "admin",
      component: adminLayout,
      children: [
        {
          path: "/",
          name: "AllTask",
          component: AllTask
        },
        {
          path: "taskDetail/:taskId",
          name: "AdminTaskDetail",
          component: TaskDetail
        },
        {
          path: "scoreList",
          name: "ScoreList",
          component: ScoreList
        }
      ]
    },
    {
        path: "/employer",
        name: "Employer",
        component: employerLayout,
        children: [
          {
            path: "/",
            name: "EmployerHome",
            component: EmployerHome
          },
          {
            path: "taskDetail/:taskId",
            name: "EmployerTaskDetail",
            component: TaskDetail
          },
          {
            path: "taskCreate",
            name: "TaskCreate",
            component: TaskCreate
          },
          {
            path: "userCenter",
            name: "EmployerUserCenter",
            component: UserCenter
          },
          {
            path: "createdTask",
            name: "CreatedTask",
            component: CreatedTask
          },
          {
            path: "reportDetail/:reportId",
            name: "EmployerReportDetail",
            component: ReportDetail
          },
          {
            path: "reportList/:taskId",
            name: "EmployerReportList",
            component: ReportList
          },
          {
            path: "scoreList",
            name: "ScoreList",
            component: ScoreList
          }]
    },
    {
        path: "/worker",
        name: "worker",
        component: workerLayout,
        children: [
          {
            path: "/",
            name: "WorkerHome",
            component: WorkerHome
          },
          {
            path: "userCenter",
            name: "workerUserCenter",
            component: UserCenter
          },
          {
            path: "taskDetail/:taskId",
            name: "WorkerTaskDetail",
            component: TaskDetail
          },
          {
            path: "selectedTask",
            name: "selectedTask",
            component: selectedTask
          },
          {
            path: "reportCreate/:taskId",
            name: "ReportCreate",
            component: ReportCreate
          },
          {
            path: "reportDetail/:reportId",
            name: "WorkerReportDetail",
            component: ReportDetail
          },
          {
            path: "reportList/:taskId",
            name: "WorkerReportList",
            component: ReportList
          },
          {
            path: "scoreList",
            name: "ScoreList",
            component: ScoreList
          }]
    }
];

const router = new VueRouter({
    routes
})

export default router
