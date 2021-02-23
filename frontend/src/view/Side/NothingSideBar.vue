<template>
  <div class="LeftSide">
    <el-menu class="el-menu-vertical-demo" :collapse="true">
      <el-menu-item index="2">
        <el-tooltip class="item" effect="dark" content="快登录打开新世界的大门吧~" placement="right">
          <i class="el-icon-user-solid" @click="dialogVisible = true"></i>
        </el-tooltip>
      </el-menu-item>
    </el-menu>

    <el-dialog
      title="提示"
      :visible.sync="dialogVisible"
      width="30%"
      :before-close="handleClose">
      <div>
        <Login @loginSuccessfully = "loginSuccessfully"></Login>
      </div>

    </el-dialog>
  </div>
</template>

<script>
  import Login from '../../components/Login/Login';

  export default {
    name: 'NothingSideBar',
    data() {
      return {
        dialogVisible: false
      };
    },

    components: {
      Login
    },

    methods: {
      handleClose(done) {
        done();
        // this.$confirm('确认关闭？')
        //   .then(_ => {
        //     done();
        //   })
        //   .catch(_ => {});
      },

      registerSuccessfully(){
        console.log('test....');
        //设置弹框不可见
        this.dialogVisible = false;
        //登陆成功消息提示
        this.$notify({
          title: '注册成功',
          // message: '这是一条成功的提示消息',
          type: 'success'
        });
      },
      loginSuccessfully() {
        console.log('test....');
        //设置弹框不可见
        this.dialogVisible = false;
        //登陆成功消息提示
        this.$notify({
          title: '登陆成功',
          // message: '这是一条成功的提示消息',
          type: 'success'
        });
      }
    }
  };
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
</style>
