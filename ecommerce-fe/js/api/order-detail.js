const token = localStorage.getItem('access_token');

export async function fetchOrderDetail(orderId) {
    const response = await fetch(`http://localhost:8080/eCommerce/api/orders/me?orderId=${orderId}`, {
        method: 'GET',
        headers: {
            'Authorization': `Bearer ${token}`,
        },
    });

    if (!response.ok) {
        throw new Error('Failed to fetch order detail;');
    }

    return response.json();
}