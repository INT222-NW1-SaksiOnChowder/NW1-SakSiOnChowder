<script setup>
import { ref, onMounted, computed } from "vue"
import { getAnnouncement } from '../composable/getInformation.js'
import { useRoute, useRouter } from 'vue-router'
import { updateAnnouncement } from '../composable/editAnnouncement'

const announcement = ref({})
const announcementObj = ref({})
const beforeAnnouncement = ref({})

onMounted(async () => {
    const route = useRoute()
    announcement.value = await getAnnouncement(route.params.id)
    announcementObj.value = await getAnnouncement(route.params.id)
    beforeAnnouncement.value = await getAnnouncement(route.params.id)
    announcementObj.value.announcementDisplay = showAnnouncementDisplay(announcementObj.value)
    beforeAnnouncement.value.announcementDisplay = showAnnouncementDisplay(beforeAnnouncement.value)
    setTime(announcementObj.value)
    beforeAnnouncementSetTime(beforeAnnouncement.value)
    showAnnouncementCategory(announcementObj.value)
    showAnnouncementCategory(beforeAnnouncement.value)
})


const router = useRouter()

const selectedPublishDate = ref('')
const selectedPublishTime = ref('')
const selectedCloseDate = ref('')
const selectedCloseTime = ref('')

const beforeAnnouncementPublishDate = ref('')
const beforeAnnouncementPublishTime = ref('')
const beforeAnnouncementCloseDate = ref('')
const beforeAnnouncementCloseTime = ref('')


const checkAnnouncement = computed(() => {
    if (announcementObj.value.announcementTitle === beforeAnnouncement.value.announcementTitle &&
        announcementObj.value.announcementDescription === beforeAnnouncement.value.announcementDescription &&
        announcementObj.value.publishDate === beforeAnnouncement.value.publishDate &&
        announcementObj.value.closeDate === beforeAnnouncement.value.closeDate &&
        announcementObj.value.announcementDisplay === beforeAnnouncement.value.announcementDisplay &&
        announcementObj.value.announcementCategory == beforeAnnouncement.value.announcementCategory) {
        if (selectedPublishDate.value === beforeAnnouncementPublishDate.value &&
            selectedPublishTime.value === beforeAnnouncementPublishTime.value &&
            selectedCloseDate.value === beforeAnnouncementCloseDate.value &&
            selectedCloseTime.value === beforeAnnouncementCloseTime.value) {
            return true
        } else {
            if (selectedPublishTime.value !== '' && selectedPublishDate.value === '' ||
                selectedCloseTime.value !== '' && selectedCloseDate.value === '') {
                return true
            } else {
                return false
            }
        }
    } else {
        return false
    }
})


const showAnnouncementDisplay = (announcement) => {
    if (announcement.announcementDisplay === "Y") {
        return true
    } else {
        return false
    }
}

const showAnnouncementCategory = (announcement) => {
    switch (announcement.announcementCategory) {
        case 'ทั่วไป':
            announcement.announcementCategory = 1
            break;
        case 'ทุนการศึกษา':
            announcement.announcementCategory = 2
            break;
        case 'หางาน':
            announcement.announcementCategory = 3
            break;
        case 'ฝึกงาน':
            announcement.announcementCategory = 4
            break;
    }
}

const beforeAnnouncementSetTime = (announcement) => {
    if (announcement.publishDate !== null) {
        // PublishDate
        const publishDateTime = new Date(announcement.publishDate)

        // selectedPublishDate
        const publishDay = `${publishDateTime.getDate() < 10 ? "0" : ""}${publishDateTime.getDate()}`
        const publishMonth = `${publishDateTime.getMonth() + 1 < 10 ? "0" : ""}${publishDateTime.getMonth() + 1}`
        const publishYear = publishDateTime.getFullYear()
        beforeAnnouncementPublishDate.value = `${publishYear}-${publishMonth}-${publishDay}`

        // selectedPublishTime
        beforeAnnouncementPublishTime.value = `${publishDateTime.getHours() < 10 ? "0" : ""}${publishDateTime.getHours()}:${publishDateTime.getMinutes() < 10 ? "0" : ""}${publishDateTime.getMinutes()}`
    }

    if (announcement.closeDate !== null) {
        //CloseDate
        const closeDateTime = new Date(announcement.closeDate)

        // selectedCloseDate
        const closeDay = `${closeDateTime.getDate() < 10 ? "0" : ""}${closeDateTime.getDate()}`
        const closeMonth = `${closeDateTime.getMonth() + 1 < 10 ? "0" : ""}${closeDateTime.getMonth() + 1}`
        const closeYear = closeDateTime.getFullYear()
        beforeAnnouncementCloseDate.value = `${closeYear}-${closeMonth}-${closeDay}`

        // selectedCloseTime
        beforeAnnouncementCloseTime.value = `${closeDateTime.getHours() < 10 ? "0" : ""}${closeDateTime.getHours()}:${closeDateTime.getMinutes() < 10 ? "0" : ""}${closeDateTime.getMinutes()}`
    }
}

