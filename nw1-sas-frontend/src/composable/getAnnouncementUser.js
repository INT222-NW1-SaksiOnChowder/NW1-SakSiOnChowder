const ROOT_API = import.meta.env.VITE_ROOT_API
const getAnnouncementsUser= async (mode) => {
    try {
        const res = await fetch(`${ROOT_API}/api/announcements?mode=${mode}`)
        // const res = await fetch(`http://localhost:5000/announcements`)
        // const res = await fetch(`http://localhost:8080/api/announcements?mode=${mode}`)
        
        if (res.status === 200) {
            const announcement = await res.json()
            console.log('All Successfully')
            return announcement
        }else if(res.status !== 200){
            const error = await res.json()
            alert(error.message)
        }
        // else if(!res.ok){
        //     const announcement = await res.json().
        // }
        
    } catch (error) {
        console.log(`ERROR cannot read data: ${error}`);
        
    }   
}

// const getClosedAnnouncementsUser= async () => {
//     try {
//         // const res = await fetch(`${ROOT_API}/api/announcements`)
//         // const res = await fetch(`http://localhost:5000/announcements`)
//         const res = await fetch(`http://localhost:8080/api/announcements?mode=close`)
        
//         if (res.status === 200) {
//             const announcement = await res.json()
//             console.log('All Successfully')
//             return announcement
//         }else {
//             throw new Error('No Announcement')
//         }
//     } catch (error) {
//         console.log(`ERROR cannot read data: ${error}`);
//     }   
// }

const getAnnouncementUser= async (id) => {
    let announcement = undefined
    try {
        const res = await fetch(`${ROOT_API}/api/announcements/${id}`)
        // const res = await fetch(`http://localhost:5000/announcements/${id}`)
        // const res = await fetch(`http://localhost:8080/api/announcements/${id}`)
        if (res.status === 200) {
            announcement = await res.json()
            console.log('Detail Successfully')
            return announcement
        } else {
            throw new Error(`Announcement id ${id} doesn't exist`)
        }
    } catch (error) {
        console.log(`ERROR cannot read data: ${error}`);
    }   
}

export { getAnnouncementsUser, getAnnouncementUser }