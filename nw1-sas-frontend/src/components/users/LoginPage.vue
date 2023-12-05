<script setup>
import { ref, computed, onMounted, watchEffect, onUpdated } from "vue";
import { useRouter } from "vue-router";
import { matchPassword } from "../../composable/users/matchPassword.js";
import { getToken } from "../../composable/users/getToken.js";
import { role } from '../../stores/role';
import { getUser, getUsers } from "../../composable/users/getUser";

const userObj = ref({
    username: "",
    password: ""
})
const result = ref(false)
const divShowMassage = ref(false)
const router = useRouter();
const currentRole = role()

onMounted(async () => {

});

const matchOrNotButton = async () => {
    result.value = await getToken(userObj.value)
    // await getUsers()
    divShowMassage.value = true
    console.log(result.value.status);
    if (result.value.status === true && currentRole.currentRole === "ROLE_admin") {
        router.push({ name: "userManagement" })
    } else if (result.value.status === true && currentRole.currentRole !== "ROLE_admin") {
        router.push({ name: "userViewAnnouncement" })
    }
}


</script>
 
<template>
    <div class="absolute w-1/6 m-5">
            <img src="../../assets/images/logo-sit-long.png"  alt="SIT Logo">
        </div>
    <div class="bg-image w-screen h-screen flex  items-center justify-center">
        <div
            class="m-5 bg-opacity-75 shadow-xl bg-gray-300 w-96 h-3/6 rounded-xl  ml-5 flex flex-col items-center justify-center">
            <div class="mb-5">
                <a href="https://www.sit.kmutt.ac.th/"><img src="../icones/logo-SIT.png" height="90" width="200" alt="SIT Logo"></a>
            </div>
            <div class="w-4/5 flex flex-col justify-center">
                <label for="helper-text"
                    class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Username</label>
                <input v-model.trim="userObj.username" type="text" aria-describedby="helper-text-explanation"
                    class="mb-5 w-full opacity-90 bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block p-2.5  dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
                    placeholder="username">
                <label for="helper-text"
                    class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Password</label>
                <input v-model.trim="userObj.password" type="password" aria-describedby="helper-text-explanation" @keydown.enter="matchOrNotButton"
                    class="mb-5 w-full opacity-90 bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block p-2.5  dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
                    placeholder="password">
            </div>

            <button @click="matchOrNotButton" type="button"
                class="text-white mt-5 bg-black hover:bg-gray-700 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center inline-flex items-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800">
                Sign in
                <svg class="rtl:rotate-180 w-3.5 h-3.5 ms-2" aria-hidden="true" xmlns="http://www.w3.org/2000/svg"
                    fill="none" viewBox="0 0 14 10">
                    <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                        d="M1 5h12m0 0L9 1m4 4L9 9" />
                </svg></button>
        </div>

    </div>
</template>
 
<style scoped>
.green-text {
    color: green;
}

.red-text {
    color: red;
}

.bg-image {
    background-image: url('../../assets/images/background.jpg');
    background-size: cover;
    background-position: bottom;
}
</style>