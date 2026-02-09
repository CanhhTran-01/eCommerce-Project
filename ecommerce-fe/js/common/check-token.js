
export async function checkToken() {
    const hasToken = !!localStorage.getItem('access_token');
    if (!hasToken) return false;

    try {
        const token = localStorage.getItem('access_token');
        const response = await fetch(`http://localhost:8080/eCommerce/api/auth/introspect`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ token }),
        });

        if (response.status == 401) throw new Error('Token hết hạn');
        if (response.status == 403) throw new Error('Sai token');

        return true;

    } catch (error) {
        console.log(error.message);
        return false;
    }
}

