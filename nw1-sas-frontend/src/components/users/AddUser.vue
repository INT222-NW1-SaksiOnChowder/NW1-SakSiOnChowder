<script setup>
import { getUsers } from "../../composable/users/getUser.js";
import { ref, onMounted, watchEffect } from "vue";
import { createUser } from "../../composable/users/addUser.js";
import { useRouter } from "vue-router";

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
const checkConfirmPassword = ref()
const confirmPassword = ref('')

onMounted(async () => {
    listUser.value = await getUsers();
    listUser.value = await getUsers();
});

watchEffect(() => { 
    if (confirmPassword.value === userObj.value.password && confirmPassword.value.length > 0) {
        confirmPasswordMessage.value = 'Password match'
        checkConfirmPassword.value = true
    } else {
        confirmPasswordMessage.value = 'The password DOES NOT match'
        checkConfirmPassword.value = false
    }
})

const save = async (event) => {
    event.preventDefault();
    userNameMessage.value = ''
    nameMessage.value = ''
    passwordMessage.value = ''
    emailMessage.value = ''
    const res = ref(true)
        res.value = await createUser(userObj.value)
        if (!res.value) {
            res.value = await createUser(userObj.value)
        }
        if (res.value !== true) {
            for (const err of res.value) {
                switch (err.field) {
                    case "username":
                        userNameMessage.value = err.errorMessage
                        break
                    case "password":
                        if (userObj.value.password.length >= 8 && userObj.value.password.length <= 14 ) {
                            passwordMessage.value = err.errorMessage
                        }else{
                            passwordMessage.value = 'Password size must be between 8 and 14'
                        }
                        break
                    case "name":
                        nameMessage.value = err.errorMessage
                        break
                    case "email":
                        emailMessage.value = err.errorMessage
                        break
                }
            }
        }
        if (userNameMessage.value === '' && passwordMessage.value === ''
            && nameMessage.value === '' && emailMessage.value === '') {
            router.push({ name: 'userManagement' })
        }
}

</script>
 
<template>
    <div class="flex justify-center w-full min-h-screen max-h-full bg-Background">
        <div class="w-4/5">
            <div class="lg:mx-32">
                <div class="rounded-full shadow-md bg-DarkBlue inline-block mt-3 mb-8">
                    <h1 class="text-BlueFonts py-1 px-2 lg:text-2xl text-lg lg:px-5 lg:py-5 font-bold">
                        User Detail:
                    </h1>
                </div>
                <form @submit="save">
                    <!-- Username -->
                    <div class="bg-LightBlue rounded-2xl text-sm lg:text-base lg:py-9 py-2 px-5 lg:px-28">

                        <div class="my-5">
                            <div class="flex justify-between">
                                <label class="font-bold">Username</label>
                                <p class="font-semibold text-gray-600">remaining: {{ 45 - userObj.username.length }}</p>
                            </div>
                            <input maxlength="45" class="ann-username bg-InputColor drop-shadow-md h-8 w-full rounded-lg"
                                required type="text" v-model.trim="userObj.username">
                            <p class="ann-error-username ml-2 text-xs text-DarkRed">{{ userNameMessage }}</p>
                        </div>

                        <!-- Password -->
                        <div class="my-5">
                            <div class="flex justify-between">
                                <label class="font-bold">Password</label>
                                <p class="font-semibold text-gray-600">remaining: {{ 14 - userObj.password.length }}</p>
                            </div>
                            <input maxlength="14" class="ann-password bg-InputColor drop-shadow-md h-8 w-full rounded-lg"
                                required type="password" v-model.trim="userObj.password">
                            <p class="ann-error-password ml-2 text-xs text-DarkRed">{{ passwordMessage }}</p>
                        </div>

                        <!--Confirm Password -->
                        <div class="my-5">
                            <div class="flex justify-between">
                                <label class="font-bold">Confirm Password</label>
                                <p class="font-semibold text-gray-600">remaining: {{ 14 - confirmPassword.length }}</p>
                            </div>
                            <input maxlength="14"
                                class="ann-confirm-password bg-InputColor drop-shadow-md h-8 w-full rounded-lg" required
                                type="password" v-model.trim="confirmPassword">
                            <p class="ann-error-password ml-2 text-xs" :class="checkConfirmPassword
                                ? 'text-DarkGreen font-semibold'
                                : 'text-DarkRed font-semibold'
                                ">{{ confirmPasswordMessage }}</p>
                        </div>

                        <!-- Name -->
                        <div class="my-5">
                            <div class="flex justify-between">
                                <label class="font-bold">Name</label><br>
                                <p class="font-semibold text-gray-600">remaining: {{ 100 - userObj.name.length }}</p>
                            </div>
                            <input maxlength="100" class="ann-name bg-InputColor drop-shadow-md h-8 w-full rounded-lg"
                                required type="text" v-model.trim="userObj.name">
                            <p class="ann-error-name ml-2 text-xs text-DarkRed">{{ nameMessage }}</p>
                        </div>

                        <!-- Email -->
                        <div class="my-5">
                            <div class="flex justify-between">
                                <label class="font-bold">Email</label><br>
                                <p class="font-semibold text-gray-600">remaining: {{ 150 - userObj.email.length }}</p>

                            </div>
                            <input maxlength="150" class="ann-email bg-InputColor drop-shadow-md h-8 w-full rounded-lg"
                                required type="email" v-model.trim="userObj.email">
                            <p class="ann-error-email ml-2 text-xs text-DarkRed">{{ emailMessage }}</p>
                        </div>

                        <!-- Role -->
                        <div class="my-5">
                            <label class="font-bold">Role</label><br>
                            <select class="ann-role drop-shadow-md bg-InputColor h-8 w-2/5 rounded-lg"
                                v-model="userObj.role">
                                <option value="admin">admin</option>
                                <option value="announcer">announcer</option>
                            </select>
                        </div>


                        <div class="my-5 text-center">
                            <button type="submit"
                                class="ann-button ml-5 shadow-md font-bold rounded-full px-5 py-2 buttonEdit bg-DarkGreen hover:bg-ButtonViewHover">
                                Save</button>
                            <!-- :disabled="checkAnnouncement"
                            :style="checkAnnouncement ? 'opacity: 0.5; background-color:lightgray; cursor: not-allowed;' : 'opacity: 1;'" -->
                            <router-link :to="{ name: 'userManagement' }">
                                <button
                                    class="ann-button shadow-md rounded-full bg-DarkRed px-6 py-2 ml-3 font-bold hover:bg-ButtonDeleteHover">Cancel</button></router-link>
                        </div>
                    </div>
                </form>
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