<script setup>
import { getUsers } from "../../composable/users/getUser.js";
import { ref, computed, onMounted, watchEffect, onUpdated } from "vue";
import { createUser } from "../../composable/users/addUser.js";
import { useRouter } from "vue-router";
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
    password: ""
});

const userNameMessage = ref('')
const nameMessage = ref('')
const emailMessage = ref('')
const passwordMessage = ref('')
const confirmPasswordMessage = ref('')
const checkUsernameLengthAndUnique = ref()
const checkNameLengthAndUnique = ref()
const checkEmailLengthAndUnique = ref()
const checkPasswordPattern = ref()
const checkConfirmPassword = ref()
const confirmPassword = ref('')

onMounted(async () => {
    listUser.value = await getUsers();
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
    if (userObj.value.password.length >= 0) {
        passwordMessage.value = validateUserInput(userObj.value, 'password', listUser.value).message
        checkPasswordPattern.value = validateUserInput(userObj.value, 'password', listUser.value).boolean
    }

    if (confirmPassword.value === userObj.value.password && confirmPassword.value.length > 0) {
        confirmPasswordMessage.value = 'Password match'
        checkConfirmPassword.value = true
    } else {
        confirmPasswordMessage.value = 'The password DOES NOT match'
        checkConfirmPassword.value = false
    }
})

const save = async (user) => {
    const status = ref(true)
    status.value = await createUser(user);
    if (status.value) {
        router.push({ name: 'userManagement' })
    }

}

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

                <!-- Username -->
                <div class="bg-LightBlue rounded-2xl py-9 px-28">
                    <div class="my-5">
                        <div class="flex justify-between">
                            <label class="font-bold">Username</label>
                            <p :class="checkUsernameLengthAndUnique
                                ? 'text-DarkGreen font-semibold'
                                : 'text-DarkRed font-semibold'
                                ">{{ userObj.username.length }} / 45</p>
                        </div>
                        <input maxlength="45" class="ann-username bg-InputColor drop-shadow-md h-8 w-full rounded-lg" required
                            type="text" v-model.trim="userObj.username" :class="checkUsernameLengthAndUnique
                                ? 'border-2 border-DarkGreen'
                                : 'border-2'">
                        <p class="ann-error-username ml-2 text-xs" :class="checkUsernameLengthAndUnique
                            ? 'text-DarkGreen '
                            : 'text-DarkRed'">{{ userNameMessage }}</p>
                    </div>

                    <!-- Password -->
                    <div class="my-5">
                        <div class="flex justify-between">
                            <label class="font-bold">Password</label>
                            <p :class="checkPasswordPattern
                                ? 'text-DarkGreen font-semibold'
                                : 'text-DarkRed font-semibold'
                                ">{{ userObj.password.length }} / 8-14</p>
                        </div>
                        <input maxlength="14" class="ann-password bg-InputColor drop-shadow-md h-8 w-full rounded-lg" required
                            type="password" v-model.trim="userObj.password" :class="checkPasswordPattern
                                ? 'border-2 border-DarkGreen'
                                : 'border-2'">
                        <p class="ann-error-password ml-2 text-xs" :class="checkPasswordPattern
                            ? 'text-DarkGreen '
                            : 'text-DarkRed'">{{ passwordMessage }}</p>
                    </div>

                    <!--Confirm Password -->
                    <div class="my-5">
                        <div class="flex justify-between">
                            <label class="font-bold">Confirm Password</label>
                            <p :class="checkConfirmPassword
                                ? 'text-DarkGreen font-semibold'
                                : 'text-DarkRed font-semibold'
                                ">{{ confirmPassword.length }} / 8-14</p>
                        </div>
                        <input maxlength="14" class="ann-confirm-password bg-InputColor drop-shadow-md h-8 w-full rounded-lg" required
                            type="password" v-model.trim="confirmPassword" :class="checkConfirmPassword
                                ? 'border-2 border-DarkGreen'
                                : 'border-2'">
                        <p class="ann-error-password ml-2 text-xs" :class="checkConfirmPassword
                            ? 'text-DarkGreen '
                            : 'text-DarkRed'">{{ confirmPasswordMessage }}</p>
                    </div>

                    <!-- Name -->
                    <div class="my-5">
                        <div class="flex justify-between">
                            <label class="font-bold">Name</label><br>
                            <p :class="checkNameLengthAndUnique
                                ? 'text-DarkGreen font-semibold'
                                : 'text-DarkRed font-semibold'
                                ">{{ userObj.name.length }} / 100</p>
                        </div>
                        <input maxlength="100" class="ann-name bg-InputColor drop-shadow-md h-8 w-full rounded-lg" required
                            type="text" v-model.trim="userObj.name" :class="checkNameLengthAndUnique
                                ? 'border-2 border-DarkGreen'
                                : 'border-2'">
                        <p class="ann-error-name ml-2 text-xs" :class="checkNameLengthAndUnique
                            ? 'text-DarkGreen'
                            : 'text-DarkRed'">{{ nameMessage }}</p>
                    </div>

                    <!-- Email -->
                    <div class="my-5">
                        <div class="flex justify-between">
                            <label class="font-bold">Email</label><br>
                            <p :class="checkEmailLengthAndUnique
                                ? 'text-DarkGreen font-semibold'
                                : 'text-DarkRed font-semibold'
                                ">{{ userObj.email.length }} / 150</p>

                        </div>
                        <input maxlength="150" class="ann-email bg-InputColor drop-shadow-md h-8 w-full rounded-lg" required
                            type="email" v-model.trim="userObj.email" :class="checkEmailLengthAndUnique
                                ? 'border-2 border-DarkGreen'
                                : 'border-2'">
                        <p class="ann-error-email ml-2 text-xs" :class="checkEmailLengthAndUnique
                            ? 'text-DarkGreen'
                            : 'text-DarkRed'">{{ emailMessage }}</p>
                    </div>

                    <!-- Role -->
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