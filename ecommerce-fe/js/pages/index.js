
import { toggleAuthLinks, logout } from "../utils/auth.js";


export function handleIndexPage() {
    const loginLink = document.getElementById('loginLink');
    const logoutLink = document.getElementById('logoutLink');

    toggleAuthLinks(loginLink, logoutLink);

    logoutLink.addEventListener('click', (event) => {
        event.preventDefault();
        logout();
    });
}