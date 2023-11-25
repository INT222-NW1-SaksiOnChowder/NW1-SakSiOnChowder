<script setup>
import { getAnnouncementsUser, getAnnouncementUser } from "../../composable/users/getAnnouncementUser.js"
import { ref, onMounted, onUpdated, computed, onBeforeMount, watchEffect } from "vue"
import { changeDateTimeFormat } from "../../composable/changeFormatDate.js"
import { annStores } from '../../stores/counter.js'
import TimeZone from '../icones/TimeZone.vue'
import CloseIcon from "../icones/CloseIcon.vue"
import ActiveIcon from "../icones/ActiveIcon.vue"
import Menubar from "../Navbar.vue"
import ModalSubCategory from "../subscription/ModalSubCategory.vue"
import UnSubScription from "../subscription/UnSubScription.vue"
import { useRoute, useRouter } from 'vue-router';
import jwt_decode from "jwt-decode"

const route = useRoute()
// console.log(route.query.token);
const announcements = ref([])
const timezone = Intl.DateTimeFormat().resolvedOptions().timeZone
const announcementStores = annStores()
const showMenubar = ref(false)
const showCloseTime = ref(false)
const isShowModal = ref(false)
const isShowUnSubModal = ref(false)
const slicePageNumberArr = ref([])
const pageNumberArr = ref([])
const isMoreThanFiveElements = ref(true)
const selectedCategory = ref(announcementStores.category)
const annoucementContent = ref()
const wordButton = ref("Closed Announcements")

const setShowCloseTime = () => {
  if (announcementStores.mode === 'close') {
    showCloseTime.value = true
  } else {
    showCloseTime.value = false
  }
}

onBeforeMount(() => {
  if (route.query.token !== undefined) {
    isShowUnSubModal.value = true
  }
})

const closeUnSubModal = () => {
  isShowUnSubModal.value = false
}

onMounted(async () => {
  checkUserLogin()
  noAnnouncement()
  announcements.value = await getAnnouncementsUser(announcementStores.mode, announcementStores.page, announcementStores.category)
  // console.log(annoucementContent.value);
  annoucementContent.value = announcements.value.content
  console.log(annoucementContent.value);
  selectedCategory.value = announcementStores.category
  setShowCloseTime()
  checkPageButton()
  noAnnouncement()
})

onUpdated(() => {
  checkUserLogin()
  noAnnouncement()
  setShowCloseTime()
  if (announcementStores.mode === 'close') {
    wordButton.value = 'Active Announcements'
  } else {
    wordButton.value = "Closed Announcements"
  }
  console.log(showMenubar.value);
})

const getListAnnouncement = async () => {
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
}

const isAnnouncementFound = ref(false)
const noAnnouncement = () => {
  if (announcements.value.length <= 0 || announcements.value.totalElements <= 0) {
    isAnnouncementFound.value = true
  } else {
    isAnnouncementFound.value = false
  }
}

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
    slicePageNumberArr.value = pageNumberArr.value.slice(0, 10)
  }
}

