import { logout } from "../api/auth-api.js";

export async function handleLogout(){
    const token = localStorage.getItem('access_token');
    
    await logout(token);
    localStorage.removeItem('access_token');
    window.location.href = window.location.origin + '/ecommerce-fe/pages/index.html';
}

