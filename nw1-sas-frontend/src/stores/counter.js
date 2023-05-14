import { defineStore, acceptHMRUpdate } from "pinia"
import { ref } from "vue"

export const annStores = defineStore("counter", () => {
  const mode = ref('active')
  const category = ref("")
  const page = ref(0)
  const setmode = (newMode) => {
    if (mode.value === 'active' || mode.value === 'close') {
      mode.value = newMode
    }
  }
  const setCategory = (newCategory) => {
    category.value = newCategory
  }
  const setPage = (newPage) => {
    page.value = newPage
  }
  return { mode, category, page, setmode, setCategory, setPage }
})

if (import.meta.hot) {
  import.meta.hot.accept(acceptHMRUpdate(annStores, import.meta.hot))
}
