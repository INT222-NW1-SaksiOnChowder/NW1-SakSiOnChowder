<script setup>
import { getAnnouncements } from "../../composable/announcements/getInformation.js";
import { ref, onMounted, onUpdated } from "vue";
import { deleteAllFiles } from "../../composable/announcements/deleteFiles";
import { changeDateTimeFormat } from "../../composable/changeFormatDate.js";
import { deleteAcc } from "../../composable/announcements/deleteAnnouncement.js";
import { useRouter } from "vue-router";
import { role } from '../../stores/role.js'
import TimeZone from "../icones/TimeZone.vue";
import AddIcon from "../icones/AddIcon.vue";
import Menubar from "../Navbar.vue";

const router = useRouter();
const announcements = ref([]);
const timezone = Intl.DateTimeFormat().resolvedOptions().timeZone;
const isShowOwner = ref(false)
const currenRole = role()

if (currenRole.currentRole === 'ROLE_admin') {
  isShowOwner.value = true
}

onMounted(async () => {
  noAnnouncement();
  announcements.value = await getAnnouncements();
  if (announcements.value === false) {
    announcements.value = await getAnnouncements();
  }
  announcements.value.sort((a, b) => b.id - a.id);
  noAnnouncement();
});

onUpdated(() => {
  noAnnouncement();
});

const isAnnouncementFound = ref(false);
const noAnnouncement = () => {
  if (announcements.value.length <= 0) {
    isAnnouncementFound.value = true;
  } else {
    isAnnouncementFound.value = false;
  }
};

const deleteAnnouncement = async (id) => {
  router.push({ name: "deleteAnnouncement", params: { id: id } });
  const confirmed = confirm(`Do you want to delete`);
  if (confirmed) {
    await deleteAcc(id);
    await deleteAllFiles(id);
  }
  announcements.value = await getAnnouncements();
  announcements.value.sort((a, b) => b.id - a.id);
  router.push({ name: "announcements" });
};
</script>

<template>
  <div class="flex w-full min-h-screen max-h-full bg-Background"> 
    <div class="w-full">
      <div class="flex font-bold py-7 items-center justify-center bg-LightBlue text-BlueFonts drop-shadow-lg">
        <h1 class="drop-shadow-lg xs:text-3xl sm:text-3xl md:text-4xl text-sm">SIT Announcement System (SAS)</h1>
        <Menubar class="items-center justify-center md:text-lg text-xs"/>
      </div>
      <div class="flex my-8 w-full text-xs  md:text-base items-center justify-between">
        <p class="mx-5 items-center justify-center text-center flex flex-col sm:flex-row">
          <TimeZone></TimeZone>&nbsp; Date/Time shown in Timezone : &nbsp;
          <span class="font-bold text-BlueFonts">{{
            timezone
          }}</span>
        </p>
        <div
          class="bg-DarkBlue shadow-md mr-5 font-semibold text-BlueFonts hover:bg-LightBlue rounded-full items-center justify-center">
          <router-link :to="{
                name: 'addAnnouncement',
              }">
            <button class="ann-button px-5 py-2 md:text-lg font-bold">
              <AddIcon class="inline mr-2 mb-1"></AddIcon>Add Announcement
            </button>
          </router-link>
        </div>
      </div>
      <div class="mx-5 mt-2 overflow-x-scroll shadow-md sm:rounded-lg">
        <table class="w-full text-sm h-full text-left">
          <thead class="md:text-base text-xs bg-DarkBlue uppercase text-BlueFonts">
            <tr>
              <th scope="col" class="px-6 py-3">No.</th>
              <th scope="col" class="px-28 xl:px-6 text-left py-3">Title</th>
              <th scope="col" class="px-6 text-center py-3">Category</th>
              <th scope="col" class="md:px-6 px-16 text-center py-3">Publish Date</th>
              <th scope="col" class="md:px-6 px-16 text-center py-3">Close Date</th>
              <th scope="col" class="px-6  py-3 text-center">Display</th>
              <!-- <th scope="col" class="px-6 py-3 text-center">Views</th> -->
              <th v-if="isShowOwner" scope="col" class="px-6 py-3 text-center">Owner</th>
              <th scope="col" class="px-6 py-3 text-center">Action</th>
            </tr>
          </thead>

          <tbody v-if="!isAnnouncementFound" class="bg-Cream md:text-base text-xs">
            <tr v-for="(announcement, index) in announcements" :key="index" class="ann-item">
              <th scope="row" class="px-6 py-4 font-medium whitespace-nowrap dark:text-white">
                {{ ++index }}
              </th>
              <td class="ann-title px-6 py-4 break-all">
                {{ announcement.announcementTitle }}
              </td>
              <td class="ann-category text-left px-6 py-4">
                {{ announcement.announcementCategory }}
              </td>
              <td class="ann-publish-date text-center px-6 py-4">
                {{ changeDateTimeFormat(announcement.publishDate) }}
              </td>
              <td class="ann-close-date text-center px-6 py-4">
                {{ changeDateTimeFormat(announcement.closeDate) }}
              </td>
              <td class="ann-display px-6 py-4 text-center justify-items-center">
                {{ announcement.announcementDisplay }}
              </td>
              <!-- <td class="ann-display px-6 py-4 text-center justify-items-center">
                {{ announcement.viewCount }}
              </td> -->
              <td v-if="isShowOwner" class="ann-display px-6 py-4 text-center justify-items-center">
                {{ announcement.announcementOwner }}
              </td>
              <td class="px-6 py-4 text-center flex items-center justify-center">
                <router-link :to="{
                  name: 'announcementDetail',
                  params: { id: announcement.id },
                }"><button
                    class="ann-button font-bold shadow-md hover:bg-ButtonViewHover rounded-full bg-DarkGreen px-5 py-2">
                    view
                  </button>
                </router-link>
                <button
                  class="ann-button font-bold shadow-md hover:bg-ButtonDeleteHover rounded-full bg-DarkRed px-5 py-2 ml-2"
                  @click="deleteAnnouncement(announcement.id)">
                  delete
                </button>
              </td>
            </tr>
          </tbody>
        </table>
        <div v-if="isAnnouncementFound" class="text-center text-3xl my-10 text-BlueFonts">
          No Announcement
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped></style>
