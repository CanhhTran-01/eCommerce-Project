import { httpClient } from "../common/httpClient.js";


export function fetchProfile() {
    return httpClient('/accounts/me/info');
}


export async function updateProfileRequest(userId, dataRequest) {
    return httpClient(`/users/${userId}`, {
        method: 'PUT',
        body: JSON.stringify(dataRequest)
    });
}

