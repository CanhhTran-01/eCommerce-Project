const token = localStorage.getItem('access_token');

export async function fetchProfile() {
    const response = await fetch('http://localhost:8080/eCommerce/api/me/info', {
        method: 'GET',
        headers: {
            'Authorization': `Bearer ${token}`,
        },
    });

    if (response.status === 401) {
        throw new Error('Unauthorized');
    }

    if (!response.ok) {
        throw new Error('Failed to get user info');
    }

    return response.json();
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

