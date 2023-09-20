<script setup>
import { getUser } from "../../composable/users/getUser.js";
import { getUsers } from "../../composable/users/getUser.js";
import { ref, onMounted, computed, watchEffect } from "vue";
import { changeDateTimeFormat } from "../../composable/changeFormatDate.js";
import { useRouter, useRoute } from "vue-router";
import { updateUser } from "../../composable/users/editUser.js";
import { validateUserInput } from "../../composable/users/validateUser.js";
import Menubar from "../Navbar.vue";

const router = useRouter();
const listUser = ref()
const userObj = ref({
    id: "",
    username: "",
    name: "",
    email: "",
    role: "announcer",
});
const oldUserData = ref('')
const userNameMessage = ref('')
const nameMessage = ref('')
const emailMessage = ref('')

const checkUsernameLengthAndUnique = ref()
const checkNameLengthAndUnique = ref()
const checkEmailLengthAndUnique = ref()

onMounted(async () => {
    const route = useRoute();
    listUser.value = await getUsers();
    userObj.value = await getUser(route.params.id);
    oldUserData.value = await getUser(route.params.id);
});

watchEffect(() => {
    if (userObj.value.username.length >= 0) {
        userNameMessage.value = validateUserInput(userObj.value, 'username', listUser.value).message
        checkUsernameLengthAndUnique.value = validateUserInput(userObj.value, 'username', listUser.value).boolean
    }

    if (userObj.value.name.length >= 0) {
        nameMessage.value = validateUserInput(userObj.value, 'name', listUser.value).message
        checkNameLengthAndUnique.value = validateUserInput(userObj.value, 'name', listUser.value).boolean
    }

    if (userObj.value.email.length >= 0) {
        emailMessage.value = validateUserInput(userObj.value, 'email', listUser.value).message
        checkEmailLengthAndUnique.value = validateUserInput(userObj.value, 'email', listUser.value).boolean
    }

})

const checkUserChange = computed(() => {
    if (
        userObj.value.username === oldUserData.value.username 
            &&
        userObj.value.name === oldUserData.value.name 
            &&
        userObj.value.email === oldUserData.value.email 
            &&
        userObj.value.role === oldUserData.value.role
    ) {
        return true;
    } else {
        return false;
    }
});

const save = async () => {
    const status = ref(true)
    status.value = await updateUser(userObj.value);
    if (status.value) {
        router.push({ name: 'userManagement' })
    }
};


</script>

<template>
    <div class="flex w-full min-h-screen max-h-full bg-Background">
        <Menubar />
        <div class="w-4/5">
            <div class="mx-32">
                <div class="rounded-full shadow-md bg-DarkBlue inline-block mt-3 mb-8">
                    <h1 class="text-BlueFonts text-2xl px-5 py-5 font-bold">
                        User Detail:
                    </h1>
                </div>
                <form @submit="save">
                <div class="bg-LightBlue rounded-2xl py-9 px-28">
                    <!-- username -->
                    <div class="my-5">
                        <div class="flex justify-between">
                            <label class="font-bold">Username</label><br />
                            <p class="font-semibold text-gray-600">remaining: {{ 45 - userObj.username.length }}</p>
                        </div>
                        <input maxlength="45" class="ann-username bg-InputColor drop-shadow-md h-8 w-full rounded-lg"
                            required type="text" v-model.trim="userObj.username" :class="checkUsernameLengthAndUnique
                                ? 'border-2 border-DarkGreen'
                                : 'border-2'
                                " />
                        <p class="ann-error-username ml-2 text-xs" :class="checkUsernameLengthAndUnique ? 'text-DarkGreen' : 'text-DarkRed'">
                            {{ userNameMessage }}
                        </p>
                    </div>

                    <!-- Name -->
                    <div class="my-5">
                        <div class="flex justify-between">
                            <label class="font-bold">Name</label><br />
                            <p class="font-semibold text-gray-600">
                                remaining: {{ 100 - userObj.name.length }}
                            </p>
                        </div>
                        <input maxlength="100" class="ann-name bg-InputColor drop-shadow-md h-8 w-full rounded-lg" required
                            type="text" v-model.trim="userObj.name" :class="checkNameLengthAndUnique
                                ? 'border-2 border-DarkGreen'
                                : 'border-2'
                                " />
                        <p class="ann-error-name ml-2 text-xs" :class="checkNameLengthAndUnique ? 'text-DarkGreen' : 'text-DarkRed'
                            ">
                            {{ nameMessage }}
                        </p>
                    </div>

                    <!-- Email -->
                    <div class="my-5">
                        <div class="flex justify-between">
                            <label class="font-bold">Email</label><br />
                            <p class="font-semibold text-gray-600">
                                remaining: {{ 150 - userObj.email.length }}
                            </p>
                        </div>
                        <input maxlength="150" class="ann-email bg-InputColor drop-shadow-md h-8 w-full rounded-lg" required
                            type="email" v-model.trim="userObj.email"
                            :class="checkEmailLengthAndUnique
                                ? 'border-2 border-DarkGreen'
                                : 'border-2'
                                "/>
                        <p class="ann-error-email ml-2 text-xs" :class="checkEmailLengthAndUnique ? 'text-DarkGreen' : 'text-DarkRed'
                            ">
                            {{ emailMessage }}
                        </p>
                    </div>

                    <!-- Role -->
                    <div class="my-5">
                        <label class="font-bold">Role</label><br />
                        <select class="ann-role drop-shadow-md bg-InputColor h-8 w-2/5 rounded-lg" v-model="userObj.role">
                            <option value="admin">admin</option>
                            <option value="announcer">announcer</option>
                        </select>
                    </div>
                    <div class="my-5 flex w-3/4 justify-between">
                        <p class="font-bold">
                            Created On:
                            <span class="font-normal ann-created-on">{{
                                changeDateTimeFormat(userObj.createdOn)
                            }}</span>
                        </p>
                        <p class="font-bold">
                            Updated On:
                            <span class="font-normal ann-updated-on">{{
                                changeDateTimeFormat(userObj.updatedOn)
                            }}</span>
                        </p>
                    </div>

                    <div class="my-5 text-center">
                        <button type="submit"
                            :disabled="checkUserChange"
                            class="ann-button ml-5 shadow-md font-bold rounded-full px-5 py-2 buttonEdit bg-DarkGreen hover:bg-ButtonViewHover"
                            :style="checkUserChange ? 'opacity: 0.5; background-color:lightgray; cursor: not-allowed;' : 'opacity: 1;'">
                            save
                        </button>
                        <router-link :to="{ name: 'userManagement' }">
                            <button
                                class="ann-button shadow-md rounded-full bg-DarkRed px-6 py-2 ml-3 font-bold hover:bg-ButtonDeleteHover">
                                Cancel
                            </button></router-link>
                    </div>
                </div>
            </form>
            </div>
        </div>
    </div>
</template>

<style scoped></style>
