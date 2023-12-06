const ROOT_API = import.meta.env.VITE_ROOT_API
const getFiles = async (id) => {
    try {
        const res = await fetch(`${ROOT_API}/api/files/view/${id}`,
            {
                method: "GET"
            })
        if (res.status === 200) {
            const files = await res.json()
            console.log('All Successfully')
            return files
        } else if (res.status === 404) {
            return []
        } else if (res.status !== 200) {
            const error = await res.json()
            alert(error.message)
        }
    } catch (error) {
        console.log(`ERROR cannot read data: ${error}`);
        return []
    }
}

const getFile = async (id,fileName) => {
    console.log(id);
    console.log(fileName);
    try {
        const res = await fetch(`${ROOT_API}/api/files/${id}/${fileName}`,
            {
                method: "GET"
            })
        if (res.status === 200) {
            console.log('All Successfully')
            console.log(res);
            return res
        } else if (res.status !== 200) {
            const error = await res.json()
            alert(error.message)
        }
    } catch (error) {
        console.log(`ERROR cannot read data: ${error}`);
        return false
    }
}

export { getFiles, getFile }