<template>
  <div>
    <el-form :model="ruleForm" status-icon :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
      <el-form-item label="账号" prop="loginName">
        <el-input v-model="ruleForm.loginName" auto-complete="true"></el-input>
      </el-form-item>
      <el-form-item label="密码" prop="pass">
        <el-input type="password" v-model="ruleForm.pass" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="确认密码" prop="checkPass">
        <el-input type="password" v-model="ruleForm.checkPass" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item>
        <!--        <el-button type="primary" @click="login(this.$refs.ruleForm.loginName, this.$refs.ruleForm.pass)">登录</el-button>-->
        <!--        <el-button @click="registe(this.$refs.ruleForm.loginName, this.$refs.ruleForm.pass)">注册</el-button>-->
        <el-button type="primary" @click="login">登录</el-button>
        <el-button @click="registe">注册并登录</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>
<script>
  import store from '../../store/store';
  import axios from 'axios';

  export default {

    data() {


      var loginNameValidate = (rule, value, callback) => {
        if (!this.loginNameRegex.test(value)) {
          callback(new Error('账号长度需10位数字'));
        } else {
          if (this.ruleForm.checkPass !== '') {
            this.$refs.ruleForm.validateField('checkPass');
          }
          callback();
        }
      };

      var validatePass = (rule, value, callback) => {
        if (!this.passRegex.test(value)) {
          callback(new Error('请输入6~16位的密码'));
        } else {
          if (this.ruleForm.checkPass !== '') {
            this.$refs.ruleForm.validateField('checkPass');
          }
          callback();
        }
      };
      var validatePass2 = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请再次输入密码'));
        } else if (value !== this.ruleForm.pass) {
          callback(new Error('两次输入密码不一致!'));
        } else {
          callback();
        }
      };
      return {
        ruleForm: {
          loginName: '',
          pass: '',
          checkPass: '',
        },
        rules: {
          loginName: [
            {validator: loginNameValidate, trigger: 'blur'}
          ],

          pass: [
            {validator: validatePass, trigger: 'blur'}
          ],
          checkPass: [
            {validator: validatePass2, trigger: 'blur'}
          ],
        },
        loginNameRegex: /\d{10}/,
        passRegex: /.{6,16}/,
      };
    },

    methods: {
      submitForm(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            alert('submit!');
            return true;
          } else {
            console.log('error submit!!');
            return false;
          }
        });
      }
      ,
      resetForm(formName) {
        this.$refs[formName].resetFields();
      },

      submitValidate(inputLoginname, inputPassword) {
        let _this = this;
        if (!this.loginNameRegex.test(inputLoginname)) {
          console.log("submitValidate: 用户名格式错误!");
          _this.$notify({
            title: '用户名格式错误!',
            message: '用户名应当为十位数字',
            type: 'warning'
          });
          return false;
        }
        if (!this.passRegex.test(inputPassword)) {
          console.log("submitValidate: 密码格式错误!");
          _this.$notify({
            title: '密码格式错误!',
            message: '密码应当6~16位',
            type: 'warning'
          });
          return false;
        }
        return true;
      },

      //axios
      registe() {
        //loading动画
        let inputLoginName = this.ruleForm.loginName;
        let inputPassword = this.ruleForm.pass;
        let _this = this;
        //提交验证
        console.log("registe axios: 用户名和密码分别为——" + inputLoginName + " " + inputPassword);
        if (!this.submitValidate(inputLoginName, inputPassword)) {
          return;
        }
        axios.post("users/registe", null, {
          params: {
            login_name: inputLoginName,
            pass_word: inputPassword,
          }
        }).then(function (response) {
          console.log("注册了一个用户,用户名为: " + inputLoginName, " 密码为: " + inputPassword);
          //注册完后显示登陆成功
          _this.login();
        }).catch(function (error) {
          _this.$message({
            message: '该用户名已注册',
            type: 'error',
            center: true,
          });
        })
      },

      login() {
        let inputLoginName = this.ruleForm.loginName;
        let inputPassword = this.ruleForm.pass;
        let _this = this;
        //提交验证
        console.log("login axios: 用户名和密码分别为——" + inputLoginName + " " + inputPassword);
        if (!this.submitValidate(inputLoginName, inputPassword)) {
          return;
        }

        this.submitValidate(inputLoginName, inputPassword);
        axios.get("/users/get", {
          params: {
            login_name: inputLoginName,
            password: inputPassword,
          }
        }).then(function (response) {
          //如何处理错误，放到catch中?
          let user = response.data;
          console.log("登录请求成功,用户为: " + user.login_name + " " + user.pass_word);
          //设置vuex
          _this.$store.commit('login', response.data);
          //登陆成功并:
          //(1)设置当前dialog不可见
          //(2)弹出登陆成功提示
          // this.$parent.loginSuccessfully();
          _this.$emit('loginSuccessfully');
          //(3)sidebar重新渲染
          console.log('试一试');
        }).catch(function (error) {
          if (error.response) {
            // The request was made and the server responded with a status code
            // that falls out of the range of 2xx
            console.log(error.response.data);
            console.log(error.response.status);
            console.log(error.response.headers);
            if (error.response.status == 500) {
              //用户名不存在;
              _this.$notify.error({
                title: '用户名不存在',
              });
            } else if (error.response.status == 501) {
              //用户密码错误!
              _this.$notify.error({
                title: '密码错误',
              });
            }
          } else if (error.request) {
            // The request was made but no response was received
            // `error.request` is an instance of XMLHttpRequest in the browser and an instance of
            // http.ClientRequest in node.js
            console.log(error.request);
          } else {
            // Something happened in setting up the request that triggered an Error
            console.log('Error', error.message);
          }
          console.log(error.config);
        })
      }
    }
  }
</script>
