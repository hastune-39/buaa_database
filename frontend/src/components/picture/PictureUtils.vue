<template>
  <div style="display: flex; flex-direction: column; font-size: 20px; padding: 3px">
    <div v-on:mouseenter="addOpacity1" v-on:mouseleave="subOpacity1">
      <el-button icon="el-icon-search" size="small" circle class="icon"
                 style="width: 32px; height: 32px;float: right"
                 :style="{'opacity': opacity1}"
      ></el-button>
    </div>
    <div v-on:mouseenter="addOpacity2" v-on:mouseleave="subOpacity2">
    <el-button @click="openModify"
               icon="el-icon-edit"
               size="small" circle class="icon"
               :style="{'opacity': opacity2}"></el-button>
    </div>
    <div v-on:mouseenter="addOpacity3" v-on:mouseleave="subOpacity3">
      <el-popconfirm
      confirm-button-text='确定'
      cancel-button-text='取消'
      icon="el-icon-info"
      icon-color="red"
      title="确定删除这张图片吗？"
      @confirm="close"
      >
      <el-button icon="el-icon-delete" size="small" circle class="icon"
                 slot="reference"
                 :style="{'opacity': opacity3}"></el-button>
      </el-popconfirm>
    </div>

    <my-modify ref="modify" :picture="picture"/>
  </div>
</template>

<script>
  import myModify from './utils/Modify';
  import axios from 'axios';

  export default {
    name: "PictureUtils",

    props: {
      picture: {
        picture_id: Number,
        painter_id: Number,
        picture_address: null,
        uploadTime: String,
        title: String,
      },
    },

    data(){
      return{
        opacity1: 0,
        opacity2: 0,
        opacity3: 0,
      }
    } ,




    components: {
      myModify,
    },

    methods: {
      //透明度变化 我是傻逼
      addOpacity1(){
        this.opacity1 = 1;
      },
      subOpacity1(){
        this.opacity1 = 0;
      },
      addOpacity2(){
        this.opacity2 = 1;
      },
      subOpacity2(){
        this.opacity2 = 0;
      },
      addOpacity3(){
        this.opacity3 = 1;
      },
      subOpacity3(){
        this.opacity3 = 0;
      },

      openModify() {
        this.$refs.modify.open();
      },

      //回调删除
      close() {
        //1.画师删除图片
        let _this = this;
        let picture_id = this.picture.picture_id;
        let painter_id = this.$store.state.user.userID;

        axios.post('/picture/delete', {
          painter_id: painter_id,
          picture_id: picture_id,
        }).then(function (response) {
          console.log(response.data);
          _this.$parent.close();
        }).catch(function (err) {
          console.log(err);
        })
      },
    }
  }
</script>

<style scoped>
  .icon {
    margin-bottom: 3px;
  }
</style>
