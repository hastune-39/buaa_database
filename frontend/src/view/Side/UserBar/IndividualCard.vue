<template>
  <el-card :body-style="{ padding: '0px' }">
    <div class="rowclass">
      <div style="display: flex"> <img :src="inputUserProfilePicture" style="align-self: center" class="image ind"></div>

      <div style="margin: 0px;padding: 0px 14px; width: 100%">
        <div style=" width: 100%">
          <div v-if="!updateUserNameStatus" class="individuleProduce username" @click="editName">{{inputUserName}}</div>
          <el-input v-else
                    size="mini"
                    v-model="inputUserName"
                    class="input-class"
                    style=""
                    @keydown.enter.native="updateUserName">
          </el-input>
          <!--          <i class="el-icon-edit" style="color: pink; float: right" @click="updateUserMsg"></i>-->
        </div>
        <div v-if="!updateUserAddressStatus" @click="editAddress" class="individuleProduce useraddress" >{{inputUserAddress}}</div>
        <el-input v-else
                  size="mini"
                  v-model="inputUserAddress"
                  class="input-address-class"
                  @keydown.enter.native="updateUserAddress">
        </el-input>
        <div v-if="!updateUserTextStatus" @click="editText" class="bottom clearfix individuleProduce usertext">
          {{inputUserSignature}}
        </div>
        <el-input v-else
                  size="mini"
                  type="textarea"
                  v-model="inputUserSignature"
                  style="width: 100%; margin-top: 10px; margin-bottom: 7px; margin-left: 5px"
                  @keydown.enter.native="updateUserSignature">
        </el-input>
      </div>
    </div>
    <my-user-update-message ref="updateMessage"/>
  </el-card>
</template>

<script>
  import myUserUpdateMessage from '../../../components/User/UpdateMessage'

  export default {
    name: "IndividualCard",
    components: {
      myUserUpdateMessage
    },

    data() {
      return {
        inputUserName: "",
        inputUserAddress: "",
        inputUserSignature: "",
        inputUserProfilePicture: "",

        updateUserNameStatus: false,
        updateUserAddressStatus: false,
        updateUserTextStatus: false,
        updateUserProfilePictureStatus: false,
      }
    },

    // computed: {
    //   userID() {
    //     return this.$store.state.user.userID;
    //   },
    //   userName() {
    //     return this.$store.state.user.userName;
    //   },
    //   sex() {
    //     return this.$store.state.user.sex;
    //   },
    //   address() {
    //     return this.$store.state.user.address;
    //   },
    //   profile_picture() {
    //     return this.$store.state.user.profile_picture;
    //   },
    //   signature() {
    //     return this.$store.state.user.signature;
    //   }
    //
    // },
    methods: {
      editName() {
        this.updateUserNameStatus = true;
      },
      editAddress() {
        this.updateUserAddressStatus = true;
      },
      editText() {
        this.updateUserTextStatus = true;
      },
      editPicture() {
        this.updateUserProfilePictureStatus = true;
      },

      debug() {
        console.log(this.userID, this.userName, this.address);
      },

      updateUserName() {
        this.updateUserNameStatus = false;
        this.$refs.updateMessage.updateUserName(this.inputUserName);
      },

      updateUserAddress() {
        this.updateUserAddressStatus = false;
        this.$refs.updateMessage.updateUserAddress(this.inputUserAddress);
      },

      updateUserSignature() {
        this.updateUserTextStatus = false;
        this.$refs.updateMessage.updateUserSignature(this.inputUserSignature);
      },

      updateUserProfilePicture() {
        this.updateUserProfilePictureStatus = false;
        this.$refs.updateMessage.updateUserProfilePicture(this.inputUserProfilePicture);
      },

      updateUserMsg() {
        console.log("正在更改用户信息...");
        this.$refs.updateMessage.open();
      }
    },

    created() {
      //1.拉取用户信息
      this.inputUserName = this.$store.state.user.userName;
      this.inputUserAddress = this.$store.state.user.address;
      this.inputUserSignature = this.$store.state.user.signature;
      this.inputUserProfilePicture = this.$store.state.user.profile_picture;
    }
  }
</script>

<style scoped>
  .rowclass {
    display: flex;
    flex-direction: row;
    flex-flow: row;
  }


  .username {
    font-size: 20px;
    letter-spacing: 4px;
    position: center;
    color: pink;
    font-weight: bolder;
  }

  .useraddress {
    font-size: 10px;
    letter-spacing: 3px;
    font-weight: 100;
  }

  .usertext {
    font-size: 10px;
    letter-spacing: 3px;
    font-weight: 100;
    text-align: left;
    margin-left: 0px;
    line-height: 18px !important;
  }

  .bottom {
    margin-top: 13px;
    line-height: 12px;
  }

  .individuleProduce {
    min-width: 200px;
    max-width: 250px;
    width: 100%;
    margin: 5px;
    padding: 5px;
  }

  .image {
    width: 200px;
    max-height: 300px;
    display: block;
    padding: 8px 0px 8px 8px;
  }

  .input-class >>> input {
    max-width: 250px;
    min-width: 200px;
    width: 100%;
    font-size: 20px;
    letter-spacing: 4px;
    color: pink;
    font-weight: bolder;
    position: center;
    border: none;
    margin: 5px;
    margin-top: 11px;
    padding: 5px;
  }

  .input-address-class >>> input {
    max-width: 250px;
    width: 100%;
    font-size: 10px;
    letter-spacing: 3px;
    font-weight: 100;
    position: center;
    border: none;
  }
</style>
