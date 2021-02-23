<template>
  <div style="height: 100%">
<!--    <el-button @click="debug"></el-button>-->
    <div style="height: 100%; display: flex; flex-direction: column" >
      <div style="height: 80%; width: 100%" >
        <img @click="skipToPicture"
          :src="card_message.address" class="item-img ">
      </div>
      <div
        style="align-self: center; width: 100%;display: flex; flex-direction: row; height: 20%; display: flex; flex-direction: row">
        <img @click="skipToPainterPage"
              :src="card_message.painter_profile_picture"
             class="el-avatar el-avatar--circle"
             style="align-self: center; "
             :style="{height: profile_picture_radius + 'px', width:profile_picture_radius + 'px'}">
        <div style="display: flex; flex-direction: row; text-align: left; margin-left: 5%; align-self: center">
          <div class="title" style="align-self: center;">
            {{card_message.title}}
          </div>
          <div style="margin-top: 5%; height: 100%;align-self: center; margin-left: 10%; width: 100%; white-space: nowrap;" class="product">
             阅读量{{card_message.browseTime}} 收藏量{{card_message.collectTime}}
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import axios from 'axios';

  export default {
    name: "winCardItem",

    props: {
      profile_picture_radius: 0,
    },

    data() {
      return {
        type: 1,

        picture_id: Number,

        card_message: {
          address: '',
          title: '',
          browseTime: 0,
          collectTime: 0,
          painter_id: 0,
          painter_profile_picture: '',
        },


      }
    },

    methods: {
      //2.获取图片信息
      getPictureMessage(picture_id, rank) {
        let _this = this;
        console.log("正在获取榜单图片信息...");
        _this.picture_id = picture_id;

        axios.get('/Rank/Winner', {
          params: {
            picture_id: picture_id,
          }
        }).then(function (response) {
          _this.card_message = response.data;
          //回调，设置冠军为背景图片
          if(rank == 1){
            _this.$emit('setbg', _this.card_message.address);
          }
        }).catch(function (err) {
          console.log(err);
        })
      },

      debug(){
        console.log("榜单图片信息为: " + this.card_message.browseTime);
      },

      getPictureAddress(){
        return this.card_message.address;
      },

      /***
       * 页面跳转部分
       */
      skipToPicture(){
        let _this = this;
        console.log("准备跳转至画作"+_this.picture_id+"的详情页...");
        this.$router.push({
          path: '/Picture',
          name:  'PicturePage',
          params: {
            picture_id: _this.picture_id,
          }
        })
      },

      skipToPainterPage() {
        let _this = this;
        this.$router.push({
          path: 'Painter',
          name: 'PainterPage',
          params: {
            painter_id: _this.card_message.painter_id,
            random: Math.floor(Math.random() * 10000),
          }
        })
      }
    }
  }
</script>

<style scoped>
  .item-img {
    width: 100%;
    height: 100%;
    transition: opacity 0.3s, transform 0.3s ease;
    object-fit: cover;
    border-radius: 8px;
    align-self: center;
  }

  .title{
    font-size: 20px;
    letter-spacing: 4px;
    font-weight: 100;
    position: center;
    color: pink;
    font-weight: bolder;
    text-align: left;
    margin-left: 0px;
  }

  .product {
    font-size: 12px;
    letter-spacing: 3px;
    font-weight: 100;
    text-align: left;
    margin-left: 0px;
    margin-bottom: 5px;
  }

  /*鼠标移动图片放大效果*/
  .gallery {
    width: 100%;
    margin: 0 auto;
  }

  .gallery > div {
    position: relative;
    float: left;
  }

  .gallery > div  img {
    width: 100%;
    transition: .1s transform;
    transform: translateZ(0);
    /* hack */
  }

  .gallery > div:hover {
    z-index:333;
  }

  .gallery > div:hover  img {
    transform: scale(1.3,1.3);
    transition: .3s transform;
  }

  .cf:before, .cf:after {
    display: table;
    content: "";
    line-height: 0;
  }

  .cf:after {
    clear: both;
  }
  /*鼠标移动图片放大效果*/
</style>
