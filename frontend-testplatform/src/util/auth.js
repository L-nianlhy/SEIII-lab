//用于测试，2为admin,1为employer，0为worker
const rule = 0;
export const judgeLogin = () => {
  return !!window.localStorage.getItem("uid");
};

export const judgeWorker = () => {
  return judgeLogin() && window.localStorage.getItem("userRole") === "worker";
};

export const judgeEmployer = () => {
  return judgeLogin() && window.localStorage.getItem("userRole") === "employer";
};

export const judgeAdmin = () => {
  return judgeLogin() && window.localStorage.getItem("userRole") === "admin";
};
