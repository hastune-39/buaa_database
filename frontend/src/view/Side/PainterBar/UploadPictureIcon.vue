<template>
  <painter-upload-picture ref="uppic"></painter-upload-picture>
</template>

<script>
  import painterUploadPicture from '../../../components/picture/PictureUploadForm';

  export default {
    name: "UploadPicture",
    components:{
      painterUploadPicture,
    },

    methods: {
      open(){
        console.log("uploadPicture-icon");
        this.$refs.uppic.open();
      }
    }
  }
</script>

<style scoped>

</style>
