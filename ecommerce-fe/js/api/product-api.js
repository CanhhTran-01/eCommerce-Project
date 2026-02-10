
// get list of products on sale
export async function fetchSaleProducts() {
    const response = await fetch('http://localhost:8080/eCommerce/api/products/sale-list');

    if (!response.ok) {
        throw new Error('Failed to fetch sale products');
    }

    return response.json();
}


// get products by category ID
export async function fetchProductByCategoryId(categoryId) {
    const response = await fetch(`http://localhost:8080/eCommerce/api/categories/${categoryId}/products`);

    if (!response.ok) {
        throw new Error('Failed to fetch products by category ID');
    }

    return response.json();
}


// get product detail 
export async function fetchProductDetail(productId) {
    const response = await fetch(`http://localhost:8080/eCommerce/api/products/${productId}/detail`);

    if (!response.ok) {
        throw new Error('Failed to fetch products detail');
    }

    return response.json();
}


// get product review 
export async function fetchProductReviews(productId) {
    const response = await fetch(`http://localhost:8080/eCommerce/api/products/${productId}/reviews`);

    if (!response.ok) {
        throw new Error('Failed to fetch products detail');
    }

    return response.json();
}