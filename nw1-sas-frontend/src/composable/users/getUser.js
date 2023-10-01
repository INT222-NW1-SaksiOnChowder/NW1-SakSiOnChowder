const ROOT_API = import.meta.env.VITE_ROOT_API;
import { getNewAccessToken } from "./getToken.js";

const getUsers = async () => {
  console.log('EEE');
  const accessToken = localStorage.getItem("accessToken");
  console.log(accessToken);
  try {
    const res = await fetch(`${ROOT_API}/api/users`,
      {
        method: "GET",
        headers: {
          "Authorization": `Bearer ${accessToken}`,
        },
      });
    if (res.status === 200) {
      const user = await res.json();
      console.log("Alls Successfully");
      console.log(user);
      return user;
    } else if (res.status !== 200) {
      const error = await res.json();
      alert(error.message);
      console.log('else');
    }
  } catch (error) {
    console.log(`ERROR cannot read data: ${error}`);
    await getNewAccessToken();
    return false
  }
};

const getUser = async (id) => {
  const accessToken = localStorage.getItem("accessToken");
  let user = undefined;
  try {
    const res = await fetch(`${ROOT_API}/api/users/${id}`, {
      method: "GET",
      headers: {
        "Authorization": `Bearer ${accessToken}`,
      },
    });

    if (res.status === 200) {
      user = await res.json();
      console.log("Detail Successfully");
      return user;
    } else if (res.status !== 200) {
      const error = await res.json();
      alert(error.message);
    }
  } catch (error) {
    console.log(`ERROR cannot read data: ${error}`);
    await getNewAccessToken();
  }
};

export { getUsers, getUser };
