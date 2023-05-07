const ROOT_API = import.meta.env.VITE_ROOT_API
const createAnnouncement = async(announcement) => {
        try {
            const res = await fetch(`${ROOT_API}/api/announcements`,
            // const res = await fetch(`http://localhost:5000/announcements`,
            // const res = await fetch(`http://localhost:8080/api/announcements`,
                {
                    method: 'POST',
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

export { createAnnouncement }