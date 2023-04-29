const getAnnouncements= async () => {
    try {
        // const res = await fetch(`http://ip22nw1.sit.kmutt.ac.th:8080/api/announcements`)
        const res = await fetch(`http://localhost:5000/announcements`)
        if (res.status === 200) {
            const announcement = await res.json()
            console.log('All Successfully')
            return announcement
        } else {
            throw new Error('No announcement')
        }
    } catch (error) {
        console.log(`ERROR cannot read data: ${error}`);
    }
}

const getAnnouncement= async (id) => {
    try {
        // const res = await fetch(`http://ip22nw1.sit.kmutt.ac.th:8080/api/announcements/${id}`)
        const res = await fetch(`http://localhost:5000/announcements/${id}`)
        if (res.status === 200) {
            const announcement = await res.json()
            console.log('Detail Successfully')
            return announcement
        } else {
            throw new Error(`Announcement id ${id} doesn't exist`)
        }
    } catch (error) {
        console.log(`ERROR cannot read data: ${error}`);
    }
}

export { getAnnouncements, getAnnouncement }