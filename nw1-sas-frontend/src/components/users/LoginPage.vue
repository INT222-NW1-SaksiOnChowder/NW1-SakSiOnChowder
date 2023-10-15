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
// const currentRole = role()

onMounted(async () => {

});

const matchOrNotButton = async () => {
    result.value = await getToken(userObj.value)
    const userDetail = await JSON.parse(localStorage.getItem("userDetail"))
    // await getUsers()
    divShowMassage.value = true
    console.log(result.value.status);
    if (result.value.status === true && userDetail.role === "ROLE_admin") {
        router.push({ name: "userManagement" })
    } else if(result.value.status === true && userDetail.role !== "ROLE_admin") {
        router.push({name: "userViewAnnouncement"})
    }
}


</script>
 
<template>
    <div class="flex w-full min-h-screen max-h-full bg-red-300">
        <div class="w-2/5 bg-cyan-100 rounded-l-xl my-5 ml-5">
            <div class="flex w-full h-2/3 justify-center">
                <p class="text-5xl font-bold my-auto text-BlueFonts drop-down text-center">Welcome to SAS</p>
            </div>
            <div class="flex w-full h-1/5">
                <img src="../icones/annIcon.png" alt="announcement-icon" width="200" height="200"
                    class="m-auto animate-bounce">
            </div>
        </div>

        <div class="flex w-3/5 justify-center bg-orange-100 rounded-r-xl my-5 mr-5">
            <div class="w-3/4 m-auto">
                <div v-if="divShowMassage" class="ann-message border rounded-md text-xl p-2 mb-5 flex w-3/5 mx-auto h-min justify-center border-DarkGreen"
                    :class="result.status ? 'text-DarkGreen border-DarkGreen' : 'text-DarkRed border-DarkRed'">
                    {{ result.message }}
                </div>
                <div class="bg-white bg-opacity-80 border-2 w-full rounded-2xl p-9">
                    <h1 class="text-2xl font-bold mb-4">Login</h1>
                    <div class="mb-3">
                        <label class="font-bold">Username</label><br />
                        <input v-model.trim="userObj.username" @keyup.enter="matchOrNotButton"
                            class="ann-username bg-InputColor drop-shadow-md h-8 w-full rounded-lg" type="text" />
                    </div>
                    <div>
                        <label class="font-bold">Password</label><br />
                        <input v-model.trim="userObj.password" @keyup.enter="matchOrNotButton"
                            class="ann-password bg-InputColor drop-shadow-md h-8 w-full rounded-lg" type="password" />
                    </div>
                    <button @click="matchOrNotButton"
                        class="ann-button shadow-md rounded-lg bg-pink-200 px-4 py-2 mt-5 font-bold hover:bg-cyan-500 hover:text-white">
                        Login
                    </button>
                </div>
            </div>
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
</style>