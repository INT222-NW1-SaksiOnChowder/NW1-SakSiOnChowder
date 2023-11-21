<script setup>
import { ref } from "vue";
// import { subScribeCategory, verifyOTP } from "../../composable/subScribeCategory.js";
// import Swal from 'sweetalert2'
// import { useRoute, useRouter } from 'vue-router';
import jwt_decode from "jwt-decode"
import { unSubScribeCategory } from '../../composable/subScribeCategory.js'
const props = defineProps({
    unSubTokenParam: {
        type: String
    }
})

// console.log(jwt_decode(props.unSubTokenParam));
const unSubScriptionObj = ref(jwt_decode(props.unSubTokenParam))
console.log(unSubScriptionObj.value);
const emits = defineEmits(["cancel"]);
// console.log(unSubScriptionToken);
const unSubScriptionButton = async () => {
    // const data = { 
    //     email: 'nw1chowder@gmail.com', 
    //     categoryId: 1 
    // }
    // console.log(data);
    // console.log(typeof (props.unSubTokenParam));
    await unSubScribeCategory(props.unSubTokenParam)
}
</script>
<template>
    <div class="relative z-10" aria-labelledby="modal-title" role="dialog" aria-modal="true">
        <div class="fixed inset-0 bg-gray-500 bg-opacity-75 transition-opacity"></div>
        <div class="fixed inset-0 z-10 w-screen overflow-y-auto">
            <div class="flex min-h-full items-end justify-center p-4 text-center sm:items-center sm:p-0">
                <div
                    class="relative transform overflow-hidden rounded-lg bg-white text-left shadow-xl transition-all sm:my-8 sm:w-full sm:max-w-lg">
                    <div class="bg-white px-4 pb-4 pt-5 sm:p-6 sm:pb-4">
                        <div class="bg-gray-50 px-4 py-3 sm:px-6 mb-4">
                            <h1 class="text-xl font-semibold mb-4">Unsubscribe</h1>
                            <div>
                                <p>Email: {{ unSubScriptionObj.sub }}</p>
                                <p>Category: {{ unSubScriptionObj.categoryId }}</p>
                            </div>
                            <div class="flex justify-end">
                                <button type="button" @click="$emit('cancel')"
                                    class="inline-flex w-full justify-center rounded-md bg-DarkGreen px-3 py-2 text-sm font-semibold text-white shadow-sm sm:ml-3 sm:w-auto">
                                    cancel
                                </button>
                                <button type="button" @click="unSubScriptionButton"
                                    class="inline-flex w-full justify-center rounded-md bg-DarkGreen px-3 py-2 text-sm font-semibold text-white shadow-sm sm:ml-3 sm:w-auto">
                                    Comfirm
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>
