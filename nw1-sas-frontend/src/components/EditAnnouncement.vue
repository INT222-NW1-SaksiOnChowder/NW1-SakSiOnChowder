<script setup>
import { ref, onMounted, computed } from "vue"
import { getAnnouncement } from '../composable/getInformation.js'
import { useRoute, useRouter } from 'vue-router'
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

const selectedPublishDate = ref('')
const selectedPublishTime = ref('')
const selectedCloseDate = ref('')
const selectedCloseTime = ref('')

const beforeAnnouncementPublishDate = ref('')
const beforeAnnouncementPublishTime = ref('')
const beforeAnnouncementCloseDate = ref('')
const beforeAnnouncementCloseTime = ref('')

const isDisabled = ref(true)

const setButton = () =>{
    isDisabled.value = false
}

// const checkAnnouncement = computed(() => {
//     if (announcementObj.value.announcementTitle === beforeAnnouncement.value.announcementTitle &&
//         announcementObj.value.announcementDescription === beforeAnnouncement.value.announcementDescription &&
//         announcementObj.value.publishDate === beforeAnnouncement.value.publishDate &&
//         announcementObj.value.closeDate === beforeAnnouncement.value.closeDate &&
//         announcementObj.value.announcementDisplay === beforeAnnouncement.value.announcementDisplay &&
//         announcementObj.value.announcementCategory === beforeAnnouncement.value.announcementCategory) {
//         if (selectedPublishDate.value === beforeAnnouncementPublishDate.value &&
//             selectedPublishTime.value === beforeAnnouncementPublishTime.value &&
//             selectedCloseDate.value === beforeAnnouncementCloseDate.value &&
//             selectedCloseTime.value === beforeAnnouncementCloseTime.value) {
//             return true
//         } else {
//             if (selectedPublishTime.value !== '' && selectedPublishDate.value === '' ||
//                 selectedCloseTime.value !== '' && selectedCloseDate.value === '') {
//                 return true
//             } else{
//                 return false
//             }
//         }
//     } else {
//         return false
//     }
// })

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
        const publishDay = `${publishDateTime.getUTCDate() + 1 < 10 ? "0" : ""}${publishDateTime.getUTCDate() + 1}`
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
        const closeDay = `${closeDateTime.getUTCDate() + 1 < 10 ? "0" : ""}${closeDateTime.getUTCDate() + 1}`
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
        const publishDay = `${publishDateTime.getUTCDate() + 1 < 10 ? "0" : ""}${publishDateTime.getUTCDate() + 1}`
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
        const closeDay = `${closeDateTime.getUTCDate() + 1 < 10 ? "0" : ""}${closeDateTime.getUTCDate() + 1}`
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

    // announcementCategory กับ categoryId
    if (!editAnnouncement.announcementTitle || !editAnnouncement.announcementDescription || !editAnnouncement.categoryId) {
        alert('cannot edit data')
    } else {
        console.log(selectedPublishDate.value);
        console.log(selectedCloseDate.value);
        await updateAnnouncement(editAnnouncement)
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
        <div class="my-5 flex">
            <label class="font-semibold">Title</label><br>
            <input maxlength="200" class="ann-title border border-black w-full rounded-sm ml-[7em]" type="text" @input="setButton"
                v-model.trim="announcementObj.announcementTitle">
        </div>
        <div class="my-5 flex">
            <label class="font-semibold">Catagory</label><br>
            <select class="ann-category border border-black w-2/5 rounded-sm ml-[4.9em]"
                v-model="announcementObj.announcementCategory" @input="setButton">
                <option value="1">ทั่วไป</option>
                <option value="2">ทุนการศึกษา</option>
                <option value="3">หางาน</option>
                <option value="4">ฝึกงาน</option>
            </select>
        </div>
        <div class="my-5 flex">
            <label class="font-semibold">Description</label><br>
            <textarea maxlength="10000" class="ann-description border border-black w-full rounded-sm ml-[3.8em]" name="desc"
                id="three" cols="100" rows="5" v-model.trim="announcementObj.announcementDescription" @input="setButton"></textarea>
        </div>
        <div class="my-5 flex">
            <label class="font-semibold">Publish Date</label><br>
            <input class="ann-publish-date border border-black w-1/5 mr-5 rounded-sm ml-[3.3em]" type="date"
                v-model="selectedPublishDate" @input="setButton">
            <input class="ann-publish-time border border-black w-1/5 rounded-sm" type="time" v-model="selectedPublishTime" @input="setButton">
        </div>
        <div class="my-5 flex">
            <label class="font-semibold">Close Date</label><br>
            <input class="ann-close-date border border-black w-1/5 mr-5 rounded-sm ml-[4.2em]" type="date"
                v-model="selectedCloseDate" @input="setButton">
            <input class="ann-close-time border border-black w-1/5 rounded-sm" type="time" v-model="selectedCloseTime" @input="setButton">
        </div>
        <div class="my-5 flex">
            <label class="font-semibold">Display</label><br>
            <input type="checkbox" id="displayShow" class="ann-display ml-[5.8em]"
                v-model="announcementObj.announcementDisplay" @input="setButton"/>
            <label for="displayShow" class="ml-2">Check to show this announcement</label>
        </div>
        <div class="my-5">
            <router-link :to="{ name: 'announcementDetail' }">
                <button
                    class="rounded-md bg-gray-300 px-5 py-2 font-semibold hover:bg-amber-100">Back</button></router-link>
            <button
                :disabled="isDisabled || !announcementObj.announcementTitle || !announcementObj.announcementDescription"
                class="ann-button ml-5 font-semibold rounded-md px-3 py-2 buttonEdit"
                :style="isDisabled || !announcementObj.announcementTitle || !announcementObj.announcementDescription ? 'opacity: 0.5; background-color:lightgray; cursor: not-allowed;' : 'opacity: 1; background-color:lightgreen;'"
                @click="submitEdit(announcementObj)">Submit</button>

        </div>
    </div>
</template>
 
<style scoped></style>