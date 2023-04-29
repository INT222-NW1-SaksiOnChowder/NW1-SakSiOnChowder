const getAnnouncements= async () => {
    try {
        const res = await fetch(`http://ip22nw1.sit.kmutt.ac.th:8080/api/announcements`)
        if (res.status === 200) {
            const user = await res.json()
            console.log('All Successfully')
            return user
        } else {
            throw new Error('No announcement')
        }
    } catch (error) {
        console.log(`ERROR cannot read data: ${error}`);
    }
}

const getAnnouncement= async (id) => {
    try {
        const res = await fetch(`http://ip22nw1.sit.kmutt.ac.th:8080/api/announcements/${id}`)
        if (res.status === 200) {
            const user = await res.json()
            console.log('Detail Successfully')
            return user
        } else {
            throw new Error(`Announcement id ${id} doesn't exist`)
        }
    } catch (error) {
        console.log(`ERROR cannot read data: ${error}`);
    }
}

export { getAnnouncements, getAnnouncement }