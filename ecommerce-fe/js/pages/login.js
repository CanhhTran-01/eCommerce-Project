
import { login } from '../api/authApi.js';


export async function handleLogin(event) {
    const loginForm = document.getElementById('loginForm');
    const passwordError = document.getElementById('passwordError');

    loginForm.addEventListener('submit', async (e) => {
        e.preventDefault();

        const username = document.getElementById('username').value;
        const password = document.getElementById('password').value;

        try {
            const response = await login({ username, password });

            localStorage.setItem('access_token', response.data.token);
            window.location.href = window.location.origin + '/ecommerce-fe/index.html';

        } catch (loginError) {
            console.error(loginError);
            passwordError.innerText = 'Invalid username or password.';
            passwordError.classList.remove('d-none');
        }
    });
}
