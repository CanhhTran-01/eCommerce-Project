import { checkToken } from "../api/check-token.js";
import { formatVND } from "../utils/format.js";
import { deleteItemFromCartRedis, loadCartDataFromRedis } from "../api/cart-api.js";

const waittingLoadCart = document.getElementById('waittingLoadCart');
const noCartData = document.getElementById('noCartData');
const cartBtn = document.getElementById('cartBtn');
const cartCount = document.getElementById('cartCount');
const cartItemsContainer = document.getElementById('cart-items');
const cartTotal = document.getElementById('cart-total');
const totalPriceBlock = document.getElementById('totalPriceBlock');
const checkoutBtn = document.getElementById('checkoutBtn');

let cartData = [];

// load cart data from server before rendering
loadCartData();

export async function loadCartData() {

    const isLoggedIn = await checkToken();
    if (!isLoggedIn) {
        noCartData.classList.remove('d-none');
        return;
    }

    waittingLoadCart.classList.remove('d-none');
    try {
        const response = await loadCartDataFromRedis();

        cartData = response.data;
        if (cartData.length <= 0) {
            waittingLoadCart.classList.add('d-none');
            noCartData.classList.remove('d-none');
            return;
        }

        cartCount.innerText = cartData.length;
        renderCart();

    } catch (error) {
        console.log(error);
        waittingLoadCart.classList.add('d-none');
        alert(error.message);
    }

}

function renderCart() {

    cartItemsContainer.innerHTML = '';
    let total = 0;

    cartData.forEach(item => {

        if (item.checked) {
            total += item.price * item.quantity;
        }

        const div = document.createElement('div');
        div.classList.add('d-flex', 'align-items-center', 'mb-3', 'border-bottom', 'pb-2');
        div.dataset.productId = item.productId;

        div.innerHTML = `
            <input type="checkbox" class="form-check-input me-2 item-check" ${item.checked ? 'checked' : ''}>
            <img src="${item.mainImageUrl}" alt="${item.productName}" class="me-3" style="width:60px; height:60px; object-fit:cover;">
            <div class="flex-grow-1">
                <h6 class="mb-1">${item.productName}</h6>
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

    waittingLoadCart.classList.add('d-none');
    noCartData.classList.add('d-none');
    totalPriceBlock.classList.remove('d-none');
    checkoutBtn.classList.remove('d-none');

    cartTotal.innerHTML = `<strong>${formatVND(total)}</strong>`;
}

cartBtn.addEventListener('click', (e) => {
    loadCartData();
});

cartItemsContainer.addEventListener('click', async (e) => {

    const itemDiv = e.target.closest('div[data-product-id]');
    if (!itemDiv) return;

    const productId = parseInt(itemDiv.dataset.productId);

    const item = cartData.find(i => i.productId === productId);

    if (e.target.classList.contains('increase')) {

        item.quantity++;
        renderCart();

    } else if (e.target.classList.contains('decrease')) {

        if (item.quantity > 1) item.quantity--;
        renderCart();

    } else if (e.target.classList.contains('remove')) {

        if (confirm('Xóa sản phẩm này khỏi giỏ hàng của bạn ?')) {
            try {
                await deleteItemFromCartRedis(productId);

                const index = cartData.findIndex(i => i.productId === productId);
                cartData.splice(index, 1);

                renderCart();

            } catch (error) {
                console.log(error);
                alert("Xóa sản phẩm thất bại");
            }
        }

    }

});

cartItemsContainer.addEventListener('change', (e) => {

    if (!e.target.classList.contains('item-check')) return;

    const itemDiv = e.target.closest('div[data-product-id]');
    if (!itemDiv) return;

    const productId = parseInt(itemDiv.dataset.productId);

    const item = cartData.find(i => i.productId === productId);
    if (!item) return;

    item.checked = e.target.checked;

    renderCart();

});

checkoutBtn.addEventListener('click', (event) => {

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