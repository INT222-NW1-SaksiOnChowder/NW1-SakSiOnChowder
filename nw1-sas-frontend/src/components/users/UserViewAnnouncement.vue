<script setup>
import { getAnnouncementsUser, getAnnouncementUser } from "../../composable/users/getAnnouncementUser.js"
import { ref, onMounted, onUpdated, computed, onBeforeMount } from "vue"
import { changeDateTimeFormat } from "../../composable/changeFormatDate.js"
import { annStores } from '../../stores/counter.js'
import TimeZone from '../icones/TimeZone.vue'
import CloseIcon from "../icones/CloseIcon.vue"
import ActiveIcon from "../icones/ActiveIcon.vue"
import Menubar from "../Navbar.vue"
import ModalSubCategory from "../subscription/ModalSubCategory.vue"
import UnSubScription from "../subscription/UnSubScription.vue"
import { useRoute } from 'vue-router';

const route = useRoute()
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
const activeMenubar = ref(false)

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
  annoucementContent.value = announcements.value.content
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
      <div class="bg-LightBlue text-BlueFonts drop-shadow-lg">
        <div class="flex font-bold py-7 items-center justify-center bg-LightBlue text-BlueFonts">
          <h1 class="drop-shadow-lg xs:text-3xl sm:text-3xl md:text-4xl text-sm">SIT Announcement System (SAS)</h1>
          <Menubar v-if="showMenubar" class="items-center justify-center md:text-lg text-xs" />
        </div>
      </div>
      <div class="flex my-8 w-full text-xs  lg:text-base items-center justify-between">
        <p class="mx-5 items-center justify-center text-center flex flex-col sm:flex-row">
          <TimeZone></TimeZone>&nbsp; Date/Time shown in Timezone : &nbsp;
          <span class="font-bold text-BlueFonts">{{
            timezone
          }}</span>
        </p>
        <div class="flex sm:flex-row flex-col mr-5 font-semibold rounded-full items-center justify-center">
          <div class=" bg-DarkBlue h-full md:mb-auto font-bold hover:bg-LightBlue text-BlueFonts rounded-full">
            <button @click="getListAnnouncement" class="ann-button md:px-5 px-2  py-2 md:text-lg text-xs font-bold">
              <ActiveIcon v-if="showCloseTime" class="inline mr-2 mb-1"></ActiveIcon>
              <CloseIcon v-else="showCloseTime" class="inline mr-2 mb-1"></CloseIcon>
              {{ wordButton }}
            </button>
          </div>
          <router-link :to="{ name: 'login' }" v-if="!showMenubar"
            class="ann-button px-5 py-2 md:text-lg mt-2 sm:mt-0 font-bold ml-5 ann-button bg-DarkBlue  hover:bg-LightBlue text-BlueFonts rounded-full ">
            Login
          </router-link>
        </div>
      </div>
      <div class="ml-5 my-5 flex items-center">
        <p class="flex font-semibold md:text-base text-xs">Choose Category : </p>&nbsp;
        <select class="ann-category-filter bg-Cream rounded-md p-1 md:text-base text-xs" @change="changeCategory($event.target.value)"
          v-model="selectedCategory">
          <option value="">ทั้งหมด</option>
          <option value="1">ทั่วไป</option>
          <option value="2">ทุนการศึกษา</option>
          <option value="3">หางาน</option>
          <option value="4">ฝึกงาน</option>
        </select>
        <button @click="togglePopUpSubscription"
          class="ml-5 bg-DarkBlue font-bold hover:bg-LightBlue text-BlueFonts rounded-full px-5 py-2 text-xs md:text-sm">Subscribe</button>
      </div>
      <div class="mx-5 mt-2 overflow-x-auto shadow-md sm:rounded-lg">
        <table class="w-full text-sm dark:text-gray-400">
          <thead class="md:text-base text-xs bg-DarkBlue uppercase text-BlueFonts">
            <tr>
              <th scope="col" class="px-6 py-3 ">No.</th>
              <th scope="col" class="px-28 xl:px-6 text-left py-3">Title</th>
              <th v-show="showCloseTime" scope="col" class="lg:px-6 px-16 py-3 text-left">Close Time</th>
              <th scope="col" class="lg:px-6 px-8 py-3 text-left">Category</th>
            </tr>
          </thead>

          <tbody class="bg-Cream md:text-base text-xs" v-if="!isAnnouncementFound">
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
