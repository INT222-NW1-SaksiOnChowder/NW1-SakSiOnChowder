import { defineStore, acceptHMRUpdate } from "pinia"
import { ref } from "vue"

export const annStores = defineStore("counter", () => {
  const mode = ref('active')
  const setmode = (newMode) => {
    if (mode.value === 'active' || mode.value === 'close') {
      mode.value = newMode
    }
  }
  return { mode, setmode }
})

if (import.meta.hot) {
  import.meta.hot.accept(acceptHMRUpdate(annStores, import.meta.hot))
}
