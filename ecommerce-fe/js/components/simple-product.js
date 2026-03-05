import { addItemToCartRedis } from '../api/cart-api.js';
import { loadCartData } from '../components/cart.js';
import { formatVND } from '../utils/format.js';

const defaultProductImage = "https://res.cloudinary.com/djw4qdufh/image/upload/v1772030872/avatar/cfcdd9b4-9850-4775-ac05-c87523b29439_no_image_product.png"

export function renderProductCard(data, container) {

    container.innerHTML = data.map(product => {
        const hasDiscount = product.discountPrice != null && product.discountPrice < product.price;
        const discountBadgeHTML = hasDiscount
            ? `<span class="discount-badge"> -${Math.round((1 - product.discountPrice / product.price) * 100)}%</span>`
            : '';
        const priceHTML = hasDiscount
            ? `<span class="current-price">${formatVND(product.discountPrice)}</span>
               <span class="original-price">${formatVND(product.price)}</span>`
            :
            `<span class="current-price">${formatVND(product.price)}</span>`;

        return `
                <div class="product-card" data-product-id="${product.id}">
                    <div class="product-image">
                        <img src="${product.mainImageUrl ?? defaultProductImage}" alt="${product.productName}">
                        ${discountBadgeHTML}
                    </div>
                    <div class="product-info">
                        <h6 style="white-space: nowrap; 
                                    overflow: hidden; 
                                    text-overflow: ellipsis;" 
                            class="product-name">${product.productName}</h6>
                        <div class="stars">     
                            <div class="stars-filled" style="width: ${(product.ratingAvg / 5) * 100}%"></div>                      
                            <small style="font-size: 10px;" class="text-muted">(${product.ratingCount} lượt đánh giá)</small>
                        </div>
                        <div class="product-price">
                            ${priceHTML}
                        </div>
                        <button class="add-to-cart-btn">Thêm vào giỏ hàng</button>
                        <button class="view-in-cart-btn d-none" data-bs-toggle="offcanvas" 
                                data-bs-target="#cartOffcanvas">Đặt mua ngay !</button>
                    </div>
                </div>
    `}).join('');


    // handle click product event
    container.addEventListener('click', (event) => {
        const targetCard = event.target.closest('.product-card');
        if (!targetCard) return;

        // skip redirect with button (add to cart)
        if (event.target.closest('button')) return;

        const productId = targetCard.dataset.productId;
        if (productId) {
            window.location.href =
                `${window.location.origin}/ecommerce-fe/pages/product-detail.html?productId=${productId}`;
        }
    });


    // handle click add to cart btn
    document.addEventListener("click", async (e) => {

        const addBtn = e.target.closest(".add-to-cart-btn");
        if (!addBtn) return;

        const productCard = addBtn.closest(".product-card");
        const productId = productCard.dataset.productId;

        const viewBtn = productCard.querySelector(".view-in-cart-btn");

        try {
            const response = await addItemToCartRedis(productId);
            alert(response.message);

            // hide add button
            addBtn.classList.add("d-none");

            // show view button
            viewBtn.classList.remove("d-none");

        } catch (error) {
            console.error(error);
        }
    });


    // handle click view in cart btn
    document.querySelectorAll('.view-in-cart-btn').forEach(btn => {
        btn.addEventListener("click", () => {
            loadCartData();
        });
    });
}

