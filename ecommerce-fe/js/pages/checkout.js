import { checkout } from "../api/checkout-api.js";
import { formatVND } from "../utils/format.js";

const receiverName = document.getElementById('name');
const receiverPhone = document.getElementById('phone');
const shippingAddress = document.getElementById('address');
const note = document.getElementById('note');
const paymentMethod = document.getElementById('paymentMethod');
const orderItemsContainer = document.getElementById('orderItemsContainer');
const submitOrderBtn = document.getElementById('submitOrderBtn');
const btnSpinner = document.getElementById('btnSpinner');
const orderData = JSON.parse(sessionStorage.getItem('orderData'));


// call functions
initPage();

function initPage() {

    if (!orderData || orderData.length === 0) {
        alert("Không có dữ liệu đơn hàng.");
        window.location.href = "/ecommerce-fe/pages/index.html";
    }

    orderItemsContainer.innerHTML = orderData.map(item => `
                <div class="d-flex justify-content-between align-items-center border-bottom pb-3 mb-3">
                    <!-- LEFT: image + info -->
                    <div class="d-flex align-items-center">
                        <img src="${item.mainImageUrl}" class="img-thumbnail me-3" style="width:70px;height:70px;object-fit:cover;">
                        <div>
                            <div class="fw-semibold">${item.productName}</div>
                            <small class="text-muted">Số lượng: <span>${item.quantity}</span></small>
                        </div>
                    </div>

                    <!-- RIGHT: price -->
                    <div class="text-danger fw-bold"> ${formatVND(item.price * item.quantity)}</div>
                </div>
        `).join('');

    calculateTotal();
    initShippingListener();
}

function calculateTotal() {

    const subtotal = orderData.reduce((sum, item) => sum + item.price * item.quantity, 0);

    const selectedShipping = document.querySelector('input[name="shipping"]:checked');

    const shippingFee = parseInt(selectedShipping.dataset.fee);

    const total = subtotal + shippingFee;

    document.getElementById('subtotal').textContent = formatVND(subtotal);
    document.getElementById('shippingFee').textContent = formatVND(shippingFee);
    document.getElementById('total').textContent = formatVND(total);
}

function initShippingListener() {
    const shippingOptions = document.querySelectorAll('.shipping-option');

    shippingOptions.forEach(option => {
        option.addEventListener('change', calculateTotal);
    });
}

submitOrderBtn.addEventListener('click', async (event) => {
    event.preventDefault();

    if (!receiverName.value.trim()) {
        alert("Vui lòng nhập tên người nhận");
        return;
    }
    if (!receiverPhone.value.trim()) {
        alert("Vui lòng nhập số điện thoại");
        return;
    }
    if (!shippingAddress.value.trim()) {
        alert("Vui lòng nhập địa chỉ");
        return;
    }

    submitOrderBtn.classList.add('d-none');
    btnSpinner.classList.remove('d-none');

    const selectedShipping = document.querySelector('input[name="shipping"]:checked');
    const checkoutRequest = {
        receiverName: receiverName.value,
        receiverPhone: receiverPhone.value,
        shippingAddress: shippingAddress.value,

        itemRequestList: orderData.map(item => ({
            productId: item.productId,
            quantity: item.quantity
        })),

        note: note.value,
        shippingMethod: selectedShipping.value,
        paymentMethod: paymentMethod.value
    };

    try {
        const response = await checkout(checkoutRequest);
        sessionStorage.setItem('checkoutResponse', JSON.stringify(response.data));

        // redirect
        window.location.href = "/ecommerce-fe/pages/success-order-submit.html";

    } catch (error) {
        alert(error.message)
        submitOrderBtn.classList.remove('d-none');
        btnSpinner.classList.add('d-none');
    }
});