const updateAnnouncement = async(announcement) => {
    try {
        // const res = await fetch(`http://ip22nw1.sit.kmutt.ac.th:8080/api/announcements`,
        const res = await fetch(`http://localhost:5000/announcements/${announcement.id}`,
            {
                method: 'PUT',
                headers: {
                    'content-type': 'application/json'
                },
                body: JSON.stringify(announcement)
            }
        )
        if (res.status === 201) {
            console.log('Create successfully')
        } else {
            throw new Error('Cannot created!')
        }
    } catch (error) {
        console.log(`ERROR cannot create data: ${error}`);
    }
}

const addNewSlot = async (information) => {
    try {
        const res = await fetch(`http://localhost:5000/Player/${information.id}`,
            {
                method: 'PUT',
                headers: {
                    'content-type': 'application/json'
                },
                body: JSON.stringify(information)
            }
        )
        if (res.status === 200) {
            console.log('Edit successfully')
        } else {
            throw new Error('Cannot edit!')
        }
    } catch (error) {
        console.log(`ERROR cannot read data: ${error}`);
    }
}

export { updateAnnouncement }