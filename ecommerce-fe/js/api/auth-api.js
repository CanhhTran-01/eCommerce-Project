
export async function login(username, password) {
    const response = await fetch(`http://localhost:8080/eCommerce/api/auth/login`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({ username, password }),
    });

    if (!response.ok) {
        throw new Error('Login failed');
    }

    return response.json();
}


export async function logout(token) {
    await fetch(`http://localhost:8080/eCommerce/api/auth/logout`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({ token })
    });
}

