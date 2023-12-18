import { getNewAccessToken } from "../users/getToken.js"

const ROOT_API = import.meta.env.VITE_ROOT_API

const addFiles = async (id, files) => {
    const accessToken = localStorage.getItem("accessToken");
    // const arrFiles = Array.from(files)

    // Create a new FormData object
    const formData = new FormData();
    
    // Append files to the FormData object
    for(const file of files)
    {
        formData.append("file",file)
    }

    // Append announcementId to the FormData object
    formData.append("announcementId", id);
    // Log the value of the "file" key

// Log the value of the "announcementId" key

    try {
        const res = await fetch(`${ROOT_API}/api/files`, {
            method: "POST",
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
            console.log("Create file Successfully");
            return true
        } else {
            console.log(res)
            return false
        }
    } catch (error) {
        console.error("Error uploading files:", error);
        await getNewAccessToken()
        return false
    }
};



export { addFiles }