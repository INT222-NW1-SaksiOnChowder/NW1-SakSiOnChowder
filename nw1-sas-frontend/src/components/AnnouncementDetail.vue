<script setup>
import { getAnnouncement } from '../composable/getInformation.js'
import { ref, onMounted} from "vue"
import {changeDateTimeFormat} from "../composable/changeFormatDate.js"
import { useRoute , useRouter } from 'vue-router';

const announcement = ref({})
const router = useRouter()
const route = useRoute()

onMounted(async() => {
    const route = useRoute()
    announcement.value = await getAnnouncement(route.params.id)
    console.log(route.params.id);
    if (!announcement.value) {
        alert('The request page is not available')
        router.push({name: "announcements"})
        announcement.value = ""
    }
})
</script>

<template>
    <div class="text-center bg-Background h-screen">
        <div class="rounded-full bg-DarkBlue inline-block mt-3">
            <h1 class="text-BlueFonts text-2xl px-5 py-5 font-bold">
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
                <p class="ann-category break-all mr-5">{{ announcement.announcementCategory }}</p>
            </div>
            <div class="flex my-5">
                <h1 class="mx-5 font-bold">
                    Description
                </h1>
                <p class="ann-description break-all text-left mr-5">{{ announcement.announcementDescription }}</p>
            </div>
            <div class="flex my-5">
                <h1 class="mx-5 font-bold">
                    Publish Date
                </h1>
                <p class="ann-publish-date mr-5 break-all">{{ changeDateTimeFormat(announcement.publishDate) }}</p>
            </div>
            <div class="flex my-5">
                <h1 class="mx-5 font-bold">
                    Close Date
                </h1>
                <p class="ann-close-date mr-5 break-all">{{ changeDateTimeFormat(announcement.closeDate) }}</p>
            </div>
            <div class="flex my-5">
                <h1 class="mx-5 font-bold">
                    Display
                </h1>
                <p class="ann-display mr-5 break-all">{{ announcement.announcementDisplay }}</p>
            </div>
        </div>
        <router-link :to="{ name: 'announcements' }"><button
                class="ann-button bg-DarkRed rounded-full py-2 px-7 ml-5 hover:bg-ButtonDeleteHover font-bold"
                @click="idDetail">Back</button>
        </router-link>

        <router-link :to="{ name: 'editAnnouncement', params: { id: route.params.id } }">
            <button class="ann-button bg-DarkGreen rounded-full py-2 px-8 ml-5 hover:bg-ButtonViewHover font-bold">Edit</button>
        </router-link>


    </div>
</template>

<style scoped></style>