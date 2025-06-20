const ROOT_API = import.meta.env.VITE_ROOT_API
const matchPassword = async (user) => {
    const accessToken = localStorage.getItem("accessToken")
    const resultMsg = {
        status: false,
        message: 'Error'
    }
    try {
        const res = await fetch(`${ROOT_API}/api/users/match`,
            {
                method: 'POST',
                headers: {
                    'content-type': 'application/json',
                    "Authorization": `Bearer ${accessToken}`
                },
                body: JSON.stringify(user)
            }
        )
        if (res.status === 200) {
            console.log('Password Matched')
            resultMsg.status = true
            resultMsg.message = 'Password Matched'
            return resultMsg
        } else if (res.status === 401) {
            const error = await res.json()
            resultMsg.status = false
            resultMsg.message = 'Password NOT Matched'
            return resultMsg
        } else if (res.status === 404) {
            const error = await res.json()
            resultMsg.status = false
            resultMsg.message = 'The specified username DOES NOT exist'
            return resultMsg
        } else {
            const error = await res.json()
            resultMsg.status = false
            resultMsg.message = 'Password NOT Matched'
            return resultMsg
        }
    } catch (error) {
        console.log(`ERROR cannot create data: ${error}`);
        return resultMsg
    }
}

export { matchPassword }