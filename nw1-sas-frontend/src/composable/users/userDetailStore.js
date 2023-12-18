import jwt_decode from "jwt-decode";

const userDetailStore = () => {
  const accessToken = localStorage.getItem("accessToken");
  if (accessToken !== undefined && accessToken !== null) {
    const userDetail = jwt_decode(accessToken);
    return userDetail;
  }
  return false
};

export { userDetailStore };
