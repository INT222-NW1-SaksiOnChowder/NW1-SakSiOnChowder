<script setup>
import { getAnnouncementsUser } from "../composable/getAnnouncementUser.js"
import { ref, onMounted, onUpdated, computed } from "vue"
import { changeDateTimeFormat } from "../composable/changeFormatDate.js"
import { annStores } from '../stores/counter.js'
import TimeZone from '../components/icones/TimeZone.vue'
const announcements = ref([])
const timezone = Intl.DateTimeFormat().resolvedOptions().timeZone
const announcementStores = annStores()

const showCloseTime = ref(false)
const setShowCloseTime = () => {
  if (announcementStores.mode === 'close') {
    showCloseTime.value = true
  } else {
    showCloseTime.value = false
  }
}

// content
const annoucementContent = ref()
onMounted(async () => {
  noAnnouncement()
  setShowCloseTime()
  announcements.value = await getAnnouncementsUser(announcementStores.mode, announcementStores.page, undefined)
  annoucementContent.value = announcements.value.content
  noAnnouncement()
  checkPageButton()
})

onUpdated(() => {
  noAnnouncement()
  setShowCloseTime()
  if (announcementStores.mode === 'close') {
    wordButton.value = 'Active Announcements'
  }else{
    wordButton.value = "Closed Announcements"
  }
})

const wordButton = ref("Closed Announcements")

const getListAnnouncement = async() => {
  announcementStores.setPage(0)
  if (announcementStores.mode === 'active') {
    announcementStores.setmode('close')
    announcements.value = await getAnnouncementsUser(announcementStores.mode, announcementStores.page, announcementStores.category)
    annoucementContent.value = announcements.value.content
    wordButton.value = "Active Announcements"
  }
  else {
    announcementStores.setmode('active')
    announcements.value = await getAnnouncementsUser(announcementStores.mode, announcementStores.page, announcementStores.category)
    annoucementContent.value = announcements.value.content
    wordButton.value = "Closed Announcements"
  }
  checkPageButton()
  console.log(announcementStores.mode);
}

const isAnnouncementFound = ref(false)
const noAnnouncement = () => {
  if (announcements.value.totalElements <= 0) {
    isAnnouncementFound.value = true
    console.log(isAnnouncementFound.value);
  } else {
    isAnnouncementFound.value = false
    console.log(isAnnouncementFound.value);
  }
}
const slicePageNumberArr = ref([])
const pageNumberArr = ref([])
const isMoreThanFiveElements = ref(true)
const checkPageButton = () => {
  pageNumberArr.value = []
  for (let i = 1; i <= announcements.value.totalPages; i++) {
      pageNumberArr.value.push(i)
  }
  isMoreThanFiveElements.value = true
  slicePageNumberArr.value = pageNumberArr.value
  if (announcements.value.totalElements <= 5) {
    isMoreThanFiveElements.value = false
  }
  if (announcements.value.totalPages > 10) {
    slicePageNumberArr.value = pageNumberArr.value.slice(0,10)
  }
}

console.log(announcementStores.page);

const changeToCurrentPage = async(page) => {
  if (announcements.value.totalPages !== page-1) {
    announcements.value = await getAnnouncementsUser(announcementStores.mode, page-1, announcementStores.category)
    annoucementContent.value = announcements.value.content
    announcementStores.setPage(page-1)
  }
  if (announcements.value.page+1 > 10) {
    slicePageNumberArr.value = pageNumberArr.value.slice(announcements.value.page-9,announcements.value.page+1, announcementStores.category)
  }
  if (announcements.value.page+1 <= 10) {
    slicePageNumberArr.value = pageNumberArr.value.slice(0,10)
  }
}

const disableNextButton = computed(() => {
    if (announcements.value.last) {
      return true
    }
})
const disablePrevButton = computed(() => {
  if (announcements.value.first) {
      return true
    }
})
const nextOrPrevButton = (move) =>{
    if (move === 'next') {
      changeToCurrentPage(announcementStores.page+2)
    } else {
      changeToCurrentPage(announcementStores.page)
    }
}

