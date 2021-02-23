import Vue from 'vue'
import Router from 'vue-router'
import HomePage from '../view/main/HomePage';
import BasicLayout from '../view/BasicLayout';
import UserCollection from '../view/main/UserCollection';
import UserFollow from '../view/main/UserFollow';
import PainterPage from '../view/main/painter/PainterPage';
import PictureUpload from '../components/picture/Upload';
import PicturePage from '../components/PicturePage/page';
import RankPage from '../components/ranklist/page';
import Nothing from '../components/nothing'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      redirect: '/Main/all/1',
    },

    {
      path: '/Main',
      component: BasicLayout,
      children: [
        {
          path: 'all/:random',
          name: 'HomePage',
          component: HomePage,
        },


        {
          path: '/search/title/:value/:random',
          name: 'SearchByTitle',
          component: HomePage,
        },

        {
          path: '/search/keywords/:value/:random',
          name: 'SearchByKeywords',
          component: HomePage,
        },

        {
          path: 'search/keywords'
        },


        {
          path: 'User/Collection/:random',
          name: 'userCollection',
          component: UserCollection,
        },
        {
          path: 'User/Follow/:random',
          name: 'userFollow',
          component: UserFollow,
        },
        {
          path: 'Painter/:painter_id/:random',
          name: 'PainterPage',
          component: PainterPage,
        },
        {
          path: '/Picture/:picture_id',
          name: 'PicturePage',
          component: PicturePage,
        },

        {
          path: '/Rank',
          name: 'Rank',
          component: RankPage,
        },
        {
          path: '/nothing',
          name: 'nothing',
          component: Nothing
        },

        {
          path: '/History/:user_id/:random',
          name: 'history',
          component: HomePage,
        }

      ]
    },

    {
      path: '/User'
    },

    {
      path: '/Upload',
      component: PictureUpload
    },





    // {
    //   path: '/Helloworld',
    //   component: Helloword,
    // }
  ]
})
