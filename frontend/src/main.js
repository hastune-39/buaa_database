// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import Antdv from 'ant-design-vue';
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import 'element-ui/lib/theme-chalk/display.css';
import 'ant-design-vue/dist/antd.css'; // or 'ant-design-vue/dist/antd.less'
import axios from 'axios'
import VueAxios from 'vue-axios'
import store from './store/store';

//global component
import mySearchBox from './view/Header/searchBox';
Vue.use(mySearchBox);
Vue.use(Antdv);
Vue.use(ElementUI);
Vue.use(VueAxios, axios);
Vue.config.productionTip = false;
Vue.prototype.$axios = axios  //???
axios.defaults.baseURL = 'http://localhost:2333' //基本配置

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  axios,
  store,
  render: h => h(App),
  components: { App },
  template: '<App/>'
})
