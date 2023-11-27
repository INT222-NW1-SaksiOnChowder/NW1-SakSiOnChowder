<script setup>
import { computed, ref } from "vue";

const files = ref([])

const chooseBinaryFiles = (event) => {
    const selectedFiles = event.target.files
    if (files.value.length + selectedFiles.length > 5) {
        console.log("You can only attach up to 5 files.")
        alert("You can only attach up to 5 files.")

    } else {
        const filesArray = Array.from(files.value)
        const newFilesArray = Array.from(event.target.files)

        const dataTransfer = new DataTransfer();
        filesArray.forEach(file => dataTransfer.items.add(file))
        newFilesArray.forEach(file => dataTransfer.items.add(file))

        files.value = dataTransfer.files
    }
    console.log(files.value);
}

const handleDrop = (event) => {
    event.preventDefault();
    // files.value = event.dataTransfer.files;
    const selectedFiles = event.dataTransfer.files
    if (files.value.length + selectedFiles.length > 5) {
        console.log("You can only attach up to 5 files.")
        alert("You can only attach up to 5 files.")

    } else {
        const filesArray = Array.from(files.value)
        const newFilesArray = Array.from(event.dataTransfer.files)

        const dataTransfer = new DataTransfer();
        filesArray.forEach(file => dataTransfer.items.add(file))
        newFilesArray.forEach(file => dataTransfer.items.add(file))

        files.value = dataTransfer.files
    }

}

const handleDragOver = (event) => {
    event.preventDefault();
}

const removeFile = (index) => {
    const filesArray = Array.from(files.value);
    filesArray.splice(index, 1);

    const dataTransfer = new DataTransfer();
    filesArray.forEach(file => dataTransfer.items.add(file));

    files.value = dataTransfer.files;
}

const createObjectURL = (file) => {
    return URL.createObjectURL(file);
}
</script>

<template>
    <div class="mb-20 mt-10">
        <label class="font-bold">Attach Files</label><br />
        <!-- <input class="file-input-bordered text-sm " id="multiple_files" type="file" multiple> -->
        <div class="flex items-center justify-center w-full mt-2 my-5" @drop="handleDrop" @dragover="handleDragOver">
            <label for="dropzone-file"
                class="flex flex-col items-center justify-center w-full h-64 border-2 border-gray-300 border-dashed rounded-lg cursor-pointer bg-gray-50 dark:hover:bg-bray-800 dark:bg-gray-700 hover:bg-gray-100 dark:border-gray-600 dark:hover:border-gray-500 dark:hover:bg-gray-600">
                <div class="flex flex-col items-center justify-center pt-5 pb-6">
                    <svg class="w-8 h-8 mb-4 text-gray-500 dark:text-gray-400" aria-hidden="true"
                        xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 20 16">
                        <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                            d="M13 13h3a3 3 0 0 0 0-6h-.025A5.56 5.56 0 0 0 16 6.5 5.5 5.5 0 0 0 5.207 5.021C5.137 5.017 5.071 5 5 5a4 4 0 0 0 0 8h2.167M10 15V6m0 0L8 8m2-2 2 2" />
                    </svg>
                    <p class="mb-2 text-sm text-gray-500 dark:text-gray-400"><span class="font-semibold">Click to
                            upload</span> or drag and drop</p>
                    <p class="text-xs text-gray-500 dark:text-gray-400">Format : SVG, PNG, JPG or PDF ( MAX. 500 MB )</p>
                </div>
                <input @change="chooseBinaryFiles" id="dropzone-file" type="file" multiple class="hidden"
                    :disabled="files.length >= 5" />
            </label>
        </div>
        <!-- <hr class="my-5 border-2 rounded-xl border-gray-100"> -->

        <div v-for="(file, index) in files" :key="index" class="flex items-center">
            <a :href="createObjectURL(file)" target="_blank"
                class="p-2  mb-2 text-sm bg-white hover:bg-neutral-400 hover:text-white rounded-l-md pr-10  w-max">{{
                    file.name
                }}</a><button @click="removeFile(index)"
                class="p-2 flex justify-between bg-DarkRed mb-2 text-sm rounded-r-md hover:bg-ButtonDeleteHover ">Delete</button>
        </div>

        <!-- <div >
            <img :src="previewSrc" alt="" v-if="canPreview">
        </div> -->
    </div>
</template>

<style scoped></style>