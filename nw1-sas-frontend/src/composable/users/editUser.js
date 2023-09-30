const ROOT_API = import.meta.env.VITE_ROOT_API;
const updateUser = async (user) => {
  const accessToken = localStorage.getItem("accessToken")
  try {
    const res = await fetch(`${ROOT_API}/api/users/${user.id}`, {
      method: "PUT",
      headers: {
        "content-type": "application/json",
        "Authorization": `Bearer ${accessToken}`
      },
      body: JSON.stringify(user),
    });
    if (res.status === 200) {
      console.log("Edit successfully");
      return true
    } else if (res.status === 400) {
      const error = await res.json();
      for (const err of error.detail) {
        if (err.errorMessage.includes("Email")) {
          alert(err.errorMessage);
        } else {
          alert(err.field + " " + err.errorMessage);
        }
      }
      console.log(error.detail);
      return error.detail
    }
  } catch (error) {
    console.log(`ERROR cannot create data: ${error}`);
  }
};

export { updateUser };
