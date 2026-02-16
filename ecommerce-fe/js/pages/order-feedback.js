import { getRatingFromCard } from "../components/rating-feedback.js";
import { sendReviewtoServer } from "../api/review-api.js";

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
            <div class="bg-white rounded shadow-sm p-3 mb-3" data-product-id="${item.productId}">
                <!-- Title -->
                <div class="d-flex mb-3">
                    <img src="${item.imageUrl}" class="rounded me-3" alt="product-item">
                    <div>
                        <h6 class="mb-1">${item.productName}</h6>
                    </div>
                </div>

                <!-- Rating -->
                <div class="fs-4 text-warning rating-stars">
                    ${[1, 2, 3, 4, 5].map(i => `
                        <i class="bi bi-star star" data-value="${i}"></i>
                    `).join('')}
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
                    <button class="btn btn-danger send-feedback">
                        <i class="bi bi-send"></i> Gửi đánh giá
                    </button>
                </div>   
            </div>                      
        `).join('');

    itemFeedback.addEventListener("click", function (e) {
        const star = e.target.closest(".star");
        if (!star) return;

        const container = star.closest("[data-product-id]");
        const value = Number(star.dataset.value);

        // save rating into dataset 
        container.dataset.rating = value;

        // update interface
        const stars = container.querySelectorAll(".star");

        stars.forEach(s => {
            const starValue = Number(s.dataset.value);

            if (starValue <= value) {
                s.classList.remove("bi-star");
                s.classList.add("bi-star-fill");
            } else {
                s.classList.remove("bi-star-fill");
                s.classList.add("bi-star");
            }
        });
    });

    itemFeedback.addEventListener("click", async function (e) {
        const button = e.target.closest(".send-feedback");
        if (!button) return;

        const card = button.closest("[data-product-id]");
        const productId = card.dataset.productId;

        const rating = getRatingFromCard(card);
        const title = card.querySelector("input").value;
        const comment = card.querySelector("textarea").value;

        const reviewRequest = {
            productId: Number(productId),
            rating: rating,
            title: title,
            comment: comment
        };

        try {
            const response = await sendReviewtoServer(reviewRequest);


            alert("Gửi đánh giá thành công!");
            card.innerHTML = `
                        <div class="text-success fw-semibold">
                            <i class="bi bi-check-circle"></i> Bạn đã đánh giá sản phẩm này.
                        </div>
                    `;

        } catch (error) {
            console.error(error);
            alert("Có lỗi xảy ra !");
        }

    });

}


document.getElementById('returnToIndexPageBtn').addEventListener('click', (e) => {
    e.preventDefault();
    window.location.href = window.location.origin + '/ecommerce-fe/pages/index.html';
});


