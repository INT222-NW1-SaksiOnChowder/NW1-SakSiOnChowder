<script setup>
import { getAnnouncements } from "../composable/getInformation.js"
import { ref, onMounted, onUpdated, watch } from "vue"
import { changeDateTimeFormat } from "../composable/changeFormatDate.js"
const announcements = ref([])
const timezone = Intl.DateTimeFormat().resolvedOptions().timeZone

onMounted(async () => {
  announcements.value = await getAnnouncements()
})

onUpdated(() => {
  noAnnouncement()
})

const isAnnouncementFound = ref(false)
const noAnnouncement = () => {
  if (announcements.value.length <= 0) {
    isAnnouncementFound.value = true
  } else {
    isAnnouncementFound.value = false
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
          Date/Time shown in Timezone :
          <span class="font-normal">{{ timezone }}</span>
        </p>
        <div class="mr-5 border hover:bg-red-200 font-semibold bg-gray-200 rounded-md items-center justify-center">
          <router-link :to="{ 
                 name: 'addAnnouncements'
              }">
              <button class="px-5 py-2 text-sm ">Add Announcement</button>
            </router-link>
        </div>
      </div>
      <div class="mx-5 mt-2 relative overflow-x-auto shadow-md sm:rounded-lg">
        <table class="w-full text-sm text-left dark:text-gray-400">
          <thead class="text-xs uppercase bg-gray-50 dark:bg-gray-700 dark:text-gray-400">
            <tr>
              <th scope="col" class="px-6 py-3">No.</th>
              <th scope="col" class="px-6 py-3">Title</th>
              <th scope="col" class="px-6 py-3">Category</th>
              <th scope="col" class="px-6 py-3">Publish Date</th>
              <th scope="col" class="px-6 py-3">Close Date</th>
              <th scope="col" class="px-6 py-3 text-center">Display</th>
              <th scope="col" class="px-6 py-3 text-center">Action</th>
            </tr>
          </thead>

          <tbody v-if="!isAnnouncementFound">
            <tr v-for="(announcement, index) in announcements" :key="index"
              class="ann-item bg-white border-b dark:bg-gray-900 dark:border-gray-700">
              <th scope="row" class="px-6 py-4 font-medium text-gray-900 whitespace-nowrap dark:text-white">
                {{ ++index }}
              </th>
              <td class="ann-title px-6 py-4">
                {{ announcement.announcementTitle }}
              </td>
              <td class="ann-category px-6 py-4">
                {{ announcement.announcementCategory }}
              </td>
              <td class="ann-publish-date px-6 py-4">
                {{ changeDateTimeFormat(announcement.publishDate) }}
              </td>
              <td class="ann-close-date px-6 py-4">
                {{ changeDateTimeFormat(announcement.closeDate) }}
              </td>
              <td class="ann-display px-6 py-4 text-center justify-items-center">
                {{ announcement.announcementDisplay }}
              </td>
              <td class="px-6 py-4 text-center">
                <router-link :to="{
                  name: 'announcementDetail',
                  params: { id: announcement.id },
                }"><button
                    class="font-medium text-blue-600 dark:text-blue-500 hover:underline rounded-md bg-gray-200 px-5 py-2">
                    view
                  </button></router-link>
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
