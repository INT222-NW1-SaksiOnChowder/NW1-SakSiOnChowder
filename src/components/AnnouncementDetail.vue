<script setup>

import { getAnnouncement } from '../composable/getInformation.js'
import { ref, onMounted } from "vue"

const announcement = ref([])

onMounted(async () => {
  announcement.value = await getAnnouncement(2)
})

</script>

<template>
    <div>
        <h1>
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
            <p>--</p> 
            <!-- announcement.announcementCategory.categoryName ยังใช้ไม่ได้ -->
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
            <p>{{ announcement.publishDate }}</p>
        </div>
        <div class="flex my-5">
            <h1 class="mx-5">
                Close Date
            </h1>
            <p>{{ announcement.closeDate }}</p>
        </div>
        <div class="flex my-5">
            <h1 class="mx-5">
                Display
            </h1>
            <p>{{ announcement.announcementDisplay }}</p>
        </div>
    </div>
</template>

<style scoped></style>