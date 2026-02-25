import { httpClient } from "../common/httpClient.js";

export async function fetchOrderDetail(orderId) {
    return httpClient(`/orders/me?orderId=${orderId}`);
}