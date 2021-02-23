<template>
  <div>
    <!--    <el-button type="primary" icon="el-icon-edit" circle @click="changestore"></el-button>-->
    <!--        <el-button type="success" icon="el-icon-check" circle @click="mydebug"></el-button>-->
    <el-row v-if="searchIsKeyword" :gutter="20" class="flex" style="margin-bottom: 10px">
      <el-tag v-for="(content,index) in keywords" :key="index" :type="tagType[index%5]" style="margin-right: 5px">
        {{content}}
      </el-tag>
    </el-row>
    <el-row :gutter="20" class="flex">
      <el-col v-for="(list,index) in lists" :key="index" :span="6">
        <div class="grid-content bg-purple">
          <div v-for="picturesMessage in list.picturesMessage" class="itemBorder">
            <my-picture-item :pictures-message="picturesMessage"
            @deletePicture="deletePicture"></my-picture-item>
          </div>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
  import myPictureItem from "./PictureItem";
  import axios from 'axios';
  import myPictureUtils from '../picture/PictureUtils';

  export default {
    components: {
      myPictureItem,
      myPictureUtils,
    },

    data() {
      return {
        displayItems: [
          {
            picture_id: Number,
            painter_id: Number,
            picture_address: String,
            uploadTime: String,
            title: String,
          }
        ],

        collectionItems: [
          {
            user_id: Number,
            picture_id: Number,
            collectTime: String,
          }
        ],

        lists: [
          {
            totalHeight: Number,
            picturesMessage: [
              {
                column: Number,
                index: Number,
                width: Number,
                height: Number,
                picture: {
                  picture_id: Number,
                  painter_id: Number,
                  picture_address: String,
                  uploadTime: String,
                  title: String,
                },
                collection: {
                  user_id: Number,
                  picture_id: Number,
                  CollectTime: String,
                }
              }
            ]
          }
        ],
        pictureWidth: 500,

        //关键字显示部分
        tagType: ['', 'success', 'info', 'warning', 'danger'],
        keywords: ["咒术回战", "五条悟", "无量空处", "DM袋喵"],
        searchIsKeyword: false,
      }
    },

    computed: {
      userID() {//监听用户id,从而对收藏内容的显示进行变更
        //id变更时,进行主页collection显示的更新

        return this.$store.state.user.userID;
      }
    },

    watch: {
      userID() {
        console.log("监听用户id变化成功...");
        this.selectAllCollections();
      }
    },

    // var: timer=setInterval(this.makeSureLoad, 50),
    methods: {

      //开始时的初始化，设置总共有listNum列pagelist
      startInit(listNum) {

        let _this = this;
        _this.lists = [];
        console.log("listNum为： " + listNum + "开始列数为： " + _this.lists.length);
        for (let i = 0; i < listNum; i++) {
          _this.lists.push({
            totalHeight: 0,
            picturesMessage: []
          });
        }
        console.log("初始化完后的总列数为：" + _this.lists.length);
      },

      //还没有重写
      initialsubList() {
        let _this = this;
        for (let i = 0; i < _this.lists.length; i++) {
          _this.lists[i].totalHeight = 0;
          _this.lists[i].picturesMessage = [];
          console.log("正在重置List" + i + " ,重置后结果为： 高度" + _this.lists[i].totalHeight);
        }
        console.log('完成pagelist的重置');
      },

      //loading picture function
      imgload(pictureItem, callback) {
        var img = new Image();
        img.src = pictureItem.picture_address;
        // if (img.complete) {
        //   //图片是否存在缓存
        //   callback(img);
        //   return;
        // }
        img.onload = function () {
          callback(img, pictureItem);
        }
      },

      calculateImageHeight(width, height) {
        console.log("宽度是: " + width, "高度是: " + height);
        console.log("相对高度是: " + ((this.pictureWidth) * height / width));
        return ((this.pictureWidth) * height / width);
      },

      allocAllPicture() {
        let _this = this;
        for (let i = 0; i < _this.displayItems.length; i++) {
          _this.imgload(_this.displayItems[i], _this.allocPicture);
        }
        //若用户已经登录，加载收藏关系
        if (_this.$store.state.user.userID >= 0) {
          _this.selectAllCollections();
        }
      },

      allocPicture(img, pictureItem) {
        let _this = this;
        console.log('图片信息为: src——' + img.src + ", height——" + img.height + ';');
        //输出调试信息
        for (let i = 0; i < _this.lists.length; i++) {
          console.log('待插入的列表的信息为,list' + i + ": " + this.lists[i].totalHeight);
        }

        //1.计算那一列是最短的
        let insertColumn = 0;
        let minHeight = _this.lists[0].totalHeight; //不能没有列
        for (let i = 0; i < _this.lists.length; i++) {
          if (_this.lists[i].totalHeight < minHeight) {
            insertColumn = i;
            minHeight = _this.lists[i].totalHeight;
          }
        }

        //2.将图片信息插入最短的一列，并更新列总高度
        _this.lists[insertColumn].totalHeight += _this.calculateImageHeight(img.width, img.height);
        let newPictureMessage = {
          column: insertColumn,
          index: _this.lists[insertColumn].picturesMessage.length,
          width: img.width,
          height: img.height,
          picture: pictureItem,
          collection: {
            user_id: -1,
            picture_id: -1,
            CollectTime: null,
          }
        }
        _this.lists[insertColumn].picturesMessage.push(newPictureMessage);
        console.log("put picture in list" + insertColumn);

        // let isCollected = false;
        // for(let i=0; i<_this.collectionItems; i++){
        //   if(_this.collectionItems[i].picture_id == pictureItem.picture_id){
        //     //
        //   }
        // }
      },


      //mydebug
      mydebug() {
        let _this = this;
        for (let i = 0; i < _this.lists.length; i++) {
          console.log("list" + i + "中picturesMessage的长度为：" + _this.lists[i].picturesMessage.length);
        }
      },

      //axios function
      search() {
        let _this = this;
        console.log("router发生变化并监听到了");
        console.log("监听的类型是: " + _this.$route.params.type);
        if (_this.$route.params.type == 'keyword') {
          let keyword = _this.$route.params.keyword;
          _this.selectByKeyword(keyword);
        } else {

        }
      },

      /***
       * 处理全图片搜集部分
       */
      selectAllPictures() {
        console.log('准备搜集所有图片...')

        let _this = this;
        //1.axios获得所有图片
        axios.get('/pictures/selectAll').then((response) => {
          _this.displayItems = response.data;
          // console.log("所有的图片信息为：" + _this.displayItems);
          //2.列表初始化
          _this.initialsubList();
          //3.分配图片
          _this.allocAllPicture();
        }).catch(function (error) {
          console.log(error);
        });
      },

      selectByTitle(){
        let _this = this;
        let title = _this.$route.params.value;
        console.log("准备搜集所有标题为"+title+"的图片");
        //1.axios获得所有图片
        axios.get('/select/title', {
          params: {
            title: title,
          }
        }).then((response) => {
          _this.displayItems = response.data;
          // console.log("所有的图片信息为：" + _this.displayItems);
          //2.列表初始化
          _this.initialsubList();
          //3.分配图片
          _this.allocAllPicture();
        }).catch(function (error) {
          console.log(error);
        });
      },


      //还没重写
      selectByKeywords(keywords) {
        let _this = this;

        console.log("准备搜集所有关键字串为"+keywords+"的图片");
        //1.axios获得所有图片
        axios.get('/select/keywords', {
          params: {
            keywords: keywords,
          }
        }).then((response) => {
          _this.displayItems = response.data;
          // console.log("所有的图片信息为：" + _this.displayItems);
          //2.列表初始化
          _this.initialsubList();
          //3.分配图片
          _this.allocAllPicture();
        }).catch(function (error) {
          console.log(error);
        });
      },

      /***
       * 处理图片收藏部分
       */
      showNowUserCollections() {
        let _this = this;

        console.log("准备获得用户所有的收藏图片...");
        //1.axios获得当前用户所有的收藏图片
        axios.get('/collection/getPictures', {
          params: {
            user_id: _this.$store.state.user.userID,
          }
        }).then(function (response) {
          _this.displayItems = response.data;
          console.log("用户获取收藏图片成功!");

          //2.初始化
          _this.startInit(3);

          //3.分配图片
          _this.allocAllPicture();
          // _this.allocAllCollectionMessage();异步请求!又忘了！八嘎
        }).catch(function (error) {
          console.log(error);
        })
      },


      selectAllCollections() {
        console.log("准备搜集用户的收藏信息...");
        let _this = this;
        let nowUserID = _this.$store.state.user.userID;
        //1.axios请求获得所有当前用户收藏信息
        axios.get('/collections/get', {
          params: {
            user_id: nowUserID,
          }
        }).then(function (response) {
          _this.collectionItems = response.data;
          console.log("获取收藏信息成功,信息数目为：" + _this.collectionItems.length);
          //debug
          for (let i = 0; i < _this.collectionItems.length; i++) {
            console.log("用户id：" + _this.collectionItems[i].user_id + "图片id：" + _this.collectionItems[i].picture_id)
          }

          _this.allocAllCollectionMessage();
        }).catch(function (error) {
          console.log(error);
        })
      },

      allocAllCollectionMessage() {
        let _this = this;
        for (let i = 0; i < _this.collectionItems.length; i++) {
          _this.allocCollectionMessage(_this.collectionItems[i]);
        }
      },

      allocCollectionMessage(collectionItem) {
        let _this = this;
        //1.从所有的list中寻找与当前collectionItem图片id相同的一项
        for (let i = 0; i < _this.lists.length; i++) {
          for (let j = 0; j < _this.lists[i].picturesMessage.length; j++) {//双循环，笨死了，但是无哈希
            if (_this.lists[i].picturesMessage[j].picture.picture_id == collectionItem.picture_id) {
              //2.找到收藏项所在pagelist中的位置后，将collectoin信息进行替换
              console.log("收藏位置在：第" + (i + 1) + "列， 第" + (j + 1) + "个");
              let newPictureMessage = _this.lists[i].picturesMessage[j];
              newPictureMessage.collection = collectionItem;
              _this.lists[i].picturesMessage.splice(j, 1, newPictureMessage);
            }
          }
        }
      },

      /***
       * 画师页部分
       */
      showPainterPictures(painter_id) {
        let _this = this;
        //1.初始化
        _this.startInit(3);
        //2.axios获得一个画师所有图片
        axios.get('/Painter/Pictures', {
          params: {
            painter_id: painter_id,
          }
        }).then(function (response) {
          _this.displayItems = response.data;
          //3.分配图片
          _this.allocAllPicture();
        }).catch(function (err) {
          console.log(err);
        })
      },

      /***
       * 画师管理图片部分
       */
      deletePicture(picture_id, column, index) {
        console.log("picture-list-delete-picture");
        // this.lists[column].picturesMessage.splice(index,1);
        //双重循环找要删的图片
        for(let column=0;column<this.lists.length;column++){
          for(let index = 0;index<this.lists[column].picturesMessage.length; index++){
            if(this.lists[column].picturesMessage[index].picture.picture_id == picture_id){
              this.lists[column].picturesMessage.splice(index,1);
            }
          }
        }

      },

      /***
       * 排行榜部分
       */
      selectAllRankPicture(pictures){
        this.displayItems = pictures;
        this.initialsubList();
        this.allocAllPicture();
      },

      /***
       * 关键字部分，实现的很笨拙...
       */
      displayKeywords(keywords){
        let newkeywords = [];
        let temp = '';
        for(let i=0;i<keywords.length;i++){
          if(keywords[i] == ' ') {
            if(temp.length > 0){
              newkeywords.push(temp);
              temp = '';
            }
            continue;
          }else{
            temp += keywords[i];
          }
        }
        if(temp.length > 0){
          newkeywords.push(temp);
          temp = '';
        }
        console.log("关键字列表为: " + newkeywords);
        this.keywords =newkeywords;
        this.searchIsKeyword = true;
      },

      nodisplayKeywords(){
        this.searchIsKeyword = false;
      },

      /***
       * 浏览历史部分
       */
      showUserHistory(){
        console.log("正在搜集用户的浏览历史...")
        let _this = this;
        let user_id = _this.$store.state.user.userID;
        axios.get('/History',{
          params: {
            user_id: user_id,
          }
        }).then(function (res) {
          _this.displayItems = res.data;
          _this.initialsubList();
          _this.allocAllPicture();
        }).catch(function (err) {
          console.log(err);
        })
      }

    },
  }
</script>

<style scoped>
  :last-child {
    margin-bottom: 0;
  }

  .bg-purple {
    background: #ffffff;
  }

  .grid-content {
    border-radius: 4px;
    min-height: 24px;
  }

  .flex {
    display: flex;
    flex-direction: row;
    justify-content: center;
  }

  .itemBorder {
    box-sizing: border-box;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    padding: 8px;
    padding-bottom: 16px;
    overflow: hidden;
  }

</style>
