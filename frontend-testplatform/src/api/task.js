import axios from "axios";
import {TASK_MODULE} from "./_prefix";

/**
 * 创建测试任务 POST /task/create
 * @param {*} payload
 * @returns
 */
export const createTask = payload => {
  const {
    uid,              //发包方id
    taskName,         //测试任务标题
    taskIntro,        //测试任务简介
    taskTime,         //测试时间段
    numOfWorker,      //现有工人
    numOfNeedWorker,  //需要工人
    taskType,         //测试类型
    taskDifficulty,
    taskDevice
  } = payload;
  return axios
    .post(`${TASK_MODULE}/create`, {
      uid,
      taskName,
      taskIntro,
      taskTime,
      numOfWorker,
      numOfNeedWorker,
      taskType,
      taskDifficulty,
      taskDevice
    })
    .then(res => {
      return res.data;
    });
};

/**
 * 删除测试任务 POST /task/remove
 * @param {*} taskId
 * @returns
 */
export const removeTask = taskId => {
  return axios
    .post(`${TASK_MODULE}/remove`, {
      taskId
    })
    .then(res => {
      return res.data;
    });
};
/**
 * 获取所有测试任务 GET task/getAll/
 * @returns
 */
export const getAllTask = () =>{
  return axios
    .get(`${TASK_MODULE}/getAll`)
    .then(res => {
      return res.data;
    });
}
/**
 * 获取正在招募的测试任务 GET task/getUnfinished/
 * @returns
 */
export const getUnfinishedTask = () =>{
  return axios
    .get(`${TASK_MODULE}/getUnfinished`)
    .then(res => {
      return res.data;
    });
}
/**
 * 获取用户发布或者选取的测试任务 GET task/uid/{uid}
 * @param {*} uid
 * @returns
 */
export const getTaskByUser = uid =>{
  return axios
    .get(`${TASK_MODULE}/uid/${uid}`)
    .then(res => {
      return res.data;
    });
}
/**
 * 获取某一测试任务信息 GET task/taskId/{taskId}
 * @param {*} taskId
 * @returns
 */
export const getTaskById = taskId =>{
  return axios
    .get(`${TASK_MODULE}/taskId/${taskId}`,taskId)
    .then(res => {
      return res.data;
    });
}

/**
 * 获取推荐任务列表 GET task/recommend{uid}
 * @param {*} uid
 * @returns
 */
export const getRecommendTask = uid =>{
  return axios
    .get(`${TASK_MODULE}/recommend/${uid}`,uid)
    .then(res => {
      return res.data;
    });
}

/**
 * 设置任务推荐规则 POST task/rule/{rule}
 * @param {*} rule
 * @returns
 */
export const changeRule = rule =>{
  console.log(rule)
  return axios
    .post(`${TASK_MODULE}/rule/${rule}`,rule)
    .then(res => {
      return res.data;
    });
}
