<template>
  <div>
    <el-card shadow="always">
      <img :src="pictureMessage.address"
           style="max-width: 100%; max-height: 650px;position: center; border-radius: 8px;">
      <!--    <div style="margin-top: 10px; text-align: right">-->
      <!--      <a-icon type="heart" style="font-size: 30px"/>-->
      <!--    </div>-->
      <div style="margin-top: 30px; padding-left: 5px; text-align: left">
        <div class="username">
          {{pictureMessage.title}}
        </div>
        <div style="margin-top: 12px">
          <el-tag v-for="(content,index) in keywords" :key="index" :type="tagType[index%5]" style="margin-right: 5px">
            {{content}}
          </el-tag>
          <!--          <span>这里可根据关键词索引</span>-->
        </div>
        <div class="usertext" style="margin-top: 10px">
          {{pictureMessage.uploadTime}}
        </div>
      </div>
    </el-card>

    <my-comment ref="comments" :picture_id="picture_id"/>
  </div>
</template>

<script>

  import myComment from '../comment/comment';
  import axios from 'axios';

  export default {
    name: "main",

    components: {
      myComment,
    },


    data() {
      return {
        tagType: ['', 'success', 'info', 'warning', 'danger'],
        tagContent: ["咒术回战", "五条悟", "无量空处", "DM袋喵"],

        picture_id: Number,

        pictureMessage: {
          address: '',
          title: '',
          uploadTime: '',
        },

        keywords: [],

      }
    },

    methods: {
      debug() {
        console.log("debug-" + this.pictureMessage.address);
        console.log("debug-" + this.pictureMessage.title);
      },

      /***
       * get_props
       */
      getPictureMessage(picture_id, pictureMessage) {
        this.picture_id = picture_id;
        this.pictureMessage = pictureMessage;
      },

      getPictureKeywords(keywords) {
        this.keywords = keywords;
      },

      getComments(comments) {
        this.$refs.comments.getcomments(comments);
      },


    }
  }
</script>

<style scoped>
  .username {
    font-size: 20px;
    letter-spacing: 4px;
    font-weight: 100;
    position: center;
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
</style>
