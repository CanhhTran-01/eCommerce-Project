import { httpClient } from "../common/httpClient.js";

export async function loadCartDataFromRedis() {
    return httpClient('/cart/me/items');
}

export async function addItemToCartRedis(productId) {
    return httpClient(`/cart/me/item`, {
        method: 'POST',
        body: JSON.stringify({ productId })
    });
}

export async function deleteItemFromCartRedis(productId) {
    return httpClient(`/cart/me/items/${productId}`, {
        method: 'DELETE'
    });
}