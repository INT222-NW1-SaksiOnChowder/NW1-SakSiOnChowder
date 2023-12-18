import { getNewAccessToken } from "../users/getToken.js"

const ROOT_API = import.meta.env.VITE_ROOT_API

const editFiles = async (id, files) => {
    const accessToken = localStorage.getItem("accessToken");
    const formData = new FormData();

    for (const file of files) {
        formData.append("file", file)
    }

    try {
        const res = await fetch(`${ROOT_API}/api/files/${id}`, {
            method: "PUT",
            headers: {
                Authorization: `Bearer ${accessToken}`,
            },
            body: formData,
        });

        if (!res.ok) {
            console.error(`Error: ${res.status} - ${res.statusText}`);
            return false
        }
        else if (res.ok) {
            console.log('Edit file Successfully');
            return true
        } else {
            console.log(res)
            return false
        }
    } catch (error) {
        console.error("Error edit files:", error);
        await getNewAccessToken()
        return false
    }
};

export { editFiles }