import { createRouter, createWebHistory } from 'vue-router'
import Announcements from '../components/announcements/Announcements.vue'
import AnnouncementDetail from '../components/announcements/AnnouncementDetail.vue'
import AddAnnouncement from '../components/announcements/AddAnnouncement.vue'
import EditAnnouncement from '../components/announcements/EditAnnouncement.vue'
import UserViewAnnouncement from '../components/users/UserViewAnnouncement.vue'
import UserViewDetail from '../components/users/UserViewDetail.vue'
import UserManagement from '../components/users/UserManagement.vue'
import UserEditDetail from '../components/users/UserEditDetail.vue'
import AddUser from '../components/users/AddUser.vue'
import MatchPassword from '../components/users/MatchPassword.vue'
import LoginPage from '../components/users/LoginPage.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes:
    [
      {
        path:'/',
        redirect: '/announcement'
      },
      {
        path: `/admin/announcement`,
        name: 'announcements',
        // beforeEnter: (to, from, next) => {
        //   if (!localStorage.getItem('accessToken')) {
        //     next('/login')
        //   } else {
        //     next()
        //   }
        // },
        component: Announcements
      },
      {
        path:`/admin/announcement/:id`,
        name:'announcementDetail',
        component: AnnouncementDetail
      },
      {
        path: `/admin/announcement/add`,
        name:'addAnnouncement',
        component: AddAnnouncement
      },
      {
        path: `/admin/announcement/:id/delete`,
        name: 'deleteAnnouncement',
        component: Announcements
      },
      {
        path: `/admin/announcement/:id/edit`,
        name: 'editAnnouncement',
        component: EditAnnouncement
      },
      {
        path: `/announcement`,
        name: 'userViewAnnouncement',
        component: UserViewAnnouncement
      },
      {
        path: `/announcement/:id`,
        name: 'userViewDetail',
        component: UserViewDetail
      },
      {
        path: `/admin/user`,
        name: 'userManagement',
        component: UserManagement
      },
      {
        path: `/admin/user/:id/edit`,
        name: 'userEditDetail',
        component: UserEditDetail
      },
      {
        path: `/admin/user/add`,
        name: 'addUser',
        component: AddUser
      },
      {
        path: `/admin/user/:id/delete`,
        name: 'deleteUser',
        component: UserManagement
      },
      {
        path: `/admin/user/match`,
        name: 'matchPassword',
        component: MatchPassword
      },
      {
        path: `/login`,
        name: 'login',
        component: LoginPage
      }
    ]
})

router.beforeEach((to, from, next) => {
  if (!localStorage.getItem('accessToken') && to.name === 'userViewAnnouncement') {
    next()
  }
  else if (!localStorage.getItem('accessToken') && to.name !== 'login') {
    next('/login')
  } else {
    next()
  }
})

export default router
