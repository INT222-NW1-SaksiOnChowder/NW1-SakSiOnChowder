<script setup>
import { getAnnouncement } from '../../composable/announcements/getInformation.js'
import { getFiles, getFile } from '../../composable/announcements/getFiles';
import { ref, onMounted } from "vue"
import { changeDateTimeFormat } from "../../composable/changeFormatDate.js"
import { useRoute, useRouter } from 'vue-router';
import Calendar from '../icones/Calendar.vue';
import EditIcon from '../icones/EditIcon.vue';

const announcement = ref({})
const files = ref([])
const fileStatus = ref("")
const router = useRouter()
const route = useRoute()

onMounted(async () => {
    const route = useRoute()
    announcement.value = await getAnnouncement(route.params.id)
    if (announcement.value === false) {
        announcement.value = await getAnnouncement(route.params.id)
    }
    if (!announcement.value) {
        alert('The request page is not available')
        router.push({ name: "announcements" })
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
    <div class="text-center bg-Background min-h-screen max-h-full">
        <div class="rounded-full shadow-md bg-DarkBlue inline-block mt-3 mb-10">
            <h1 class="text-BlueFonts lg:text-2xl text-lg px-3 py-2 lg:px-5 lg:py-5 font-bold">
                Announcement Detail:
            </h1>
        </div>
        <div class="rounded-3xl mx-5 text-sm lg:text-base bg-LightBlue lg:mx-10 py-3">
            <div class="flex my-5 lg:justify-center items-center">
                <h1 class="text-left mx-5 font-bold">
                    Title
                </h1>
                <p class="ann-title rounded-lg bg-Cream px-2 py-1 md:p-2 break-words text-left mr-5">{{ announcement.announcementTitle }}</p>
            </div>
            <div class="flex my-5 items-center">
                <h1 class="mx-5 text-left font-bold">
                    Category
                </h1>
                <p class="ann-category rounded-lg bg-Cream px-2 py-1 md:p-2 break-words mr-5">{{ announcement.announcementCategory }}</p>
            </div>
            <div class="flex my-5 items-center">
                <h1 class="mx-5 text-left font-bold">
                    Description
                </h1>
                <p class="ann-description rounded-lg bg-Cream px-2 py-1 md:p-2 break-words text-left mr-5 ql-editor" v-html="announcement.announcementDescription">
                </p>
            </div>
            <div class="flex my-5 items-center">
                <h1 class="mx-5  text-left font-bold">
                    Publish Date
                </h1>
                <p class="ann-publish-date rounded-lg bg-Cream px-2 py-1 md:p-2 mr-3 break-words">{{ changeDateTimeFormat(announcement.publishDate) }}</p>
                <Calendar></Calendar>
            </div>
            <div class="flex my-5 items-center">
                <h1 class="mx-5 text-left font-bold">
                    Close Date
                </h1>
                <p class="ann-close-date rounded-lg bg-Cream px-2 py-1 md:p-2 mr-3 break-words">{{ changeDateTimeFormat(announcement.closeDate) }}</p>
                <Calendar></Calendar>
            </div>
            <div class="flex my-5 items-center">
                <h1 class="mx-5 text-left font-bold">
                    Display
                </h1>
                <p class="ann-display rounded-lg bg-Cream px-2 py-1 md:p-2 mr-5 break-words">{{ announcement.announcementDisplay }}</p>
            </div>
            <div class="flex justify-start items-center">
                <h1 class="mx-5 text-left font-bold">
                    Files
                </h1>
                <div v-if="files.length !== 0" class="flex flex-col items-start">
                    <div v-for="file in files" :key="file" class="ann-display mx-5 my-2 break-words">
                        <button @click="previewFile(route.params.id, file)"
                            class="p-2 mb-2 text-sm bg-white hover:bg-neutral-400 hover:text-white rounded-md pr-10  w-max">{{
                                file }}</button>
                    </div>
                </div>
                <div class="mx-5 rounded-lg bg-Cream px-2 py-1 md:p-2" v-else>
                    <p>{{ fileStatus }}</p>
                </div>
            </div>
        </div>
        <div class="mt-5 ">
            <router-link :to="{ name: 'announcements' }"><button
                    class="ann-button bg-DarkRed rounded-full shadow-md py-2 px-7 ml-5 hover:bg-ButtonDeleteHover font-bold"
                    @click="idDetail">Back</button>
            </router-link>

            <router-link :to="{ name: 'editAnnouncement', params: { id: route.params.id } }">
                <button
                    class="ann-button bg-DarkGreen shadow-md rounded-full py-2 px-5 ml-5 hover:bg-ButtonViewHover font-bold">
                    <EditIcon class="inline mr-2 mb-1"></EditIcon> Edit
                </button>
            </router-link>
        </div>

    </div>
</template>

<style scoped></style>