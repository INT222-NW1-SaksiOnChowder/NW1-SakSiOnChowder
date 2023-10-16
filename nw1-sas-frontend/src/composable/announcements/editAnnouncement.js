import { getNewAccessToken } from "../users/getToken.js"

const ROOT_API = import.meta.env.VITE_ROOT_API
const updateAnnouncement = async (announcement) => {
    const accessToken = localStorage.getItem("accessToken")
    try {
        const res = await fetch(`${ROOT_API}/api/announcements/${announcement.id}`,
            {
                method: 'PUT',
                headers: {
                    'content-type': 'application/json',
                    "Authorization": `Bearer ${accessToken}`
                },
                body: JSON.stringify(announcement)
            }
        )
        if (res.status === 200) {
            console.log('Create successfully')
        }else if (res.status === 403){
            const error = await res.json();
            for (const err of error.detail) {
              alert(err.errorMessage);
            } 
        } else if (res.status === 400) {
           const error = await res.json();
            for (const err of error.detail) {
              alert(err.errorMessage);
            } 
        }
    } catch (error) {
        console.log(`ERROR cannot create data: ${error}`);
        await getNewAccessToken()
        return false
    }
}



export { updateAnnouncement }