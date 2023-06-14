import axios from "axios";
import { USER_MODULE } from "./_prefix";

/**
 * 用户注册 POST /user/register
 * @param {*} payload
 * @returns
 */
export const register = payload => {
  console.log(payload);
  const { uname, phone, password, userRole,ability,preference,device,intro} = payload;
  console.log(preference);
  return axios
    .post(`${USER_MODULE}/register`, {
      uname,      //用户名
      phone,      //手机号
      password,   //密码
      userRole,    //用户身份
      ability,
      preference,
      device,
      intro
    })
    .then(res => {
      return res.data;
    });
};

/**
 * 更新用户信息 POST /user/update/{uid}
 * @param {*} payload
 * @returns
 */
export const updateUser = payload => {
  const { uid,uname, phone, password, userRole,ability,preference,device,intro} = payload;
  return axios
    .post(`${USER_MODULE}/update/${uid}`, {
      uid,
      uname,      //用户名
      phone,      //手机号
      password,   //密码
      userRole,    //用户身份
      ability,//专业能力
      preference,//任务偏好
      device,//测试设备
      intro
    })
    .then(res => {
      return res.data;
    });
};

/**
 *  用户登录 POST /user/login
 * @param {*} payload
 * @returns
 */
export const login = payload => {
  const { phone, password } = payload;
  return axios.post(`${USER_MODULE}/login`, { phone, password }).then(res => {
    return res.data;
  });
};

/**
 *  获取用户信息 GET /user/{uid}
 * @param {*} uid
 * @returns
 */
export const getUser = uid => {
  return axios.get(`${USER_MODULE}/${uid}`).then(res => {
    return res.data;
  });
};

/**
 *  用户选择某一测试任务 POST /user/choose
 * @param {*} payload
 * @returns
 */
export const chooseTask = payload => {
  const { uid, taskId } = payload;
  return axios.post(`${USER_MODULE}/choose`, { uid, taskId }).then(res => {
    return res.data;
  });
};

/**
 *  获取用户活跃度 GET /user/active/{uid}
 * @param {*} uid
 * @returns
 */
export const getActive = uid => {
  return axios.get(`${USER_MODULE}/active/${uid}`).then(res => {
    return res.data;
  });
};

/**
 *  获取用户评分列表 GET /user/getScore/{uid}
 * @param {*} uid
 * @returns
 */
export const getScore = uid => {
  return axios.get(`${USER_MODULE}/getScore/${uid}`).then(res => {
    return res.data;
  });
};

/**
 *  获取用户评分列表 GET /user/getScoreList
 * @returns
 */
export const getScoreList = () => {
  return axios.get(`${USER_MODULE}/getScoreList`).then(res => {
    return res.data;
  });
};
