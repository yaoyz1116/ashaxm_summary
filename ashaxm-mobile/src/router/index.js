import Vue from 'vue'
import Router from 'vue-router'
import Home from '@/components/Home'
import UserLogin from '@/components/UserLogin'
import Countdown from '@/components/Countdown'
import Pwdmanage from '@/components/Pwdmanage'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'UserLogin',
      component: UserLogin
    },
    {
      path: '/home',
      name: 'Home',
      component: Home
    },
    {
      path: '/countdown',
      name: 'Countdown',
      component: Countdown
    },
    {
      path: '/pwdmanage',
      name: 'Pwdmanage',
      component: Pwdmanage
    }
  ]
})
