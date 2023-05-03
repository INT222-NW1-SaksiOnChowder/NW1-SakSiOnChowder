import { createRouter, createWebHistory } from 'vue-router'
import Announcements from '../components/Announcements.vue'
import AnnouncementDetail from '../components/AnnouncementDetail.vue'
import AddEditAnnouncement from '../components/AddEditAnnouncement.vue'
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
        component: AddEditAnnouncement
      },
      {
        path: '/admin/announcement/:id/delete',
        name: 'deleteAnnouncement',
        component: Announcements
      },
      {
        path: '/admin/announcement/:id/edit',
        name: 'editAnnouncement',
        component: AnnouncementDetail
      }
    ]
})

export default router
