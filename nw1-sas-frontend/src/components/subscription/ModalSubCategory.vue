<script setup>
import { ref, watchEffect } from "vue";
import { subScribeCategory, verifyOTP } from "../../composable/subScribeCategory.js";
import { userDetailStore } from "../../composable/users/userDetailStore";
import Swal from 'sweetalert2'
const finishSendOTP = ref(true);
const tokenOTP = ref();
const emits = defineEmits(["cancel"]);
const selectedCategory = ref("1");
const emailInput = ref("");
const otp = ref("");
const userDetail = ref(userDetailStore())

const subScribeCategorySubmit = async () => {
    const data = {
        email: emailInput.value,
        categoryId: Number(selectedCategory.value),
    };
    tokenOTP.value = await subScribeCategory(data);
    console.log(tokenOTP.value);

    if (tokenOTP.value !== 'You have been already subscribed') {
        finishSendOTP.value = false;
    } else {
        Swal.fire({
            title: "Can not Submit",
            text: "You have been already subscribed",
            icon: "error"
        });
        emailInput.value = ""
        selectedCategory.value = "1"
    }
};

const verifyOTPSubmit = async () => {
    const data = {
        otp: otp.value
    };
    console.log(data);
    console.log(tokenOTP.value);
    const resultawait = await verifyOTP(data, tokenOTP.value);
    if (resultawait === true) {
        emits("cancel")
    }
    if (resultawait === false) {
        otp.value = ''
    }
};

watchEffect(() => {
    if (userDetail.value !== false) {
        emailInput.value = userDetail.value.user_email
        console.log(userDetail.value.user_email);
    }
})
</script>
<template>
    <div class="relative z-10" aria-labelledby="modal-title" role="dialog" aria-modal="true">
        <div class="fixed inset-0 bg-gray-500 bg-opacity-75 transition-opacity"></div>
        <div class="fixed inset-0 z-10 w-screen overflow-y-auto">
            <div class="flex min-h-full items-end justify-center p-4 text-center sm:items-center sm:p-0">
                <div
                    class="relative transform overflow-hidden rounded-lg bg-white text-left shadow-xl transition-all sm:my-8 sm:w-full sm:max-w-lg">
                    <div class="bg-white px-4 pb-4 pt-5 sm:p-6 sm:pb-4">
                        <div class="sm:flex sm:items-start">
                            <div v-if="finishSendOTP" class="mt-3 text-center sm:ml-4 sm:mt-0 sm:text-left">
                                <h3 class="text-base font-semibold leading-6 text-gray-900" id="modal-title">
                                    Please fill your <b>email</b>
                                </h3>
                                <div class="mt-2">
                                    <input v-model.trim="emailInput" type="email" class="bg-white border" />
                                </div>
                                <p class="flex font-semibold">Choose Category :</p>
                                &nbsp;
                                <select class="ann-category-filter bg-Cream rounded-md p-1" v-model="selectedCategory">
                                    <option value="1">ทั่วไป</option>
                                    <option value="2">ทุนการศึกษา</option>
                                    <option value="3">หางาน</option>
                                    <option value="4">ฝึกงาน</option>
                                </select>
                            </div>

                            <div v-else class="mt-3 text-center sm:ml-4 sm:mt-0 sm:text-left">
                                <h3 class="text-base font-semibold leading-6 text-gray-900" id="modal-title">
                                    Please fill your <b>otp</b>
                                </h3>
                                <div class="mt-2">
                                    <input type="number" v-model.trim="otp" class="bg-white border" maxlength="6" />
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="bg-gray-50 px-4 py-3 sm:flex sm:flex-row-reverse sm:px-6">
                        <button v-if="finishSendOTP" type="button" @click="subScribeCategorySubmit"
                            class="inline-flex w-full justify-center rounded-md bg-DarkGreen px-3 py-2 text-sm font-semibold text-white shadow-sm sm:ml-3 sm:w-auto">
                            submit
                        </button>
                        <button v-else type="button" @click="verifyOTPSubmit"
                            class="inline-flex w-full justify-center rounded-md bg-DarkGreen px-3 py-2 text-sm font-semibold text-white shadow-sm sm:ml-3 sm:w-auto">
                            submit
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
