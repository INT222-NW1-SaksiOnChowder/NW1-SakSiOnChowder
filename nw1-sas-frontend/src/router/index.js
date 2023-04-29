import { createRouter, createWebHistory } from 'vue-router'
import Announcements from '../components/Announcements.vue'
import AnnouncementDetail from '../components/AnnouncementDetail.vue'
const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes:
    [
      {
        path: '/admin/announcement',
        name: 'announcements',
        component: Announcements
      },
      {
        path:'/admin/announcement/:id',
        name:'announcementDetail',
        component: AnnouncementDetail
      }
    ]
})

export default router
