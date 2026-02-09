import { login } from "../api/auth-api.js";


document.getElementById('loginForm').addEventListener('submit', async (e) => {
    e.preventDefault();

    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;
    const passwordError = document.getElementById('passwordError');

    try {
        const response = await login(username, password);
        localStorage.setItem('access_token', response.data.token);

        window.location.href = window.location.origin + '/ecommerce-fe/pages/index.html';

    } catch (loginError) {
        console.error(loginError);
        passwordError.innerText = 'Invalid username or password.';
        passwordError.classList.remove('d-none');
    }
});

