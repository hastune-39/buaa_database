<template>
  <my-page-list ref="HomePageList"></my-page-list>
</template>

<script>
  import myPageList from '../../components/picturelist/PictureList';

  export default {
    name: "HomePage",

    components: {
      myPageList,
    },

    watch: {
      '$route': 'search',
    },

    methods: {
      search(){
        this.$refs.HomePageList.nodisplayKeywords();
        if(this.$route.name=='SearchByTitle'){
          this.$refs.HomePageList.startInit(3);
          this.$refs.HomePageList.selectByTitle();
        }else if(this.$route.name=='SearchByKeywords'){
          let keywords = this.$route.params.value;
          this.$refs.HomePageList.startInit(3);
          this.$refs.HomePageList.selectByKeywords(keywords);
          this.$refs.HomePageList.displayKeywords(keywords);
        } else if(this.$route.name == 'HomePage'){
          console.log("不要进来啊...")
          this.$refs.HomePageList.startInit(3);
          this.$refs.HomePageList.selectAllPictures();
        } else if(this.$route.name == 'history'){
          this.$refs.HomePageList.startInit(3);
          this.$refs.HomePageList.showUserHistory();
        }
      }
    },

    created() {
      let _this = this;
      this.$nextTick(()=>{
        _this.search();
      })
    }
  }
</script>

<style scoped>

</style>
