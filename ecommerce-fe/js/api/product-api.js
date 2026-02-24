
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


// get products by filter search
export async function fetchProductByFilter(filter) {

    const params = new URLSearchParams();

    if (filter.searchText) {
        params.append("searchText", filter.searchText);
    }
    if (filter.categoryId && filter.categoryId.length > 0) {
        filter.categoryId.forEach(id =>
            params.append("categoryId", id)
        );
    }
    if (filter.minPrice != null) {
        params.append("minPrice", filter.minPrice);
    }
    if (filter.maxPrice != null) {
        params.append("maxPrice", filter.maxPrice);
    }
    if (filter.sort) {
        params.append("sort", filter.sort);
    }

    const response = await fetch(`http://localhost:8080/eCommerce/api/products?${params.toString()}`);

    if (!response.ok) {
        throw new Error('Failed to fetch products by filter');
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


// get related product for product detail page
export async function fetchRelatedProducts(productId) {
    const response = await fetch(`http://localhost:8080/eCommerce/api/products/${productId}/related`);

    if (!response.ok) {
        throw new Error('Failed to fetch products detail');
    }

    return response.json();
}
