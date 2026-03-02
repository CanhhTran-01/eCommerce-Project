import { checkToken } from "../api/check-token.js";
import { handleLogout } from "../pages/logout.js";
import { formatVND } from "../utils/format.js";

const searchTextInput = document.getElementById('searchTextInput');
const searchTextBtn = document.getElementById('searchTextBtn');
const userInfoLink = document.getElementById('infoLink');
const myAccountLink = document.getElementById('myAccountLink');
const loginLink = document.getElementById('loginLink');
const logoutLinkInNavbar = document.getElementById('logoutLinkInNavbar');


// call function
handleUserIcon();


async function handleUserIcon() {
    const isLoggedIn = await checkToken();

    // toggle view
    if (isLoggedIn) {
        loginLink.classList.add('d-none');
        logoutLinkInNavbar.classList.remove('d-none');
    } else {
        loginLink.classList.remove('d-none');
        logoutLinkInNavbar.classList.add('d-none');
    }

    // search text click event
    searchTextBtn.addEventListener('click', (e) => {
        e.preventDefault();

        const searchText = searchTextInput.value.trim();
        if (searchText) {
            window.location.href =
                `${window.location.origin}/ecommerce-fe/pages/product-list.html?searchText=${encodeURIComponent(searchText)}`;
        }
    });

    // profile click event
    userInfoLink.addEventListener('click', (event) => {
        if (!isLoggedIn) {
            event.preventDefault();
            alert('Vui lòng đăng nhập để truy cập trang này.');
        } else {
            window.location.href = window.location.origin + '/ecommerce-fe/pages/profile.html';
        }
    });

    // my account click event
    myAccountLink.addEventListener('click', (event) => {
        if (!isLoggedIn) {
            event.preventDefault();
            alert('Vui lòng đăng nhập để truy cập trang này.');
        } else {
            window.location.href = window.location.origin + '/ecommerce-fe/pages/my-account.html';
        }
    });

    // login click event
    loginLink.addEventListener('click', (event) => {
        event.preventDefault();
        window.location.href = window.location.origin + '/ecommerce-fe/pages/login.html';
    });

    // logout click event
    logoutLinkInNavbar.addEventListener('click', (event) => {
        event.preventDefault();
        handleLogout();
    });
}