import { getNewAccessToken } from "./getToken.js";

const ROOT_API = import.meta.env.VITE_ROOT_API
const createUser = async(user) => {
    const accessToken = localStorage.getItem("accessToken"); 
        try {
            const res = await fetch(`${ROOT_API}/api/users`,
                {
                    method: 'POST',
                    headers: {
                        'content-type': 'application/json',
                        "Authorization": `Bearer ${accessToken}`,
                    },
                    body: JSON.stringify(user)
                }
            )
            if (res.status === 200) {
                console.log('Create successfully')
                return true
            }
            else if(res.status === 400){
                const error = await res.json()
                return error.detail
               
            }
        } catch (error) {
            console.log(`ERROR cannot create data: ${error}`);
            await getNewAccessToken()
            return false
        }
}

export { createUser }