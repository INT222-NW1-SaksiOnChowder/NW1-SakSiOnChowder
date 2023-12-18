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
                },
                body: JSON.stringify(usersubscribe)
            }
        )
        if (res.status === 200) {
            const data = await res.json()
            console.log('Subscribe successfully')
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
            console.log('OTP successfully')
            alert("Your subscription is successful.")
            return true
        } else if (res.status === 401) {
            alert("OTP is invalid. Check your mail and please try again.")
            return false

        } else if (res.status === 403) {
            alert("OTP is expired. Please try again.")
            return true
        } else if (res.status === 400) {
            alert("OTP is invalid. Check your mail and please try again.")
            return false
        }
    } catch (error) {
        console.log(`ERROR cannot create data: ${error}`);
        alert("Error verify otp")
        // await getNewAccessToken()
        return true
    }
}


const unSubScribeCategory = async (unSubScriptionToken) => {
    try {
        const res = await fetch(`${ROOT_API}/api/subscription/unsubscribe`,
            {
                method: "DELETE",
                headers: {
                    'content-type': 'application/json',
                    'UnsubToken': unSubScriptionToken
                },
            })
        if (res.status === 200) {
            console.log('UnSubscribe successfully')
            alert('UnSubscribe successfully')
            return true
        } else if (res.status === 401) {
            alert ("UnSubscribe is not successful. You don't have permission to edit unsubscribtion link.")
            return true
        } else if (res.status === 403) {
            alert("UnSubscribe is not successful. The category is not subscribed")
            return true
        }
    } catch (error) {
        console.log(`ERROR cannot read data: ${error}`);
        return false
    }
}

export { subScribeCategory, verifyOTP, unSubScribeCategory }