<template>
  <el-card class="artist-list"
           shadow="hover"
           style="margin-top: 0px; margin-bottom: 20px; padding: 5px">
    <el-row :gutter="20" style="margin: 0px">
      <!--个人头像,1.圆形图标：el-circle, 2.调整图标大小80px-->
      <el-col :span="3" style="margin-top: 10px">
        <img src="static/profilePicture/2.jpg"
             class="el-avatar el-avatar--circle"
             style="height: 80px; width: 80px"
             @click="skipToPainterPage">
        <el-button v-if="status"
                   type="primary"
                   round class="button"
                   @click="subCancelFollow">已关注
        </el-button>
        <el-button v-else
                   type="primary"
                   round class="button"
                   @click=subAddFollow>关注
        </el-button>
      </el-col>
      <!--      个人介绍-->
      <el-col :span="6" style="margin-top: 10px">
        <div>
          <div class="username">{{painterInfo.userName}}</div>
          <div class="useraddress">{{painterInfo.address}}</div>
          <div class="usertext">{{painterInfo.signature}}</div>
        </div>
      </el-col>
      <!--个人画作-->
      <el-col :span="15">
        <div>
          <template>
            <!-- 怎么调整走马灯不要下面的指示器？-->
            <el-carousel :autoplay="false" type="card" height="200px" style="margin-top: 5px">
              <el-carousel-item v-for="(picture,pictureIndex) in picturesInfo.pictures" :key="pictureIndex"
                                style="background-color: #d3dce6">
                <h3 class="medium">
                  <img :src="picture.picture_address" style="width: 322.5px;">
                </h3>
              </el-carousel-item>
            </el-carousel>
          </template>
        </div>
      </el-col>
    </el-row>
  </el-card>
</template>

<script>
  import axios from 'axios';

  export default {
    name: "FollowItem",

    props: {
      status: Boolean,

      painterInfo: {
        painter_id: Number,
        userName: "loading...",
        address: "loading...",
        signature: "loading...",
        profile_picture: "break.img",
      },

      picturesInfo: {
        painter_id: Number,
        pictures: [
          {
            picture_id: Number,
            picture_address: String
          }
        ]
      }

    },

    data() {
      return {
        painter_id: Number,
      }
    },
    methods: {
      /***
       * 基本方法
       */
      ItemAddFollow() {
        let _this = this;
        let follower_id = _this.$store.state.user.userID;
        let painter_id = _this.painterInfo.painter_id;
        axios.post('/User/AddFollow', {
          follower_id: follower_id,
          painter_id: painter_id,
        }).then(function (response) {
          this.status = true;
          console.log("关注成功!");
        }).catch(function (error) {
          console.log(error);
        })
      },

      ItemCancelFollow() {
        let _this = this;
        let follower_id = _this.$store.state.user.userID;
        let painter_id = _this.painterInfo.painter_id;
        axios.post('/User/CancelFollow', {
          follower_id: follower_id,
          painter_id: painter_id,
        }).then(function (response) {
          this.status = false;
          console.log("取关成功!");
        }).catch(function (error) {
          console.log(error);
        })

      },

      //获得一个画师的信息
      ItemGetPainterMessage() {
        let _this = this;
        axios.get('/User/getPainterInfo', {
          params: {
            painter_id: _this.painter_id,
          }
        }).then(function (response) {
          _this.painterInfo = response.data;
          console.log("获取一个画师信息成功！");
        }).catch(function (err) {
          console.log(err);
        })
      },

      //获得一个画师的展示图片
      ItemGetPainterMessagePictures() {
        let _this = this;
        axios.get('/User/getPainterPicturesInfo', {
          params: {
            painter_id: _this.painter_id,
          }
        }).then(function (response) {
          _this.picturesInfo = response.data;
          console.log("获取一个画师所有图片信息成功!");
        }).catch(function (err) {
          console.log(err);
        })
      },

      ItemGetFollowStatus(user_id, painter_id) {
        let _this = this;
        console.log("正在获取关注状态aaa...");
        console.log("111111");
        axios.get('/follow/get', {
          params: {
            user_id: user_id,
            painter_id: painter_id,
          }
        }).then(function (res) {
          console.log("222222");
          _this.status = res.data.status;
          console.log("关注状态为："+ _this.status);
        }).catch(function (err) {
          console.log(err);
        })
        console.log("33333333");
      },
      /***
       * 关注/取关,都交给父来做吧!
       */
      subAddFollow() {
        this.status = true;
        let follower_id = this.$store.state.user.userID;
        let painter_id = this.painterInfo.painter_id;
        this.ItemAddFollow();
        this.$emit('addFollow', follower_id, painter_id);
      },

      subCancelFollow() {
        this.status = false;
        let follower_id = this.$store.state.user.userID;
        let painter_id = this.painterInfo.painter_id;
        this.ItemCancelFollow();
        this.$emit('cancelFollow', follower_id, painter_id);
      },

      /***
       * 画师页部分
       */
      showPainterMessage(painter_id) {
        let _this = this;
        _this.painter_id = painter_id;
        _this.ItemGetPainterMessage();
        _this.ItemGetPainterMessagePictures();
      },

      skipToPainterPage() {
        let _this = this;
        this.$router.push({
          path: 'Painter',
          name: 'PainterPage',
          params: {
            painter_id: _this.painterInfo.painter_id,
            random: Math.floor(Math.random() * 10000),
          }
        })
      }
    }
  }
</script>

<style scoped>
  .username {
    font-size: 20px;
    letter-spacing: 4px;
    font-weight: 100;
    position: center;
    color: pink;
    font-weight: bolder;
    text-align: left;
    margin-left: 0px;
  }

  .useraddress {
    font-size: 12px;
    letter-spacing: 3px;
    font-weight: 100;
    text-align: left;
    margin-left: 0px;
    margin-bottom: 5px;
  }

  .usertext {
    font-size: 10px;
    letter-spacing: 3px;
    font-weight: 100;
    text-align: left;
    margin-left: 0px;
  }

  .button {
    margin-top: 5px;
    background-color: pink;
    border: pink;
    padding: 10px 15px;
    font-size: 12px;
    /*.el-button.is-round{*/
    /*  padding: 10px 15px;*/
    /*}*/
  }

  /*图片轮换*/
  .el-carousel__item h3 {
    color: #475669;
    font-size: 14px;
    opacity: 0.75;
    line-height: 200px;
    margin: 0;
  }

  .el-carousel__item:nth-child(2n) {
    background-color: #99a9bf;
  }

  .el-carousel__item:nth-child(2n+1) {
    background-color: #d3dce6;
  }

  .artist-list {
    max-height: calc(100vh - 60px);
    width: 1044px;
    padding: 0px;
    align-self: center;
    align-content: center;
    /*设置居中样式，除了外部留白应该怎么做？*/
    margin-left: auto;
    margin-right: auto;
  }
</style>
