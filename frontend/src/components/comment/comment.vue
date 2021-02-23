<template>
  <el-card shadow="always" style="margin-top: 10px">
    <div v-loading="loading"
         style="height: 150px; display: flex; flex-direction: row; ">
      <div style="height: 100px; width: 100px; display: flex">
        <img :src="profile_picture"
             class="el-avatar el-avatar--circle imgstyle"

        >
      </div>
      <div style="display: flex; flex-direction: column; width: 100%">
        <div style="display: flex; flex-direction: row; width: 100%">
          <div style="height: 100px; width: 100%; margin-left: 5px;  display: flex">
            <el-input
              type="textarea"
              :autosize="{ minRows: 2, maxRows: 2}"
              placeholder="来发条友善的评论吧~"
              v-model="inputContent"
              style="color: #f4f5f7; align-self: center">
            </el-input>
          </div>
          <div style="width: 100px; height: 100px;margin-left: 10px;display: flex ">
            <el-button @click="uploadComment"
                       type="primary" style="position: center;align-self: center">发表
            </el-button>
          </div>
        </div>
        <el-divider/>
      </div>
    </div>

    <div v-for="(comment,index) in comments" :key="index"
         style="maxheight: 180px; width: 100%; display: flex; flex-direction: row">
      <div>
        <img :src="comment.commentator_message.profile_picture"
             class="el-avatar el-avatar--circle imgstyle">
      </div>
      <div style="margin-left: 32px;text-align: left; width: 100%">
        <div style="width: 100%; display: flex; flex-direction: row">
          <div class="username" style="width: 20%">{{comment.commentator_message.user_name}}</div>
          <div style="width: 80%"></div>
          <div v-if="user_id==comment.commentator_message.user_id"><i class="el-icon-delete" @click="deleteComment(comment.comment_message.comment_id)"/></div>
        </div>
        <div style="margin-top: 8px">{{comment.comment_message.content}}</div>
        <div style="display: flex; flex-direction: row; margin-top: 10px">
          <div style="align-self: center" class="useraddress">{{comment.comment_message.commentTime}}</div>
          <a-icon v-if="comment.comment_message.like_status"
                  @click="cancelThumb(user_id, comment.comment_message.comment_id, index)"
                  type="like" style="margin-left: 20px; color: deepskyblue"/>
          <a-icon v-else
                  @click="thumb(user_id, comment.comment_message.comment_id, index)"
                  type="like" style="margin-left: 20px;"/>
          <div v-if="comment.comment_message.commentLikeTimes>0" class="usertext">
            {{comment.comment_message.commentLikeTimes}}
          </div>
        </div>
        <el-divider/>
      </div>
    </div>

  </el-card>
</template>

<script>
  import axios from 'axios';

  export default {
    name: "comment",

    props: {
      picture_id: Number,
    },

    computed: {
      profile_picture() {
        return this.$store.state.user.profile_picture;
      },

      user_id() {
        return this.$store.state.user.userID;
      },

    },

    data() {
      return {
        inputContent: "",

        loading: false,

        comments: [
          {
            comment_message: {
              comment_id: Number,
              content: '',
              commentTime: '',
              commentLikeTimes: '',
              like_status: false,
            },

            commentator_message: {
              user_id: Number,
              user_name: '',
              profile_picture: '',
            },
          }
        ],

      }
    },
    methods: {
      getcomments(comments) {
        this.comments = comments;
      },

      uploadComment() {
        let _this = this;
        _this.loading = true;
        console.log("正在准备发表评论...");
        //0.判空验证
        if (!_this.inputContent) {
          _this.$message({
            message: '请输入评论',
            center: true,
            type: "warning",
          });
          return;
        }

        //1.发表
        axios.post('/Picture/Comment/Upload', {
          commentator_id: _this.$store.state.user.userID,
          picture_id: _this.picture_id,
          content: _this.inputContent,
        }).then(function (response) {
          console.log(response);
          //2.反馈
          _this.loading = false;
          _this.$message({
            message: '发表评论成功!',
            center: true,
            type: "success",
          });
          //3.重新拉取评论
          _this.getPictureComments(_this.picture_id);
        }).catch(function (err) {
          console.log(err);
        })
      },

      deleteComment(comment_id) {
        let _this = this;
        console.log("正在删除评论"+ comment_id+"...");
        axios.post('/Picture/Comment/Delete',{
            comment_id: comment_id,
        }).then(function (res) {
          console.log(res);
          //2.反馈
          _this.loading = false;
          _this.$message({
            message: '删除评论成功!',
            center: true,
            type: "success",
          });
          //3.重新拉取评论
          _this.getPictureComments(_this.picture_id);
        }).catch(function (err) {
          console.log(err);
        })
      },

      getPictureComments(picture_id) {
        console.log("正在获取图片的评论...");
        let _this = this;
        axios.get('/Picture/Comments', {
          params: {
            user_id: _this.$store.state.user.userID,
            picture_id: picture_id,
          }
        }).then(function (response) {
          _this.comments = response.data;
        }).catch(function (err) {
          console.log(err);
        })
      },

      /***
       * 点赞系统
       */
      thumb(user_id, comment_id, index) {
        console.log("正在点赞...");
        let _this = this;
        //1.点赞axios请求
        axios.post('/Comment/Thumb', {
          user_id: user_id,
          comment_id: comment_id,
        }).then(function (response) {
          //2.状态改变
          console.log("user_id为:"+ user_id + " comment_id为"+ comment_id);
          let newMessage = _this.comments[index];
          newMessage.comment_message.like_status = true;
          newMessage.comment_message.commentLikeTimes = newMessage.comment_message.commentLikeTimes + 1;
          _this.comments.splice(index,1,newMessage);
          console.log("点赞成功");
        }).then(function (err) {
          console.log(err);
        })
      },

      cancelThumb(user_id, comment_id, index) {
        console.log("正在取消点赞...");
        let _this = this;

        axios.post('/Comment/CancelThumb', {
          user_id: user_id,
          comment_id: comment_id,
        }).then(function (response) {
          //2.状态改变
          console.log("user_id为:"+ user_id + " comment_id为"+ comment_id);
          let newMessage = _this.comments[index];
          newMessage.comment_message.like_status = false;
          newMessage.comment_message.commentLikeTimes = newMessage.comment_message.commentLikeTimes - 1;
          _this.comments.splice(index,1,newMessage);
          console.log("取消点赞成功");
        }).then(function (err) {
          console.log(err);
        })
      },

      debug() {
        console.log(this.picture_id);
      }
    },
  }
</script>

<style scoped>
  .imgstyle {
    height: 60px;
    width: 60px;
    position: center;
    align-self: center;
  }

  .username {
    font-size: 15px;
    letter-spacing: 2px;
    font-weight: 100;
    position: center;
    color: #ffa2c4;
    font-weight: bolder;
    text-align: left;
    margin-left: 0px;
  }

  .useraddress {
    font-size: 12px;
    letter-spacing: 1px;
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
