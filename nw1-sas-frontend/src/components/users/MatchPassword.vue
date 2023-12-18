<script setup>
import { ref } from "vue";
import Menubar from "../Navbar.vue";
import { matchPassword } from "../../composable/users/matchPassword.js";


const userObj = ref({
    username: "",
    password: ""
})
const showMessage = ref(false)
const divShowMassage = ref(false)

const matchOrNotButton = async () => {
    showMessage.value = await matchPassword(userObj.value)
    divShowMassage.value = true
}


</script>
 
<template>
    <div class="w-full min-h-screen max-h-full bg-Background">
        <div class="flex font-bold py-7 items-center justify-center bg-LightBlue text-BlueFonts drop-shadow-lg">
            <h1 class="drop-shadow-lg xs:text-3xl sm:text-3xl md:text-4xl text-sm">Match Password</h1>
            <Menubar class="items-center z-50 justify-center md:text-lg text-xs" />
        </div>
        <div class="justify-center mx-5">
            <div v-if="divShowMassage" class="mx-0 my-10 flex justify-center w-full">
                <div class="ann-message border w-96  text-center border-DarkGreen"
                    :class="showMessage.status ? 'text-DarkGreen border-DarkGreen' : 'text-DarkRed border-DarkRed'">
                    {{ showMessage.message }}
                </div>
            </div>
            <div class="mx-0 my-10 flex justify-center w-full">
                <div class="bg-LightBlue w-96 h-64 rounded-2xl py-9 px-9">
                    <div class="mb-3">
                        <label class="font-medium md:text-sm text-xs">Username</label><br />
                        <input v-model.trim="userObj.username"
                            class="ann-username bg-InputColor shadow-md h-8 w-full rounded-lg md:text-base text-xs"
                            type="text" />
                    </div>
                    <div class="">
                        <label class="font-medium md:text-sm text-xs">Password</label><br />
                        <input v-model.trim="userObj.password"
                            class="ann-password bg-InputColor shadow-md h-8 w-full rounded-lg md:text-base text-xs"
                            type="password" />
                    </div>
                    <div class="w-full flex justify-center mt-3">
                        <button @click="matchOrNotButton" class="ann-button md:text-lg text-sm shadow-md rounded-lg bg-DarkGreen px-4 py-2 mt-5 font-bold hover:bg-ButtonViewHover">
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