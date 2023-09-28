const ROOT_API = import.meta.env.VITE_ROOT_API
const getToken = async(user) => {
    const result = {
        status : false,
        message : 'Error',
        accessToken : '',
        refreshToken : ''
    }
        try {
            const res = await fetch(`${ROOT_API}/api/token`,
                {
                    method: 'POST',
                    headers: {
                        'content-type': 'application/json'
                    },
                    body: JSON.stringify(user)
                }
            )
            if (res.status === 200) {
                const data = await res.json()
                console.log(data);
                result.status = true
                result.message = 'Password Matched'
                result.accessToken = data.token
                result.refreshToken = data.refreshToken
                return result
            }else if(res.status === 401){
                const error = await res.json()
                // for(const err of error.detail){
                //     alert(err.errorMessage)
                // }
                result.status = false
                result.message = 'Password NOT Matched'
                return result
            }else if(res.status === 404){
                const error = await res.json()
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

export { getToken }