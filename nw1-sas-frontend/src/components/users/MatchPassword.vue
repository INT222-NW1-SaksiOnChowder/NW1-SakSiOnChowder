<script setup>
import { ref, computed, onMounted, watchEffect, onUpdated } from "vue";
import { useRouter } from "vue-router";
import Menubar from "../Navbar.vue";
import { matchPassword } from "../../composable/users/matchPassword.js";


const userObj = ref({
    username : "",
    password : ""
})
const showMessage = ref(false)
const divShowMassage = ref(false)

const matchOrNotButton = async() => {
    showMessage.value = await matchPassword(userObj.value)
    divShowMassage.value = true
} 


</script>
 
<template>
    <div>
        <div class="flex w-full min-h-screen max-h-full bg-Background">
            <Menubar />
            <div class="w-4/5 justify-center">
                <div v-if="divShowMassage" class="mx-0 my-10 flex justify-center w-full">
                    <div class="ann-message border w-96  text-center border-DarkGreen" :class="showMessage.status ? 'text-DarkGreen border-DarkGreen' : 'text-DarkRed border-DarkRed'">
                        {{showMessage.message}}
                    </div>
                </div>
                <div class="mx-0 my-10 flex justify-center w-full">
                    <div class="bg-LightBlue w-96 h-72 rounded-2xl py-9 px-9">
                        <h1 class="text-2xl font-bold mb-4">Match Password</h1>
                        <div class="mb-3">
                            <label class="font-bold">Username</label><br />
                            <input v-model.trim="userObj.username" class="ann-username bg-InputColor drop-shadow-md h-8 w-full rounded-lg"
                                type="text" />
                        </div>
                        <div class="">
                            <label class="font-bold">Password</label><br />
                            <input v-model.trim="userObj.password" class="ann-password bg-InputColor drop-shadow-md h-8 w-full rounded-lg"
                                type="password" />
                        </div>
                        <button @click="matchOrNotButton"
                            class="ann-button shadow-md rounded-lg bg-DarkGreen px-4 py-2 mt-5 font-bold hover:bg-ButtonViewHover">
                            Match or not
                        </button>
                    </div>
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