const selectedCategory = ref("")
const changeCategory = async (event) =>{
  if(event.target.value !== announcementStores.category){
    announcementStores.setCategory(event.target.value)
    announcements.value = await getAnnouncementsUser(announcementStores.mode, announcementStores.page, event.target.value)
    annoucementContent.value = announcements.value.content
  }
  checkPageButton()
  noAnnouncement()
}



</script>

<template>
  <div class="w-screen h-screen bg-Background">
    <div class="">
      <div class="bg-LightBlue text-BlueFonts drop-shadow-lg">
      <h1 class="h-24 flex justify-center items-center drop-shadow-lg text-4xl font-bold">
        SIT Announcement System (SAS)
      </h1>
      </div>
      <div class="flex mt-5 w-full justify-between">
        <p class="mx-5 items-center flex">
          <TimeZone></TimeZone>&nbsp;
          Date/Time shown in Timezone : &nbsp;
          <span class="font-bold text-BlueFonts drop-shadow-sm">{{ timezone }}</span>
        </p>
        <div class="mr-5 bg-DarkBlue hover:bg-red-200 font-bold hover:bg-LightBlue text-BlueFonts rounded-full items-center justify-center">
            <button @click="getListAnnouncement" class="ann-button px-5 py-2 text-lg ">{{ wordButton }}</button>
        </div>
      </div>
      <div class="ml-5 my-5 flex items-center">
        <p class="flex font-semibold">Choose Category : </p>&nbsp;
          <select class="ann-category-filter bg-Cream rounded-md p-1" @change="changeCategory($event)" v-model="selectedCategory">
            <option value="">ทั้งหมด</option>
            <option value="1">ทั่วไป</option>
            <option value="2">ทุนการศึกษา</option>
            <option value="3">หางาน</option>
            <option value="4">ฝึกงาน</option>
          </select>
        </div>
      <div class="mx-5 mt-2 relative overflow-x-auto shadow-md sm:rounded-lg">
        <table class="w-full text-sm dark:text-gray-400">
          <thead class="bg-DarkBlue text-base text-BlueFonts  uppercase bg-gray-50 dark:bg-gray-700 dark:text-gray-400">
            <tr>
              <th scope="col" class="px-6 py-3 ${}">No.</th>
              <th scope="col" class="px-6 py-3 text-left">Title</th>
              <th v-show="showCloseTime" scope="col" class="px-6 py-3 text-left">Close Time</th>
              <th scope="col" class="px-6 py-3 text-left">Category</th>
            </tr>
          </thead>

          <tbody class="bg-Cream" v-if="!isAnnouncementFound">
            <tr v-for="(announcement, index) in annoucementContent" :key="index"
              class="ann-item bg-white dark:bg-gray-900">
              <th scope="row" class="px-6 py-4 font-medium text-gray-900 whitespace-nowrap dark:text-white">
                {{ ++index + (announcements.size * announcements.page) }}
              </th>
              <router-link :to="{name:'userViewDetail', params:{id : announcement.id}}">                
                  <td class="ann-title px-6 py-4 hover:font-bold hover:text-BlueFonts">
                     {{ announcement.announcementTitle }}
                  </td>                
              </router-link>
              <td v-show="showCloseTime" class="ann-close-date px-6 py-4 text-left">
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
    <div v-if="isMoreThanFiveElements" class="flex justify-center mt-8">
        <button :disabled="disablePrevButton " @click="nextOrPrevButton('prev')" class="ann-page-prev font-bold px-5 py-2 mx-1 rounded-lg bg-DarkBlue">Prev</button>
        <button @click="changeToCurrentPage(pageNumber)" v-for="(pageNumber,index) in slicePageNumberArr" :key="index" 
        :class="[
          pageNumber === announcementStores.page + 1
            ? 'bg-DarkGreen px-5 py-2 mx-1 rounded-lg'
            : 'bg-Cream px-5 py-2 mx-1 rounded-lg hover:bg-ButtonViewHover',
          'ann-page-' + index
        ]"
        >
          {{ pageNumber }}
        </button>
        <button :disabled="disableNextButton" @click="nextOrPrevButton('next')" class="ann-page-next  px-5 py-2 mx-1 rounded-lg bg-DarkBlue font-bold">Next</button>
      </div>
  </div>
</template>

<style scoped></style>