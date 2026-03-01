import { httpClient } from "../common/httpClient.js";


export async function login(username, password) {
    return httpClient('/auth/login', {
        method: 'POST',
        body: JSON.stringify({ username, password })
    });
}


export async function logout(token) {
    await fetch(`http://localhost:8080/eCommerce/api/auth/logout`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({ token })
    });
}


export async function register(registerRequest) {
    return httpClient('/accounts', {
        method: 'POST',
        body: JSON.stringify(registerRequest)
    });
}


export async function forgotPass(forgotPassRequest) {
    return httpClient('/accounts/forgot-password', {
        method: 'POST',
        body: JSON.stringify(forgotPassRequest)
    });
}


export async function resetPass(updatePassRequest) {
    return httpClient('/accounts/reset-password', {
        method: 'PUT',
        body: JSON.stringify(updatePassRequest)
    });
}
