<script setup>
import { getAnnouncementsUser,getClosedAnnouncementsUser } from "../composable/getAnnouncementUser.js"
import { ref, onMounted, onUpdated } from "vue"
import { changeDateTimeFormat } from "../composable/changeFormatDate.js"
import { deleteAcc } from "../composable/deleteAnnouncement.js"
import { useRouter, useRoute } from 'vue-router';

const announcements = ref([])
const timezone = Intl.DateTimeFormat().resolvedOptions().timeZone

onMounted(async () => {
  noAnnouncement()
  announcements.value = await getAnnouncementsUser()
  announcements.value.sort((a, b) => b.id - a.id)
})

onUpdated(() => {
  noAnnouncement()
})

const isActiveOrClosed = ref(false)
const wordButton = ref("Closed Announcements")
const toggleAnnouncement = async() =>{
  isActiveOrClosed.value = !isActiveOrClosed.value
  if (isActiveOrClosed.value === true) {
    wordButton.value = "Active Announcements"
    announcements.value = await getClosedAnnouncementsUser()
    noAnnouncement()
  }
  if (isActiveOrClosed.value === false) {
    wordButton.value = "Closed Announcements"
    announcements.value = await getAnnouncementsUser()
    noAnnouncement()
  }
}

const isAnnouncementFound = ref(false)
const noAnnouncement = () => {
  if (announcements.value.length <= 0) {
    isAnnouncementFound.value = true
    console.log(isAnnouncementFound.value);
  } else {
    isAnnouncementFound.value = false
    console.log(isAnnouncementFound.value);
  }
}


</script>

<template>
  <div>
    <div class="w-full h-full my-5">
      <h1 class="flex justify-center items-center text-3xl font-bold">
        SIT Announcement System (SAS)
      </h1>
      <div class="flex mt-5 w-full justify-between">
        <p class="mx-5 items-center font-semibold flex">
          Date/Time shown in Timezone : &nbsp;
          <span class="font-normal">{{ timezone }}</span>
        </p>
        <div class="mr-5 border hover:bg-red-200 font-semibold bg-gray-200 rounded-md items-center justify-center">
            <button @click="toggleAnnouncement" class="ann-button px-5 py-2 text-sm ">{{wordButton}}</button>
        </div>
      </div>
      <div class="mx-5 mt-2 relative overflow-x-auto shadow-md sm:rounded-lg">
        <table class="w-full text-sm dark:text-gray-400">
          <thead class="text-xs uppercase bg-gray-50 dark:bg-gray-700 dark:text-gray-400">
            <tr>
              <th scope="col" class="px-6 py-3">No.</th>
              <th scope="col" class="px-6 py-3 text-left">Title</th>
              <th v-show="isActiveOrClosed" scope="col" class="px-6 py-3 text-left">Close Time</th>
              <th scope="col" class="px-6 py-3 text-left">Category</th>
            </tr>
          </thead>

          <tbody v-if="!isAnnouncementFound">
            <tr v-for="(announcement, index) in announcements" :key="index"
              class="ann-item bg-white border-b dark:bg-gray-900 dark:border-gray-700">
              <th scope="row" class="px-6 py-4 font-medium text-gray-900 whitespace-nowrap dark:text-white">
                {{ ++index }}
              </th>
              <router-link :to="{name:'userViewDetail', params:{id : announcement.id}}">                
                  <td class="ann-title px-6 py-4">
                     {{ announcement.announcementTitle }}
                  </td>                
              </router-link>
              <td v-show="isActiveOrClosed" class="ann-category px-6 py-4 text-left">
                {{ changeDateTimeFormat(announcement.closeDate) }}
              </td>
              <td class="ann-category px-6 py-4 text-left">
                {{ announcement.announcementCategory }}
              </td>
            </tr>
          </tbody>
        </table>
        <div v-if="isAnnouncementFound" class="text-center text-3xl my-10">No Announcement</div>
      </div>
    </div>
  </div>
</template>

<style scoped></style>