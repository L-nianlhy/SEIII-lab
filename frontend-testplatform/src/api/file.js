import axios from "axios";
import {FILE_MODULE} from "./_prefix";

/**
 * 下载待测应用 GET /file/downloadApp/{taskId}
 * @param {*} taskId
 * @returns
 */
export const downloadApp = taskId => {
  return axios
    .get(`${FILE_MODULE}/downloadApp/${taskId}`)
    .then(res => {
      return res.data;
    });
};

/**
 * 下载测试文档 GET /file/downloadApp/{taskId}
 * @param {*} taskId
 * @returns
 */
export const downloadDoc = taskId => {
  return axios
    .get(`${FILE_MODULE}/downloadDoc/${taskId}`)
    .then(res => {
      return res.data;
    });
};

/**
 * 下载缺陷截图 GET /file/downloadPhoto/{reportId}
 * @param {*} reportId
 * @returns
 */
export const downloadPhoto = reportId => {
  return axios
    .get(`${FILE_MODULE}/downloadPhoto/${reportId}`)
    .then(res => {
      return res.data;
    });
};
/**
 * 上传待测应用 POST /file/uploadApp/{taskId}
 * @param {*} taskId
 * @param taskApp
 * @returns
 */
export const uploadApp = (taskId,taskApp) => {
  const formData = new FormData();
  formData.append("taskApp",taskApp);
  return axios
    .post(`${FILE_MODULE}/uploadApp/${taskId}`, formData)
    .then(res => {
      return res.data;
    });
};

/**
 * 下载测试文档 GET /file/downloadApp/{taskId}
 * @param {*} taskId
 * @param taskDoc
 * @returns
 */
export const uploadDoc = (taskId,taskDoc) => {
  const formData = new FormData();
  formData.append("taskDoc",taskDoc);
  return axios
    .post(`${FILE_MODULE}/uploadDoc/${taskId}`,formData)
    .then(res => {
      return res.data;
    });
};

/**
 * 上传缺陷截图 POST /file/uploadPhoto/{reportId}
 * @param {*} reportId
 * @param bugPhoto
 * @returns
 */
export const uploadPhoto = (reportId,bugPhoto) => {
  const formData  = new FormData();
  formData.append("bugPhoto",bugPhoto)
  console.log(formData)
  return axios
    .post(`${FILE_MODULE}/uploadPhoto/${reportId}`,formData)
    .then(res => {
      return res.data;
    });
};
