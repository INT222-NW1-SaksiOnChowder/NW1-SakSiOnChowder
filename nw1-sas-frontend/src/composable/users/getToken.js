const ROOT_API = import.meta.env.VITE_ROOT_API
import jwt_decode from "jwt-decode"
import {role} from "../../stores/role.js"
import { useRouter } from "vue-router";

const getToken = async (user) => {
    const currentRole = role()
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
        if (res.status === 200) {

            const data = await res.json()
            const userDetail = await jwt_decode(data.token)

            result.status = true
            result.message = 'Password Matched'

            localStorage.setItem("refreshToken", data.refreshToken);
            localStorage.setItem("accessToken", data.token);
            currentRole.setRole(userDetail.role)
            return result
        } else if (res.status === 401) { //res.status === 403
            result.status = false
            result.message = 'Password NOT Matched'
            return result
        } else if (res.status === 404) {
            result.status = false
            result.message = 'The specified username DOES NOT exist'
            return result
        } else {
            const error = await res.json()
            result.status = false
            result.message = 'Password NOT Matched'
            return result
        }
    } catch (error) {
        console.log(`ERROR cannot create data: ${error}`);
        return result
    }
}

const getNewAccessToken = async () => {
    const refreshToken = localStorage.getItem("refreshToken");
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
        }
    } catch (error) {
        console.log(`ERROR cannot read data: ${error}`);
    }
}

export { getToken, getNewAccessToken }