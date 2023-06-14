import axios from "axios";
import {TEST_REPORT_MODULE} from "./_prefix";

/**
 * 创建测试报告 POST /test_report/create
 * @param {*} payload
 * @returns
 */
export const createReport = payload => {
  const {
    uid,//众包工人id
    taskId,//测试任务id
    cooperationId,
    bugIntro,//缺陷情况说明
    bugStep,//缺陷复现步骤
    deviceInformation//测试设备信息
  } = payload;
  return axios
    .post(`${TEST_REPORT_MODULE}/create`, {
      uid,
      taskId,
      cooperationId,
      bugIntro,
      bugStep,
      deviceInformation
    })
    .then(res => {
      return res.data;
    });
};

/**
 * 删除测试报告 POST /test_report/remove
 * @param {*} reportId
 * @returns
 */
export const removeReport = reportId => {
  return axios
    .post(`${TEST_REPORT_MODULE}/create`, {
      reportId
    })
    .then(res => {
      return res.data;
    });
};
/**
 * 获取某一任务的测试报告 GET /test_report/getReport/{taskId}
 * @param taskId
 * @returns
 */
export const getReportByTask = taskId => {
  return axios
    .get(`${TEST_REPORT_MODULE}/getReport/${taskId}`)
    .then(res => {
      return res.data;
    });
};
/**
 * 获取某一测试报告信息 GET /test_report/reportId/{reportId}
 * @param reportId
 * @returns
 */
export const getReportById = reportId => {
  return axios
    .get(`${TEST_REPORT_MODULE}/reportId/${reportId}`)
    .then(res => {
      return res.data;
    });
};
/**
 * 获取相似的测试报告 GET /test_report/getSimilar/${reportId}
 * @returns
 * @param reportId
 */
export const getSimilarReport = reportId => {
  return axios
    .get(`${TEST_REPORT_MODULE}/getSimilar/${reportId}`)
    .then(res => {
      return res.data;
    });
};
/**
 * 获取协作的测试报告 GET /test_report/getCooperation/${reportId}
 * @returns
 * @param reportId
 */
export const getCooperationReport = reportId => {
  return axios
    .get(`${TEST_REPORT_MODULE}/getCooperation/${reportId}`)
    .then(res => {
      return res.data;
    });
};
