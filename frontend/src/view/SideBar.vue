<template>
  <div class="LeftSide">
    <my-nothing-side-bar v-if="userID < 0"></my-nothing-side-bar>
    <my-user-side-bar style="position: fixed;" v-else></my-user-side-bar>
  </div>
</template>

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

  .beian {
    width: 15px;
    margin: 0 auto;
    line-height: 24px;
    font-size: 12px;
    word-wrap: break-word; /*英文的时候需要加上这句，自动换行*/
    word-break: break-all;
  }
  }


</style>

<script>
  import store from '../store/store';
  import myNothingSideBar from './Side/NothingSideBar';
  import myUserSideBar from './Side/UserSideBar';


  export default {
    name: 'SideBar',

    components: {
      myNothingSideBar,
      myUserSideBar,
    },

    data() {
      return {
        isCollapse: true
      };
    },

    computed: {
      userID(){
        return this.$store.state.user.userID;
      }
    },

    methods: {
      handleOpen(key, keyPath) {
        console.log(key, keyPath);
      },
      handleClose(key, keyPath) {
        console.log(key, keyPath);
      }
    }
  }
</script>
