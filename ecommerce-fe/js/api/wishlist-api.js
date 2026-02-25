import { httpClient } from "../common/httpClient.js";

export async function fetchWishlist() {
    return httpClient('/users/me/wish-list');
}

export async function addProductToWishList(productId) {
    return httpClient('/users/me/wish-list', {
        method: 'POST',
        body: JSON.stringify({ productId })
    });
}
