<script setup>
import { ref, watchEffect, onMounted } from "vue";
import { subScribeCategory, verifyOTP } from "../../composable/subScribeCategory.js";
import { userDetailStore } from "../../composable/users/userDetailStore";
import Swal from 'sweetalert2'

const finishSendOTP = ref(true);
const tokenOTP = ref();
const emits = defineEmits(["cancel"]);
const selectedCategory = ref("1");
const emailInput = ref("");
const mailName = ref('')
const atSign = ref('')
const otp = ref("");
const userDetail = ref(userDetailStore())
const loadingIcon = ref(false)

const subScribeCategorySubmit = async () => {
    loadingIcon.value = true
    const data = {
        email: emailInput.value,
        categoryId: Number(selectedCategory.value),
    };
    tokenOTP.value = await subScribeCategory(data);
    console.log(tokenOTP.value);

    if (tokenOTP.value !== 'You have been already subscribed') {
        finishSendOTP.value = false;
        loadingIcon.value = false
    } else {
        Swal.fire({
            title: "Can not Submit",
            text: "You have been already subscribed",
            icon: "error"
        });
        emailInput.value = ""
        selectedCategory.value = "1"
        loadingIcon.value = false
    }
};

const verifyOTPSubmit = async () => {
    loadingIcon.value = true
    const data = {
        otp: otp.value
    };
    console.log(data);
    console.log(tokenOTP.value);
    const resultawait = await verifyOTP(data, tokenOTP.value);
    if (resultawait === true) {
        emits("cancel")
        loadingIcon.value = false
    }
    if (resultawait === false) {
        otp.value = ''
        loadingIcon.value = false
    }
};

watchEffect(() => {
    mailName.value = emailInput.value.substring(0, 2)
    const atIndex = emailInput.value.indexOf('@')
    atSign.value = emailInput.value.substring(atIndex + 1)
})

onMounted(async () => {
    if (userDetail.value !== false) {
        emailInput.value = userDetail.value.user_email
        console.log(userDetail.value.user_email);
    }
});

</script>
<template>
    <div class="relative z-10" aria-labelledby="modal-title" role="dialog" aria-modal="true">
        <div class="fixed inset-0 bg-gray-500 bg-opacity-75 transition-opacity"></div>
        <div class="fixed inset-0 z-10 w-screen overflow-y-auto">
            <div class="flex min-h-full items-center justify-center p-4 text-center sm:items-center sm:p-0">
                <div
                    class="relative transform overflow-hidden rounded-lg bg-white text-left shadow-xl transition-all sm:my-8 sm:w-full sm:max-w-lg">
                    <div class="bg-white px-4 pb-4 pt-5 sm:p-6 sm:pb-4">

                        <div class="sm:flex sm:items-start w-full mb-5">

                            <div v-if="loadingIcon" role="status" class="w-full flex items-center justify-center">
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
                        </div>

                        <div class="sm:flex sm:items-start">
                            <div v-if="finishSendOTP" class="mt-3 text-center sm:ml-4 sm:mt-0 sm:text-left w-full">
                                <label class="block mb-2 text-xl text-center font-semibold text-gray-900 dark:text-white">Subscription</label>
                                <label for="helper-text"
                                    class="block mb-2 text-sm font-medium text-left text-gray-900 dark:text-white">Your
                                    email</label>
                                <input v-model.trim="emailInput" type="email" id="helper-text"
                                    aria-describedby="helper-text-explanation"
                                    class="mb-5 bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5  dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
                                    placeholder="name@gmail.com">

                                <label for="countries"
                                    class="block mb-2 text-sm font-medium text-left text-gray-900 dark:text-white">Choose
                                    Category</label>
                                <select v-model="selectedCategory" id="countries"
                                    class="mb-5 bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500">
                                    <option value="1">ทั่วไป</option>
                                    <option value="2">ทุนการศึกษา</option>
                                    <option value="3">หางาน</option>
                                    <option value="4">ฝึกงาน</option>
                                </select>

                            </div>

                            <div v-else
                                class="mt-3 text-center sm:ml-4 sm:mt-0 sm:text-left w-full flex flex-col items-center justify-center">
                                <label class="block mb-2 text-xl font-semibold text-gray-900 dark:text-white">Verification
                                    OTP</label>
                                <label class="block text-sm font-medium text-gray-500 dark:text-white">We have sent a code
                                    to your email {{ mailName }}**@{{ atSign }}</label>
                                <input v-model.trim="otp" type="text" maxlength="6"
                                    class="my-5 bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-20 p-2.5  dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
                                    placeholder="123456">
                                <label class="block mb-2 text-sm font-medium text-gray-500 dark:text-white">Didn't recieve
                                    code? <button @click="subScribeCategorySubmit"
                                        class="text-gray-900 dark:text-white hover:text-DarkGreen">Resend</button></label>
                            </div>
                        </div>

                    </div>
                    <div class="bg-gray-50 px-4 pt-3 pb-5 sm:flex sm:flex-row-reverse sm:px-6">
                        <button v-if="finishSendOTP" type="button" @click="subScribeCategorySubmit"
                            class="inline-flex w-full justify-center rounded-md bg-DarkGreen px-3 py-2 text-sm font-semibold text-white shadow-sm sm:ml-3 hover:bg-ButtonViewHover sm:w-auto">
                            Subscribe
                        </button>
                        <button v-else type="button" @click="verifyOTPSubmit"
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
