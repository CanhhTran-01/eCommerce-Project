
import { formatVND } from "../utils/format.js";
import { fetchProductByCategoryId } from "../api/productApi.js";


document.addEventListener('DOMContentLoaded', () => {
    generateProductByCategoryView();
});
document.addEventListener('DOMContentLoaded', () => {
    generateCategoryViewForSidebar();
});

// handle product listing by category view
async function generateProductByCategoryView() {
    const categoryId = Number(new URLSearchParams(window.location.search).get('categoryId'));
    const productViewList = document.getElementById('productViewList');

    try {
        const products = await fetchProductByCategoryId(categoryId);

        // Clear existing products
        productViewList.innerHTML = '';

        // Handle case when no products are found
        if (!products || products.data.length === 0) {
            productViewList.innerHTML = '<p class="text-danger"><strong>Không có sản phẩm nào trong danh mục này.</strong></p>';
            return;
        }

        // Render products for the selected category
        renderProducts(products.data, productViewList);

    } catch (error) {
        console.error('Error loading products by category:', error);
        productViewList.innerHTML = '<p class="text-danger"><strong>Không thể tải sản phẩm cho danh mục này.</strong></p>';
    }
}

// generate category view for sidebar
function generateCategoryViewForSidebar() {
    const categorySidebar = document.getElementById('categorySidebar');
    const categories = JSON.parse(sessionStorage.getItem('categoriesList')) || [];

    renderCategoryLinks(categories.data, categorySidebar);
}

// render product list HTML
function renderProducts(productData, productViewList) {
    productViewList.innerHTML = productData.map(product => `
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
                                <span class="current-price">$${formatVND(product.price)}</span>
                                <span class="original-price">$${formatVND(product.price * 1.3)}</span>
                            </div>
                            <button class="add-to-cart-btn">Add to Cart</button>
                        </div>
                    </div>
    `).join('');
}

// render category links HTML in sidebar
function renderCategoryLinks(categoriesData, categorySidebar) {
    const selectedCategoryId = Number(new URLSearchParams(window.location.search).get('categoryId'));
    
    categorySidebar.innerHTML = categoriesData.map(category => {
        const isChecked = (category.id == selectedCategoryId) ? 'checked' : '';
        return ` 
            <div class="category-item" data-category-id="${category.id}">
                <input type="checkbox" id="cat${category.id}" class="category-checkbox" ${isChecked}>
                <label for="cat${category.id}">${category.categoryName}</label>
            </div>
    `}).join('');
}

