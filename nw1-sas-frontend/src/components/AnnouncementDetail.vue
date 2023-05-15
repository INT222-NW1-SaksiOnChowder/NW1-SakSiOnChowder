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
    <div class="text-center bg-Background w-screen h-screen">
        <div class="rounded-full bg-DarkBlue inline-block mt-3">
            <h1 class="text-BlueFonts text-2xl px-5 py-5 font-bold">
                Announcement Detail:
            </h1>
        </div>
        <div class="rounded-lg bg-LightBlue m-10 py-3">
            <div class="flex my-5 justify-center">
                <h1 class="mx-5 font-bold">
                    Title
                </h1>
                <p class="ann-title">{{ announcement.announcementTitle }}</p>
            </div>
            <div class="flex my-5">
                <h1 class="mx-5 font-bold">
                    Category
                </h1>
                <p class="ann-category">{{ announcement.announcementCategory }}</p>
            </div>
            <div class="flex my-5">
                <h1 class="mx-5 font-bold">
                    Description
                </h1>
                <p class="ann-description">{{ announcement.announcementDescription }}</p>
            </div>
            <div class="flex my-5">
                <h1 class="mx-5 font-bold">
                    Publish Date
                </h1>
                <p class="ann-publish-date">{{ changeDateTimeFormat(announcement.publishDate) }}</p>
            </div>
            <div class="flex my-5">
                <h1 class="mx-5 font-bold">
                    Close Date
                </h1>
                <p class="ann-close-date">{{ changeDateTimeFormat(announcement.closeDate) }}</p>
            </div>
            <div class="flex my-5">
                <h1 class="mx-5 font-bold">
                    Display
                </h1>
                <p class="ann-display">{{ announcement.announcementDisplay }}</p>
            </div>
        </div>
        <router-link :to="{ name: 'announcements' }"><button
                class="ann-button bg-DarkRed rounded-full py-2 px-5 ml-5 hover:text-xl"
                @click="idDetail">Back</button>
        </router-link>

        <router-link :to="{ name: 'editAnnouncement', params: { id: route.params.id } }">
            <button class="ann-button bg-DarkGreen rounded-full py-2 px-6 ml-5 hover:text-xl">Edit</button>
        </router-link>


    </div>
</template>

<style scoped></style>