<script setup>
import { getAnnouncementUser } from '../../composable/users/getAnnouncementUser.js'
import { ref, onMounted} from "vue"
import { getFiles, getFile } from '../../composable/announcements/getFiles.js';
import { useRoute , useRouter } from 'vue-router';
import { changeDateTimeFormat } from '../../composable/changeFormatDate';
import { annStores } from '../../stores/counter.js'
import Calendar from '../icones/Calendar.vue';
const announcementStores = annStores()
const showCloseTime = ref(false)
const setShowCloseTime = () => {
  if (announcementStores.mode === 'close') {
    showCloseTime.value = true
  } else {
    showCloseTime.value = false
  }
}
const announcement = ref({})
const router = useRouter()
const route = useRoute();

const files = ref([])
const fileStatus = ref("")

onMounted(async() => {
    const route = useRoute()
    setShowCloseTime()
    announcement.value = await getAnnouncementUser(route.params.id, announcementStores.mode)
    if (!announcement.value) {
        alert('The request page is not available')
        router.push({name: "userViewAnnouncement"})
        announcement.value = ""
    }

    files.value = await getFiles(route.params.id)
    if (files.value.length === 0) {
        fileStatus.value = "No files available"
    }
})

const previewFile = async (id, fileName) => {
    const file = await getFile(id, fileName)
    const url = file.url;
    window.open(url);
}

</script>

<template>
    <div class="text-center min-h-screen max-h-full bg-Background">
        <div class="rounded-full shadow-md bg-DarkBlue inline-block mt-3">
            <h1 class="text-BlueFonts  text-2xl px-5 py-5 font-bold">
                Announcement Detail:
            </h1>
        </div>
        <div class="rounded-3xl bg-LightBlue m-10 py-3">
            <div class="flex my-5 justify-center">
                <h1 class="mx-5 font-bold">
                    Title
                </h1>
                <p class="ann-title break-all text-left mr-5">{{ announcement.announcementTitle }}</p>
            </div>
            <div class="flex my-5">
                <h1 class="mx-5 font-bold">
                    Category
                </h1>
                <p class="ann-category break-all">{{ announcement.announcementCategory }}</p>
            </div>
            <div v-show="showCloseTime" class="flex my-5">
                <h1 class="mx-5 font-bold">
                    Closed on
                </h1>
                <p class="ann-close-date break-all mr-3">{{ changeDateTimeFormat(announcement.closeDate) }}</p>
                <Calendar></Calendar>
            </div>
            <div class="flex my-5 items-center">
                <h1 class="mx-5 font-bold">
                    Description
                </h1>
                <p class="ann-description break-all text-left mr-5 ql-editor" v-html="announcement.announcementDescription"></p>
            </div>
            <div class="flex justify-start">
                <h1 class="mx-5 font-bold">
                    Files
                </h1>
                <div v-if="files.length !== 0" class="flex flex-col items-start">
                    <div v-for="file in files" :key="file" class="ann-display mx-5 my-2 break-all">
                        <button @click="previewFile(route.params.id, file)"
                            class="p-2 mb-2 text-sm bg-white hover:bg-neutral-400 hover:text-white rounded-md pr-10  w-max">{{
                                file }}</button>
                    </div>
                </div>
                <div class="mx-5" v-else>
                    <p>{{ fileStatus }}</p>
                </div>
            </div>
        </div>
        <router-link :to="{ name: 'userViewAnnouncement' }"><button
                class="ann-button font-bold bg-DarkRed rounded-full py-2 px-6 ml-5 hover:bg-ButtonDeleteHover">
                Back
                </button>
        </router-link>


    </div>
</template>

<style scoped></style>