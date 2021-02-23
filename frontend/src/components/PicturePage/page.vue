<template>
  <el-row :gutter="20" style="margin-left: 10%; margin-right: 10%">
    <el-col :span="16">
      <div class="grid-content">
        <my-main ref="pageMain"/>
      </div>
    </el-col>
    <el-col :span="8">
      <div class="grid-conten">
        <my-aside ref="pageAside"/>
      </div>
    </el-col>
  </el-row>
</template>

<script>
  import myMain from './PageMain';
  import myAside from './aside';
  import axios from 'axios';

  export default {
    name: "page",
    components: {
      myMain,
      myAside
    },

    data() {
      return {
        picture_id: Number,
        pictureMessage: {
          address: '',
          title: '',
          uploadTime: '',
        },

        keywords: [],

        painterMessage: {
          painter_id: Number,
          painterName: '',
          address: '',
          profile_picture: '',
          signature: '',
        },

        follow_status: false,

        otherPictures: [
          {
            picture_id: Number,
            address: '',
          }
        ],

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

    watch: {
      '$route': 'BeforeSkip',
    },

    methods: {
      getPictureMessage(picture_id) {
        let _this = this;
        console.log("正在获取图片" + picture_id + "的信息...");
        axios.get('/Picture/Message', {
          params: {
            picture_id: picture_id,
          }
        }).then(function (response) {
          _this.picturesMessage = response.data;
          console.log("获得的图片信息为: " + _this.picturesMessage.title);
          _this.$refs.pageMain.getPictureMessage(picture_id, _this.picturesMessage);
        }).catch(function (err) {
          console.log(err);
        })
      },

      getPictureKeywords(picture_id) {
        let _this = this;
        console.log("正在获取图片的关键字...");
        axios.get('/Picture/Keywords', {
          params: {
            picture_id: picture_id,
          }
        }).then(function (response) {
          _this.keywords = response.data;
          _this.$refs.pageMain.getPictureKeywords(_this.keywords);
        }).catch(function (err) {
          console.log(err);
        })
      },

      getPainterMessage(picture_id) {
        let _this = this;
        console.log("正在获取图片所属画师的信息...");
        axios.get('/Picture/PainterMessage', {
          params: {
            picture_id: picture_id,
          }
        }).then(function (response) {
          _this.painterMessage = response.data;
          _this.$refs.pageAside.insertPainterMessage(_this.painterMessage);

          //获取关注信息
          let user_id = _this.$store.state.user.userID;
          let painter_id = _this.painterMessage.painter_id;
          _this.getFollowMessage(user_id, painter_id);
          _this.getPainterOtherPictures(picture_id, painter_id);
        }).catch(function (err) {
          console.log(err);
        })
      },

      getFollowMessage(user_id, painter_id) {
        let _this = this;
        console.log("正在获取关注信息...");
        axios.get('/Picture/FollowMessage', {
          params: {
            user_id: user_id,
            painter_id: painter_id,
          }
        }).then(function (response) {
          console.log(response.data.status);
          if (response.data.status == "true") {
            _this.follow_status = true;
          } else {
            _this.follow_status = false;
          }
          _this.$refs.pageAside.insertFollowStatus(_this.follow_status);
        }).catch(function (err) {
          console.log(err);
        })
      },

      getPainterOtherPictures(picture_id, painter_id) {
        console.log("正在获取画师" + painter_id + "的其他作品信息");
        let _this = this;
        axios.get('/Picture/OtherPictures', {
          params: {
            picture_id: picture_id,
            painter_id: painter_id,
          }
        }).then(function (response) {
          _this.otherPictures = response.data;
          _this.$refs.pageAside.insertOtherPictures(_this.otherPictures);
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
          console.log("开始评论的调试信息...");
          _this.comments = response.data;
          for(let i=0;i<_this.comments.length;i++) console.log(_this.comments[i].comment_message.like_status)
          _this.$refs.pageMain.getComments(_this.comments);
        }).catch(function (err) {
          console.log(err);
        })

      },

      // getCommentLikeStatus(user_id, picture_id) {
      //   let _this = this;
      //   console.log("正在搜集用户对评论的点赞情况...");
      //   axios.get('',{
      //     params:{
      //       user_id: user_id,
      //       picture_id: picture_id,
      //     }
      //   }).then(function (response) {
      //     console.log("获取用户评论点赞情况成功!");
      //     this.$refs.pageMain.getCommentLikeStatus(response.data);
      //   }).catch(function (err) {
      //     console.log(rerr);
      //   })
      // }

      skip(){
        this.picture_id = this.$route.params.picture_id;
        let picture_id = this.picture_id;
        let user_id = this.$store.state.user.userID;

        //0.浏览量+1
        this.addBrowse(user_id,picture_id);


        //1.获得图片信息
        this.getPictureMessage(picture_id);
        this.getPictureKeywords(picture_id);
        this.getPainterMessage(picture_id);
        this.getPictureComments(picture_id);
      },

      BeforeSkip(){
        console.log("非create的路由监听跳转至画作详情页");
        if(this.$route.name == 'PicturePage'){
          this.skip();
        }
      },

      addBrowse(user_id, picture_id){
        let _this = this;
        console.log("用户"+user_id+"正在浏览图片"+picture_id);
        axios.post('/Picture/Browse', {
          user_id: user_id,
          picture_id: picture_id
        }).then(function (res) {
          console.log(res);
        }).catch(function (err) {
          console.log(err);
        })
      }
    },


    watch: {
      '$route': 'skip',
    },

    created() {
      this.skip();
    }

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
  .el-col {
    border-radius: 4px;
  }

  .bg-purple-dark {
    background: #99a9bf;
  }

  .bg-purple {
    background: #d3dce6;
  }

  .bg-purple-light {
    background: #e5e9f2;
  }

  .grid-content {
    border-radius: 4px;
    min-height: 36px;
  }

  .row-bg {
    padding: 10px 0;
    background-color: #f9fafc;
  }
</style>
