const token = localStorage.getItem('access_token');

import { httpClient } from "../common/httpClient.js";

export function fetchProfile() {
    return httpClient('/accounts/me/info');
}


export async function updateProfileRequest(userId, dataRequest) {
    const response = await fetch(`http://localhost:8080/eCommerce/api/users/${userId}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`
        },
        body: JSON.stringify(dataRequest)
    });

    if (!response.ok) {
        throw new Error('Failed to update user info');
    }

    return response.json();
};

