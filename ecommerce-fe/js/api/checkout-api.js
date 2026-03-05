import { httpClient } from "../common/httpClient.js";

export async function checkout(checkoutRequest) {
    return httpClient('/checkout', {
        method: 'POST',
        body: JSON.stringify(checkoutRequest)
    });
}
