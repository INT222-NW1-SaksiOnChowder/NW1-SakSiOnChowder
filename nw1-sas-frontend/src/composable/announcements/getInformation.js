import { getNewAccessToken } from "../users/getToken.js"

const ROOT_API = import.meta.env.VITE_ROOT_API
const getAnnouncements = async () => {
    const accessToken = localStorage.getItem("accessToken")
    try {
        const res = await fetch(`${ROOT_API}/api/announcements`,
            {
                method: "GET",
                headers: {
                    "Authorization": `Bearer ${accessToken}`,
                },
            })
        if (res.status === 200) {
            const announcement = await res.json()
            console.log('All Successfully')
            return announcement
        } else if (res.status !== 200) {
            const error = await res.json()
            alert(error.message)
        }
    } catch (error) {
        console.log(`ERROR cannot read data: ${error}`);
        await getNewAccessToken()
        return false
    }
}

const getAnnouncement = async (id) => {
    const accessToken = localStorage.getItem("accessToken")
    let announcement = undefined
    try {
        const res = await fetch(`${ROOT_API}/api/announcements/${id}`,
            {
                method: "GET",
                headers: {
                    "Authorization": `Bearer ${accessToken}`,
                },
            })
        if (res.status === 200) {
            announcement = await res.json()
            console.log('Detail Successfully')
            return announcement
        } else if (res.status !== 200) {
            const error = await res.json()
            alert(error.message)
        }
    } catch (error) {
        console.log(`ERROR cannot read data: ${error}`);
        await getNewAccessToken()
        return false
    }
}

export { getAnnouncements, getAnnouncement }