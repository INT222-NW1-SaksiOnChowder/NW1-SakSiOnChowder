const ROOT_API = import.meta.env.VITE_ROOT_API
const matchPassword = async(user) => {
    const resultMsg = {
        status : false,
        message : 'Error'
    }
        try {
            const res = await fetch(`${ROOT_API}/api/users/match`,
                {
                    method: 'POST',
                    headers: {
                        'content-type': 'application/json'
                    },
                    body: JSON.stringify(user)
                }
            )
            if (res.status === 200) {
                console.log('Password Matched')
                resultMsg.status = true
                resultMsg.message = 'Password Matched'
                return resultMsg
            }else if(res.status === 401){
                const error = await res.json()
                // for(const err of error.detail){
                //     alert(err.errorMessage)
                // }
                resultMsg.status = false
                resultMsg.message = 'Password Not Matched'
                return resultMsg
            }else if(res.status === 404){
                const error = await res.json()
                // for(const err of error.detail){
                //     alert(err.errorMessage)
                // }
                resultMsg.status = false
                resultMsg.message = 'The specified username DOES NOT exist'
                return resultMsg
            }
        } catch (error) {
            console.log(`ERROR cannot create data: ${error}`);
            return resultMsg
        }
}

export { matchPassword }