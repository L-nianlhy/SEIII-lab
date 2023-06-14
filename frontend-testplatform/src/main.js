// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import './assets/css/global.css'
import './assets/font/iconfont.css'
import './assets/font/faceicon.css'
Vue.use(ElementUI)

//导入axios进行跨域
import axios from 'axios'
//挂载axios
Vue.prototype.$http = axios
    //设置访问根路径
//axios.defaults.baseURL = "http://106.14.182.244:9000"
axios.defaults.baseURL = "http://localhost:9000"

Vue.config.productionTip = false

/* eslint-disable no-new */
new Vue({
    el: '#app',
    router,
    components: { App },
    template: '<App/>'
})
