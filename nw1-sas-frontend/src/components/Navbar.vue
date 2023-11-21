<script setup>
import { clearToken } from '../composable/users/clearToken';
import { role } from '../stores/role';
import {ref} from 'vue'

// const currentRole = role()
const isShowAllNav = ref(false)
const currentRole = role()

if (currentRole.currentRole === "ROLE_admin") {
    isShowAllNav.value = true
}

</script>
 
<template>
    <div class="flex flex-col w-1/6 max-h-full bg-Cream border-black border-r-2">
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
    </div>
</template>
 
<style scoped></style>