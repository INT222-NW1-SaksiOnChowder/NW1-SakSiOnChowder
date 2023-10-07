import {role} from "../../stores/role"
const clearToken = () =>{
    const currentRole = role()
    localStorage.removeItem("accessToken");
    localStorage.removeItem("refreshToken");
    currentRole.setRole(undefined)
}

export {clearToken}