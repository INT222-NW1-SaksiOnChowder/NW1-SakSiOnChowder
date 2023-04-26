const getAnnouncements= async () => {
    try {
        const res = await fetch(`http://localhost:5000/announcements`)
        if (res.status === 200) {
            const user = await res.json()
            console.log('Successfully')
            return user
        } else {
            throw new Error('Announcement not found')
        }
    } catch (error) {
        console.log(`ERROR cannot read data: ${error}`);
    }
}

const getAnnouncement= async (id) => {
    try {
        const res = await fetch(`http://localhost:5000/announcements/${id}`)
        if (res.status === 200) {
            const user = await res.json()
            console.log('Successfully')
            return user
        } else {
            throw new Error('Announcement not found')
        }
    } catch (error) {
        console.log(`ERROR cannot read data: ${error}`);
    }
}


// const addNewSlot = async (information) => {
//     try {
//         const res = await fetch(`http://localhost:5000/Player/${information.id}`,
//             {
//                 method: 'PUT',
//                 headers: {
//                     'content-type': 'application/json'
//                 },
//                 body: JSON.stringify(information)
//             }
//         )
//         if (res.status === 200) {
//             console.log('Successfully')
//         } else {
//             throw new Error('Announcement not found')
//         }
//     } catch (error) {
//         console.log(`ERROR cannot read data: ${error}`);
//     }
// }

export { getAnnouncements, getAnnouncement }