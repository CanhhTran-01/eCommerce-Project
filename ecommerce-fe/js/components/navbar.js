import { checkToken } from "../common/check-token.js";
import { handleLogout } from "../common/logout.js";
import { formatVND } from "../utils/format.js";

const searchTextInput = document.getElementById('searchTextInput');
const searchTextBtn = document.getElementById('searchTextBtn');
const userInfoLink = document.getElementById('infoLink');
const myOrdersLink = document.getElementById('myOrdersLink')
const wishListLink = document.getElementById('wishListLink');
const loginLink = document.getElementById('loginLink');
const logoutLinkInNavbar = document.getElementById('logoutLinkInNavbar');
const cartItemsContainer = document.getElementById('cart-items');
const cartTotal = document.getElementById('cart-total');
const cartData = [
    { id: 1, name: 'iPhone 15 Pro', price: 26990000, quantity: 1, image: '/ecommerce-fe/assets/images/product1.jpg' },
    { id: 2, name: 'Samsung Galaxy S24 Ultra', price: 28990000, quantity: 2, image: '/ecommerce-fe/assets/images/product1.jpg' },
    { id: 3, name: 'OPPO Reno 11', price: 9990000, quantity: 1, image: '/ecommerce-fe/assets/images/product1.jpg' }
];


// call function
handleUserIcon();
renderCart();


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

    // my orders click event
    myOrdersLink.addEventListener('click', (event) => {
        if (!isLoggedIn) {
            event.preventDefault();
            alert('Vui lòng đăng nhập để truy cập trang này.');
        } else {
            window.location.href = window.location.origin + '/ecommerce-fe/pages/my-orders.html';
        }
    });

    // wishlist click event
    wishListLink.addEventListener('click', (event) => {
        if (!isLoggedIn) {
            event.preventDefault();
            alert('Vui lòng đăng nhập để truy cập trang này.');
        } else {
            window.location.href = window.location.origin + '/ecommerce-fe/pages/wishlist.html';
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



cartItemsContainer.addEventListener('click', (e) => {
    const itemDiv = e.target.closest('div[data-id]');
    if (!itemDiv) return;
    const id = parseInt(itemDiv.dataset.id);
    const item = cartData.find(i => i.id === id);

    if (e.target.classList.contains('increase')) {
        item.quantity++;
        renderCart();
    } else if (e.target.classList.contains('decrease')) {
        if (item.quantity > 1) item.quantity--;
        renderCart();
    } else if (e.target.classList.contains('remove')) {
        const index = cartData.findIndex(i => i.id === id);
        cartData.splice(index, 1);
        renderCart();
    }
});



function renderCart() {
    cartItemsContainer.innerHTML = '';
    let total = 0;

    cartData.forEach(item => {
        total += item.price * item.quantity;
        const div = document.createElement('div');
        div.classList.add('d-flex', 'align-items-center', 'mb-3', 'border-bottom', 'pb-2');
        div.dataset.id = item.id;

        div.innerHTML = `
            <input type="checkbox" class="form-check-input me-2">
            <img src="${item.image}" alt="${item.name}" class="me-3" style="width:60px; height:60px; object-fit:cover;">
            <div class="flex-grow-1">
                <h6 class="mb-1">${item.name}</h6>
                <div class="d-flex align-items-center">
                    <button class="btn btn-sm btn-outline-secondary me-2 decrease">-</button>
                    <span class="mx-1 quantity">${item.quantity}</span>
                    <button class="btn btn-sm btn-outline-secondary ms-2 increase">+</button>
                </div>
                <p class="mb-0 text-danger fw-bold">${(item.price * item.quantity).toLocaleString()} ₫</p>
            </div>
            <button class="btn btn-sm btn-outline-danger ms-2 remove">&times;</button>
            `;

        cartItemsContainer.appendChild(div);
    });

    cartTotal.innerText = formatVND(total);
}
