
import { handleLogin } from "./pages/login.js";


document.addEventListener('DOMContentLoaded', () => {
    if (window.location.pathname.includes('login')) {
        handleLogin();
    }
});

