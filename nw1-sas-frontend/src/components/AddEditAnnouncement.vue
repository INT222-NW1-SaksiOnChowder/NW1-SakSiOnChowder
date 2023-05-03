<script setup>
import { ref, onMounted, computed } from 'vue'

const updatedAnnouncement = ref({
    announcementTitle: "",
    announcementDescription: "",
    publishDate: "",
    closeDate: "",
    announcementDisplay: "",
    categoryId: {
        categoryId: 1,
        categoryName: ""
    }
})
const props = defineProps({
    announcement: { type: Object }
})

const selectedDate = ref()
const selectedTime = ref()



const timer = () => {
    // const localDateTime = new Date(`${selectedDate.value} ${selectedTime.value}`)
    const utcDatetime = new Date(`${selectedDate.value} ${selectedTime.value}`)
    console.log(utcDatetime)
   
    const year = utcDatetime.getUTCFullYear();
    const month = String(utcDatetime.getUTCMonth() + 1).padStart(2, '0');
    const day = String(utcDatetime.getUTCDate()).padStart(2, '0');
    const hours = String(utcDatetime.getUTCHours()).padStart(2, '0');
    const minutes = String(utcDatetime.getUTCMinutes()).padStart(2, '0');
    const seconds = String(utcDatetime.getUTCSeconds()).padStart(2, '0');
    const isoString = `${year}-${month}-${day}T${hours}:${minutes}:${seconds}Z`
    console.log(isoString)


};

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
            <input class="bg-gray-400 w-full rounded-sm" type="text" v-model="updatedAnnouncement.announcementTitle">
        </div>
        <div class="my-3">
            <label class="">Catagory</label><br>
            <select class="bg-gray-400 w-2/5 rounded-sm" v-model="updatedAnnouncement.categoryId.categoryId">
                <option value="1">ทั่วไป</option>
                <option value="2">ทุนการศึกษา</option>
                <option value="3">หางาน</option>
                <option value="4">ฝึกงาน</option>
            </select>
            {{ updatedAnnouncement.categoryId.categoryId }}
        </div>
        <div class="my-3">
            <label>Description</label><br>
            <textarea class="bg-gray-400 w-full rounded-sm" name="desc" id="three" cols="100" rows="5"
                v-model="updatedAnnouncement.announcementDescription"></textarea>
        </div>
        <div class="my-3">
            <label>Publish Date</label><br>
            <input class="bg-gray-400 w-1/5 mr-5 rounded-sm" type="date" v-model="selectedDate">
            <input class="bg-gray-400 w-1/5 rounded-sm" type="time" v-model="selectedTime">
            {{ timer() }}
        </div>
        <div class="my-3">
            <label>Close Date</label><br>
            <input class="bg-gray-400 w-1/5 mr-5 rounded-sm" type="date" v-model="updatedAnnouncement.closeDate">
            <input class="bg-gray-400 w-1/5 rounded-sm" type="time" v-model="updatedAnnouncement.closeDate">
        </div>
        <div>
            <label>Display</label><br>
            <input type="checkbox" />
            <span class="ml-2">Check to show this announcement</span>
        </div>
        <div class="mt-5">
            <button class="mr-2 rounded-sm bg-gray-400 px-3 py-1">Submit</button>
            <button class="rounded-sm bg-gray-400 px-3 py-1">Cancel</button>
        </div>
    </div>
</template>
 
<style scoped></style>