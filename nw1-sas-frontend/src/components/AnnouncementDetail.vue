<script setup>
import { getAnnouncement } from '../composable/getInformation.js'
import { ref, onMounted} from "vue"
import {changeDateTimeFormat} from "../composable/changeFormatDate.js"
import { useRoute , useRouter } from 'vue-router';
import Calendar from './icones/Calendar.vue';
import EditIcon from './icones/EditIcon.vue';

const announcement = ref({})
const router = useRouter()
const route = useRoute()

onMounted(async() => {
    const route = useRoute()
    announcement.value = await getAnnouncement(route.params.id)
    if (!announcement.value) {
        alert('The request page is not available')
        router.push({name: "announcements"})
        announcement.value = ""
    }
})
</script>

<template>
    <div class="text-center bg-Background min-h-screen max-h-full">
        <div class="rounded-full shadow-md bg-DarkBlue inline-block mt-3">
            <h1 class="text-BlueFonts text-2xl px-5 py-5 font-bold">
                Announcement Detail:
            </h1>
        </div>
        <div class="text-right mr-20 text-xl font-bold mt-10 text-BlueFonts drop-shadow-lg">#VIEWS : {{ announcement.viewCount }}</div>
        <div class="rounded-3xl bg-LightBlue mx-10 py-3">
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
                <p class="ann-category break-all mr-5">{{ announcement.announcementCategory }}</p>
            </div>
            <div class="flex my-5 items-center">
                <h1 class="mx-5 font-bold">
                    Description
                </h1>
                <p v-html="announcement.announcementDescription" class="ann-description break-all text-left mr-5 ql-editor"></p>
            </div>
            <div class="flex my-5">
                <h1 class="mx-5 font-bold">
                    Publish Date
                </h1>
                <p class="ann-publish-date mr-3 break-all">{{ changeDateTimeFormat(announcement.publishDate) }}</p>
                <Calendar></Calendar>
            </div>
            <div class="flex my-5">
                <h1 class="mx-5 font-bold">
                    Close Date
                </h1>
                <p class="ann-close-date mr-3 break-all">{{ changeDateTimeFormat(announcement.closeDate) }}</p>
                <Calendar></Calendar>
            </div>
            <div class="flex my-5">
                <h1 class="mx-5 font-bold">
                    Display
                </h1>
                <p class="ann-display mr-5 break-all">{{ announcement.announcementDisplay }}</p>
            </div>
        </div>
        <div class="mt-5">
        <router-link :to="{ name: 'announcements' }"><button
                class="ann-button bg-DarkRed rounded-full shadow-md py-2 px-7 ml-5 hover:bg-ButtonDeleteHover font-bold"
                @click="idDetail">Back</button>
        </router-link>

        <router-link :to="{ name: 'editAnnouncement', params: { id: route.params.id } }">
            <button class="ann-button bg-DarkGreen shadow-md rounded-full py-2 px-5 ml-5 hover:bg-ButtonViewHover font-bold">
                <EditIcon class="inline mr-2 mb-1"></EditIcon> Edit</button>
        </router-link>
        </div>

    </div>
</template>

<style scoped></style>