<script setup>
import { clearToken } from '../composable/users/clearToken';
import { ref, watchEffect } from 'vue'
import Logout from "./icones/Logout.vue"
import { userDetailStore } from '../composable/users/userDetailStore.js';
import Burger from "./icones/Burger.vue"
import { useRouter } from 'vue-router';
import Swal from 'sweetalert2'
// const currentRole = role()
const isShowAllNav = ref(false)
// const currentRole = role()
const router = useRouter()
const userDetails = ref(userDetailStore())
console.log(userDetails.value);
if (userDetails.value.role === "ROLE_admin") {
    isShowAllNav.value = true
}

const isShowMenuBar = ref(false)
const toggleMenuBar = () => {
    isShowMenuBar.value = !isShowMenuBar.value
    // console.log(isShowMenuBar.value);
}
// const result = ref()
// watchEffect(() => {
//     if (result.value) {
//         router.push({ name: 'userViewAnnouncement' })
//     }
//     console.log(result.value);
// }
// )

const clearTokenButton = () => {
    Swal.fire({
        title: "Are you sure you want to sign out?",
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: "#999B86",
        cancelButtonColor: "#BD6666",
        confirmButtonText: "Yes, Sign out"
    }).then((result) => {
        if (result.isConfirmed) {
            Swal.fire({
                title: "Signed Out",
                text: "You are now signed out",
                icon: "success"
            });
            clearToken()
            router.push({name: 'userViewAnnouncement'})
        }
    });
}

</script>
 
<template>
    <!-- <div class="flex flex-col w-1/6 max-h-full bg-Cream border-black border-r-2">
        <h1 class="flex pl-5 bg-DarkBlue py-5 text-3xl font-bold border-black border-b-2">SAS</h1>
        <router-link :to="{ name: 'userViewAnnouncement' }"
            class="flex pl-5 py-5 border-black border-b-2 font-bold hover:bg-LightBlue">
            Announcement(Viewer)
        </router-link>
        <router-link :to="{ name: 'announcements' }"
            class="flex pl-5 py-5 border-black border-b-2 font-bold hover:bg-LightBlue">
            Announcement
        </router-link>
        <router-link :to="{ name: 'userManagement' }" v-if="isShowAllNav"
            class="flex pl-5 py-5 border-black border-b-2 font-bold hover:bg-LightBlue">
            User
        </router-link>
        <router-link :to="{ name: 'matchPassword' }" v-if="isShowAllNav"
            class="flex pl-5 py-5 border-black border-b-2 font-bold hover:bg-LightBlue">
            Match Password
        </router-link>
        <router-link :to="{ name: 'userViewAnnouncement' }"
            class="flex pl-5 py-5 border-black border-b-2 font-bold hover:bg-LightBlue" @click="clearToken">
            Sign Out
        </router-link>
    </div> -->
    <div class="absolute right-12">
        <button @click="toggleMenuBar" id="dropdownNavbarLink" data-dropdown-toggle="dropdownNavbar"
            class="flex items-center justify-between w-full py-2 px-3 ">
            <Burger />
        </button>
        <!-- Dropdown menu -->
        <div id="dropdownNavbar" v-show="isShowMenuBar"
            class="z-13 text-sm right-12 absolute font-normal rounded-xl bg-white divide-y divide-gray-100 shadow w-44">
            <div class="flex flex-col items-center w-full">
                <router-link :to="{ name: 'userViewAnnouncement' }"
                    class="py-2 w-full text-center hover:bg-gray-200/60">Announcement(Viewer)
                </router-link>
                <router-link :to="{ name: 'announcements' }"
                    class="py-2 w-full text-center hover:bg-gray-200/60">Announcement
                </router-link>
                <router-link :to="{ name: 'userManagement' }" v-if="isShowAllNav"
                    class="py-2 w-full text-center hover:bg-gray-200/60">User
                </router-link>
                <router-link :to="{ name: 'matchPassword' }" v-if="isShowAllNav"
                    class="py-2 w-full text-center hover:bg-gray-200/60">Match
                    Password
                </router-link>
                <!-- <router-link :to="{ name: 'userViewAnnouncement' }" @click="clearToken"
                    class="bg-DarkRed justify-center space-x-2 py-2 w-full rounded-b-xl flex items-center">
                    <p>Sign out</p>
                    <Logout />
                </router-link> -->
                <button @click="clearTokenButton"
                    class="bg-DarkRed justify-center space-x-2 py-2 w-full rounded-b-xl flex items-center">
                    <p>Sign out</p>
                    <Logout />
                </button>
            </div>
        </div>
    </div>
</template>
 
<style scoped></style>