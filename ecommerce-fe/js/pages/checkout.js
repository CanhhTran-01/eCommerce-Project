const submitOrderBtn = document.getElementById('submitOrderBtn');
const btnSpinner = document.getElementById('btnSpinner');
const orderData = {
    productId: 1,
    name: 'Áo thun nam',
    price: 200000,
    quantity: 1
};

// call functions
initPage();

function formatPrice(v) {
    return v.toLocaleString('vi-VN') + '₫';
}

function calculateTotal() {
    const subtotal = orderData.price * orderData.quantity;

    // Lấy shipping đang chọn
    const selectedShipping = document.querySelector('input[name="shipping"]:checked');
    const shippingFee = parseInt(selectedShipping.dataset.fee);

    const total = subtotal + shippingFee;

    // Update DOM
    document.getElementById('subtotal').textContent = formatPrice(subtotal);
    document.getElementById('shippingFee').textContent = formatPrice(shippingFee);
    document.getElementById('total').textContent = formatPrice(total);
}

function initShippingListener() {
    const shippingOptions = document.querySelectorAll('.shipping-option');

    shippingOptions.forEach(option => {
        option.addEventListener('change', calculateTotal);
    });
}

function initPage() {
    document.getElementById('productName').textContent = orderData.name;
    document.getElementById('quantity').textContent = orderData.quantity;
    document.getElementById('price').textContent = formatPrice(orderData.price);

    calculateTotal();       // Tính lần đầu
    initShippingListener(); // Lắng nghe thay đổi
}

function submitOrder() {
    const selectedShipping = document.querySelector('input[name="shipping"]:checked');

    const payload = {
        receiverName: document.getElementById('name').value,
        phone: document.getElementById('phone').value,
        address: document.getElementById('address').value,
        paymentMethod: document.getElementById('paymentMethod').value,
        shippingMethod: selectedShipping.value,
        shippingFee: parseInt(selectedShipping.dataset.fee),
        items: [
            { productId: orderData.productId, quantity: orderData.quantity }
        ]
    };

    console.log('Submit order payload:', payload);
    alert('Đặt hàng thành công');
}

submitOrderBtn.addEventListener('click', (event) => {
    event.preventDefault();

    submitOrderBtn.classList.add('d-none');
    btnSpinner.classList.remove('d-none');
    try {
        // call API

        window.location.href = "/ecommerce-fe/pages/success-order-submit.html";


    } catch (error) {
        alert(error.message)
        submitOrderBtn.classList.remove('d-none');
        btnSpinner.classList.add('d-none');
    }
});