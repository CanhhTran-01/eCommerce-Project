
export async function fetchCategories() {
    const response = await fetch('http://localhost:8080/eCommerce/api/categories/list');

    if (!response.ok) {
        throw new Error('Failed to fetch categories');
    }

    return await response.json();
}