// import { getNewAccessToken } from "./getToken.js";

const ROOT_API = import.meta.env.VITE_ROOT_API
const subScribeCategory = async (usersubscribe) => {
    // const accessToken = localStorage.getItem("accessToken");
    try {
        const res = await fetch(`${ROOT_API}/api/subscription/subscribe`,
            {
                method: 'POST',
                headers: {
                    'content-type': 'application/json',
                    // "Authorization": `Bearer ${accessToken}`
                },
                body: JSON.stringify(usersubscribe)
            }
        )
        if (res.status === 200) {
            const data = await res.json()
            console.log('Subscribe successfully')
            // console.log(data);
            return data.otpToken
        }
        else if (res.status === 400) {
            const error = await res.json()
            return error.detail

        }
    } catch (error) {
        console.log(`ERROR cannot create data: ${error}`);
        // await getNewAccessToken()
        return false
    }
}

const verifyOTP = async (otpNumber ,otpToken) => {
    try {
        const res = await fetch(`${ROOT_API}/api/subscription/verify`,
            {
                method: 'POST',
                headers: {
                    'content-type': 'application/json',
                    "OtpToken": `${otpToken}`
                },
                body: JSON.stringify(otpNumber)
            }
        )
        if (res.status === 200) {
            console.log('Subscribe successfully')
            return true
        }
        else if (res.status === 400) {
            const error = await res.json()
            return error.detail

        }
    } catch (error) {
        console.log(`ERROR cannot create data: ${error}`);
        // await getNewAccessToken()
        return false
    }
}
export { subScribeCategory, verifyOTP }