import { getNewAccessToken } from "./getToken.js";
const ROOT_API = import.meta.env.VITE_ROOT_API;
const deleteUser = async (id) => {
  const accessToken = localStorage.getItem("accessToken");
  try {
    const res = await fetch(`${ROOT_API}/api/users/${id}`,
      {
        method: "DELETE",
        headers: {
          "Authorization": `Bearer ${accessToken}`
        }
      })
    if (res.ok) {
      console.log("Delete Successfully");
      return true
    } else if (res.status !== 200) {
      const error = await res.json();
      alert(error.message);
    }
  } catch (err) {
    alert(`Error: ${err}`);
    await getNewAccessToken()
    return false
  }
};

export { deleteUser };
