import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  name: 'store',
  state: {
    user: {
      userID: -1,
      login_name: String,
      userName: String,
      pass_word: String,
      sex: String,
      address: String,
      profile_picture: String,
      signature: String,

      //画师验证
      isPainter: false,
    },
  },

  mutations: {
    login (state, inputUser) {
      state.user = inputUser;
    },

    updateUserMessage(state, inputUser){
      state.user = inputUser;
    },

    painterRegister(state){
      state.user.isPainter = true;
    }
  },
})
