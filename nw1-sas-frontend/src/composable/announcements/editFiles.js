import { getNewAccessToken } from "../users/getToken.js"

const ROOT_API = import.meta.env.VITE_ROOT_API

const editFiles = async (id, files) => {
    const accessToken = localStorage.getItem("accessToken");
    console.log(files)
    // const arrFiles = Array.from(files)

    // Create a new FormData object
    const formData = new FormData();
    
    // Append files to the FormData object
    for(const file of files)
    {
        console.log(files,"file");
        formData.append("file",file)
    }

    // Append announcementId to the FormData object

    // formData.append("announcementId", id);

    // Log the value of the "file" key
    console.log(formData.get("file"));

// Log the value of the "announcementId" key

    // console.log(formData.get("announcementId"));

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
            // const data = await res
            // console.log(data);
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