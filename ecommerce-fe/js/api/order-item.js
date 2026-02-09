const token = localStorage.getItem('access_token');

export async function fetchActiveOrderItemsByUser() {
    const response = await fetch(`http://localhost:8080/eCommerce/api/order-items/me/active`, {
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


export async function fetchOrderItemsHistory() {
    const response = await fetch(`http://localhost:8080/eCommerce/api/order-items/me/history`, {
        method: "GET",
        headers: {
            "Authorization": `Bearer ${token}`
        }
    });

    if (!response.ok) {
        throw new Error("Failed to fetch order items history");
    }

    return response.json();
}
