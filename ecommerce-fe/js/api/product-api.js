import { httpClient } from "../common/httpClient.js";

// get list of products on sale
export async function fetchSaleProducts() {
    return httpClient('/products/sale-list');
}


// get products by category ID
export async function fetchProductByCategoryId(categoryId) {
    return httpClient(`/categories/${categoryId}/products`);
}


// get products by filter search
export async function fetchProductByFilter(filter) {

    const params = new URLSearchParams();

    if (filter.searchText) {
        params.append("searchText", filter.searchText);
    }

    if (filter.categoryId && filter.categoryId.length > 0) {
        filter.categoryId.forEach(id => params.append("categoryId", id));
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

    return httpClient(`/products?${params}`);
}


// get product detail 
export async function fetchProductDetail(productId) {
    return httpClient(`/products/${productId}/detail`);
}


// get product review 
export async function fetchProductReviews(productId) {
    return httpClient(`/products/${productId}/reviews`);
}


// get related product for product detail page
export async function fetchRelatedProducts(productId) {
    return httpClient(`/products/${productId}/related`);
}


// get product gallery
export async function fetchProductGallery(productId) {
    return httpClient(`/product-gallery?productId=${productId}`);
}

// get product and keyword suggestions
export async function fetchSuggestions(keyword) {
    return httpClient(`/products/suggestion?keyword=${keyword}`);
}