import { checkToken } from "../api/check-token.js";
import { formatVND } from "../utils/format.js";
import { loadCartDataFromRedis } from "../api/cart-api.js";

const noCartData = document.getElementById('noCartData');
const cartItemsContainer = document.getElementById('cart-items');
const cartTotal = document.getElementById('cart-total');
const totalPriceBlock = document.getElementById('totalPriceBlock');
const checkoutBtn = document.getElementById('checkoutBtn');

let cartData = [
    {
        id: 1,
        name: "iPhone 15 Pro",
        price: 26990000,
        quantity: 1,
        image: "",
        checked: false
    },
    {
        id: 2,
        name: "Samsung Galaxy S24 Ultra",
        price: 28990000,
        quantity: 1,
        image: "",
        checked: false
    },
    {
        id: 3,
        name: "OPPO Reno 11",
        price: 9990000,
        quantity: 1,
        image: "",
        checked: false
    }
];


// load cart data from server before rendering
renderCart();
async function loadCartData() {
    try {
        const response = await loadCartDataFromRedis();

        cartData = response.data;
        // renderCart();

    } catch (error) {
        console.log(error);
        alert(error.message);
    }

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

cartItemsContainer.addEventListener('change', (e) => {
    if (e.target.classList.contains('item-check')) {
        const itemDiv = e.target.closest('div[data-id]');
        const id = parseInt(itemDiv.dataset.id);
        const item = cartData.find(i => i.id === id);

        item.checked = e.target.checked;
        renderCart();
    }
});

async function renderCart() {
    const isLoggedIn = await checkToken();

    if (isLoggedIn) {
        cartItemsContainer.innerHTML = '';
        let total = 0;

        cartData.forEach(item => {

            if (item.checked) {
                total += item.price * item.quantity;
            }

            const div = document.createElement('div');

            div.classList.add('d-flex', 'align-items-center', 'mb-3', 'border-bottom', 'pb-2');
            div.dataset.id = item.id;

            div.innerHTML = `
                <input type="checkbox" class="form-check-input me-2 item-check" ${item.checked ? 'checked' : ''}>
                <img src="${item.image}" alt="${item.name}" class="me-3" style="width:60px; height:60px; object-fit:cover;">
                <div class="flex-grow-1">
                    <h6 class="mb-1">${item.name}</h6>
                    <div class="d-flex align-items-center">
                        <button class="btn btn-sm btn-outline-secondary me-2 decrease">-</button>
                        <span class="mx-1 quantity">${item.quantity}</span>
                        <button class="btn btn-sm btn-outline-secondary ms-2 increase">+</button>
                    </div>
                    <p class="mb-0 text-danger fw-bold">${formatVND(item.price * item.quantity)}</p>
                </div>
                <button class="btn btn-sm btn-outline-danger ms-2 remove">&times;</button>
            `;

            cartItemsContainer.appendChild(div);
        });

        noCartData.classList.add('d-none');
        totalPriceBlock.classList.remove('d-none');
        checkoutBtn.classList.remove('d-none');

        cartTotal.innerHTML = `<strong>${formatVND(total)}</strong>`;
    }
}

checkoutBtn.addEventListener('click', (event) => {
    event.preventDefault();

    const selectedItems = cartData.filter(item => item.checked);

    if (selectedItems.length === 0) {
        alert("Chọn ít nhất 1 sản phẩm.");
        return;
    }

    // save in sessionStorage
    sessionStorage.setItem('orderData', JSON.stringify(selectedItems));

    // redirect
    window.location.href = window.location.origin + "/ecommerce-fe/pages/checkout.html";
});