import { formatVND } from "../utils/format.js";
import { renderStatusBadgeHTML } from "../utils/status-badge.js";

export function renderOrderItem(data, container){
    if (!data || data.length === 0) {
        container.innerHTML = '<p class="text-danger"><strong>Bạn chưa có đơn đặt.</strong></p>';
        return;
    }
    container.innerHTML = data.map(item => `
            <div class="list-group-item">
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
                        <button id="orderDetailBtn" class="btn btn-outline-danger btn-sm">Chi tiết</button>
                    </div>

                </div>
            </div>
    `).join('');
}