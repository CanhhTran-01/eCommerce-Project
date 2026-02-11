import { formatVND } from '../utils/format.js';

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
                        <img src="${product.imageUrl}" alt="${product.productName}">
                        ${discountBadgeHTML}
                        <button class="wishlist-btn">
                            <i class="far fa-heart"></i>
                        </button>
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
                        <button class="add-to-cart-btn">Add to Cart</button>
                    </div>
                </div>
    `}).join('');


    // handle click product event
    container.addEventListener('click', (event) => {
        const targetCard = event.target.closest('.product-card');
        if (!targetCard) return;

        // skip redirect with button (add to cart and wishlist)
        if (event.target.closest('button')) return;

        const productId = targetCard.dataset.productId;
        if (productId) {
            window.location.href =
                `${window.location.origin}/ecommerce-fe/pages/product-detail.html?productId=${productId}`;
        }
    });

}
