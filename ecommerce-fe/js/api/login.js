import { login } from "./auth-api.js";

const passwordInput = document.getElementById("password");


// normal login
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


// login with google 
document.getElementById('loginWithGoogle').addEventListener('click', (e) => {
    e.preventDefault();
    window.location.href = 'http://localhost:8080/eCommerce/api/auth/social-login?loginType=google';
});

// login with facebook 
document.getElementById('loginWithFacebook').addEventListener('click', (e) => {
    e.preventDefault();
    window.location.href = 'http://localhost:8080/eCommerce/api/auth/social-login?loginType=facebook';
});


document.getElementById("togglePassword").addEventListener("click", function () {
    const type = passwordInput.getAttribute("type") === "password" ? "text" : "password";
    passwordInput.setAttribute("type", type);

    this.querySelector("i").classList.toggle("fa-eye");
    this.querySelector("i").classList.toggle("fa-eye-slash");
});
