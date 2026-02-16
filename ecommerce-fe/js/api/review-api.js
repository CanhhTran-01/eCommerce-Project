const token = localStorage.getItem('access_token');

export async function sendReviewtoServer(reviewRequest) {
    const response = await fetch(`http://localhost:8080/eCommerce/api/reviews`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${token}`
        },
        body: JSON.stringify(reviewRequest)
    });

    if (!response.ok) {
        throw new Error("Failed to submit review");
    }

    return response.json();
}