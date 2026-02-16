

const orderDetailCache = JSON.parse(sessionStorage.getItem('order_detail'));
const feedbackTitle = document.getElementById('feedbackTitle');
const itemFeedback = document.getElementById('itemFeedback');


// call functions
handleOrderFeedbackPage();


function handleOrderFeedbackPage() {
    feedbackTitle.innerHTML = `
            <div class="fw-semibold">Đánh giá đơn hàng #${orderDetailCache.orderCode}</div>
            <div class="small text-muted">${orderDetailCache.orderItemResponseList.length} sản phẩm cần đánh giá</div>
        `;
    itemFeedback.innerHTML = orderDetailCache.orderItemResponseList.map(item => `
            <div class="bg-white rounded shadow-sm p-3 mb-3">
                <!-- Title -->
                <div class="d-flex mb-3">
                    <img src="${item.imageUrl}" class="rounded me-3" alt="product-item">
                    <div>
                        <h6 class="mb-1">${item.productName}</h6>
                    </div>
                </div>

                <!-- Rating -->
                <div class="mb-3">
                    <div class="mb-1 fw-semibold">Đánh giá của bạn</div>
                    <div class="fs-4 text-warning">
                        <i class="bi bi-star"></i>
                        <i class="bi bi-star"></i>
                        <i class="bi bi-star"></i>
                        <i class="bi bi-star"></i>
                        <i class="bi bi-star"></i>
                    </div>
                </div>

                <!-- Title -->
                <div class="mb-3">
                    <label class="form-label fw-semibold">Tiêu đề đánh giá</label>
                    <input type="text" class="form-control" maxlength="100" placeholder="Ví dụ: Sản phẩm rất đáng tiền">
                    <div class="text-end small text-muted">0/100</div>
                </div>

                <!-- Comment -->
                <div class="mb-3">
                    <label class="form-label fw-semibold">Nội dung đánh giá</label>
                    <textarea class="form-control" rows="3" maxlength="500"
                        placeholder="Hãy chia sẻ cảm nhận chi tiết của bạn về sản phẩm..."></textarea>
                    <div class="text-end small text-muted">0/500</div>
                </div>

                <!-- Submit -->
                <div class="d-grid">
                    <button id="sendFeedback" class="btn btn-danger">
                        <i class="bi bi-send"></i> Gửi đánh giá
                    </button>
                </div>   
            </div>                      
        `).join('');
   
}


document.getElementById('returnToIndexPageBtn').addEventListener('click', (e) => {
    e.preventDefault();
    window.location.href = window.location.origin + '/ecommerce-fe/pages/index.html';
});