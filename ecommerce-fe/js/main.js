
import { handleLogin } from "./pages/login.js";
import { handleIndexPage, handleSaleProductsView, generateCategoryLinks} from "./pages/index.js";
import { isAuthenticated } from "./utils/auth.js";

// navigate to login page
if (window.location.pathname.endsWith('login.html')){
    handleLogin();
}

// navigate to index page
if (window.location.pathname.endsWith('index.html')){
    handleIndexPage(); // auth link toggling and logout handling
    generateCategoryLinks();  // gen category links
    handleSaleProductsView(); // load sale products  
}

// navigate to account info page
document.getElementById('infoLink').addEventListener('click', (e) => {

    // check authentication before accessing account info page
    if (!isAuthenticated()) {
        e.preventDefault(); // prevent default link behavior

        alert('Vui lòng đăng nhập để truy cập trang này.');
    }     

});

