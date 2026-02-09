const token = localStorage.getItem('access_token');

export async function fetchOrderItemsByUser() {
    const response = await fetch(`http://localhost:8080/eCommerce/api/order-items/me`, {
        method: "GET",
        headers: {
            "Authorization": `Bearer ${token}`
        }
    });

    if (!response.ok) {
        throw new Error("Failed to fetch order items");
    }

    return response.json();
}
