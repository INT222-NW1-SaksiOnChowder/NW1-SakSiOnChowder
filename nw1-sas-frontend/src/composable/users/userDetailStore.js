import jwt_decode from "jwt-decode"

const userDetailStore = () => {
    const accessToken = localStorage.getItem("accessToken")
    const userDetail = jwt_decode(accessToken)
    console.log(userDetail);
    return userDetail
}

export {userDetailStore} 