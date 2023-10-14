import { defineStore, acceptHMRUpdate } from "pinia"
import { ref } from "vue"

export const role = defineStore("role", () => {
  const currentRole = ref(undefined)
  const setRole = (role) => {
    currentRole.value = role
  }
  return { currentRole, setRole }
})

if (import.meta.hot) {
  import.meta.hot.accept(acceptHMRUpdate(role, import.meta.hot))
}