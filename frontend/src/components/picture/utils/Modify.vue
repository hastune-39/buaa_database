<template>
  <el-dialog
    :visible.sync="dialogFormVisible"
    width="30%"
    style="margin-top: 100px">
    <div style="display: flex; flex-direction: row">
      <div style="display: flex; flex-direction: column; width: 150px">
        <!--        <my-upload ref="pictureUpload"></my-upload>-->
        <div style="width: 148px; height: 148px;">
          <img :src="picture_address" style="width: 100%; max-height: 100%;object-fit: cover; border-radius: 16px">
        </div>
        <el-input v-model="title"
                  placeholder="请输入标题"
                  style="height: 35px; margin-top: 10px">
        </el-input>
      </div>
      <div style="height: 200px; width: 100%; text-align: left; margin-left: 15px; position: relative">
        <span class="title" style="margin-bottom: 5px">关键字</span>
        <div style="border-color: lightgreen; width: 100%; max-height: 150px; display: flex; flex-direction: column">
          <div>
            <my-key-word-tag ref="keyWordsTags"/>
          </div>
        </div>
        <div style="position: absolute; bottom: 1%; right: 0%">
          <el-button @click="update" size="small">更新</el-button>
        </div>
      </div>
    </div>

  </el-dialog>
</template>

<script>
  import myKeyWordTag from '../KeyWordTag';
  import axios from 'axios';

  export default {
    name: "Modify",

    components: {
      myKeyWordTag
    },

    props: {
      picture: {
        picture_id: Number,
        painter_id: Number,
        picture_address: null,
        uploadTime: String,
        title: String,
      },
    },

    data() {
      return {
        picture_id: Number,
        picture_address: '',
        dialogFormVisible: false,
        title: '',
      }
    },

    methods: {
      open() {
        console.log("picture-update-form");
        this.dialogFormVisible = true;
        this.drag();
        this.dragPictureKeywords();
      },

      /***
       * 安全性验证：
       * ①keyword和title长度要小于16个字符(8个汉字)
       *
       */

      /***
       * 拉取更新前内容
       */
      drag() {
          this.picture_address = this.picture.picture_address;
          this.title = this.picture.title;
          this.picture_id = this.picture.picture_id;
          this.dragPictureKeywords();
      },

      dragPictureKeywords() {
          let _this = this;
          console.log("正在拉取更改图片的关键词...");
          axios.get('/picture/get/keywords',{
            params: {
              picture_id: _this.picture_id,
            }
          }).then(function (response) {
            console.log("获得的关键词列表为: "+ response.data);
            _this.$refs.keyWordsTags.insertKeyWords(response.data);
          }).catch(function (err) {
            console.log(err);
          })
      },


      /***
       * 提交更新后的内容
       */
      update() {
         this.updateTitle(this.picture_id, this.title);
         this.updateKeywords(this.picture_id, this.$refs.keyWordsTags.getdata());
        this.$message({
          message: '更改画作信息成功!',
          center: true,
          showClose: true,
          type: 'success'
        });
      },

      updateTitle(picture_id, title){
          axios.post('/picture/update/title', {
            picture_id: picture_id,
            title: title,
          }).then(function (response) {
            console.log(response.data);
          }).catch(function (err) {
            console.log(err);
          })
      },

      updateKeywords(picture_id, keywords){
        axios.post('/picture/update/keywords', {
            picture_id: picture_id,
            keywords: keywords,
        }).then(function (response) {
          console.log(response.data);
        }).catch(function (err) {
          console.log(err);
        })
      }
    }
  }
</script>

<style scoped>
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
