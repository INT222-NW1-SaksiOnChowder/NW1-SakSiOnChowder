const ROOT_API = import.meta.env.VITE_ROOT_API
const createUser = async(user) => {
        try {
            const res = await fetch(`${ROOT_API}/api/users`,
                {
                    method: 'POST',
                    headers: {
                        'content-type': 'application/json'
                    },
                    body: JSON.stringify(user)
                }
            )
            if (res.status === 200) {
                console.log('Create successfully')
                return true
            }
            // else if(res.status === 400){
            //     const error = await res.json()
            //     for(const err of error.detail){
            //         if(err.errorMessage.includes("Email")){
            //             alert(err.errorMessage)
            //             return false
            //         }else{
            //             alert(err.field + " " + err.errorMessage)
            //             return false
            //         }
            //     }
            // }
        } catch (error) {
            console.log(`ERROR cannot create data: ${error}`);
        }
}

export { createUser }