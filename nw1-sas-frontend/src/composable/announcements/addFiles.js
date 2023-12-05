import { getNewAccessToken } from "../users/getToken.js"

const ROOT_API = import.meta.env.VITE_ROOT_API
// const addFiles = async (id, files) => {
//     // const filesArray = Array.from(files)
//     // console.log(filesArray)
//     const arr = Array.from(files)
//     console.log(arr[0])
//     const accessToken = localStorage.getItem("accessToken")
//     try {
//         const res = await fetch(`${ROOT_API}/api/files?file=${arr[0]}&announcementId=${id}`,
//             {
//                 method: 'POST',
//                 headers: {
//                     'content-type': 'application/json',
//                     "Authorization": `Bearer ${accessToken}`
//                 },
//                 // body: JSON.stringify(announcement)
//             }
//         )
//         if (res.status === 200) {
//             const result = await res.json()
//             console.log(result);
//             console.log('Create successfully')
//             return true
//         } else if (res.status !== 200) {
//             const error = await res.json()
//             for (const err of error.detail) {
//                 alert(err.field + " " + err.errorMessage);
//             }
//             return false
//         }
//     } catch (error) {
//         console.log(`ERROR cannot create data: ${error}`);
//         await getNewAccessToken()
//         return false
//     }
// }

// const addFiles = async (id, files) => {
//     const accessToken = localStorage.getItem("accessToken");
//     const formData = new FormData();

//     for (let i = 0; i < files.length; i++) {
//         formData.append('file', files[i]);
//     }

//     formData.append('announcementId', id);

//     try {
//         const res = await fetch(`${ROOT_API}/api/files`, {
//             method: 'POST',
//             headers: {
//                 "Authorization": `Bearer ${accessToken}`
//             },
//             body: formData
//         });

//         if (res.ok) {
//             const result = await res.json();
//             console.log(result);
//             console.log('Create successfully');
//             return true;
//         } else {
//             const error = await res.json();
//             for (const err of error.detail) {
//                 alert(err.field + " " + err.errorMessage);
//             }
//             return false;
//         }
//     } catch (error) {
//         console.log(`ERROR cannot create data: ${error}`);
//         await getNewAccessToken();
//         return false;
//     }
// };

const addFiles = async (id, files) => {
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
    formData.append("announcementId", id);
    // Log the value of the "file" key
    console.log(formData.get("file"));

// Log the value of the "announcementId" key
    console.log(formData.get("announcementId"));

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
            return true
        } else {
            // const data = await res
            // console.log(data);
            console.log(res)
            return false
        }
    } catch (error) {
        console.error("Error uploading files:", error);
        return false
    }
};



export { addFiles }