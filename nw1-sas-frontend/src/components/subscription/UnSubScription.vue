<script setup>
import { ref, watchEffect } from "vue";
import jwt_decode from "jwt-decode"
import { unSubScribeCategory } from '../../composable/subScribeCategory.js'

const props = defineProps({
    unSubTokenParam: {
        type: String
    }
})

const mailName = ref('')
const atSign = ref('')
const categorieName = ref('')
const loadingIcon = ref(false)
const unSubScriptionObj = ref(jwt_decode(props.unSubTokenParam))
const emits = defineEmits(["cancel"]);

const convertCategories = (categorie) => {
    switch (unSubScriptionObj.value.categoryId) {
        case 1:
            categorieName.value = "general";
            break;
        case 2:
            categorieName.value = "scholarship";
            break;
        case 3:
            categorieName.value = "job";
            break;
        case 4:
            categorieName.value = "internship";
            break;
    }
}

const unSubScriptionButton = async () => {
    loadingIcon.value = true
    const result = await unSubScribeCategory(props.unSubTokenParam)
    if (result === true) {
        emits('cancel')
        loadingIcon.value = false
    }
}

watchEffect(() => {
    mailName.value = unSubScriptionObj.value.sub.substring(0, 2)
    const atIndex = unSubScriptionObj.value.sub.indexOf('@')
    atSign.value = unSubScriptionObj.value.sub.substring(atIndex + 1)
    convertCategories(unSubScriptionObj.value.categoryId)
})

</script>
<template>
    <div class="relative z-10" aria-labelledby="modal-title" role="dialog" aria-modal="true">
        <div class="fixed inset-0 bg-gray-500 bg-opacity-75 transition-opacity"></div>
        <div class="fixed inset-0 z-10 w-screen overflow-y-auto">
            <div class="flex min-h-full items-center justify-center p-4 text-center sm:items-center sm:p-0">
                <div
                    class="relative transform overflow-hidden rounded-lg bg-white text-left shadow-xl transition-all sm:my-8 sm:w-full sm:max-w-lg">
                    <div class="bg-white px-4 pb-4 pt-5 w-80 sm:w-auto sm:p-6 sm:pb-4">

                        <div v-if="loadingIcon" role="status" class="w-full mb-5 flex items-center justify-center">
                            <svg aria-hidden="true"
                                class="w-10 h-10 text-gray-200 animate-spin dark:text-gray-600 fill-blue-600"
                                viewBox="0 0 100 101" fill="none" xmlns="http://www.w3.org/2000/svg">
                                <path
                                    d="M100 50.5908C100 78.2051 77.6142 100.591 50 100.591C22.3858 100.591 0 78.2051 0 50.5908C0 22.9766 22.3858 0.59082 50 0.59082C77.6142 0.59082 100 22.9766 100 50.5908ZM9.08144 50.5908C9.08144 73.1895 27.4013 91.5094 50 91.5094C72.5987 91.5094 90.9186 73.1895 90.9186 50.5908C90.9186 27.9921 72.5987 9.67226 50 9.67226C27.4013 9.67226 9.08144 27.9921 9.08144 50.5908Z"
                                    fill="currentColor" />
                                <path
                                    d="M93.9676 39.0409C96.393 38.4038 97.8624 35.9116 97.0079 33.5539C95.2932 28.8227 92.871 24.3692 89.8167 20.348C85.8452 15.1192 80.8826 10.7238 75.2124 7.41289C69.5422 4.10194 63.2754 1.94025 56.7698 1.05124C51.7666 0.367541 46.6976 0.446843 41.7345 1.27873C39.2613 1.69328 37.813 4.19778 38.4501 6.62326C39.0873 9.04874 41.5694 10.4717 44.0505 10.1071C47.8511 9.54855 51.7191 9.52689 55.5402 10.0491C60.8642 10.7766 65.9928 12.5457 70.6331 15.2552C75.2735 17.9648 79.3347 21.5619 82.5849 25.841C84.9175 28.9121 86.7997 32.2913 88.1811 35.8758C89.083 38.2158 91.5421 39.6781 93.9676 39.0409Z"
                                    fill="currentFill" />
                            </svg>
                            <span class="sr-only">Loading...</span>
                        </div>

                        <div class="sm:flex sm:items-start">
                            <div
                                class="mt-3 text-center sm:ml-4 sm:mt-0 sm:text-left w-full flex flex-col items-center justify-center">
                                <label
                                    class="block mb-2 text-xl font-semibold text-gray-900 dark:text-white">Unsubscribe</label>
                                <label class="mb-5 block text-center text-sm font-medium text-gray-500 dark:text-white">You
                                    want to unsubscribe from <span class="text-black">{{ categorieName }}</span> categories associated with
                                    this email address, <span class="text-black">{{ mailName }}**@{{ atSign }}</span>?</label>
                            </div>
                        </div>

                    </div>
                    <div class="bg-gray-50 px-4 py-3 sm:flex sm:flex-row-reverse sm:px-6">
                        <button type="button" @click="unSubScriptionButton"
                            class="inline-flex w-full justify-center rounded-md bg-DarkGreen px-3 py-2 text-sm font-semibold text-white shadow-sm sm:ml-3 hover:bg-ButtonViewHover sm:w-auto">
                            Submit
                        </button>
                        <button @click="$emit('cancel')" type="button"
                            class="mt-3 inline-flex w-full justify-center rounded-md bg-white px-3 py-2 text-sm font-semibold text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 hover:bg-gray-50 sm:mt-0 sm:w-auto">
                            Cancel
                        </button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>
