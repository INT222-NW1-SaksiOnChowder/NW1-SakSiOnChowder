import { getNewAccessToken } from "../users/getToken.js"

const ROOT_API = import.meta.env.VITE_ROOT_API;

const deleteAllFiles = async (id) => {
  const accessToken = localStorage.getItem("accessToken")
  try {
    const res = await fetch(`${ROOT_API}/api/files/${id}`,
      {
        method: "DELETE",
        headers: {
          Authorization: `Bearer ${accessToken}`
      },
      })
    if (res.ok) {
      console.log("Delete file Successfully");
      // alert("Delete Files in announcement id: " + id + " successfully")
      return true
    } else if (!res.ok) {
        console.error(`Error: ${res.status} - ${res.statusText}`);
    }
  } catch (err) {
    console.error("Error delete files:", err);
    await getNewAccessToken()
    return false
  }
};

const deleteFile = async (id, fileName) => {
  const accessToken = localStorage.getItem("accessToken")
  try {
    const res = await fetch(`${ROOT_API}/api/files/${id}/${fileName}`,
      {
        method: "DELETE",
        headers: {
          Authorization: `Bearer ${accessToken}`
      },
      })
    if (res.ok) {
      console.log(`Delete file : ${fileName} Successfully`);
      // alert("Delete Files in announcement id: " + id + " successfully")
      return true
    } else if (!res.ok) {
        console.error(`Error: ${res.status} - ${res.statusText}`);
    }
  } catch (err) {
    console.error("Error delete files:", err);
    await getNewAccessToken()
    return false
  }
};

export { deleteAllFiles, deleteFile };