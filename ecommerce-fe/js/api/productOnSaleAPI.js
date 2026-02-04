

export async function fetchSaleProducts() {
    const response = await fetch('http://localhost:8080/eCommerce/api/products/sale-list');

    if (!response.ok) {
        throw new Error('Failed to fetch sale products');
    }

    return response.json();
}