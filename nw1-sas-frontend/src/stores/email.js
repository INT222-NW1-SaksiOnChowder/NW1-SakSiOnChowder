import { defineStore, acceptHMRUpdate } from "pinia"
import { ref } from "vue"

export const email = defineStore("email", () => {
  const currentEmail = ref(undefined)
  const setEmail = (email) => {
    currentEmail.value = email
  }
  return { currentEmail, setEmail }
})

if (import.meta.hot) {
  import.meta.hot.accept(acceptHMRUpdate(email, import.meta.hot))
}