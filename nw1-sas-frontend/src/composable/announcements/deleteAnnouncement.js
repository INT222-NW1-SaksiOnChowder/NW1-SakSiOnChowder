import { getNewAccessToken } from "../users/getToken.js"

const ROOT_API = import.meta.env.VITE_ROOT_API
const deleteAcc = async (id) => {
    const accessToken = localStorage.getItem("accessToken")
    try {
        const res = await fetch(`${ROOT_API}/api/announcements/${id}`,
            {
                method: 'DELETE',
                headers: {
                    "Authorization": `Bearer ${accessToken}`
                }
            })
        if (res.ok) {
            alert(`Delete id: ${id} Successfully`)
            console.log('Delete Successfully');
        } else {
            throw new Error(`Cannot delete`)
        }
    } catch (err) {
        alert(`Error: ${err}`)
        await getNewAccessToken()
        return false
    }
}

export { deleteAcc }