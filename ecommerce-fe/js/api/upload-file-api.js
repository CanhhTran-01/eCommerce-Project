const token = localStorage.getItem('access_token');

export async function uploadAvatar(formData) {
    const response = await fetch(`http://localhost:8080/eCommerce/api/upload/avatar-image`, {
        method: 'POST',
        headers: {
            'Authorization': `Bearer ${token}`
        },
        body: formData
    });

    if (!response.ok) {
        throw Error("failed to upload avatar");
    }

    return response.json();
}