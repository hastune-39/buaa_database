<template>
  <div>
    <div v-for="(follow,index) in followList" :key="index">
        <FollowItem :status="followList[index].status" :painter-info="painterInfo[index]" :picturesInfo="picturesInfo[index]"
        @addFollow="addFollow" @cancelFollow = "cancelFollow" ref="getFollowStatus"></FollowItem>
    </div>

    <el-row :gutter="20">
      <el-col :span="24">
        <div class="grid-content"></div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
  import axios from 'axios';
  import FollowItem from './FollowItem';

  export default {
    name: "FollowList",

    components: {
      FollowItem
    },

    data() {
      return {
        followList: [
          {
            painter_id: Number,
            followTime: String,
            status: Boolean,
          }
        ],


        painterInfo: [
          {
            painter_id: Number,
            userName: "loading...",
            address: "loading...",
            signature: "loading...",
            profile_picture: "break.img",
          }
        ],

        picturesInfo: [
          {
            painter_id: Number,
            pictures: [
              {
                picture_id: Number,
                picture_address: String
              }
            ]
          }

        ],
      }


      // testIamges: [
      //   'static/img/Rolua/1.jpg',
      //   'static/img/Rolua/2.jpg',
      //   'static/img/Rolua/3.jpg',
      //   'static/img/Rolua/4.jpg',
      //   'static/img/Rolua/5.jpg',
      //   'static/img/Rolua/6.jpg'
      // ],
    },

    methods: {
      /***
       * 获取
       */
      //获取当前用户关注的画师id列表
      getPaintersID() {
        let _this = this;
        let user_id = _this.$store.state.user.userID;
        console.log("准备获取用户关注画师id...");
        //1.axios
        axios.get('/User/Follow', {
          params: {
            user_id: user_id,
          }
        }).then(function (response) {
          _this.followList = response.data;
          console.log("获取关注画师id成功！");
        }).catch(function (error) {
          console.log(error);
        })
      }
      ,

      //获取所有关注画师的信息
      getAllPaintersInfo() {
        let _this = this;
        let user_id = _this.$store.state.user.userID;
        console.log("正在获取画师信息...");
        //1.获取画师信息
        axios.get('/User/Follow/getPainters', {
          params: {
            user_id: user_id,
          }
        }).then(function (response) {
          console.log("获取画师信息成功!");
          _this.painterInfo = response.data;
        }).catch(function (error) {
          console.log(error);
        })
      }
      ,

      //根据画师id拉取画师信息
      getPainterInfo() {
        let _this = this;
        let user_id = _this.$store.state.user.userID;
        console.log("正在获取画师信息...");
        //1.获取画师信息
        axios.get('/User/Follow/getPictures', {
          params: {
            user_id: user_id,
          }
        }).then(function (response) {
          console.log("获取画师信息成功!");
          _this.picturesInfo = response.data;
        }).catch(function (error) {
          console.log(error);
        })
      }
      ,

      /***
       * 关注/取关
       */
      findPlace(painter_id){

      },

      addFollow(follower_id, painter_id){
        let _this = this;
        console.log("正在关注...");
        axios.post('/User/AddFollow',{
          follower_id: follower_id,
          painter_id: painter_id,
        }).then(function (response) {



          //加入列表
          //从列表中移除
          let index = 0;
          for(let i = 0;i<_this.followList.length;i++){
            if(_this.followList[i].painter_id < painter_id){
              index = i;
              break;
            }
          }

          _this.followList.slice(index,0,);
          _this.painterInfo.slice(index,1);
          _this.picturesInfo.slice(index,1);
          console.log("关注成功!");
        }).catch(function (error) {
          console.log(error);
        })

      },

      cancelFollow(follower_id, painter_id){
        let _this = this;
        console.log("正在取关...");
        axios.post('/User/CancelFollow',{
          follower_id: follower_id,
          painter_id: painter_id,
        }).then(function (response) {
          //从列表中移除
          let index = 0;
          for(let i = 0;i<_this.followList.length;i++){
            if(_this.followList[i].painter_id == painter_id){
              index = i;
              break;
            }
          }

          _this.followList.slice(index,1);
          _this.painterInfo.slice(index,1);
          _this.picturesInfo.slice(index,1);
          console.log("取关成功!");
        }).catch(function (error) {
          console.log(error);
        })


      }
    },


  }
</script>

<style scoped>
  .el-row {
    margin-bottom: 20px;

  &
  :last-child {
    margin-bottom: 0;
  }

  }
  .bg-purple {
    background: #d3dce6;
  }

  .grid-content {
    border-radius: 4px;
    min-height: 33px;
  }

  /*new*/



  /*个人头像*/
  .profile-picture {
  }

  .artist-item {
    width: 1044px;
    margin: 0 auto;
    display: flex;
    align-items: flex-start;
    list-style: none;
    padding: 24px 0px;
    border-bottom: 1px solid rgb(235, 235, 235);
  }

  /*.name {*/
  /*  font-size: 16px;*/
  /*  font-weight: 700;*/
  /*  color: #333;*/
  /*  text-decoration: none;*/
  /*}*/
  /*.address {*/
  /*  margin-top: 5px;*/
  /*  word-break: break-all;*/
  /*  font-size: 14px;*/
  /*  line-height: 20px;*/
  /*  color: #333;*/
  /*  overflow: hidden;*/
  /*  text-overflow: ellipsis;*/
  /*  display: -webkit-box;*/
  /*  -webkit-line-clamp: 4;*/
  /*  -webkit-box-orient: vertical;*/
  /*}*/
  /*.followed-button {*/
  /*  margin-top: 14px;*/
  /*}*/


</style>
