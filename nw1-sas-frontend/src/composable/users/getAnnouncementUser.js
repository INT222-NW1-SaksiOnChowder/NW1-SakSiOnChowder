const ROOT_API = import.meta.env.VITE_ROOT_API
const getAnnouncementsUser = async (mode, id, category) => {
    // const accessToken = localStorage.getItem("accessToken");
    let pagesId = ''
    let pagesMode = ''
    let categoryType = ''
    if (id !== undefined && id !== null) {
        pagesId = `?page=${id}`
    }
    if (mode !== undefined && mode !== null) {
        pagesMode = `&mode=${mode}`
    }
    if (category !== undefined && category !== null) {
        categoryType = `&category=${category}`
    }
    try {
        const res = await fetch(`${ROOT_API}/api/announcements/pages${pagesId}${pagesMode}${categoryType}`,
            {
                method: "GET"
            })
        if (res.status === 200) {
            const announcement = await res.json()
            console.log('All Successfully')
            return announcement
        } else if (res.status !== 200) {
            const error = await res.json()
            alert(error.message)
        }

    } catch (error) {
        console.log(`ERROR cannot read data: ${error}`);

    }
}

const getAnnouncementUser = async (id, mode) => {
    const accessToken = localStorage.getItem("accessToken");
    let announcement = undefined
    try {
        const res = await fetch(`${ROOT_API}/api/announcements/${id}?mode=${mode}&count=true`,
            {
                method: "GET",
            })
        if (res.status === 200) {
            announcement = await res.json()
            console.log('Detail Successfully')
            return announcement
        } else if (res.status === 403) {
            const error = await res.json();
            for (const err of error.detail) {
                alert(err.errorMessage);
            }
        } else if (res.status === 404) {
            const error = await res.json();
            for (const err of error.detail) {
                alert(err.errorMessage);
            }
        }
    } catch (error) {
        console.log(`ERROR cannot read data: ${error}`);
    }
}

export { getAnnouncementsUser, getAnnouncementUser }