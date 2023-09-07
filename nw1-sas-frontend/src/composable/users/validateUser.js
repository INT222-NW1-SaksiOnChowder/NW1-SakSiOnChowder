import { getUsers } from "./getUser.js";

const validateUserInput = (userObj, mode, listUser) => {
    // const emailPattern = /([a-zA-Z0-9._-]+@[a-zA-Z0-9._-]+\.[a-zA-Z0-9._-]+)/g
    const emailPattern = /^[a-zA-Z0-9_%+-]+[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{1,}$/
    // const passwordPattern = /(?=.[a-z])(?=.[A-Z])(?=.\d)(?=.[@#$%^&+=!])/g
    const passwordPattern = /^(?!.*\s)(?=.*[A-Z])(?=.*[a-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,14}$/;
       
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

    if (mode === 'password') {
        if (userObj.password.length >= 8 && userObj.password.length <= 14) {
            if (userObj.password.match(passwordPattern) === null) {
                user.boolean = false
                user.message = 'Invalid format'
                return user
            }
            user.boolean = true
            user.message = 'Correct'
            return user
        } else {
            user.boolean = false
            user.message = 'Password must be 8-14 characters'
            return user
        }
    }

}



export { validateUserInput }

