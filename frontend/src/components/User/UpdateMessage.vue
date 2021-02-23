<template>
</template>


<script>//本文件只管用户的信息更新事务，与template无关
import axios from "axios";
export default {

  name: "UpdateMessage",

  components:{

  },

  data() {
    return{
      dialogFormVisible: false,
    }
  },
  /***
   * 提供单一的更改姓名、更改地址、更改个性签名、更改用户个人头像方法
   * 暂时未提供更改用户密码功能
   */
  methods: {
    //1.设置可见度
    open(){
      this.dialogFormVisible = true;
    },

    //重新拉取用户信息
    getUserMessage(id){
      let _this = this;
      console.log(id);
      //1.发送axios请求
      axios.get('/users/getByID', {
         params: {
          userID: id,
        },
      }).then(function (response) {
        //2.将response.data结果存储到store里
        _this.$store.commit('updateUserMessage',response.data);
        //3.更新信息成功反馈
        _this.$message({
          message: '成功更改用户信息~',
          type: 'success'
        });
      }).catch(function (error) {
        console.log(error);
      })
    },

    updateUserName(name) {//post和put的区别是什么?
      console.log("正在更改用户名为"+ name);
      let _this = this;
      //axios post body json
      axios.post('/users/update', {
        userID: _this.$store.state.user.userID,
        userName: name,
      }).then(function (response) {//axios函数function返回的response究竟包括什么?
        //打印response信息
        console.log(response);
        //重新拉取用户信息并更改store
        _this.getUserMessage(_this.$store.state.user.userID);
      }).catch(function (error) {
        console.log(error);
      })
    },

    updateUserAddress(address){
      let _this = this;
      //axios post body json
      axios.post('/users/update', {
        userID: _this.$store.state.user.userID,
        address: address,
      }).then(function (response) {//axios函数function返回的response究竟包括什么?
        //打印response信息
        console.log(response);
        //重新拉取用户信息并更改store
        _this.getUserMessage(_this.$store.state.user.userID);
      }).catch(function (error) {
        console.log(error);
      })
    },

    updateUserSignature(signature){
      let _this = this;
      //axios post body json
      axios.post('/users/update', {
        userID: _this.$store.state.user.userID,
        signature: signature,
      }).then(function (response) {//axios函数function返回的response究竟包括什么?
        //打印response信息
        console.log(response);
        //重新拉取用户信息并更改store
        _this.getUserMessage(_this.$store.state.user.userID);
      }).catch(function (error) {
        console.log(error);
      })
    },

    updateUserProfilePicture(profile_picture){
      let _this = this;
      //axios post body json
      axios.post('/users/update', {
        userID: _this.$store.state.user.userID,
        profile_picture: profile_picture,
      }).then(function (response) {//axios函数function返回的response究竟包括什么?
        //打印response信息
        console.log(response);
        //重新拉取用户信息并更改store
        _this.getUserMessage(_this.$store.state.user.userID);
      }).catch(function (error) {
        console.log(error);
      })
    },
  }
}
</script>

<style scoped>

</style>
