import { defineStore, acceptHMRUpdate } from "pinia"
import { ref } from "vue"

export const username = defineStore("username", () => {  //ตอนแรกเป็นusername ละไมเปลี่ยนเป็นcounter หว่า?
  const currentUsername = ref("Eve")
  const setUsername = (user) => {
    currentUsername.value = user
  }
  return { currentUsername, setUsername }
})

if (import.meta.hot) {
  import.meta.hot.accept(acceptHMRUpdate(username, import.meta.hot))
}