const setTime = (announcement) => {
    if (announcement.publishDate !== null) {
        // PublishDate
        const publishDateTime = new Date(announcement.publishDate)

        // selectedPublishDate
        const publishDay = `${publishDateTime.getDate() < 10 ? "0" : ""}${publishDateTime.getDate()}`
        const publishMonth = `${publishDateTime.getMonth() + 1 < 10 ? "0" : ""}${publishDateTime.getMonth() + 1}`
        const publishYear = publishDateTime.getFullYear()
        selectedPublishDate.value = `${publishYear}-${publishMonth}-${publishDay}`

        // selectedPublishTime
        selectedPublishTime.value = `${publishDateTime.getHours() < 10 ? "0" : ""}${publishDateTime.getHours()}:${publishDateTime.getMinutes() < 10 ? "0" : ""}${publishDateTime.getMinutes()}`
    }

    if (announcement.closeDate !== null) {
        //CloseDate
        const closeDateTime = new Date(announcement.closeDate)

        // selectedCloseDate
        const closeDay = `${closeDateTime.getDate()< 10 ? "0" : ""}${closeDateTime.getDate()}`
        const closeMonth = `${closeDateTime.getMonth() + 1 < 10 ? "0" : ""}${closeDateTime.getMonth() + 1}`
        const closeYear = closeDateTime.getFullYear()
        selectedCloseDate.value = `${closeYear}-${closeMonth}-${closeDay}`

        // selectedCloseTime
        selectedCloseTime.value = `${closeDateTime.getHours() < 10 ? "0" : ""}${closeDateTime.getHours()}:${closeDateTime.getMinutes() < 10 ? "0" : ""}${closeDateTime.getMinutes()}`
    }
}



const submitEdit = async (announcement) => {

    const editAnnouncement = {
        announcementTitle: announcement.announcementTitle,
        announcementDescription: announcement.announcementDescription,
        publishDate: announcement.publishDate,
        closeDate: announcement.closeDate,
        announcementDisplay: announcement.announcementDisplay,
        categoryId: announcement.announcementCategory,
        id: announcement.id
    }

    editAnnouncement.categoryId = Number(editAnnouncement.categoryId)

    if (editAnnouncement.announcementDisplay === true) {
        editAnnouncement.announcementDisplay = 'Y'
    } else {
        editAnnouncement.announcementDisplay = 'N'
    }

    editAnnouncement.publishDate = new Date(`${selectedPublishDate.value} ${selectedPublishTime.value}`)
    editAnnouncement.closeDate = new Date(`${selectedCloseDate.value} ${selectedCloseTime.value}`)

    if (editAnnouncement.publishDate === undefined || editAnnouncement.publishDate === '') {
        editAnnouncement.publishDate = null
    }
    if (editAnnouncement.closeDate === undefined || editAnnouncement.closeDate === '') {
        editAnnouncement.closeDate = null
    }

    await updateAnnouncement(editAnnouncement)
    router.push({ name: 'announcements' })


}

</script>
 
<template>
    <div class="w-full min-h-screen max-h-full bg-Background">
        <div class="mx-32">
            <div class="rounded-full shadow-md bg-DarkBlue inline-block mt-3 mb-8">
                <h1 class="text-BlueFonts text-2xl px-5 py-5 font-bold">
                    Announcement Detail:
                </h1>
            </div>
            <div class="bg-LightBlue rounded-2xl py-9 px-28">
                <div class="my-5">
                    <label class="font-bold">Title</label><br>
                    <input maxlength="200" class="ann-title bg-InputColor drop-shadow-md h-8 w-full rounded-lg" type="text"
                         v-model.trim="announcementObj.announcementTitle">
                </div>
                <div class="my-5">
                    <label class="font-bold">Catagory</label><br>
                    <select class="ann-category drop-shadow-md bg-InputColor h-8  w-2/5 rounded-lg"
                        v-model="announcementObj.announcementCategory" >
                        <option value="1">ทั่วไป</option>
                        <option value="2">ทุนการศึกษา</option>
                        <option value="3">หางาน</option>
                        <option value="4">ฝึกงาน</option>
                    </select>
                </div>
                <div class="my-5">
                    <label class="font-bold">Description</label><br>
                        <QuillEditor  maxlength="10000" cols="100" rows="5" 
                         class="ann-description drop-shadow-md bg-InputColor w-full rounded-lg" 
                         theme="snow" toolbar="full" v-model:content="announcementObj.announcementDescription" contentType="html"/>
                </div>
                <div class="my-5">
                    <label class="font-bold">Publish Date</label><br>
                    <input class="ann-publish-date drop-shadow-md bg-InputColor w-1/5 mr-5 rounded-lg px-5 py-1" type="date"
                        v-model="selectedPublishDate" >
                    <input class="ann-publish-time w-1/5 bg-InputColor drop-shadow-md rounded-lg px-5 py-1" type="time"
                        v-model="selectedPublishTime" >
                </div>
                <div class="my-5">
                    <label class="font-bold">Close Date</label><br>
                    <input class="ann-close-date bg-InputColor drop-shadow-md sm:w-1/5 mr-5 rounded-lg px-5 py-1" type="date"
                        v-model="selectedCloseDate">
                    <input class="ann-close-time w-1/5 bg-InputColor drop-shadow-md rounded-lg px-5 py-1" type="time"
                        v-model="selectedCloseTime">
                </div>
                <div class="my-5">
                    <label class="font-bold">Display</label><br>
                    <input type="checkbox" id="displayShow" class="ann-display"
                        v-model="announcementObj.announcementDisplay"/>
                    <label for="displayShow" class="ml-2">Check to show this announcement</label>
                </div>
                <div class="my-5 text-center">
                    <router-link :to="{ name: 'announcementDetail' }">
                        <button
                            class="ann-button shadow-md rounded-full bg-DarkRed px-6 py-2 font-bold hover:bg-ButtonDeleteHover">Back</button></router-link>
                    <button :disabled="checkAnnouncement"
                        class="ann-button ml-5 shadow-md font-bold rounded-full px-5 py-2 buttonEdit bg-DarkGreen hover:bg-ButtonViewHover"
                        :style="checkAnnouncement ? 'opacity: 0.5; background-color:lightgray; cursor: not-allowed;' : 'opacity: 1;'"
                        @click="submitEdit(announcementObj)">Submit</button>

                </div>
            </div>
        </div>
    </div>
</template>
 
<style scoped></style>