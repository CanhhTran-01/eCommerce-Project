
import { handleLogin } from "./pages/login.js";
import { handleIndexPage } from "./pages/index.js";


if (window.location.pathname.includes('login')){
    handleLogin();
}

if (window.location.pathname.includes('index')){
    handleIndexPage();
}
