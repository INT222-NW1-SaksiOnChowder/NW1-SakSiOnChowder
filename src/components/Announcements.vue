<script setup>
import { getAnnouncements } from '../composable/getInformation.js'
import { ref, onMounted } from "vue"

const announcements = ref([])

onMounted(async () => {
  announcements.value = await getAnnouncements()
})

</script>

<template>
  <div class="w-full h-full my-5">
    <h1 class="flex justify-center items-center text-3xl font-bold">
      SIT Announcement System (SAS)
    </h1>
    <p class="mx-10 mt-5">Date/Time shown in Timezone : {{ text }}</p>
  </div>
  <div class="mx-3 relative overflow-x-auto shadow-md sm:rounded-lg">
    <table class="w-full text-sm text-left dark:text-gray-400">
      <thead class="text-xs uppercase bg-gray-50 dark:bg-gray-700 dark:text-gray-400">
        <tr>
          <th scope="col" class="px-6 py-3">
            No.
          </th>
          <th scope="col" class="px-6 py-3">
            Title
          </th>
          <th scope="col" class="px-6 py-3">
            Category
          </th>
          <th scope="col" class="px-6 py-3">
            Publish Date
          </th>
          <th scope="col" class="px-6 py-3">
            Close Date
          </th>
          <th scope="col" class="px-6 py-3">
            Display
          </th>
          <th scope="col" class="px-6 py-3">
            Action
          </th>
        </tr>
      </thead>

      <tbody>
        <tr v-for="(announcement, index) in announcements" :key="index"
          class="bg-white border-b dark:bg-gray-900 dark:border-gray-700">
          <th scope="row" class="px-6 py-4 font-medium text-gray-900 whitespace-nowrap dark:text-white">
            {{ ++index }}
          </th>
          <td class="px-6 py-4">
            {{ announcement.announcementTitle }}
          </td>
          <td class="px-6 py-4">
            {{ announcement.announcementCategory.categoryName }}
          </td>
          <td class="px-6 py-4">
            {{ announcement.publishDate }}
          </td>
          <td class="px-6 py-4">
            {{ announcement.closeDate }}
          </td>
          <td class="px-6 py-4">
            {{ announcement.announcementDisplay }}
          </td>
          <td class="px-6 py-4">
            <router-link :to="{ name: 'announcementDetail' }" active-class="active">
              <a href="#" class="font-medium text-blue-600 dark:text-blue-500 hover:underline">View</a>
            </router-link>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<style scoped></style>