const changeToCurrentPage = async (page) => {
  if (announcements.value.totalPages !== page - 1) {
    announcements.value = await getAnnouncementsUser(announcementStores.mode, page - 1, announcementStores.category)
    annoucementContent.value = announcements.value.content
    announcementStores.setPage(page - 1)
  }
  if (announcements.value.page + 1 > 10) {
    slicePageNumberArr.value = pageNumberArr.value.slice(announcements.value.page - 9, announcements.value.page + 1, announcementStores.category)
  }
  if (announcements.value.page + 1 <= 10) {
    slicePageNumberArr.value = pageNumberArr.value.slice(0, 10)
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

const nextOrPrevButton = (move) => {
  if (move === 'next') {
    changeToCurrentPage(announcementStores.page + 2)
  } else {
    changeToCurrentPage(announcementStores.page)
  }
}

const checkUserLogin = () => {
  if (localStorage.getItem('accessToken')) {
    showMenubar.value = true
  } else {
    showMenubar.value = false
  }
}

const changeCategory = async (category) => {
  if (category !== announcementStores.category) {
    announcementStores.setCategory(category)
    announcements.value = await getAnnouncementsUser(announcementStores.mode, announcementStores.page, category)
    annoucementContent.value = announcements.value.content
  }
  changeToCurrentPage(1)
  checkPageButton()
  noAnnouncement()
}

const togglePopUpSubscription = () => {
  isShowModal.value = !isShowModal.value
}

</script>

<template>
  <div class="flex w-full min-h-screen max-h-full bg-Background">
    <div class="w-full">
      <div class="flex font-bold py-7 items-center justify-center bg-LightBlue text-BlueFonts">
        <h1 class="drop-shadow-lg text-4xl">SIT Announcement Systems (SAS)</h1>
        <Menubar v-if="showMenubar" @click="checkUserLogin" />
      </div>
      <div class="flex mt-5 w-full">
        <p class="w-full mx-5 items-center flex">
          <TimeZone></TimeZone>&nbsp;
          Date/Time shown in Timezone : &nbsp;
          <span class="font-bold text-BlueFonts drop-shadow-sm">{{ timezone }}</span>
        </p>
        <div class="w-full mr-5 flex justify-end">
          <div class=" bg-DarkBlue font-bold hover:bg-LightBlue text-BlueFonts rounded-full">
            <button @click="getListAnnouncement" class="ann-button px-5 py-2 text-lg ">
              <ActiveIcon v-if="showCloseTime" class="inline mr-2 mb-1"></ActiveIcon>
              <CloseIcon v-else="showCloseTime" class="inline mr-2 mb-1"></CloseIcon>
              {{ wordButton }}
            </button>
          </div>
          <router-link :to="{ name: 'login' }" v-if="!showMenubar"
            class="ml-5 ann-button px-5 py-2 text-lg bg-DarkBlue font-bold hover:bg-LightBlue text-BlueFonts rounded-full ">
            <!-- <router-link :to="{ name: 'login' }" class="ann-button px-5 py-2 text-lg"> -->
            Login
            <!-- </router-link> -->
          </router-link>
        </div>
      </div>
      <div class="ml-5 my-5 flex items-center">
        <p class="flex font-semibold">Choose Category : </p>&nbsp;
        <select class="ann-category-filter bg-Cream rounded-md p-1" @change="changeCategory($event.target.value)"
          v-model="selectedCategory">
          <option value="">ทั้งหมด</option>
          <option value="1">ทั่วไป</option>
          <option value="2">ทุนการศึกษา</option>
          <option value="3">หางาน</option>
          <option value="4">ฝึกงาน</option>
        </select>
        <button @click="togglePopUpSubscription"
          class="ml-5 bg-DarkBlue font-bold hover:bg-LightBlue text-BlueFonts rounded-full px-5 py-2 text-sm">Subscribe</button>
      </div>
      <div class="mx-5 mt-2 overflow-x-auto shadow-md sm:rounded-lg">
        <table class="w-full text-sm dark:text-gray-400">
          <thead class="bg-DarkBlue text-base text-BlueFonts uppercase dark:bg-gray-700 dark:text-gray-400">
            <tr>
              <th scope="col" class="px-6 py-3 ${}">No.</th>
              <th scope="col" class="px-6 py-3 text-left">Title</th>
              <th v-show="showCloseTime" scope="col" class="px-6 py-3 text-left">Close Time</th>
              <th scope="col" class="px-6 py-3 text-left">Category</th>
            </tr>
          </thead>

          <tbody class="bg-Cream" v-if="!isAnnouncementFound">
            <tr v-for="(announcement, index) in annoucementContent" :key="index" class="ann-item">
              <th scope="row" class="px-6 py-4 font-medium text-gray-900 whitespace-nowrap dark:text-white">
                {{ ++index + (announcements.size * announcements.page) }}
              </th>
              <router-link :to="{ name: 'userViewDetail', params: { id: announcement.id } }">
                <td class="ann-title px-6 py-4 hover:font-bold hover:text-BlueFonts break-all">
                  {{ announcement.announcementTitle }}
                </td>
              </router-link>
              <td v-show="showCloseTime" class="ann-close-date px-6 py-4 text-left">
                {{ changeDateTimeFormat(announcement.closeDate) }}
              </td>
              <td class="ann-category px-6 py-4 text-left break-all">
                {{ announcement.announcementCategory }}
              </td>
            </tr>
          </tbody>
        </table>
        <div v-if="isAnnouncementFound" class="text-center text-3xl my-10 text-BlueFonts">No Announcement</div>
      </div>
      <div v-if="isMoreThanFiveElements" class="flex justify-center mt-8">
        <button :disabled="disablePrevButton" @click="nextOrPrevButton('prev')"
          class="ann-page-prev font-bold px-5 py-2 mx-1 rounded-lg bg-DarkBlue text-BlueFonts">Prev</button>
        <button @click="changeToCurrentPage(pageNumber)" v-for="(pageNumber, index) in slicePageNumberArr" :key="index"
          :class="[
            pageNumber === announcementStores.page + 1
              ? 'bg-DarkGreen px-5 py-2 mx-1 rounded-lg'
              : 'bg-Cream px-5 py-2 mx-1 rounded-lg hover:bg-ButtonViewHover',
            'ann-page-' + index
          ]">
          {{ pageNumber }}
        </button>
        <button :disabled="disableNextButton" @click="nextOrPrevButton('next')"
          class="ann-page-next  px-5 py-2 mx-1 rounded-lg bg-DarkBlue font-bold text-BlueFonts">Next</button>
      </div>
    </div>
    <ModalSubCategory v-if="isShowModal" @cancel="togglePopUpSubscription" />
    <UnSubScription :unSubTokenParam="`${route.query.token}`" v-if="isShowUnSubModal" @cancel="closeUnSubModal" />
  </div>
</template>

<style scoped></style>
