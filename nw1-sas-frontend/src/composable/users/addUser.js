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
            }else if(res.status !== 200){
                const error = await res.json()
                alert(error.message)
            }
        } catch (error) {
            console.log(`ERROR cannot create data: ${error}`);
        }
}

export { createUser }