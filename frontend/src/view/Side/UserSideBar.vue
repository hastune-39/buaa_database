<template>
  <div class="LeftSide Position">
    <el-menu style="height: 100%;" class="el-menu-vertical-demo" :collapse="true">
      <el-menu-item>
        <el-tooltip class="item" effect="dark" content="主页" placement="right">
          <i class="el-icon-s-home" @click="skipToHome"></i>
        </el-tooltip>
      </el-menu-item>
      <el-submenu index="1">
        <template slot="title">
          <el-tooltip class="item" effect="dark" content="个人名片" placement="right">
            <a-icon type="idcard"/>
          </el-tooltip>
        </template>
          <my-individual-card style="height: 100%; width: 100%"/>
          <!--          <span slot="title">分组一</span>-->
          <!--          <el-menu-item index="1-1">选项1</el-menu-item>-->
          <!--          <el-menu-item index="1-2">选项2</el-menu-item>-->
        <!--        <el-submenu index="1-4">-->
        <!--          <span slot="title">选项4</span>-->
        <!--          <el-menu-item index="1-4-1">选项1</el-menu-item>-->
        <!--        </el-submenu>-->
      </el-submenu>
      <el-menu-item>
        <el-tooltip class="item" effect="dark" content="我的关注" placement="right">
          <a-icon type="user-add" @click="skipToUserFollow"/>
        </el-tooltip>
      </el-menu-item>
      <el-menu-item>
        <!--        怎么调整tooltip参数使得图标不变小?-->
        <el-tooltip class="item" effect="dark" content="我的收藏" placement="right">
          <i class="el-icon-star-off" @click="skipToUserCollection"></i>
        </el-tooltip>
      </el-menu-item>
      <el-menu-item>
        <el-tooltip class="item" effect="dark" content="浏览历史" placement="right">
          <i @click="skipToHistory" class="el-icon-video-camera"></i>
        </el-tooltip>
      </el-menu-item>

      <el-menu-item @click="skipToRank">
        <el-tooltip class="item" effect="dark" content="排行榜" placement="right">
          <i class="el-icon-trophy-1"></i>
        </el-tooltip>
      </el-menu-item>


      <el-menu-item :disabled="true">
        <i class="el-icon-minus"></i>
      </el-menu-item>

      <el-menu-item v-if="!isPainter">
        <el-tooltip class="item" effect="dark" content="上传你的第一幅作品，快注册成为小画家吧~" placement="right">
          <a-icon @click="openPictureForm" type="smile"/>
        </el-tooltip>
      </el-menu-item>
      <template v-else>
        <el-menu-item>
          <el-tooltip class="item" effect="dark" content="上传作品" placement="right">
            <i class="el-icon-circle-plus-outline" @click="openPictureForm"></i>
          </el-tooltip>
        </el-menu-item>
        <el-menu-item>
          <el-tooltip class="item" effect="dark" content="我的画作" placement="right">
            <i @click="skipToPainterPage" class="el-icon-picture"></i>
          </el-tooltip>
        </el-menu-item>
      </template>
    </el-menu>
    <!--    </a-layout-sider>-->

    <my-upload-picture ref="upPictureIcon"></my-upload-picture>
  </div>
</template>

<script>
  import myPictureItem from '../../components/picturelist/PictureItem';
  import myIndividualCard from './UserBar/IndividualCard';
  import myPainterSideBar from './PainterSideBar';
  import myUploadPicture from './PainterBar/UploadPictureIcon';

  export default {
    name: "UserSideBar",
    components: {
      myPictureItem,
      myIndividualCard,
      myPainterSideBar,
      myUploadPicture,
    },

    computed: {
      isPainter(){
        return this.$store.state.user.isPainter;
      }
    },


    data() {
      return {};
    },

    methods: {
      skipToHome() {
        console.log("正在跳转至主页...");
        let random = Math.floor(Math.random() * 10000);
        console.log("random为：" + random);
        this.$router.push({
          path: '/Main/all',
          name: 'HomePage',
          params: {
            random: random,//随机路由，刷新作用
          }
        })
      },
      skipToUserCollection() {
        console.log("正在跳转至用户收藏页...");
        let random = Math.floor(Math.random() * 10000);
        console.log("random为：" + random);
        this.$router.push({
          path: '/Main/User/Collection',
          name: 'userCollection',
          params: {
            random: random,//随机路由，刷新作用
          }
        })
      },

      skipToUserFollow(){
        console.log("正在跳转至用户关注页...");
        let random = Math.floor(Math.random() * 10000);
        console.log("random为：" + random);
        this.$router.push({
          path: '/Main/User/Follow',
          name: 'userFollow',
          params: {
            random: random,//随机路由，刷新作用
          }
        })
      },

      skipToRank(){
        console.log("正在跳转至排行榜...");
        this.$router.push({
          path: '/Rank',
          name: 'Rank',
        })
      },
      openPictureForm(){
        console.log("user-side-bar");
        this.$refs.upPictureIcon.open();
      },

      skipToHistory(){
        console.log("正在跳转至浏览历史");
        this.$router.push({
          path: '/History',
          name: 'history',
          params: {
            user_id: this.$store.state.user.userID,
            random: Math.floor(Math.random() * 10000),
          }
        })
      },

      skipToPainterPage() {
        let _this = this;
        this.$router.push({
          path: 'Painter',
          name: 'PainterPage',
          params: {
            painter_id: _this.$store.state.user.userID,
            random: Math.floor(Math.random() * 10000),
          }
        })
      }
    }
  }
</script>

<style scoped>
  .border {
    margin-right: auto;
    border: 5px solid deepskyblue;
  }

  .el-menu-vertical-demo:not(.el-menu--collapse) {
    width: 100px;
    min-height: 400px;
  }

  .LeftSide {
    overflow: hidden;
    height: 100%;
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    align-content: center;
  }

  .Position {
    /*position: absolute;*/
    /*top: 20px;*/
  }
</style>
