import { httpClient } from "../common/httpClient.js";

export async function checkToken() {
    const token = localStorage.getItem('access_token');
    if (!token) return false;

    try {
        await httpClient('/auth/introspect', {
            method: 'POST',
            body: JSON.stringify({ token })
        });

        return true;
    } catch (error) {
        console.log(error.message);
        return false;
    }
}

