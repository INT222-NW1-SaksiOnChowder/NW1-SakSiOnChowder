const ROOT_API = import.meta.env.VITE_ROOT_API
const getUsers = async () => {
    const accessToken = localStorage.getItem("accessToken"); 
    // console.log(accessToken);
    // const headers = new Headers();
    // headers.append('Content-Type', 'application/json'); 
    // headers.append('Authorization', `Bearer ${accessToken}`); 

    try {
        const res = await fetch(`${ROOT_API}/api/users`,
            {
                method: 'GET',
                headers: {
                    'Authorization': `Bearer ${accessToken}`
                },
            })
        console.log(res);
        if (res.status === 200) {
            const user = await res.json()
            console.log('All Successfully')
            return user
        } else if (res.status !== 200) {
            const error = await res.json()
            alert(error.message)
        }
    } catch (error) {
        console.log(`ERROR cannot read data: ${error}`);
    }
}

const getUser = async (id) => {
    let user = undefined
    try {
        const res = await fetch(`${ROOT_API}/api/users/${id}`)
        if (res.status === 200) {
            user = await res.json()
            console.log('Detail Successfully')
            return user
        } else if (res.status !== 200) {
            const error = await res.json()
            alert(error.message)
        }
    } catch (error) {
        console.log(`ERROR cannot read data: ${error}`);
    }
}

export { getUsers, getUser }