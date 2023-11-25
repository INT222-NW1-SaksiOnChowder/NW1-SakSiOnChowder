// import {role} from "../../stores/role"
const clearToken = () => {
    // const currentRole = role()
    localStorage.removeItem("accessToken");
    localStorage.removeItem("refreshToken");
    localStorage.removeItem("userDetail");
    // currentRole.setRole(undefined)
}

export { clearToken }