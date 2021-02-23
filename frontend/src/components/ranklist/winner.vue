<!--<template>-->
<!--  <el-row style="margin-left: 10%; margin-right: 10%">-->
<!--    <el-col :span="24">-->
<!--      <el-row :gutter="40" class="bg" style="border-radius: 16px">-->
<!--        <div style="display: flex; flex-direction: row">-->
<!--        <el-col :span="7">-->
<!--          <div class="grid-content bg-purple"-->
<!--               :style="{height: leftheight + 'px', marginTop:middleheight-leftheight +'px'}"></div>-->
<!--        </el-col>-->
<!--        <el-col :span="10">-->
<!--          &lt;!&ndash;            <div class="grid-content bg-purple" :style="{height: middleheight + 'px'}"></div>&ndash;&gt;-->
<!--          <el-card shadow="always" style="width: 100%; position: center">-->
<!--            <my-win-card-item :style="{height: middleheight + 'px'}"/>-->
<!--          </el-card>-->
<!--        </el-col>-->
<!--        <el-col :span="7">-->
<!--          <div class="grid-content bg-purple"-->
<!--               :style="{height: rightheight + 'px', marginTop:middleheight-rightheight +'px' }"></div>-->
<!--        </el-col>-->
<!--        </div>-->
<!--      </el-row>-->
<!--    </el-col>-->
<!--  </el-row>-->
<!--</template>-->

<template>
  <el-row style="margin-left: 10%; margin-right: 10%">
    <el-col :span="24">
      <el-row :gutter="40"  :style="{backgroundImage: 'url(' + bgimg + ')'}"class="bg" style="border-radius: 16px; padding: 2%">
        <div style="display: flex; flex-direction: row">
          <div style="width: 30%; align-self: center">
            <!--            <div class="grid-content bg-purple"-->
            <!--                 :style="{height: leftheight + 'px'}"></div>-->
            <!--            &lt;!&ndash;            <div class="grid-content bg-purple" :style="{height: middleheight + 'px'}"></div>&ndash;&gt;-->
            <el-card :body-style="bodyStyle" shadow="always" style="width: 100%; position: center; position: relative">
              <my-win-card-item ref="silver" :style="{height: leftheight + 'px'}" :profile_picture_radius="60"/>
              <img src="static/medal/2.png" style="width: 80px; position: absolute; top: 0px; left: 0px">
            </el-card>
          </div>
          <div style="width: 40%; align-self: center; margin-left: 2%; margin-right: 2%">
            <el-card :body-style="bodyStyle" shadow="always" style="width: 100%; position: center; position: relative">
              <my-win-card-item @setbg="setbg" ref="gold" :style="{height: middleheight + 'px'}" :profile_picture_radius="70"/>
              <img src="static/medal/1.png" style="width: 80px; position: absolute; top: 0px; left: 0px">
            </el-card>
          </div>
          <div style="width: 30%; align-self: center">
            <!--            <div class="grid-content bg-purple"-->
            <!--                 :style="{height: rightheight + 'px'}"></div>-->
            <el-card :body-style="bodyStyle" shadow="always" style="width: 100%; position: center; position: relative">
              <my-win-card-item ref="copper" :style="{height: rightheight + 'px'}" :profile_picture_radius="50"/>
              <img src="static/medal/3.png" style="width: 80px; position: absolute; top: 0px; left: 0px">
            </el-card>

          </div>
        </div>
      </el-row>
    </el-col>
  </el-row>
</template>

<script>
  import myWinCardItem from './winCardItem';

  export default {
    name: "winner",

    data() {
      return {
        leftheight: 500 * 0.8,
        middleheight: 500,
        rightheight: 500 * 0.6,

        bodyStyle: {
          padding: '2%',
          borderradius: '8px',
        },

        rankPictureID:[],

        bgimg: 'static/img/DM袋喵/5.jpg',
      }
    },


    components: {
      myWinCardItem,
    },

    methods: {
      allocWinner(rankpictureid){
        this.rankPictureID = rankpictureid;
        if(rankpictureid.length > 0){
          console.log("正在为冠军颁奖...");
          this.$refs.gold.getPictureMessage(rankpictureid[0], 1);
          this.bgimg = this.$refs.gold.getPictureAddress();
        }
        if(rankpictureid.length > 1){
          console.log("正在为亚军颁奖...");
          this.$refs.silver.getPictureMessage(rankpictureid[1], 2);
        }
        if(rankpictureid.length > 2){
          console.log("正在为季军颁奖...");
          this.$refs.copper.getPictureMessage(rankpictureid[2],3);
        }
      },

      setbg(bgimg){
        this.bgimg = bgimg;
      },

      /***
       * 页面跳转部分
       */

    },

  }
</script>

<style scoped>

  .bg-purple {
    background: #d3dce6;
  }


  .grid-content {
    border-radius: 4px;
    min-height: 36px;
  }

  .row-bg {
    padding: 10px 0;
    background-color: #f9fafc;
  }

  .bg {
    /*-webkit-background-size: cover;*/
    /*-o-background-size: cover;*/


    width: 100%;
    background-size: 100%;
    position: relative;
    background-repeat: no-repeat;
    background-position: center 0;
    padding: 1px;
    box-sizing: border-box;
    z-index: 1;
    /*filter: blur(2px);*/

    /*-webkit-filter:blur(15px);*/

    /*-moz-filter:blur(15px);*/

    /*-o-filter:blur(15px);*/

    /*-ms-filter:blur(15px);*/

    /*filter:blur(15px);*/
  }
</style>
