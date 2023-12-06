<script setup>
import { computed, ref } from "vue";

const emits = defineEmits(["filesSubmit"])

const props = defineProps({
    maxlength: {
        type: Number,
        default: 5
    },
    filesName: {
        type: Array,
        default: []
    }
})

const files = ref([])

// const checkDuplicateFlieName = (newfileName) => {
//     props.filesName.forEach(fileName => {
//         if (fileName === newfileName) {
//             alert('You can not add duplicate file name.')
//             return false
//         } else {
//             return true
//         }
//     })

// }

const chooseBinaryFiles = (event) => {
    console.log(props.maxlength);
    const selectedFiles = event.target.files
    console.log(selectedFiles);
    if (files.value.length + selectedFiles.length > 5) {
        console.log("You can only attach up to 5 files.")
        alert("You can only attach up to 5 files.")

    } else {
        const filesArray = Array.from(files.value)
        const newFilesArray = Array.from(event.target.files)

        const dataTransfer = new DataTransfer();
        filesArray.forEach(file => {
            const fileChecked = checkFiles(file)
            if (fileChecked !== undefined && fileChecked !== null) {
                dataTransfer.items.add(fileChecked)
            }
        })
        newFilesArray.forEach(file => {
            const fileChecked = checkFiles(file)
            if (fileChecked !== undefined && fileChecked !== null) {
                dataTransfer.items.add(fileChecked)
            }
        })

        files.value = dataTransfer.files
        emits('filesSubmit', files.value)
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

        let dataTransfer = new DataTransfer();
        filesArray.forEach(file => {
            const fileChecked = checkFiles(file)
            if (fileChecked !== undefined && fileChecked !== null) {
                dataTransfer.items.add(fileChecked)
            }
        })
        newFilesArray.forEach(file => {
            const fileChecked = checkFiles(file)
            if (fileChecked !== undefined && fileChecked !== null) {
                dataTransfer.items.add(fileChecked)
            }
        })

        files.value = dataTransfer.files
        emits('filesSubmit', files.value)
    }

}

const handleDragOver = (event) => {
    event.preventDefault();
}

const removeFile = (index) => {
    const filesArray = Array.from(files.value);
    filesArray.splice(index, 1);

    const dataTransfer = new DataTransfer();
    filesArray.forEach(file => dataTransfer.items.add(file))

    files.value = dataTransfer.files;
    emits('filesSubmit', files.value)
}

const createObjectURL = (file) => {
    return URL.createObjectURL(file);
}

const checkFiles = (file) => {
    let duplicateFileName = false
    if (file.size <= (20 * 1024 * 1024)) {
        // Add the file to your files array or process it as needed
        props.filesName.forEach(fileName => {
            if (fileName === file.name) {
                alert('You can not add duplicate file name.')
                duplicateFileName = true
            }
        })
        if (!duplicateFileName) {
            return file
        }
    } else {
        // Display an error message or take appropriate action for oversized files
        alert(`File ${files.name} size exceeds the limit (20 MB).`);
    }
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
                    <p class="text-xs text-gray-500 dark:text-gray-400">Format : SVG, PNG, JPG or PDF or ETC. ( MAX. 20 MB )</p>
                </div>
                <input @change="chooseBinaryFiles" id="dropzone-file" type="file" max="100" multiple class="hidden"
                    :disabled="files.length >= props.maxlength" />
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