<script setup>
import { getAnnouncements } from "../composable/getInformation.js"
import { ref, onMounted, onUpdated } from "vue"
import { changeDateTimeFormat } from "../composable/changeFormatDate.js"
import { deleteAcc } from "../composable/deleteAnnouncement.js"
import { useRouter } from 'vue-router';
import TimeZone from '../components/icones/TimeZone.vue'
import AddIcon from "./icones/AddIcon.vue";

const router = useRouter()
const announcements = ref([])
const timezone = Intl.DateTimeFormat().resolvedOptions().timeZone

onMounted(async () => {
  announcements.value = await getAnnouncements()
  announcements.value.sort((a, b) => b.id - a.id)
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

const deleteAnnouncement = async (id) => {
  router.push({ name: 'deleteAnnouncement', params: { id: id } })
  const confirmed = confirm(`Do you want to delete`)
  if (confirmed) {
    await deleteAcc(id)
  }
  announcements.value = await getAnnouncements()
  announcements.value.sort((a, b) => b.id - a.id)
  router.push({ name: 'announcements' })
}
</script>

<template>
    <div class="w-full h-full bg-Background">
      <div class="bg-LightBlue text-BlueFonts drop-shadow-lg">
        <h1 class="h-24 flex justify-center items-center drop-shadow-lg text-4xl font-bold">
          SIT Announcement System (SAS)
        </h1>
      </div>
      <div class="flex my-8 w-full justify-between">
        <p class="mx-5 items-center flex">
          <TimeZone></TimeZone>&nbsp;
          Date/Time shown in Timezone : &nbsp;
          <span class="font-bold text-BlueFonts drop-shadow-sm">{{ timezone }}</span>
        </p>
        <div class="bg-DarkBlue shadow-md mr-5 hover:bg-red-200 font-semibold text-BlueFonts hover:bg-LightBlue rounded-full items-center justify-center">
          <router-link :to="{
            name: 'addAnnouncement'
          }">
            <button class="ann-button px-5 py-2 text-lg font-bold">
              <AddIcon class="inline mr-2 mb-1"></AddIcon>Add Announcement</button>
          </router-link>
        </div>
      </div>
      <div class="mx-5 mt-2 relative overflow-x-auto shadow-md sm:rounded-lg">
        <table class="w-full text-sm text-left">
          <thead class="text-base bg-DarkBlue uppercase text-BlueFonts">
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

          <tbody v-if="!isAnnouncementFound" class="bg-Cream">
            <tr v-for="(announcement, index) in announcements" :key="index"
              class="ann-item bg-white dark:bg-gray-900">
              <th scope="row" class="px-6 py-4 font-medium whitespace-nowrap dark:text-white">
                {{ ++index }}
              </th>
              <td class="ann-title px-6 py-4 w-1/3 break-all">
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
                  params: { id: announcement.id }
                }"><button
                    class="ann-button font-bold text-blue-600 shadow-md  hover:bg-ButtonViewHover rounded-full bg-DarkGreen px-5 py-2">
                    view
                  </button>
                </router-link>
                <button
                  class="ann-button font-bold text-blue-600 shadow-md  hover:bg-ButtonDeleteHover rounded-full bg-DarkRed px-5 py-2 ml-2 "
                  @click="deleteAnnouncement(announcement.id)">delete</button>
              </td>
            </tr>
          </tbody>
        </table>
        <div v-if="isAnnouncementFound" class="text-center text-3xl my-10 text-BlueFonts">No Announcement</div>
      </div>
    </div>
</template>

<style scoped></style>
