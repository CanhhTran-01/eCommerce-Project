const BASE_URL = 'http://localhost:8080/eCommerce/api';

let isHandlingUnauthorized = false; // a flag to fix bug repetitive alert()

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

    // handle session timeout
    if (response.status === 401) {

        if (!isHandlingUnauthorized) {
            isHandlingUnauthorized = true;

            alert('Hết phiên đăng nhập!');
            localStorage.removeItem('access_token');

            window.location.href = window.location.origin + "/ecommerce-fe/pages/index.html";
        }

        throw new Error('Unauthorized');
    }

    // handle error
    if (!response.ok) {
        let message = 'Request failed!';

        try {
            const err = await response.json();
            message = err.message || message;
        } catch {
            message = await response.text();
        }

        throw new Error(message);
    }

    const text = await response.text(); // handle DELETE method, empty body
    return text ? JSON.parse(text) : null;
}