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
                <div class="product-card">
                    <div class="product-image">
                        <img src="${product.imageUrl}" alt="${product.productName}">
                        ${discountBadgeHTML}
                        <button class="wishlist-btn">
                            <i class="far fa-heart"></i>
                        </button>
                    </div>
                    <div class="product-info">
                        <h6 class="product-name">${product.productName}</h6>
                        <div class="product-rating">
                            <span class="stars">★★★★★</span>
                            <span class="review-count">(${product.reviewCount})</span>
                        </div>
                        <div class="product-price">
                            ${priceHTML}
                        </div>
                        <button class="add-to-cart-btn">Add to Cart</button>
                    </div>
                </div>
    `}).join('');
}
