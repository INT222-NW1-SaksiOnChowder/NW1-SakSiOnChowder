const API_ROOT = import.meta.env.VITE_API_ROOT
const updateAnnouncement = async(announcement) => {
    try {
        // const res = await fetch(`${API_ROOT}/api/announcements`,
        const res = await fetch(`http://localhost:5000/announcements/${announcement.id}`,
        // const res = await fetch(`http://localhost:8080/api/announcements/${announcement.id}`,
            {
                method: 'PUT',
                headers: {
                    'content-type': 'application/json'
                },
                body: JSON.stringify(announcement)
            }
        )
        if (res.status === 200) {
            console.log('Create successfully')
        } else {
            throw new Error('Cannot created!')
        }
    } catch (error) {
        console.log(`ERROR cannot create data: ${error}`);
    }
}



export { updateAnnouncement }