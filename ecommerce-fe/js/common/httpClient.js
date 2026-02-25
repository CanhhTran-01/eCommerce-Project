const BASE_URL = 'http://localhost:8080/eCommerce/api';

export async function httpClient(url, options = {}) {
    const token = localStorage.getItem('access_token');

    const response = await fetch(BASE_URL + url, {
        ...options,
        headers: {
            'Content-Type': 'application/json',
            ...(token && { Authorization: `Bearer ${token}` }),
            ...options.headers
        }
    });

    if (response.status === 401) {
        alert('Hết phiên đăng nhập !');
        window.location.href = window.location.origin + "/ecommerce-fe/pages/index.html";
        throw new Error('Unauthorized');
    }

    if (!response.ok) {
        let message = 'Request failed !';

        try {
            const err = await response.json();
            message = err.message || message;
        } catch {
            message = await response.text();
        }

        throw new Error(message);
    }

    return response.json();
}