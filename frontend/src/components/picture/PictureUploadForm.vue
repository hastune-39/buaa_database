<template>
  <el-dialog
    :visible.sync="dialogFormVisible"
    width="30%"
    :append-to-body="true"
    style="margin-top: 100px">
    <div style="display: flex; flex-direction: row">
      <div style="display: flex; flex-direction: column; width: 150px">
        <my-upload ref="pictureUpload"></my-upload>
        <!--        <span class="title">标题</span>-->
        <el-input v-model="title"
                  placeholder="请输入标题"
                  style="height: 35px; margin-top: 10px">
        </el-input>
      </div>
      <div style="height: 200px; width: 100%; text-align: left; margin-left: 15px; position: relative">
        <span class="title" style="margin-bottom: 5px">关键字</span>
        <div style="border-color: lightgreen; width: 100%; max-height: 150px; display: flex; flex-direction: column">
          <div>
            <my-key-word-tage ref="keyWordsTages"/>
          </div>
        </div>
        <div style="position: absolute; bottom: 1%; right: 0%">
          <el-button @click="uploadForm" size="small" >上传</el-button>
        </div>
      </div>
    </div>

  </el-dialog>
</template>

<script>
  import myUpload from './Upload';
  import myKeyWordTage from './KeyWordTag';
  import axios from 'axios';

  export default {
    name: "PictureUploadForm",

    components: {
      myUpload,
      myKeyWordTage,
    },

    data() {
      return {
        dialogFormVisible: false,
        title: '',
      }
    },

    computed: {
      isPainter(){
        return this.$store.state.user.isPainter;
      }
    },


    methods: {
      open() {
        console.log("picture-upload-form");
        this.dialogFormVisible = true;
      },

      uploadForm(){
        let _this = this;
        _this.dialogFormVisible = false;
        //0.若当前用户不是画师，则注册为画师
        if(!_this.isPainter){
          let user_id = _this.$store.state.user.userID;
          axios.post('/painters/painter/register', {
            user_id: user_id,
          }).then(function (res) {
            //vuex状态提交
            _this.$store.commit('painterRegister');
            console.log(_this.$store.state.user.isPainter);
            //1.上传图片
            _this.$refs.pictureUpload.submit(_this.title, _this.$refs.keyWordsTages.getdata());
            //2.注册反馈
            _this.$notify({
              title: '注册成功',
              message: '你已经是一名合格的小画家啦~',
            });
          }).catch(function (err) {
            console.log(err);
          })
        }else{
          //1.上传图片
          _this.$refs.pictureUpload.submit(_this.title, _this.$refs.keyWordsTages.getdata());
        }
      },
    }
  }
</script>

<style scoped>
  .img {
    width: 100%;
    height: 100%;
    transition: opacity 0.3s, transform 0.3s ease;
    object-fit: cover;
    border-radius: 16px;

  }

  .title {
    /*padding-left: 10px;*/
    text-align: left;
    font-size: 15px;
    letter-spacing: 4px;
    font-weight: 100;
    color: pink;
    position: center;
    font-weight: bolder;
  }
</style>
