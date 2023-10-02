import { defineStore, acceptHMRUpdate } from "pinia"
import { ref } from "vue"

export const username = defineStore("counter", () => {
  const currentUsername = ref("Eve")
  const setUsername = (user) => {
    currentUsername.value = user
  }
  return { currentUsername, setUsername }
})

if (import.meta.hot) {
  import.meta.hot.accept(acceptHMRUpdate(username, import.meta.hot))
}