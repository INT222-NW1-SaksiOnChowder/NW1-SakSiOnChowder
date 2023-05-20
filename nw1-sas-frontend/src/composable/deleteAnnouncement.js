const ROOT_API = import.meta.env.VITE_ROOT_API
const deleteAcc = async (id) => {
    try {
        const res = await fetch(`${ROOT_API}/api/announcements/${id}`,
        { method: 'DELETE' })
        if (res.ok) {
            console.log('Delete Successfully');
        } else {
            throw new Error(`Cannot delete`)
        }
    } catch (err) {
        alert(`Error: ${err}`)
    }
}

export { deleteAcc }