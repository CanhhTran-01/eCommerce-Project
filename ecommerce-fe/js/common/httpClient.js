
export async function httpClient(url, options = {}) {
    const token = getAccessToken();

    const res = await fetch(url, {
        ...options,
        headers: {
            'Content-Type': 'application/json',
            ...(token && { Authorization: `Bearer ${token}` }),
            ...options.headers
        }
    });

    // session expired
    if (res.status === 401) {
        alert ('Hết phiên đăng nhập !'); // it's only a specific subset, perhaps not authenticated is more reasonable
        throw new Error('Unauthorized');
    }

    // generic error handling
    if (!res.ok) {
        const message = await res.text();
        throw new Error(message || 'Request failed');
    }

    return res.json();
}