import { httpClient } from "../common/httpClient.js";

export async function sendReviewtoServer(reviewRequest) {
    return httpClient('/reviews', {
        method: 'POST',
        body: JSON.stringify(reviewRequest)
    });
}