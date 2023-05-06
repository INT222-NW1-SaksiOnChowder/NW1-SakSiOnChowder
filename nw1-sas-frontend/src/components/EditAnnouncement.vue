<script setup>
import { ref, onMounted, computed } from "vue"
import { getAnnouncement } from '../composable/getInformation.js'
import { useRoute, useRouter } from 'vue-router';
import { updateAnnouncement } from '../composable/editAnnouncement'

const announcementObj = ref({})
const beforeAnnouncement = ref({})

onMounted(async () => {
    const route = useRoute()
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

const selectedPublishDate = ref()
const selectedPublishTime = ref()
const selectedCloseDate = ref()
const selectedCloseTime = ref()

const beforeAnnouncementPublishDate = ref()
const beforeAnnouncementPublishTime = ref()
const beforeAnnouncementCloseDate = ref()
const beforeAnnouncementCloseTime = ref()

const checkAnnouncement = computed(() => {
    if (announcementObj.value.announcementTitle === beforeAnnouncement.value.announcementTitle &&
        announcementObj.value.announcementDescription === beforeAnnouncement.value.announcementDescription &&
        announcementObj.value.publishDate === beforeAnnouncement.value.publishDate &&
        announcementObj.value.closeDate === beforeAnnouncement.value.closeDate &&
        announcementObj.value.announcementDisplay === beforeAnnouncement.value.announcementDisplay &&
        announcementObj.value.announcementCategory === beforeAnnouncement.value.announcementCategory) {
        if (selectedPublishDate.value === beforeAnnouncementPublishDate.value &&
            selectedPublishTime.value === beforeAnnouncementPublishTime.value &&
            selectedCloseDate.value === beforeAnnouncementCloseDate.value &&
            selectedCloseTime.value === beforeAnnouncementCloseTime.value) {
            return true
        } else {
            return false
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
        const publishDay = `${publishDateTime.getUTCDate() < 10 ? "0" : ""}${publishDateTime.getUTCDate()}`
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
        const closeDay = `${closeDateTime.getUTCDate() < 10 ? "0" : ""}${closeDateTime.getUTCDate()}`
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
        const publishDay = `${publishDateTime.getUTCDate() < 10 ? "0" : ""}${publishDateTime.getUTCDate()}`
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
        const closeDay = `${closeDateTime.getUTCDate() < 10 ? "0" : ""}${closeDateTime.getUTCDate()}`
        const closeMonth = `${closeDateTime.getMonth() + 1 < 10 ? "0" : ""}${closeDateTime.getMonth() + 1}`
        const closeYear = closeDateTime.getFullYear()
        selectedCloseDate.value = `${closeYear}-${closeMonth}-${closeDay}`

        // selectedCloseTime
        selectedCloseTime.value = `${closeDateTime.getHours() < 10 ? "0" : ""}${closeDateTime.getHours()}:${closeDateTime.getMinutes() < 10 ? "0" : ""}${closeDateTime.getMinutes()}`
    }
}



const submit = (addAnnouncement) => {

    addAnnouncement.announcementCategory = Number(addAnnouncement.announcementCategory)

    if (addAnnouncement.announcementDisplay === true) {
        addAnnouncement.announcementDisplay = 'Y'
    } else {
        addAnnouncement.announcementDisplay = 'N'
    }

    addAnnouncement.publishDate = new Date(`${selectedPublishDate.value} ${selectedPublishTime.value}`)
    addAnnouncement.closeDate = new Date(`${selectedCloseDate.value} ${selectedCloseTime.value}`)

    if (addAnnouncement.publishDate === undefined || addAnnouncement.closeDate === undefined) {
        addAnnouncement.publishDate = null
        addAnnouncement.closeDate = null
    }

    if (!addAnnouncement.announcementTitle || !addAnnouncement.announcementDescription || !addAnnouncement.announcementCategory) {
        alert('cannot edit data')
    } else {
        updateAnnouncement(addAnnouncement)
        router.push({ name: 'announcements' })
    }
}

</script>
 
<template>
    <div class="mx-5">
        <div class="mt-3">
            <h1 class="text-2xl font-bold ">
                Announcement Detail:
            </h1>
        </div>
        <div class="my-3">
            <label>Title</label><br>
            <input maxlength="200" class="bg-gray-400 w-full rounded-sm" type="text"
                v-model.trim="announcementObj.announcementTitle">
        </div>
        <div class="my-3">
            <label class="">Catagory</label><br>
            <select class="bg-gray-400 w-2/5 rounded-sm" v-model="announcementObj.announcementCategory">
                <option value="1">ทั่วไป</option>
                <option value="2">ทุนการศึกษา</option>
                <option value="3">หางาน</option>
                <option value="4">ฝึกงาน</option>
            </select>
            {{ announcementObj.announcementCategory }}
        </div>
        <div class="my-3">
            <label>Description</label><br>
            <textarea maxlength="10000" class="bg-gray-400 w-full rounded-sm" name="desc" id="three" cols="100" rows="5"
                v-model.trim="announcementObj.announcementDescription"></textarea>
        </div>
        <div class="my-3">
            <label>Publish Date</label><br>
            <input class="bg-gray-400 w-1/5 mr-5 rounded-sm" type="date" v-model="selectedPublishDate">
            <input class="bg-gray-400 w-1/5 rounded-sm" type="time" v-model="selectedPublishTime">
        </div>
        <div class="my-3">
            <label>Close Date</label><br>
            <input class="bg-gray-400 w-1/5 mr-5 rounded-sm" type="date" v-model="selectedCloseDate">
            <input class="bg-gray-400 w-1/5 rounded-sm" type="time" v-model="selectedCloseTime">
        </div>
        <div>
            <label>Display</label><br>
            <input type="checkbox" id="displayShow" v-model="announcementObj.announcementDisplay" />
            <label for="displayShow" class="ml-2">Check to show this announcement</label>
        </div>
        <div class="mt-5">
            <button :disabled="checkAnnouncement" class="mr-2 rounded-sm bg-gray-400 px-3 py-1"
                @click="submit(announcementObj)">Submit</button>
            <router-link :to="{ name: 'announcementDetail' }"><button
                    class="rounded-sm bg-gray-400 px-3 py-1">Cancel</button></router-link>
        </div>
    </div>
</template>
 
<style scoped></style>