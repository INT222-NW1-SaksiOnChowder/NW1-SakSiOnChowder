<script setup>
import { clearToken } from '../composable/users/clearToken';
import { ref, watchEffect } from 'vue'
import Logout from "./icones/Logout.vue"
import { userDetailStore } from '../composable/users/userDetailStore.js';
import Burger from "./icones/Burger.vue"
import { useRouter } from 'vue-router';
import Swal from 'sweetalert2'

const isShowAllNav = ref(false)
const router = useRouter()
const userDetails = ref(userDetailStore())
const isShowMenuBar = ref(false)

if (userDetails.value.role === "ROLE_admin") {
    isShowAllNav.value = true
}

const toggleMenuBar = () => {
    isShowMenuBar.value = !isShowMenuBar.value
}

const closeMenuBar = () => {
    isShowMenuBar.value = false
}

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
            isShowMenuBar.value = false
            router.push({ name: 'userViewAnnouncement' }).then(() => {
                router.go(0)
            })
        }
    });
}

</script>
 
<template>
    <div class="absolute lg:right-12 right-5">
        <button @click="toggleMenuBar" id="dropdownNavbarLink" data-dropdown-toggle="dropdownNavbar"
            class="flex items-center justify-between w-full py-2 px-3 ">
            <Burger />
        </button>
        <!-- Dropdown menu -->
        <div id="dropdownNavbar" v-show="isShowMenuBar"
            class="z-13 text-sm lg:right-12 right-5 absolute font-normal rounded-xl bg-white divide-y divide-gray-100 shadow w-44">
            <div class="flex flex-col items-center w-full">
                <router-link :to="{ name: 'userViewAnnouncement' }" @click="closeMenuBar"
                    class="py-2 w-full text-center hover:bg-gray-200/60">Announcement(Viewer)
                </router-link>
                <router-link :to="{ name: 'announcements' }" @click="closeMenuBar"
                    class="py-2 w-full text-center hover:bg-gray-200/60">Announcement
                </router-link>
                <router-link :to="{ name: 'userManagement' }" @click="closeMenuBar" v-if="isShowAllNav"
                    class="py-2 w-full text-center hover:bg-gray-200/60">User
                </router-link>
                <router-link :to="{ name: 'matchPassword' }" @click="closeMenuBar" v-if="isShowAllNav"
                    class="py-2 w-full text-center hover:bg-gray-200/60">Match
                    Password
                </router-link>
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