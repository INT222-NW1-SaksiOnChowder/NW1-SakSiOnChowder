import { createApp } from 'vue'
import App from './App.vue'
import router from '../src/router/index.js'
import { QuillEditor } from '@vueup/vue-quill'
import '@vueup/vue-quill/dist/vue-quill.snow.css';

import './assets/main.css'
import { createPinia } from 'pinia'

const app = createApp(App)
app.component('QuillEditor', QuillEditor)
app.use(createPinia())
app.use(router)
app.mount('#app')
