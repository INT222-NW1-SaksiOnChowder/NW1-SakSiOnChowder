import { getUsers } from "./getUser.js";

const validateUsernameNameEmail = (userObj, mode, listUser) => {
    const emailPattern = /([a-zA-Z0-9._-]+@[a-zA-Z0-9._-]+\.[a-zA-Z0-9._-]+)/g
    const user = {
        boolean: false,
        message: ""
    }
    if (mode === 'username') {
            if (userObj.username.length >= 1 && userObj.username.length <= 45) {
                for (const user of listUser) {
                    if (userObj.username === user.username) {
                        user.boolean = false
                        user.message = 'Username already exits'
                        return user
                    }
                }
                user.boolean = true
                user.message = 'Correct'
                return user
            } else {
                user.boolean = false
                user.message = 'Username must be 1-45 characters'
                return user
            }
        } 

    if (mode === 'name') {
            if (userObj.name.length >= 1 && userObj.name.length <= 100) {
                for (const user of listUser) {
                    if (userObj.name === user.name) {
                        user.boolean = false
                        user.message = 'Name already exits'
                        return user
                    }
                }
                user.boolean = true
                user.message = 'Correct'
                return user
            } else {
                user.boolean = false
                user.message = 'Name must be 1-100 characters'
                return user
            }
    }

    if (mode === 'email') {
            if (userObj.email.length >= 1 && userObj.email.length <= 150) {
                for (const user of listUser) {
                    if (userObj.email === user.email) {
                        user.boolean = false
                        user.message = 'Email already exits'
                        return user
                    }
                }
                if (userObj.email.match(emailPattern) === null) {
                    user.boolean = false
                    user.message = 'Invalid format'
                    return user
                }
                user.boolean = true
                user.message = 'Correct'
                return user
            } else {
                user.boolean = false
                user.message = 'Email must be 1-150 characters'
                return user
            }
    }

}



export { validateUsernameNameEmail }

