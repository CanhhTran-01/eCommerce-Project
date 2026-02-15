import { formatVND } from "../utils/format.js";
import { renderStatusBadgeHTML } from "../utils/status-badge.js";

export function renderOrderItem(data, container){
    if (!data || data.length === 0) {
        container.innerHTML = '<p class="text-danger"><strong>Không có dữ liệu.</strong></p>';
        return;
    }
    container.innerHTML = data.map(item => `
            <div class="list-group-item" data-order-id="${item.orderId}">
                <div class="row align-items-center">

                    <!-- product -->
                    <div class="col-md-5 d-flex align-items-center">
                        <img src="${item.imageUrl}" class="img-thumbnail me-3"
                            style="width:70px;height:70px;object-fit:cover;">

                        <div>
                            <div class="fw-semibold">${item.productName}</div>
                            <small class="text-muted">SL: ${item.quantity}</small>
                        </div>
                    </div>

                    <!-- status -->
                    <div class="col-md-2 text-md-center">
                        ${renderStatusBadgeHTML(item.status)}
                    </div>

                    <!-- price -->
                    <div class="col-md-3 text-danger fw-semibold text-md-end">
                        ${formatVND(item.totalPrice)}
                    </div>

                    <!-- action -->
                    <div class="col-md-2 text-md-end mt-2 mt-md-0">
                        <button id="orderDetailBtn" class="btn btn-outline-danger btn-sm">Chi tiết đơn</button>
                    </div>

                </div>
            </div>
    `).join('');

    document.getElementById('orderDetailBtn').addEventListener('click', (event) => {
        event.preventDefault();
        const target = event.target.closest('.list-group-item');
            if (target) {
                const orderId = target.dataset.orderId;
                if (orderId) {
                    window.location.href =
                        `${window.location.origin}/ecommerce-fe/pages/order-detail.html?orderId=${orderId}`;
                }
            }
    });
}