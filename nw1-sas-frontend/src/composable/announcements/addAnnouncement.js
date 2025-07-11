import { getNewAccessToken } from "../users/getToken.js"

const ROOT_API = import.meta.env.VITE_ROOT_API
const createAnnouncement = async (announcement) => {
    const accessToken = localStorage.getItem("accessToken")
    try {
        const res = await fetch(`${ROOT_API}/api/announcements`,
            {
                method: 'POST',
                headers: {
                    'content-type': 'application/json',
                    "Authorization": `Bearer ${accessToken}`
                },
                body: JSON.stringify(announcement)
            }
        )
        if (res.status === 200) {
            const result = await res.json()
            console.log('Create successfully')
            return result
        } else if (res.status !== 200) {
            const error = await res.json()
            for (const err of error.detail) {
                alert(err.field + " " + err.errorMessage);
            }
            return false
        }
    } catch (error) {
        console.log(`ERROR cannot create data: ${error}`);
        await getNewAccessToken()
        return false
    }
}

export { createAnnouncement }