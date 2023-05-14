import { createRouter, createWebHistory } from 'vue-router'
import Announcements from '../components/Announcements.vue'
import AnnouncementDetail from '../components/AnnouncementDetail.vue'
import AddAnnouncement from '../components/AddAnnouncement.vue'
import EditAnnouncement from '../components/EditAnnouncement.vue'
import UserViewAnnouncement from '../components/UserViewAnnouncement.vue'
import UserViewDetail from '../components/UserViewDetail.vue'
const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes:
    [
      {
        path:'/',
        redirect: '/admin/announcement'
      },
      {
        path: '/admin/announcement',
        name: 'announcements',
        component: Announcements
      },
      {
        path:'/admin/announcement/:id',
        name:'announcementDetail',
        component: AnnouncementDetail
      },
      {
        path: '/admin/announcement/add',
        name:'addAnnouncement',
        component: AddAnnouncement
      },
      {
        path: '/admin/announcement/:id/delete',
        name: 'deleteAnnouncement',
        component: Announcements
      },
      {
        path: '/admin/announcement/:id/edit',
        name: 'editAnnouncement',
        component: EditAnnouncement
      },
      {
        path: '/announcement',
        name: 'userViewAnnouncement',
        component: UserViewAnnouncement
      },
      {
        path: '/announcement/:id',
        name: 'userViewDetail',
        component: UserViewDetail
      }
    ]
})

export default router
