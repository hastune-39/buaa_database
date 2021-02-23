<template>
  <div>
<!--    <i class="el-icon-star-off" @click="submit"></i>-->
    <upload-template
      ref="upload"
      action="https://jsonplaceholder.typicode.com/posts/"
      list-type="picture-card"
      :auto-upload="false"
      :on-preview="handlePictureCardPreview"
      :on-remove="handleRemove">
      <i class="el-icon-plus"></i>
    </upload-template>
    <el-dialog :visible.sync="dialogVisible">
      <img width="100%" :src="dialogImageUrl" alt="">
    </el-dialog>
  </div>
</template>

<script>
  import uploadTemplate from './myUploadTemplate';
  import axios from 'axios';
  export default {
    data() {
      return {
        dialogImageUrl: '',
        dialogVisible: false
      };
    },
    components:{
      uploadTemplate,
    },
    methods: {
      handleRemove(file) {
        console.log(file);
      },
      handlePictureCardPreview(file) {
        this.dialogImageUrl = file.url;
        this.dialogVisible = true;
      },


      submit(title, keywords){
        let _this = this;

        //1.进行validate验证(还没学)
        //2.准备参数
        let painter_id = _this.$store.state.user.userID;


        const file = this.$refs.upload.uploadFiles[0].raw;
        let data = new FormData();
        data.append('file', file);
        let fileName = file.name;

        // console.log(title);
        // console.log(keywords);
        // console.log(file);
        // console.log(fileName);

        //3.axios
        _this.savePicture(painter_id, data);
        _this.uploadPictureForm(painter_id, title, fileName, keywords);
      },

      savePicture(painter_id, data){
        axios.post('/picture/upload', data, {
          params:{
            painter_id: painter_id,
          },
          headers: {
            'Content-Type': 'multipart/form-data',
          }
        }).then(function (response){
          console.log(response.data);
          //4.添加
        }) .catch(function (err) {
            console.log(err);
        })
        console.log(this.$refs.upload.uploadFiles);
      },

      //更新pictures表
      uploadPictureForm(painter_id, title, fileName, keywords){
        let _this = this;
        console.log("准备添加图片...");
        axios.post('/picture/uploadForm', {
          painter_id: painter_id,
          title: title,
          fileName: fileName,
          keywords: keywords
        }).then(function (response) {
            console.log(response.data);
        }).catch(function (error) {
          console.log(error);
        })
      }
    }
  }
</script>

