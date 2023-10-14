const ROOT_API = import.meta.env.VITE_ROOT_API
import { username } from "../../stores/username.js"
const getToken = async (user) => {
    const currentUsername = username()
    const result = {
        status: false,
        message: 'Error'
    }
    try {
        const res = await fetch(`${ROOT_API}/api/token`,
            {
                method: 'POST',
                headers: {
                    'content-type': 'application/json',
                },
                body: JSON.stringify(user)
            }
        )
        console.log(res.status);
        if (res.status === 200) {
            const data = await res.json()
            console.log(data);
            result.status = true
            result.message = 'Password Matched'
            localStorage.setItem("refreshToken", data.refreshToken);
            localStorage.setItem("accessToken", data.token);
            currentUsername.setUsername(user.username);
            console.log(currentUsername.currentUsername);
            return result
        } else if (res.status === 401) { //res.status === 401
            // const error = await res.json()
            // for(const err of error.detail){
            //     alert(err.errorMessage)
            // }
            result.status = false
            result.message = 'Password NOT Matched'
            return result
        } else if (res.status === 404) {
            // const error = await res.json()
            // for(const err of error.detail){
            //     alert(err.errorMessage)
            // }
            result.status = false
            result.message = 'The specified username DOES NOT exist'
            return result
        }
    } catch (error) {
        console.log(`ERROR cannot create data: ${error}`);
        return result
    }
}

const getNewAccessToken = async () => {
    console.log('getNewAccessToken');
    const refreshToken = localStorage.getItem("refreshToken");
    console.log('refreshToken'+refreshToken);
    try {
        const res = await fetch(`${ROOT_API}/api/token`,
            {
                method: 'GET',
                headers: {
                    'Authorization': `Bearer ${refreshToken}`
                },
            })
        console.log(res);
        if (res.status === 200) {
            const data = await res.json()
            localStorage.setItem("accessToken", data.token);
            console.log(localStorage.getItem("accessToken"));
        } else if (res.status !== 200) {
            const error = await res.json()
            alert(error.message)
            console.log('else if');
        }
    } catch (error) {
        console.log(`ERROR cannot read data: ${error}`);
    }
}

export { getToken, getNewAccessToken }