<template>
  <my-picture-list ref="userCollection"></my-picture-list>
</template>

<script>
  import myPictureList from '../../components/picturelist/PictureList';

  export default {
    name: "UserCollection",

    components: {
      myPictureList,
    },

    watch: {
      '$route': 'getCollections',
    },

    methods: {
      //拉取当前用户收藏并展示
      getCollections(){
        console.log("22222222");
        if(this.$route.name == 'userCollection'){
          console.log("handler route: name-userCollection");
          this.$refs.userCollection.showNowUserCollections();
        }
      }
    },

    //好像在未访问之间都不会创建?
    created() {
      let _this = this;
      //未创建之前没有dom，$ref无法访问
      _this.$nextTick(() =>{
        console.log("11111111," + this.$route.name);
        if(this.$route.name == 'userCollection'){
          console.log("handler route: name-userCollection");
          _this.$refs.userCollection.showNowUserCollections();
        }
      })
    }
  }
</script>

<style scoped>

</style>
