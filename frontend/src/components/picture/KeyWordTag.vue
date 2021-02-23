<template>
  <div style="display: flex; flex-wrap: wrap">
    <el-tag
      v-for="(tag, index) in dynamicTags"
      :key="index"
      :type="tagType[index%5]"
      closable
      :disable-transitions="false"
      @close="handleClose(tag)"
      style="margin-top: 5px; margin-bottom: 5px; margin-right: 5px}">
      {{tag}}
    </el-tag>
    <el-input
      class="input-new-tag"
      v-if="inputVisible"
      v-model="inputValue"
      ref="saveTagInput"
      size="small"
      @keyup.enter.native="handleInputConfirm"
      @blur="handleInputConfirm"
      style="margin-top: 5px"
    >
    </el-input>
    <el-button v-else class="button-new-tag" size="small" @click="showInput" style="margin-top: 5px">+ New Tag</el-button>
  </div>
</template>


<style>

  .button-new-tag {
    margin-left: 10px;
    height: 32px;
    line-height: 30px;
    padding-top: 0;
    padding-bottom: 0;
  }

  .input-new-tag {
    width: 90px;
    margin-left: 10px;
    vertical-align: bottom;
  }
</style>

<script>
  export default {
    data() {
      return {
        dynamicTags: [],
        inputVisible: false,
        inputValue: '',
        tagType: ['','success', 'info','warning','danger'],
      };
    },
    methods: {
      getdata(){
        return this.dynamicTags;
      },

      handleClose(tag) {
        this.dynamicTags.splice(this.dynamicTags.indexOf(tag), 1);
      },

      showInput() {
        this.inputVisible = true;
        this.$nextTick(_ => {
          this.$refs.saveTagInput.$refs.input.focus();
        });
      },

      handleInputConfirm() {
        let inputValue = this.inputValue;
        if (inputValue) {
          this.dynamicTags.push(inputValue);
        }
        this.inputVisible = false;
        this.inputValue = '';
      },

      /***
       * 更改图片
       */
       insertKeyWords(keywords){
          this.dynamicTags = keywords;
      }
    }
  }
</script>
