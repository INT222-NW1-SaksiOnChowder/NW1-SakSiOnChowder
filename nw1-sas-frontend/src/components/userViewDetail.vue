<script setup>
import { getAnnouncementUser } from '../composable/getAnnouncementUser.js'
import { ref, onMounted} from "vue"
import {changeDateTimeFormat} from "../composable/changeFormatDate.js"
import { useRoute , useRouter } from 'vue-router';

const announcement = ref({})
const router = useRouter()
const route = useRoute();
console.log(route.params.id);

onMounted(async() => {
    const route = useRoute()
    announcement.value = await getAnnouncementUser(route.params.id)
    console.log(route.params.id);
    if (!announcement.value) {
        alert('The request page is not available')
        router.push({name: "userViewAnnouncement"})
        announcement.value = ""
    }
})
</script>

<template>
    <div>
        <div class="mt-3">
            <h1 class="text-2xl ml-5 font-bold">
                Announcement Detail:
            </h1>
        </div>
        <div class="border m-5">
            <div class="flex my-5">
                <h1 class="mx-5">
                    Title
                </h1>
                <p class="ann-title">{{ announcement.announcementTitle }}</p>
            </div>
            <div class="flex my-5">
                <h1 class="mx-5">
                    Category
                </h1>
                <p class="ann-category">{{ announcement.announcementCategory }}</p>
            </div>
            <div class="flex my-5">
                <h1 class="mx-5">
                    Description
                </h1>
                <p class="ann-description">{{ announcement.announcementDescription }}</p>
            </div>
        </div>
        <router-link :to="{ name: 'userViewAnnouncement' }"><button
                class="ann-button bg-gray-200 rounded-md py-2 px-5 ml-5 hover:bg-amber-100"
                @click="idDetail">Back</button>
        </router-link>


    </div>
</template>

<style scoped></style>