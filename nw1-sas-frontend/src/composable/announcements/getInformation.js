const ROOT_API = import.meta.env.VITE_ROOT_API
const getAnnouncements= async () => {
    try {
        const res = await fetch(`${ROOT_API}/api/announcements`)
        
        if (res.status === 200) {
            const announcement = await res.json()
            console.log('All Successfully')
            return announcement
        }else if(res.status !== 200){
            const error = await res.json()
            alert(error.message)
        }
    } catch (error) {
        console.log(`ERROR cannot read data: ${error}`);
    }   
}

const getAnnouncement= async (id) => {
    let announcement = undefined
    try {
        const res = await fetch(`${ROOT_API}/api/announcements/${id}`)
        if (res.status === 200) {
            announcement = await res.json()
            console.log('Detail Successfully')
            return announcement
        }else if(res.status !== 200){
            const error = await res.json()
            alert(error.message)
        }
    } catch (error) {
        console.log(`ERROR cannot read data: ${error}`);
    }   
}

export { getAnnouncements, getAnnouncement }