import jwt_decode from "jwt-decode";

const userDetailStore = () => {
  const accessToken = localStorage.getItem("accessToken");
  console.log(accessToken);
  if (accessToken !== undefined && accessToken !== null) {
    const userDetail = jwt_decode(accessToken);
    console.log(userDetail);
    return userDetail;
  }
  return false
};

export { userDetailStore };
