<script setup>
import { ref, onMounted } from 'vue'

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

const time = ref()
const time2 = ref()

const timer = () => {
    const x = time2 + ':00'
    const y = time + ' '+ x
    console.log(y);
    const ict = 7*60*60*1000
    const date = new Date(y)
    date.setTime(date.getTime()+ict)
    console.log(date);
    date.toISOString()
    console.log(date);

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
            <input class="bg-gray-400 w-1/5 mr-5 rounded-sm" type="date" v-model="time">
            <input class="bg-gray-400 w-1/5 rounded-sm" type="time" v-model="time2">
            {{ timer()}}
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