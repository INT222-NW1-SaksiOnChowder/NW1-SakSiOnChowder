<script setup>
import { getUsers } from "../../composable/users/getUser.js";
import { ref, computed, onMounted } from "vue";
import { createUser } from "../../composable/users/addUser.js";
import { useRouter } from "vue-router";
import { validateUsernameNameEmail } from "../../composable/users/validateUser.js";
import Menubar from "../Navbar.vue";

const router = useRouter();
const listUser = ref()

const userNameMassage = ref('')
const nameMassage = ref('')
const emailMassage = ref('')

onMounted(async () => {
    listUser.value = await getUsers();
});

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

const checkUsernameLengthAndUnique = computed(() => {
    userNameMassage.value = validateUsernameNameEmail(userObj.value, 'username', listUser.value).message
    return validateUsernameNameEmail(userObj.value, 'username', listUser.value).boolean
})

const checkNameLengthAndUnique = computed(() => {
    nameMassage.value = validateUsernameNameEmail(userObj.value, 'name', listUser.value).message
    return validateUsernameNameEmail(userObj.value, 'name', listUser.value).boolean
})

const checkEmailLengthAndUnique = computed(() => {
    emailMassage.value = validateUsernameNameEmail(userObj.value, 'email', listUser.value).message
    return validateUsernameNameEmail(userObj.value, 'email', listUser.value).boolean
})


</script>
 
<template>
    <div class="flex w-full min-h-screen max-h-full bg-Background">
        <Menubar />
        <div class="w-3/4">
            <div class="mx-32">
                <div class="rounded-full shadow-md bg-DarkBlue inline-block mt-3 mb-8">
                    <h1 class="text-BlueFonts text-2xl px-5 py-5 font-bold">
                        User Detail:
                    </h1>
                </div>
                <div class="bg-LightBlue rounded-2xl py-9 px-28">
                    <div class="my-5">
                        <div class="flex justify-between">
                            <label class="font-bold">Username</label>
                            <p :class="checkUsernameLengthAndUnique
                                ? 'text-DarkGreen font-semibold'
                                : 'text-DarkRed font-semibold'
                                ">{{ userObj.username.length }} / 45</p>
                        </div>
                        <input maxlength="200" class="ann-username bg-InputColor drop-shadow-md h-8 w-full rounded-lg"
                            type="text" v-model.trim="userObj.username" :class="checkUsernameLengthAndUnique
                                ? 'border-2 border-DarkGreen'
                                : 'border-2'">
                        <p class="ml-2 text-xs" :class="checkUsernameLengthAndUnique
                                ? 'text-DarkGreen '
                                : 'text-DarkRed'">{{ userNameMassage }}</p>
                    </div>
                    <div class="my-5">
                        <div class="flex justify-between">
                            <label class="font-bold">Name</label><br>
                            <p :class="checkNameLengthAndUnique
                                ? 'text-DarkGreen font-semibold'
                                : 'text-DarkRed font-semibold'
                                ">{{ userObj.name.length }} / 100</p>
                        </div>
                        <input maxlength="200" class="ann-name bg-InputColor drop-shadow-md h-8 w-full rounded-lg"
                            type="text" v-model.trim="userObj.name" :class="checkNameLengthAndUnique
                                ? 'border-2 border-DarkGreen'
                                : 'border-2'">
                        <p class="ml-2 text-xs" :class="checkNameLengthAndUnique
                                ? 'text-DarkGreen'
                                : 'text-DarkRed'">{{ nameMassage }}</p>
                    </div>
                    <div class="my-5">
                        <div class="flex justify-between">
                            <label class="font-bold">Email</label><br>
                            <p :class="checkEmailLengthAndUnique
                                ? 'text-DarkGreen font-semibold'
                                : 'text-DarkRed font-semibold'
                                ">{{ userObj.email.length }} / 150</p>

                        </div>
                        <input maxlength="200" class="ann-email bg-InputColor drop-shadow-md h-8 w-full rounded-lg"
                            type="text" v-model.trim="userObj.email" :class="checkEmailLengthAndUnique
                                ? 'border-2 border-DarkGreen'
                                : 'border-2'">
                        <p class="ml-2 text-xs" :class="checkEmailLengthAndUnique
                                ? 'text-DarkGreen'
                                : 'text-DarkRed'">{{ emailMassage }}</p>
                    </div>
                    <div class="my-5">
                        <label class="font-bold">Role</label><br>
                        <select class="ann-role drop-shadow-md bg-InputColor h-8 w-2/5 rounded-lg" v-model="userObj.role">
                            <option value="admin">admin</option>
                            <option value="announcer">announcer</option>
                        </select>
                    </div>

                    <div class="my-5 text-center">
                        <button
                            class="ann-button ml-5 shadow-md font-bold rounded-full px-5 py-2 buttonEdit bg-DarkGreen hover:bg-ButtonViewHover"
                            @click="save(userObj)">
                            Save</button>
                        <!-- :disabled="checkAnnouncement"
                            :style="checkAnnouncement ? 'opacity: 0.5; background-color:lightgray; cursor: not-allowed;' : 'opacity: 1;'" -->
                        <router-link :to="{ name: 'userManagement' }">
                            <button
                                class="ann-button shadow-md rounded-full bg-DarkRed px-6 py-2 ml-3 font-bold hover:bg-ButtonDeleteHover">Cancel</button></router-link>
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