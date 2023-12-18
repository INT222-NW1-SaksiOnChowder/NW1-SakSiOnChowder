<script setup>
import { computed, ref } from "vue";
import { createAnnouncement } from "../../composable/announcements/addAnnouncement.js";
import { useRouter } from "vue-router";
import PreviewFile from "./PreviewFile.vue";
import { addFiles } from "../../composable/announcements/addFiles.js";
import AlertLoadingVue from "../AlertLoading.vue";

const router = useRouter();
const announcementObj = ref();
const displayShow = ref(false);
const announcementId = ref()
const filesValue = ref()
const selectedPublishDate = ref();
const selectedPublishTime = ref();
const selectedCloseDate = ref();
const selectedCloseTime = ref();
const nowDate = ref(new Date())
const waitingIcon = ref(false)

announcementObj.value = {
  announcementTitle: "",
  announcementDescription: "",
  publishDate: "",
  closeDate: "",
  announcementDisplay: displayShow.value,
  categoryId: 1
};

const addNewFiles = (files) => {
  filesValue.value = files
  console.log(filesValue.value);
}

const submit = async (addAnnouncement) => {
  waitingIcon.value = true
  addAnnouncement.categoryId = Number(addAnnouncement.categoryId);
  if (addAnnouncement.announcementDisplay === true) {
    addAnnouncement.announcementDisplay = "Y";
  } else {
    addAnnouncement.announcementDisplay = "N";
  }

  addAnnouncement.publishDate = new Date(
    `${selectedPublishDate.value} ${selectedPublishTime.value}`
  );
  addAnnouncement.closeDate = new Date(
    `${selectedCloseDate.value} ${selectedCloseTime.value}`
  );

  if (
    addAnnouncement.publishDate === undefined ||
    addAnnouncement.closeDate === undefined
  ) {
    addAnnouncement.publishDate = null;
    addAnnouncement.closeDate = null;
  }

  const result = await createAnnouncement(addAnnouncement)
  announcementId.value = result.id
  console.log(result.id);
  console.log(announcementId.value);
  if (result !== undefined && result !== false) {
    console.log(filesValue.value);
    if (filesValue.value !== undefined) {
      const addFilesResult = await addFiles(announcementId.value, filesValue.value)
      console.log(addFilesResult);
      if (addFilesResult) {
        alert('Create successfully')
        router.push({ name: 'announcements' })
        waitingIcon.value = false
      }
      waitingIcon.value = false
    }
    waitingIcon.value = false
    router.push({ name: 'announcements' })
  }
  waitingIcon.value = false
};

const isDisabledPublishTime = computed(() => {
  if (!selectedPublishDate.value) {
    return true;
  } else {
    selectedPublishTime.value = "06:00:00";
    return false;
  }
});

const isDisabledCloseTime = computed(() => {
  if (!selectedCloseDate.value) {
    return true;
  } else {
    selectedCloseTime.value = "18:00:00";
    return false;
  }
});

</script>

<template>
  <div class="w-full min-h-screen max-h-full bg-Background">
    <div class="lg:mx-32 mx-5">
      <div class="rounded-full shadow-md bg-DarkBlue inline-block mt-3 mb-8">
        <h1 class="text-BlueFonts py-1 px-2 lg:text-2xl text-lg lg:px-5 lg:py-5 font-bold">
          Announcement Detail:
        </h1>
      </div>
      <div class="bg-LightBlue rounded-2xl lg:py-9 py-2 px-5 lg:px-28">
        <div class="my-5">
          <label class="font-bold">Title</label><br />
          <input class="ann-title mt-2 bg-InputColor drop-shadow-md h-8 w-full rounded-lg" type="text"
            v-model.trim="announcementObj.announcementTitle" maxlength="200" />
        </div>
        <div class="my-5">
          <label class="font-bold">Catagory</label><br />
          <select class="ann-category mt-2 drop-shadow-md bg-InputColor h-8  w-2/5 rounded-lg"
            v-model="announcementObj.categoryId">
            <option value="1">ทั่วไป</option>
            <option value="2">ทุนการศึกษา</option>
            <option value="3">หางาน</option>
            <option value="4">ฝึกงาน</option>
          </select>
        </div>
        <div class="my-5">
          <label class="font-bold">Description</label><br />
          <QuillEditor maxlength="10000" cols="100" rows="5"
            class="ann-description drop-shadow-md mt-2 bg-InputColor w-full rounded-lg" theme="snow" toolbar="full"
            v-model:content="announcementObj.announcementDescription" contentType="html" />
        </div>
        <div class="my-5">
          <label class="font-bold">Publish Date</label><br />
          <input class="ann-publish-date drop-shadow-md mt-2 bg-InputColor sm:w-1/5 mr-5 rounded-lg px-5 py-1" type="date"
            v-model="selectedPublishDate" />
          <input :disabled="isDisabledPublishTime" :style="isDisabledPublishTime
            ? 'opacity: 0.5; background-color:darkgray; cursor: not-allowed;'
            : 'opacity: 1;'
            " class="sm:w-1/5 bg-InputColor drop-shadow-md mt-2 rounded-lg px-5 py-1" type="time"
            v-model="selectedPublishTime" />
        </div>
        <div class="my-5">
          <label class="font-bold">Close Date</label><br />
          <input class="bg-InputColor drop-shadow-md sm:w-1/5 mr-5 mt-2 rounded-lg px-5 py-1" type="date"
            v-model="selectedCloseDate" />
          <input :disabled="isDisabledCloseTime" :style="isDisabledCloseTime
            ? 'opacity: 0.5; background-color:darkgray; cursor: not-allowed;'
            : 'opacity: 1; background-color:InputColor;'
            " class="sm:w-1/5 mt-2 bg-InputColor drop-shadow-md rounded-lg px-5 py-1" type="time"
            v-model="selectedCloseTime" />
        </div>
        <div>
          <label class="font-bold ">Display</label><br />
          <input class="bg-InputColor" type="checkbox" id="displayShow" v-model="announcementObj.announcementDisplay" />
          <label for="displayShow" class="ml-2">Check to show this announcement</label>
        </div>
        <PreviewFile @filesSubmit="addNewFiles" />
        <div class="mb-5 text-center">
          <button class="ann-button shadow-md hover:bg-ButtonViewHover mr-2 rounded-full bg-DarkGreen px-5 py-2 font-bold"
            @click="submit(announcementObj)">
            Submit
          </button>
          <router-link :to="{ name: 'announcements' }">
            <button class=" rounded-full shadow-md hover:bg-ButtonDeleteHover bg-DarkRed px-5 py-2 font-bold">
              Cancel
            </button>
          </router-link>
        </div>
      </div>
    </div>
  </div>
  <AlertLoadingVue v-if="waitingIcon" message="Creating an announcement, please wait a moment."/>
</template>

<style scoped></style>