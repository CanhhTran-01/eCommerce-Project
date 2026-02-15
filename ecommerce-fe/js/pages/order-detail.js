import { fetchOrderDetail } from '../api/order-detail.js';
import { renderOrderProgress } from '../components/order-progress.js';
import { formatVND, formatDateVN } from '../utils/format.js';
import { renderStatusBadgeHTML } from '../utils/status-badge.js';

const orderId = Number(new URLSearchParams(window.location.search).get('orderId'));
const orderDetailTitle = document.getElementById('orderDetailTitle');
const shippingInfo = document.getElementById('shippingInfo');
const orderProgress = document.getElementById('orderProgress');
const orderItems = document.getElementById('orderItems');
const totalAmount = document.getElementById('totalAmount');
const shippingFee = document.getElementById('shippingFee');
const paymentMethod = document.getElementById('paymentMethod');
const finalAmount = document.getElementById('finalAmount');
const repurchaseItemsHTML = document.getElementById('repurchaseItems');


// call functions
handleOrderDetailPage();


async function handleOrderDetailPage() {
    try {
        const response = await fetchOrderDetail(orderId);
        sessionStorage.setItem('order_items_for_repurchase', 
            JSON.stringify(response.data.orderItemResponseList));

        orderDetailTitle.innerHTML = `
                    <div>
                        <h5 class="mb-1">Đơn hàng #${response.data.orderCode || '...'}</h5>
                        <small class="text-muted">Ngày đặt: ${formatDateVN(response.data.updatedAt) || '...'}</small>
                    </div>
                    ${renderStatusBadgeHTML(response.data.status)}
            `;

        shippingInfo.innerHTML = `
                    <p class="mb-1 fw-semibold">${response.data.receiverName || '...'}</p>
                    <p class="mb-1 text-muted">Số điện thoại: ${response.data.receiverPhone || '...'}</p>
                    <p class="mb-0 text-muted">Địa chỉ nhận hàng: ${response.data.shippingAddress || '...'}</p>
            `;

        renderOrderProgress(orderProgress,response.data.status);

        orderItems.innerHTML = response.data.orderItemResponseList.map(item => `
                    <div class="d-flex mb-3">
                        <img src="${item.imageUrl}" class="rounded me-3" alt="order-item">
                        <div class="flex-grow-1">
                            <h6 class="mb-1">${item.productName || '...'}</h6>
                            <small class="text-muted">Số lượng: x${item.quantity || '...'}</small>
                        </div>
                        <div class="text-end">
                            <span class="fw-semibold text-danger">${formatVND(item.totalPrice) || '...'}</span>
                        </div>
                    </div>
            `).join('');

        totalAmount.innerText = formatVND(response.data.totalAmount);
        shippingFee.innerText = formatVND(response.data.shippingFee);
        paymentMethod.innerText = response.data.shippingMethod;
        finalAmount.innerHTML = formatVND(response.data.finalAmount);

    } catch (error) {
        console.log(error);
        alert('Có lỗi xảy ra - không thể load đơn hàng');
    }
}


document.getElementById('repurchaseBtn').addEventListener('click', (e) => {
    e.preventDefault();

    const repurchaseItemsObj = JSON.parse(sessionStorage.getItem('order_items_for_repurchase'));
    repurchaseItemsHTML.innerHTML = repurchaseItemsObj.map(item => `
                <div class="d-flex align-items-center border-bottom pb-3 mb-3">
                    <img src="${item.imageUrl}" class="rounded me-3" alt="order-item">
                    <div class="flex-grow-1">
                        <h6 class="mb-1">${item.productName || '...'}</h6>
                        <small class="text-muted">Đã mua: x${item.quantity || '...'}</small>
                    </div>
                    <button class="btn btn-sm btn-danger">
                        <i class="bi bi-cart-plus"></i> Thêm vào giỏ hàng
                    </button>
                </div>
        `).join('');

});

