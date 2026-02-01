
export function getAuthToken() {
    return localStorage.getItem('access_token');  // take token from local storage
}

export function isAuthenticated() {
    return !!localStorage.getItem('access_token');  // have token or not ?
}

export function logout() {
    localStorage.removeItem('access_token');
    window.location.href = window.location.origin + '/ecommerce-fe/index.html';
}

export function toggleAuthLinks(loginLink, logoutLink) {
    if (isAuthenticated()) {
        loginLink.classList.add('d-none');
        logoutLink.classList.remove('d-none');
    } else {
        loginLink.classList.remove('d-none');
        logoutLink.classList.add('d-none');
    }   
}

