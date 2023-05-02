<script setup>
import { ref, onMounted } from 'vue'
const updatedAnnouncement = ref('')
const a = ref(1)
const props = defineProps({
    announcement: { type: Object }
})

onMounted(() => {
    // if (props.announcement === undefined) {
        //add
        updatedAnnouncement.value = {
            announcementTitle: "ทีสุดหล่อ",
            announcementDescription: "",
            publishDate: "",
            closeDate: "",
            announcementDisplay: "",
            categoryId: 1,
            categoryName: ""
        }
        const ddd = ref(updatedAnnouncement.value.categoryId.categoryName)
    // } else {
    //     //edit
    //     updatedAnnouncement.value = props.announcement
    // }
})

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
            <select class="bg-gray-400 w-2/5 rounded-sm" v-model="updatedAnnouncement.categoryId">
                <option value="1">ทั่วไป</option>
                <option value="2">ทุนการศึกษา</option>
                <option value="3">หางาน</option>
                <option value="4">ฝึกงาน</option>
            </select>
            {{ updatedAnnouncement.categoryId }}
        </div>
        <div class="my-3">
            <label>Description</label><br>
            <textarea class="bg-gray-400 w-full rounded-sm" name="desc" id="three" cols="100" rows="5" v-model="updatedAnnouncement.announcementDescription"></textarea>
        </div>
        <div class="my-3">
            <label>Publish Date</label><br>
            <input class="bg-gray-400 w-1/5 mr-5 rounded-sm" type="date" v-model="updatedAnnouncement.announcementTitle">
            <input class="bg-gray-400 w-1/5 rounded-sm" type="time" v-model="updatedAnnouncement.announcementTitle">
        </div>
        <div class="my-3">
            <label>Close Date</label><br>
            <input class="bg-gray-400 w-1/5 mr-5 rounded-sm" type="date">
            <input class="bg-gray-400 w-1/5 rounded-sm" type="time">
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