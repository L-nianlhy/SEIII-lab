import axios from "axios";
import {COMMENT_MODULE} from "./_prefix";

/**
 * 创建评论 POST /comment/create
 * @param {*} payload
 * @returns
 */
export const createComment = payload => {
  const {
    uid,
    uname,
    reportId,
    score,
    content,
    timeStamp
  } = payload;
  console.log(payload);
  return axios
    .post(`${COMMENT_MODULE}/create`, {
      uid,
      uname,
      reportId,
      score,
      content,
      timeStamp
    })
    .then(res => {
      return res.data;
    });
};


/**
 * 获取评价列表 GET /comment/getComment/{reportId}
 * @param {*} reportId
 * @returns
 */
export const getComment = reportId =>{
  return axios
    .get(`${COMMENT_MODULE}/getComment/${reportId}`)
    .then(res => {
      return res.data;
    });
}
