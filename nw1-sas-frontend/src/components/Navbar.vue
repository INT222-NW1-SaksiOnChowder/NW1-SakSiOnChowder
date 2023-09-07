<script setup>

</script>
 
<template>
    <div class="flex flex-col w-1/5 max-h-full bg-Cream border-solid border-r-2">
        <h1 class="flex pl-5 bg-DarkBlue py-5 text-3xl font-bold border-solid border-b-2">SAS</h1>
        <router-link :to="{ name: 'announcements' }"
            class="flex pl-5 py-5 border-solid border-b-2 font-bold hover:bg-LightBlue">
            Announcement
        </router-link>
        <router-link :to="{ name: 'userManagement' }"
            class="flex pl-5 py-5 border-solid border-b-2 font-bold hover:bg-LightBlue">
            User
        </router-link>
        <router-link :to="{ name: 'matchPassword' }"
            class="flex pl-5 py-5 border-solid border-b-2 font-bold hover:bg-LightBlue">
            Match Password
        </router-link>
    </div>
</template>
 
<style scoped></style>