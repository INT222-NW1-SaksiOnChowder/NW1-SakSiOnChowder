<script setup>
import { getUsers } from "../../composable/users/getUser.js";
import { ref, onMounted, onUpdated } from "vue";
import { changeDateTimeFormat } from "../../composable/changeFormatDate.js";
import { useRouter } from "vue-router";
import TimeZone from "../icones/TimeZone.vue";
import AddIcon from "../icones/AddIcon.vue";
import { deleteUser } from "../../composable/users/deleteUser.js"
import Menubar from "../Navbar.vue"
import { getToken, getNewAccessToken } from "../../composable/users/getToken.js";
const router = useRouter();
const timezone = Intl.DateTimeFormat().resolvedOptions().timeZone;
const users = ref([]);

onMounted(async () => {
    noUser();
    users.value = await getUsers();
    if (users.value === false) {
        users.value = await getUsers();
    }
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

const deleteUserById = async (id) => {
    router.push({ name: "deleteUser", params: { id: id } })
    const confirmed = confirm(`Do you want to delete`)
    if (confirmed) {
        await deleteUser(id);
    }
    users.value = await getUsers();
    router.push({ name: "userManagement" })
};
</script>
 
<template>
    <div class="flex w-full min-h-screen max-h-full bg-Background">
        <Menubar />
        <div class="w-4/5">
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
                    class="bg-DarkBlue shadow-md mr-5 font-semibold text-BlueFonts hover:bg-LightBlue rounded-full items-center justify-center">
                    <router-link :to="{
                        name: 'addUser',
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
                            <th scope="col" class="px-6 py-3">Username</th>
                            <th scope="col" class="px-6 py-3">Name</th>
                            <th scope="col" class="px-6 py-3">Email</th>
                            <th scope="col" class="px-6 py-3">Role</th>
                            <th scope="col" class="px-6 py-3 text-center">Created On</th>
                            <th scope="col" class="px-6 py-3 text-center">Updated On</th>
                            <th scope="col" class="px-6 py-3 text-center">Action</th>
                        </tr>
                    </thead>

                    <tbody v-if="!isUserFound" class="bg-Cream">
                        <tr v-for="(user, index) in users" :key="index" class="ann-item">
                            <th scope="row" class="px-6 py-4 font-medium whitespace-nowrap dark:text-white">
                                {{ ++index }}
                            </th>
                            <td class="ann-username px-6 py-4 w-1/6 break-all">
                                {{ user.username }}
                            </td>
                            <td class="ann-name px-6 py-4">
                                {{ user.name }}
                            </td>
                            <td class="ann-email px-6 py-4">
                                {{ user.email }}
                            </td>
                            <td class="ann-role px-6 py-4">
                                {{ user.role }}
                            </td>
                            <td class="ann-created-on px-6 py-4 text-center justify-items-center">
                                {{ changeDateTimeFormat(user.createdOn) }}
                            </td>
                            <td class="ann-updated-on px-6 py-4 text-center justify-items-center">
                                {{ changeDateTimeFormat(user.updatedOn) }}
                            </td>
                            <td class="px-6 py-4 text-center">
                                <router-link :to="{
                                    name: 'userEditDetail',
                                    params: { id: user.id },
                                }"><button
                                        class="ann-button font-bold shadow-md hover:bg-ButtonViewHover rounded-full bg-DarkGreen px-5 py-2">
                                        edit
                                    </button>
                                </router-link>
                                <button
                                    class="ann-button font-bold shadow-md hover:bg-ButtonDeleteHover rounded-full bg-DarkRed px-5 py-2 ml-2"
                                    @click="deleteUserById(user.id)">
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