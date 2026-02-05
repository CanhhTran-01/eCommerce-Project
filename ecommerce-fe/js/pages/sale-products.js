
import { formatVND } from '../utils/format.js';

document.addEventListener('DOMContentLoaded', () => {
    renderSaleProductsPage();
});

// Initialize sale products page
function renderSaleProductsPage() {
    const saleProductList = document.getElementById('saleProductList');

    const saleProducts = JSON.parse(sessionStorage.getItem('saleProducts'));
    generateSaleProductCard(saleProducts.data, saleProductList);
}

// render sale product list HTML
function generateSaleProductCard(saleProducts, saleProductList) {
    saleProductList.innerHTML = saleProducts.map(product => `
                <div class="product-card">
                    <div class="product-image">
                        <img src="${product.imageUrl}" alt="${product.productName}">
                        <span class="discount-badge">-${Math.round((1 - product.discountPrice / product.price) * 100)}%</span>
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
                            <span class="current-price">${formatVND(product.discountPrice)}</span>
                            <span class="original-price">${formatVND(product.price)}</span>
                        </div>
                        <button class="add-to-cart-btn">Add to Cart</button>
                    </div>
                </div>
    `).join('');
}