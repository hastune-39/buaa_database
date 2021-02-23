<template>
  <el-row :gutter="20">
    <el-col :span="24">
      <el-select v-model="value" :placeholder="options[0].label" style="width: 80px; margin-top: 10px">
        <el-option
          v-for="item in options"
          :key="item.label"
          :label="item.label"
          :value="item.label">
        </el-option>
      </el-select>
      <a-input-search
        enter-button="搜索"
        size="large"
        @search="onSearch"
        style="position: center; width: 300px; margin-top: 10px; "
      />
    </el-col>
    <el-col :span="4">
      <div class="grid-content bg-purple"></div>
    </el-col>
  </el-row>
  <!--  <div style="width: 100%; height: 100%;">-->
  <!--    -->

  <!--    -->
  <!--  </div>-->
</template>

<script>
  import mysearchBox from './Header/searchBox';
  import axios from 'axios';

  export default {
    name: 'HeadBar',

    components: {
      mysearchBox,
    },

    data() {
      return {
        activeIndex: '1',
        activeIndex2: '1',

        options: [{
          value: '选项1',
          label: '标题'
        }, {
          value: '选项2',
          label: '关键字'
        }, {
          value: '选项3',
          label: '画家id'
        }],
        value: '标题'
      };
    },

    methods: {
      onSearch(value) {
        let _this = this
        console.log("搜索类型是: " + this.value + ", 搜索栏内容是: " + value);
        if(!value){
          this.$message({
            message: '请输入搜索内容',
            center: true,
            type: 'warning',
            showClose: true,
          });
          return;
        }
        //路由跳转
        if (this.value == '标题') {
          this.$router.push({
            path: '/search/title',
            name: 'SearchByTitle',
            params: {
              value: value,
              random: Math.floor(Math.random() * 10000),
            }
          })
        } else if (this.value == '关键字') {
          this.$router.push({
            path: '/search/keywords',
            name: 'SearchByKeywords',
            params: {
              value: value,
              random: Math.floor(Math.random() * 10000),
            }
          })
        } else if (this.value == '画家id') {
           //1.查询有没有该画家
          axios.get('/search/painter',{
            params: {
              painter_id: value,
            },
          }).then(function (res) {
            _this.$router.push({
              path: 'Painter',
              name: 'PainterPage',
              params: {
                painter_id: value,
                random: Math.floor(Math.random() * 10000),
              }
            })
          }).catch(function (err) {
            _this.router.push({
              //什么都没有找到
              path: 'nothing',
              name: 'nothing',
            })
          })
        } else {
          console.log("错误的搜索类型!");
        }


      },
    },
  }
</script>

<style>
  .flextitle {
    flex-direction: row;
    display: flex;
    justify-content: center;
  }
</style>
