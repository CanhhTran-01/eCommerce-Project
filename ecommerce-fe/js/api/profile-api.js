

export async function fetchProfile() {
    const token = localStorage.getItem('access_token');

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

