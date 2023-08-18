<script setup>
import { ref } from "vue";
import { createUser } from "../composable/addUser.js";
import { useRouter } from "vue-router";
import Menubar from "./navbar.vue"

const router = useRouter();

const userObj = ref({
    id: "",
    username: "",
    name: "",
    email: "",
    role: "announcer",
});

const save = async (user) => {
    await createUser(user)
    router.push({ name: 'userManagement' })
}

</script>
 
<template>
    <div class="flex w-full min-h-screen max-h-full bg-Background">
        <Menubar/>
        <div class="w-3/4">
            <div class="mx-32">
                <div class="rounded-full shadow-md bg-DarkBlue inline-block mt-3 mb-8">
                    <h1 class="text-BlueFonts text-2xl px-5 py-5 font-bold">
                        User Detail:
                    </h1>
                </div>
                <div class="bg-LightBlue rounded-2xl py-9 px-28">
                    <div class="my-5">
                        <label class="font-bold">Username</label><br>
                        <input maxlength="200" class="ann-title bg-InputColor drop-shadow-md h-8 w-full rounded-lg"
                            type="text" v-model.trim="userObj.username">
                    </div>
                    <div class="my-5">
                        <label class="font-bold">Name</label><br>
                        <input maxlength="200" class="ann-title bg-InputColor drop-shadow-md h-8 w-full rounded-lg"
                            type="text" v-model.trim="userObj.name">
                    </div>
                    <div class="my-5">
                        <label class="font-bold">Email</label><br>
                        <input maxlength="200" class="ann-title bg-InputColor drop-shadow-md h-8 w-full rounded-lg"
                            type="text" v-model.trim="userObj.email">
                    </div>
                    <div class="my-5">
                        <label class="font-bold">Role</label><br>
                        <select class="ann-category drop-shadow-md bg-InputColor h-8 w-2/5 rounded-lg"
                            v-model="userObj.role">
                            <option value="admin">admin</option>
                            <option value="announcer">announcer</option>
                        </select>
                    </div>

                    <div class="my-5 text-center">
                        <button :disabled="checkAnnouncement"
                            class="ann-button ml-5 shadow-md font-bold rounded-full px-5 py-2 buttonEdit bg-DarkGreen hover:bg-ButtonViewHover"
                            :style="checkAnnouncement ? 'opacity: 0.5; background-color:lightgray; cursor: not-allowed;' : 'opacity: 1;'"
                            @click="save(userObj)">Save</button>
                        <router-link :to="{ name: 'userManagement' }">
                            <button
                                class="ann-button shadow-md rounded-full bg-DarkRed px-6 py-2 ml-3 font-bold hover:bg-ButtonDeleteHover">Cancel</button></router-link>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>
 
<style scoped></style>