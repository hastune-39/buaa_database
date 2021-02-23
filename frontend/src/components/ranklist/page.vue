<template>
  <div>
<!--    <el-button @click="debug"></el-button>-->
    <my-winner ref="winner"/>
    <my-picture-list style="margin-top: 50px" ref="list"/>
  </div>
</template>

<script>
  import myWinner from './winner';
  import myPictureList from '../picturelist/PictureList';
  import axios from 'axios';

  export default {
    name: "page",

    data() {
      return {
        allPictures: [{
          picture_id: Number,
          painter_id: Number,
          picture_address: String,
          uploadTime: String,
          title: String,
        }],
      }
    },

    components: {
      myWinner,
      myPictureList,
    },

    created() {
      this.$nextTick(() => {
        this.$refs.list.startInit(3);
        this.getRankPictures();
      })
    },

    methods: {
      //要搜集哪些图片?
      //1.所有发布时间在一天内的图片
      //2.将这些图片浏览量的前三名发给winner
      //3.剩下的发给homepagelist
      getRankPictures() {
        let _this = this;
        console.log("正在获取所有排行图片...");
        axios.get('/Rank/allPictures', {}).then(function (response) {
          _this.allPictures = response.data;
          _this.alloc();
        }).catch(function (err) {
          console.log(err);
        })
      },

      alloc() {
        //1.picturelist有多余的图片
        console.log("所有rank图片为:" + this.allPictures.length);
        if (this.allPictures.length > 3) {
          //前三给winner
          let picturesID = [];
          for (let i = 0; i < 3; i++) picturesID.push(this.allPictures[i].picture_id);
          console.log("前三图片的id为: "+ picturesID);
          this.$refs.winner.allocWinner(picturesID);
          //后面给picturelist
          this.allPictures.splice(0, 3);
          this.$refs.list.selectAllRankPicture(this.allPictures);
        } else {
          //全是winner的图片
          let picturesID = [];
          for (let i = 0; i < this.allPictures.length; i++) picturesID.push(this.allPictures[i].picture_id);
          this.$refs.winner.allocWinner(picturesID);
        }
      },

      debug(){

      }
    }
  }
</script>

<style scoped>

</style>
