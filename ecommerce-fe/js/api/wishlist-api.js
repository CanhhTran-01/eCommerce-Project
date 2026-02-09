const token = localStorage.getItem('access_token');

export async function fetchWishlist() {
    const response = await fetch(`http://localhost:8080/eCommerce/api/users/me/wish-list`, {
        method: "GET",
        headers: {
            "Authorization": `Bearer ${token}`
        }
    });

    if (!response.ok) {
        throw new Error("Failed to fetch wish list");
    }

    return response.json();
}
