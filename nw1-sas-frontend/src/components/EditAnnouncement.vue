<script setup>
import { ref, onMounted } from "vue"
import { getAnnouncement } from '../composable/getInformation.js'
import { useRoute, useRouter} from 'vue-router';
import { updateAnnouncement } from '../composable/editAnnouncement'
const announcementObj = ref({})
onMounted(async () => {
    const route = useRoute()
    announcementObj.value = await getAnnouncement(route.params.id)
})

const displayShow = ref(false)
const router = useRouter()
const selectedPublishDate = ref()
const selectedPublishTime = ref()
const selectedCloseDate = ref()
const selectedCloseTime = ref()


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
        alert('cannot edit data')
    } else {
        updateAnnouncement(addAnnouncement)
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
            <input maxlength="200" class="bg-gray-400 w-full rounded-sm" type="text" v-model.trim="announcementObj.announcementTitle">
        </div>
        <div class="my-3">
            <label class="">Catagory</label><br>
            <select class="bg-gray-400 w-2/5 rounded-sm">
                <option value="1">ทั่วไป</option>
                <option value="2">ทุนการศึกษา</option>
                <option value="3">หางาน</option>
                <option value="4">ฝึกงาน</option>
            </select>
            {{ announcementObj.categoryId?.category }}
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
            <button class="mr-2 rounded-sm bg-gray-400 px-3 py-1" @click="submit(announcementObj)">Submit</button>
            <router-link :to="{ name: 'announcementDetail' }"><button
                    class="rounded-sm bg-gray-400 px-3 py-1">Cancel</button></router-link>
        </div>
    </div>
</template>
 
<style scoped></style>