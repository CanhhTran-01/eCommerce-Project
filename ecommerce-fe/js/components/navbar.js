import { checkToken } from "../api/check-token.js";
import { handleLogout } from "../pages/logout.js";
import { fetchSuggestions } from "../api/product-api.js";

const searchTextInput = document.getElementById('searchTextInput');
const searchTextBtn = document.getElementById('searchTextBtn');
const userInfoLink = document.getElementById('infoLink');
const myAccountLink = document.getElementById('myAccountLink');
const loginLink = document.getElementById('loginLink');
const logoutLinkInNavbar = document.getElementById('logoutLinkInNavbar');

const input = document.getElementById("searchTextInput");
const suggestionBox = document.getElementById("searchSuggestion");
const keywordBox = document.getElementById("suggestionKeywords");
const productBox = document.getElementById("suggestionProducts");
const defaultProductImage = "https://res.cloudinary.com/djw4qdufh/image/upload/v1772030872/avatar/cfcdd9b4-9850-4775-ac05-c87523b29439_no_image_product.png";

let timer;


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

// handle type search text
input.addEventListener("keyup", function () {

    const keyword = input.value.trim();

    clearTimeout(timer);

    if (keyword.length === 0) {
        suggestionBox.classList.add("d-none");
        return;
    }

    timer = setTimeout(async () => {

        const response = await fetchSuggestions(keyword);

        renderKeywords(response.data.suggestionTexts);
        renderProducts(response.data.productSuggestionResponses);

        suggestionBox.classList.remove("d-none");

    }, 300);

});

function renderKeywords(keywords) {

    keywordBox.innerHTML = "";

    keywords.forEach(k => {

        const item = document.createElement("div");

        item.className = "p-2 small border-bottom";
        item.style.cursor = "pointer";

        item.innerHTML = `<i class="fa fa-search me-2"></i>${k}`;

        item.onclick = () => {
            window.location.href =
                `${window.location.origin}/ecommerce-fe/pages/product-list.html?searchText=${encodeURIComponent(keywords)}`;
        };

        keywordBox.appendChild(item);

    });

}

function renderProducts(products) {

    productBox.innerHTML = "";

    products.forEach(p => {

        const item = document.createElement("div");

        item.className = "d-flex align-items-center p-2 border-bottom";
        item.style.cursor = "pointer";

        item.innerHTML = `
            <img src="${p.mainImageUrl ?? defaultProductImage}" width="40" height="40" class="me-2">
            <span>${p.productName}</span>
        `;

        item.onclick = () => {
            window.location.href =
                `${window.location.origin}/ecommerce-fe/pages/product-detail.html?productId=${p.id}`;
        };

        productBox.appendChild(item);

    });

}

// hide popup when click outside
document.addEventListener("click", function (e) {

    if (!e.target.closest(".search-box")) {
        suggestionBox.classList.add("d-none");
    }

});
