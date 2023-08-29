<script setup>
import { computed, ref } from "vue";
import { createAnnouncement } from "../../composable/announcements/addAnnouncement.js";
import { useRouter } from "vue-router";

const router = useRouter();

const announcementObj = ref();
const displayShow = ref(false);

const selectedPublishDate = ref();
const selectedPublishTime = ref();
const selectedCloseDate = ref();
const selectedCloseTime = ref();
const nowDate = ref(new Date())

announcementObj.value = {
  announcementTitle: "",
  announcementDescription: "",
  publishDate: "",
  closeDate: "",
  announcementDisplay: displayShow.value,
  categoryId: 1,
};

const submit = async (addAnnouncement) => {
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

  if (!announcementObj.announcementTitle || !announcementObj.announcementDescription || !announcementObj.categoryId || announcementObj.categoryName 
    || announcementObj.publishDate < announcementObj.closeDate || announcementObj.publishDate < nowDate.value || announcementObj.closeDate < nowDate.value
    ) 
    {
        await createAnnouncement(addAnnouncement)  
    }   else {
        await createAnnouncement(addAnnouncement)  
        router.push({ name: 'announcements' })
    }
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
  <div class="mx-32">
    <div class="rounded-full shadow-md bg-DarkBlue inline-block mt-3 mb-8">
        <h1 class="text-BlueFonts text-2xl px-5 py-5 font-bold">
                Announcement Detail:
        </h1>
    </div>
    <div class="bg-LightBlue rounded-2xl py-9 px-28">
      <div class="my-5">
        <label class="font-bold">Title</label><br/>
        <input
          class="ann-title bg-InputColor drop-shadow-md h-8 w-full rounded-lg"
          type="text"
          v-model.trim="announcementObj.announcementTitle"
          maxlength="200"
        />
        </div>
      <div class="my-5">
        <label class="font-bold">Catagory</label><br />
        <select
          class="ann-category drop-shadow-md bg-InputColor h-8  w-2/5 rounded-lg"
          v-model="announcementObj.categoryId"
        >
          <option value="1">ทั่วไป</option>
          <option value="2">ทุนการศึกษา</option>
          <option value="3">หางาน</option>
          <option value="4">ฝึกงาน</option>
        </select>
      </div>
      <div class="my-5">
        <label class="font-bold">Description</label><br />
        <QuillEditor maxlength="10000" cols="100" rows="5" 
                         class="ann-description drop-shadow-md bg-InputColor w-full rounded-lg" 
                         theme="snow" toolbar="full" v-model:content="announcementObj.announcementDescription" contentType="html"/>
      </div>
      <div class="my-5">
        <label class="font-bold">Publish Date</label><br />
        <input
          class="ann-publish-date drop-shadow-md bg-InputColor w-1/5 mr-5 rounded-lg px-5 py-1"
          type="date"
          v-model="selectedPublishDate"
        />
        <input
          :disabled="isDisabledPublishTime"
          :style="
            isDisabledPublishTime
              ? 'opacity: 0.5; background-color:darkgray; cursor: not-allowed;'
              : 'opacity: 1;'
          "
          class="w-1/5 bg-InputColor drop-shadow-md rounded-lg px-5 py-1"
          type="time"
          v-model="selectedPublishTime"
        />
      </div>
      <div class="my-5">
        <label class="font-bold">Close Date</label><br />
        <input
          class="bg-InputColor drop-shadow-md sm:w-1/5 mr-5 rounded-lg px-5 py-1"
          type="date"
          v-model="selectedCloseDate"
        />
        <input
          :disabled="isDisabledCloseTime"
          :style="
            isDisabledCloseTime
              ? 'opacity: 0.5; background-color:darkgray; cursor: not-allowed;'
              : 'opacity: 1; background-color:InputColor;'
          "
          class="w-1/5 bg-InputColor drop-shadow-md rounded-lg px-5 py-1"
          type="time"
          v-model="selectedCloseTime"
        />
      </div>
      <div>
        <label class="font-bold ">Display</label><br />
        <input class="bg-InputColor"
          type="checkbox"
          id="displayShow"
          v-model="announcementObj.announcementDisplay"
        />
        <label for="displayShow" class="ml-2"
          >Check to show this announcement</label
        >
      </div>
      <div class="mt-5 text-center">
        <button
          class="ann-button shadow-md hover:bg-ButtonViewHover mr-2 rounded-full bg-DarkGreen px-5 py-2 font-bold"
          @click="submit(announcementObj)"
        >
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
</template>

<style scoped></style>