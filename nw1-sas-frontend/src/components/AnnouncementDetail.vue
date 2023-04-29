<script setup>

import { getAnnouncement } from '../composable/getInformation.js'
import { ref, watchEffect} from "vue"
import {changeDateTimeFormat} from "../composable/changeFormatDate.js"
import { useRoute , useRouter } from 'vue-router';
const announcement = ref([])
const router = useRouter()

watchEffect(async() => {
    const route = useRoute()
    announcement.value = await getAnnouncement(route.params.id)
    if (!announcement.value) {
        alert('KUY')
        router.push({name: "announcements"})
        announcement.value = ""
    }
})


</script>

<template>
    <div>
        <h1 class="text-2xl ml-5">
            Announcement Detail:
        </h1>
    </div>
    <div class="border m-5">
        <div class="flex my-5">
            <h1 class="mx-5">
                Title
            </h1>
            <p>{{ announcement.announcementTitle }}</p>
        </div>
        <div class="flex my-5">
            <h1 class="mx-5">
                Category
            </h1>
            <p>{{announcement.announcementCategory}}</p> 
        </div>
        <div class="flex my-5">
            <h1 class="mx-5">
                Description
            </h1>
            <p>{{ announcement.announcementDescription }}</p>
        </div>
        <div class="flex my-5">
            <h1 class="mx-5">
                Publish Date
            </h1>
            <p>{{ changeDateTimeFormat(announcement.publishDate) }}</p>
        </div>
        <div class="flex my-5">
            <h1 class="mx-5">
                Close Date
            </h1>
            <p>{{ changeDateTimeFormat(announcement.closeDate) }}</p>
        </div>
        <div class="flex my-5">
            <h1 class="mx-5">
                Display
            </h1>
            <p>{{ announcement.announcementDisplay }}</p>
        </div>
    </div>
    <router-link :to="{name: 'announcements'}"><button class="bg-red-200 rounded-lg p-2 ml-5" @click="idDetail">Back</button></router-link>
</template>

<style scoped></style>