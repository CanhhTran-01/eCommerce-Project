import { httpClient } from "../common/httpClient.js";

export async function fetchActiveOrderItemsByUser() {
    return httpClient('/order-items/me/active');
}

export async function fetchOrderItemsHistory() {
    return httpClient('/order-items/me/history');
}
