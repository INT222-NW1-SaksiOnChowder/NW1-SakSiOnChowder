<script setup>
import { ref } from 'vue'
import { createAnnouncement } from '../composable/addAnnouncement.js'
import { useRouter } from 'vue-router';
const router = useRouter()

const announcementObj = ref()

const displayShow = ref(false)

const selectedPublishDate = ref()
const selectedPublishTime = ref()
const selectedCloseDate = ref()
const selectedCloseTime = ref()

//เพิ่มเงื่อนไข ตรงนี้
announcementObj.value = {
    announcementTitle: "",
    announcementDescription: "",
    publishDate: "",
    closeDate: "",
    announcementDisplay: displayShow.value,
    categoryId: 1
}


const submit = async(addAnnouncement) => {
    addAnnouncement.categoryId = Number(addAnnouncement.categoryId)

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

   
        await createAnnouncement(addAnnouncement)
        router.push({ name: 'announcements' })
    

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
            <label class="font-semibold">Title</label><br>
            <input class="ann-title border border-black w-full rounded-sm" type="text" v-model.trim="announcementObj.announcementTitle" maxlength="200">
        </div>
        <div class="my-3">
            <label class="font-semibold">Catagory</label><br>
            <select class="ann-category border border-black w-2/5 rounded-sm" v-model="announcementObj.categoryId">
                <option value="1">ทั่วไป</option>
                <option value="2">ทุนการศึกษา</option>
                <option value="3">หางาน</option>
                <option value="4">ฝึกงาน</option>
            </select>
        </div>
        <div class="my-3">
            <label class="font-semibold">Description</label><br>
            <textarea class="ann-description border border-black w-full rounded-sm" name="desc" id="three" cols="100" rows="5" maxlength="10000"
                v-model.trim="announcementObj.announcementDescription"></textarea>
        </div>
        <div class="my-3">
            <label class="font-semibold">Publish Date</label><br>
            <input class="ann-publish-date border border-black w-1/5 mr-5 rounded-sm px-5 py-1" type="date" v-model="selectedPublishDate">
            <input class="ann-publish-time border border-black w-1/5 rounded-sm px-5 py-1" type="time" v-model="selectedPublishTime">
        </div>
        <div class="my-3">
            <label class="font-semibold">Close Date</label><br>
            <input class="border border-black w-1/5 mr-5 rounded-sm px-5 py-1" type="date" v-model="selectedCloseDate">
            <input class="border border-black w-1/5 rounded-sm px-5 py-1" type="time" v-model="selectedCloseTime">
        </div>
        <div>
            <label class="font-semibold">Display</label><br>
            <input type="checkbox" id="displayShow" v-model="announcementObj.announcementDisplay" />
            <label for="displayShow" class="ml-2">Check to show this announcement</label>
        </div>
        <div class="mt-5">
            <button class="ann-button mr-2 rounded-sm bg-gray-400 px-3 py-1 font-semibold" @click="submit(announcementObj)">Submit</button>
            <router-link :to="{ name: 'announcements' }">
                <button class="rounded-sm bg-gray-400 px-3 py-1 font-semibold">Cancel</button>
            </router-link>
        </div>
    </div>
</template>
 
<style scoped></style>