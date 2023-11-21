import { createRouter, createWebHistory } from 'vue-router'
import { ref, onMounted } from "vue";
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
import UnsubscriptionPage from '../components/subscription/UnSubScription.vue'
import { getAnnouncements } from "../composable/announcements/getInformation.js";
import {userDetailStore} from "../composable/users/userDetailStore.js"

// const userDetail = ref(JSON.parse(localStorage.getItem("userDetail")))
const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes:
    [
      {
        path: '/',
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
        path: `/admin/announcement/:id`,
        name: 'announcementDetail',
        component: AnnouncementDetail,
        beforeEnter: async (to, from, next) => {
          const userDetail = userDetailStore()
          if (userDetail.role !== 'ROLE_admin') {
            const announcement = ref(await getAnnouncements())
            const announcementId = ref([])
            const announcementExits = ref(true)
            announcement.value.forEach(element => {
              announcementId.value.push(element.id)
            });
            console.log(announcementId.value);

            announcementId.value.forEach(element => {
              console.log(element);
              if (Number(to.params.id) === element) {
                next();
                announcementExits.value = false
              } 

            });
            if (announcementExits.value === true) {
              alert(`You don't have permission to access this page.`)
              console.log(to.params.id);
              next('/announcement');
            }
          } else {
            next();
          }
        }
      },
      {
        path: `/admin/announcement/add`,
        name: 'addAnnouncement',
        component: AddAnnouncement
      },
      {
        path: `/admin/announcement/:id/delete`,
        name: 'deleteAnnouncement',
        component: Announcements,
        beforeEnter: async (to, from, next) => {
          const userDetail = userDetailStore()
          if (userDetail.role !== 'ROLE_admin') {
            const announcement = ref(await getAnnouncements())
            const announcementId = ref([])
            const announcementExits = ref(true)
            announcement.value.forEach(element => {
              announcementId.value.push(element.id)
            });
            console.log(announcementId.value);

            announcementId.value.forEach(element => {
              console.log(element);
              if (Number(to.params.id) === element) {
                next();
                announcementExits.value = false
              } 

            });
            if (announcementExits.value === true) {
              alert(`You don't have permission to access this page.`)
              console.log(to.params.id);
              next('/announcement');
            }
          } else {
            next();
          }
        }
      },
      {
        path: `/admin/announcement/:id/edit`,
        name: 'editAnnouncement',
        component: EditAnnouncement,
        beforeEnter: async (to, from, next) => {
          const userDetail = userDetailStore()
          if (userDetail.role !== 'ROLE_admin') {
            const announcement = ref(await getAnnouncements())
            const announcementId = ref([])
            const announcementExits = ref(true)
            announcement.value.forEach(element => {
              announcementId.value.push(element.id)
            });
            console.log(announcementId.value);

            announcementId.value.forEach(element => {
              console.log(element);
              if (Number(to.params.id) === element) {
                next();
                announcementExits.value = false
              } 

            });
            if (announcementExits.value === true) {
              alert(`You don't have permission to access this page.`)
              console.log(to.params.id);
              next('/announcement');
            }
          } else {
            next();
          }
        }
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
        component: UserManagement,
        beforeEnter: (to, from, next) => {
          const userDetail = userDetailStore()
          if (userDetail.role !== 'ROLE_admin') {
            alert(`You don't have permission to access this page.`)
            next('/announcement');
          } else {
            next();
          }
        }
      },
      {
        path: `/admin/user/:id/edit`,
        name: 'userEditDetail',
        component: UserEditDetail,
        beforeEnter: (to, from, next) => {
          const userDetail = userDetailStore()
          if (userDetail.role !== 'ROLE_admin') {
            alert(`You don't have permission to access this page.`)
            next('/announcement');
          } else {
            next();
          }
        }
      },
      {
        path: `/admin/user/add`,
        name: 'addUser',
        component: AddUser,
        beforeEnter: (to, from, next) => {
          const userDetail = userDetailStore()
          if (userDetail.role !== 'ROLE_admin') {
            alert(`You don't have permission to access this page.`)
            next('/announcement');
          } else {
            next();
          }
        }
      },
      {
        path: `/admin/user/:id/delete`,
        name: 'deleteUser',
        component: UserManagement,
        beforeEnter: (to, from, next) => {
          const userDetail = userDetailStore()
          if (userDetail.role  !== 'ROLE_admin') {
            alert(`You don't have permission to access this page.`)
            next('/announcement');
          } else {
            next();
          }
        }
      },
      {
        path: `/admin/user/match`,
        name: 'matchPassword',
        component: MatchPassword,
        beforeEnter: (to, from, next) => {
          const userDetail = userDetailStore()
          if (userDetail.role  !== 'ROLE_admin') {
            alert(`You don't have permission to access this page.`)
            next('/announcement');
          } else {
            next();
          }
        }
      },
      {
        path: `/login`,
        name: 'login',
        component: LoginPage
      },
      {
        path: '/announcement/unsubscription',
        name: 'unsubscription',
        component: UserViewAnnouncement,
        props:(route) => ({token: route.query.token})
      }
    ]
})

router.beforeEach((to, from, next) => {
  if (!localStorage.getItem('accessToken') && to.name === 'userViewAnnouncement') {
    next()
  }
  else if (!localStorage.getItem('accessToken') && to.name === 'unsubscription') {
    next()
  }
  else if (!localStorage.getItem('accessToken') && to.name === 'userViewDetail') {
    next()
  }
  else if (!localStorage.getItem('accessToken') && to.name !== 'login') {
    next('/login')
  } else {
    next()
  }
})

export default router
