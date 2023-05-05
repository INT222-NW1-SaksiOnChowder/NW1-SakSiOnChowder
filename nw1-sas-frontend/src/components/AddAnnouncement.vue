<script setup>
import { ref, onMounted, computed } from 'vue'
import { createAnnouncement } from '../composable/addAnnouncement.js'
import { useRoute, useRouter } from 'vue-router';
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
    categoryId: {
        categoryId: 1,
        categoryName: ""
    }
}





const setCategoryName = (addAnnouncement) => {
    switch (Number(addAnnouncement.categoryId.categoryId)) {
        case 1:
            addAnnouncement.categoryId.categoryName = 'ทั่วไป'
            break;
        case 2:
            addAnnouncement.categoryId.categoryName = 'ทุนการศึกษา'
            break;
        case 3:
            addAnnouncement.categoryId.categoryName = 'หางาน'
            break;
        case 4:
            addAnnouncement.categoryId.categoryName = 'ฝึกงาน'
            break;
    }
}

const submit = (addAnnouncement) => {

    setCategoryName(addAnnouncement)

    addAnnouncement.categoryId.categoryId = Number(addAnnouncement.categoryId.categoryId)
    console.log(addAnnouncement.categoryId.categoryId)

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

    if (!addAnnouncement.announcementTitle || !addAnnouncement.announcementDescription || !addAnnouncement.categoryId.categoryId) {
        alert('cannot create data')
    } else {
        createAnnouncement(addAnnouncement)
        // console.log(addAnnouncement);
        // console.log(addAnnouncement.publishDate);
        // console.log(addAnnouncement.closeDate);
        router.push({ name: 'announcements' })
    }
    // router.push({ name: 'announcements' })
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
            <input class="bg-gray-400 w-full rounded-sm" type="text" v-model.trim="announcementObj.announcementTitle">
        </div>
        <div class="my-3">
            <label class="">Catagory</label><br>
            <select class="bg-gray-400 w-2/5 rounded-sm" v-model="announcementObj.categoryId.category">
                <option value="1">ทั่วไป</option>
                <option value="2">ทุนการศึกษา</option>
                <option value="3">หางาน</option>
                <option value="4">ฝึกงาน</option>
            </select>
            {{ announcementObj.categoryId.category }}
        </div>
        <div class="my-3">
            <label>Description</label><br>
            <textarea class="bg-gray-400 w-full rounded-sm" name="desc" id="three" cols="100" rows="5"
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
            <button class="mr-2 rounded-sm bg-gray-400 px-3 py-1" @click="submit(announcementObj)">Submit</button>
            <button class="rounded-sm bg-gray-400 px-3 py-1"><a href="/">Cancel</a></button>
        </div>
    </div>
</template>
 
<style scoped></style>