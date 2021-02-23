<template>
  <div>
    <my-follow-item @addFollow="addFollow" @cancelFollow="cancelFollow"
                    ref="FollowItem"></my-follow-item>
    <my-picture-list ref="PictureList"></my-picture-list>
  </div>
</template>

<script>
  import myFollowItem from '../../../components/follow/FollowItem';
  import myPictureList from '../../../components/picturelist/PictureList';

  export default {
    components: {
      myFollowItem,
      myPictureList,
    },

    data() {
      return {
        painter_id: Number,
        painterInfo: {},
        picturesInfo: {},
        status: Boolean,
      }
    },

    watch: {
      '$route' : 'updatePage',
    },
    created() {
      let _this = this;
      this.$nextTick(() => {
        _this.updatePage();
      })
    },
    methods: {
      /***
       * 关注/取关部分
       */



      addFollow(follower_id, painter_id) {
        console.log("已经没什么可做的了...");
      },

      cancelFollow(follower_id, painter_id) {
        console.log("已经没什么可做的了...");
      },

      updatePage(){
        let _this = this;
        _this.painter_id = _this.$route.params.painter_id;
        console.log("正在搭建画师主页...");
        //1.根据painter_id获得一个画师的信息
        _this.$refs.FollowItem.showPainterMessage(_this.painter_id);
        //2.根据painter_id获得一个画师的所有图片信息
        _this.$refs.PictureList.showPainterPictures(_this.painter_id);
        //3.获得关注状态
        let user_id = _this.$store.state.user.userID;
        _this.$refs.FollowItem.ItemGetFollowStatus(user_id, _this.painter_id);
      }

    }
  }
</script>

<style scoped>
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
