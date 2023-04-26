import { createRouter, createWebHistory } from 'vue-router'
import Announcements from '../components/Announcements.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes:
    [
      {
        path: '/',
        redirect: '/announcements'
      },
      {
        path: '/announcements',
        name: 'announcements',
        component: Announcements
      }
    ]
})

export default router
