<script setup>
import { getUsers } from "../composable/getUser.js";
import { ref, onMounted, onUpdated } from "vue";
import { changeDateTimeFormat } from "../composable/changeFormatDate.js";
import { useRouter } from "vue-router";
import TimeZone from "../components/icones/TimeZone.vue";
import AddIcon from "./icones/AddIcon.vue";
const router = useRouter();
const timezone = Intl.DateTimeFormat().resolvedOptions().timeZone;
const users = ref([]);

onMounted(async () => {
  noUser();
  users.value = await getUsers();
  noUser();
});

const isUserFound = ref(false);
const noUser = () => {
  if (users.value.length <= 0) {
    isUserFound.value = true;
  } else {
    isUserFound.value = false;
  }
}
</script>
 
<template>
    <div class="flex w-full min-h-screen max-h-full bg-Background">
        <div class="flex flex-col w-1/3 max-h-full bg-Cream border-solid border-r-2">
            <h1 class="flex pl-5 bg-DarkBlue py-5 text-3xl font-bold border-solid border-b-2">SAS</h1>
            <router-link :to = "{ name : 'announcements' }" class="flex pl-5 py-5 border-solid border-b-2 font-bold hover:bg-LightBlue">
                Announcement
            </router-link>
            <router-link :to ="{name: 'userManagement'}" class="flex pl-5 py-5 border-solid border-b-2 font-bold hover:bg-LightBlue">
                User
            </router-link>
        </div>
        <div class="w-3/4">
            <div class="bg-LightBlue text-BlueFonts drop-shadow-lg">
                <h1 class="h-24 flex justify-center items-center drop-shadow-lg text-4xl font-bold">
                    User Management
                </h1>
            </div>
            <div class="flex my-8 w-full justify-between">
                <p class="mx-5 items-center flex">
                    <TimeZone></TimeZone>&nbsp; Date/Time shown in Timezone : &nbsp;
                    <span class="font-bold text-BlueFonts drop-shadow-sm">
                        {{ timezone }}
                    </span>
                </p>
                <div
                    class="bg-DarkBlue shadow-md mr-5 hover:bg-red-200 font-semibold text-BlueFonts hover:bg-LightBlue rounded-full items-center justify-center">
                    <router-link :to="{
                        name: 'addAnnouncement',
                    }">
                        <button class="ann-button px-5 py-2 text-lg font-bold">
                            <AddIcon class="inline mr-2 mb-1"></AddIcon>Add User
                        </button>
                    </router-link>
                </div>
            </div>
            <div class="mx-5 mt-2 relative overflow-x-auto shadow-md sm:rounded-lg">
                <table class="w-full text-sm h-full text-left">
                    <thead class="text-base bg-DarkBlue uppercase text-BlueFonts">
                        <tr>
                            <th scope="col" class="px-6 py-3">No.</th>
                            <th scope="col" class="px-6 py-3">Usermane</th>
                            <th scope="col" class="px-6 py-3">Name</th>
                            <th scope="col" class="px-6 py-3">Email</th>
                            <th scope="col" class="px-6 py-3">Role</th>
                            <th scope="col" class="px-6 py-3 text-center">Created On</th>
                            <th scope="col" class="px-6 py-3 text-center">Updated On</th>
                            <th scope="col" class="px-6 py-3 text-center">Action</th>
                        </tr>
                    </thead>

                    <tbody v-if="!isUserFound" class="bg-Cream">
                        <tr v-for="(user, index) in users" :key="index"
                            class="ann-item bg-white dark:bg-gray-900">
                            <th scope="row" class="px-6 py-4 font-medium whitespace-nowrap dark:text-white">
                                {{ ++index }}
                            </th>
                            <td class="ann-title px-6 py-4 w-1/6 break-all">
                                {{ user.username }}
                            </td>
                            <td class="ann-category px-6 py-4">
                                {{ user.name }}
                            </td>
                            <td class="ann-publish-date px-6 py-4">
                                {{ user.email }}
                            </td>
                            <td class="ann-close-date px-6 py-4">
                                {{ user.role }}
                            </td>
                            <td class="ann-display px-6 py-4 text-center justify-items-center">
                                {{ changeDateTimeFormat(user.createdOn) }}
                            </td>
                            <td class="ann-display px-6 py-4 text-center justify-items-center">
                                {{ changeDateTimeFormat(user.updatedOn) }}
                            </td>
                            <td class="px-6 py-4 text-center">
                                <router-link :to="{
                                    name: 'announcementDetail',
                                    params: { id: user.id },
                                }"><button
                                        class="ann-button font-bold text-blue-600 shadow-md hover:bg-ButtonViewHover rounded-full bg-DarkGreen px-5 py-2">
                                        view
                                    </button>
                                </router-link>
                                <button
                                    class="ann-button font-bold text-blue-600 shadow-md hover:bg-ButtonDeleteHover rounded-full bg-DarkRed px-5 py-2 ml-2"
                                    @click="deleteAnnouncement(user.id)">
                                    delete
                                </button>
                            </td>
                        </tr>
                    </tbody>
                </table>
                <div v-if="isUserFound" class="text-center text-3xl my-10 text-BlueFonts">
                    No Users
                </div>
            </div>
        </div>
    </div>
</template>
<style scoped></style>