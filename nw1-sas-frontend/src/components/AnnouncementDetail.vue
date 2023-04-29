<script setup>

import { getAnnouncement } from '../composable/getInformation.js'
import { ref, onMounted, watchEffect } from "vue"
import {changeDateTimeFormat} from "../composable/changeFormatDate.js"

const announcement = ref([])
// const announcementID = ref()

const props = defineProps({
    id : {
        type: Number
    }
})

watchEffect(async() => {
    if (props.id === null || props.id === undefined) {
        return ""
    }
    return announcement.value = await getAnnouncement(props.id)
})



</script>

<template>
    <div>
        <h1 class="text-2xl">
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
    <slot></slot>
</template>

<style scoped></style>