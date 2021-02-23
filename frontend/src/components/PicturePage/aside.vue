<template>
  <div>
<!--    <el-button @click="debug"></el-button>-->
    <el-card shadow="always">
      <div style="display: flex; flex-direction: row;">
        <div style=" width: 90px">
          <img :src="painterMessage.profile_picture"
               class="el-avatar el-avatar--circle"
               style="height: 80px; width: 80px">
          <el-button v-if="follow_status"
                     type="primary"
                     round class="button">已关注
          </el-button>
          <el-button v-else
                     type="primary"
                     round class="button">关注
          </el-button>
        </div>
        <!--      个人介绍-->
        <div style="margin-left: 20px">
          <div class="username">{{painterMessage.painterName}}</div>
          <div class="useraddress">{{painterMessage.address}}</div>
          <div class="usertext">{{painterMessage.signature}}</div>
        </div>
      </div>
    </el-card>

    <el-card shadow="always" style="margin-top: 15px">
      <div class="useraddress" style="margin-bottom: 15px">
        其他作品
      </div>
      <div>
        <el-row v-for="row in rowNum" :key="row"
          :gutter="2" style="margin-top: 10px; margin-bottom: 10px">
          <el-col :span="8" v-if="(row-1)*3 < otherPictures.length"><img  :src="otherPictures[(row-1)*3].address" class="asideImg"
                                                                          @click="skipToPicture(otherPictures[(row-1)*3].picture_id)"/></el-col>
          <el-col :span="8" v-if="(row-1)*3+1 < otherPictures.length"><img  :src="otherPictures[(row-1)*3+1].address" class="asideImg"
                                                                                      @click="skipToPicture(otherPictures[(row-1)*3+1].picture_id)"/></el-col>
          <el-col :span="8" v-if="(row-1)*3+2 < otherPictures.length"><img  :src="otherPictures[(row-1)*3+2].address" class="asideImg"
                                                                            @click="skipToPicture(otherPictures[(row-1)*3+2].picture_id)"/></el-col>
        </el-row>
      </div>
    </el-card>
  </div>
</template>

<script>
  export default {
    name: "aside",

    data() {
      return {
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
      }
    },

    computed: {
      rowNum(){
        return Math.floor((this.otherPictures.length-1)/3+1);
      },
    },
    methods: {
      insertPainterMessage(painterMessage){
        this.painterMessage = painterMessage;
      },

      insertFollowStatus(follow_status){
        this.follow_status = follow_status;
      },

      insertOtherPictures(otherPictures){
        this.otherPictures = otherPictures;
      },

      debug(){
        // console.log(this.painterMessage.painterName);
        // console.log(this.follow_status);
        // console.log(this.otherPictures[0].address);
        // console.log(this.otherPictures.length);
        console.log(this.otherPictures.length);
      },

      overbound(rowNum, column){
        return ((rowNum-1)*3+column -1 > this.otherPictures.length)? true:false;
      },

      /***
       * 跳转至画作详情页部分
       */
      skipToPicture(picture_id){
        let _this = this;
        console.log("准备跳转至画作详情页...");
        this.$router.push({
          path: '/Picture',
          name:  'PicturePage',
          params: {
            picture_id: picture_id,
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

  .asideImg {
    height: 90px;
    width: 90px;
    border-radius: 8px;
    object-fit: cover;
  }

</style>
