
import { toggleAuthLinks, logout } from "../utils/auth.js";
import { fetchSaleProducts } from "../api/productApi.js";
import { formatVND } from "../utils/format.js";
import { fetchCategories } from "../api/categoryApi.js";


// auth link toggling and logout handling
export function handleIndexPage() {
    const loginLink = document.getElementById('loginLink');
    const logoutLink = document.getElementById('logoutLink');

    toggleAuthLinks(loginLink, logoutLink);

    logoutLink.addEventListener('click', (event) => {
        event.preventDefault();
        logout();
    });
}


// generate category links for index page
export async function generateCategoryLinks() {
    const categoryHomeView = document.getElementById('categoryHomeView');

    try {
        const categories = await fetchCategories();

        // store categories in session storage for later use
        sessionStorage.setItem('categoriesList', JSON.stringify(categories));

        // render category links HTML
        renderCategoryLinks(categories.data, categoryHomeView);

        // handle category click events
        handleCategoryClick();

    } catch (error) {
        console.error('Error generating category links:', error);
        categoryHomeView.innerHTML = `<div class="text-danger"><strong>Không thể tải danh mục.</strong></div>`;
    }
}
// generate category links HTML
function renderCategoryLinks(categories, categoryHomeView) {
    categoryHomeView.innerHTML = categories.map(category => `
                <div class="category-item-wrapper" data-category-id="${category.id}">
                    <div class="category-item-circle">
                        <img src="${category.imageUrl}" alt="${category.categoryName}">
                    </div>
                    <span class="category-item-text">${category.categoryName}</span>
                </div>
    `).join('');
}
// handle category click at home page
function handleCategoryClick() {
    const categoryItems = document.querySelectorAll('.category-item-wrapper');
 
    categoryItems.forEach(item => {
        item.addEventListener('click', () => {
            const categoryId = item.dataset.categoryId;
            
            // navigate to product list page with categoryId as query parameter
            window.location.href =
                `${window.location.origin}/ecommerce-fe/pages/product-list.html?categoryId=${categoryId}`;
        });
    });
}


// load and display sale products on index page
export async function handleSaleProductsView() {
    const saleProductsSection = document.getElementById('saleProductHomeView');

    try {
        const saleProducts = await fetchSaleProducts();

        sessionStorage.setItem('saleProducts', JSON.stringify(saleProducts));

        // render 6 products
        generSaleProductsHTML(saleProducts.data.slice(0, 6), saleProductsSection);

        // show "View All" link if more than 6 products available
        if (saleProducts.data.length > 6) {
            document.querySelector('.view-all-link').classList.remove('d-none');
        }

    } catch (error) {
        console.error('Error fetching sale products:', error);
        saleProductsSection.innerHTML = `<div class="text-danger"><strong>Không thể tải lên sản phẩm.</strong></div>`;
    }
}
// generate HTML for sale products
function generSaleProductsHTML(products, saleProductsSection) {
    // handle case when no products are available
    if (products.length === 0) {
        saleProductsSection.innerHTML = `<div class="text-danger"><strong>Không có sản phẩm giảm giá nào.</strong></div>`;
        return;
    }

    // generate product cards HTML
    saleProductsSection.innerHTML = products.map(product => `
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
                    <span class="review-count">(256)</span>
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


// handle "View All Sale Products" link click
const viewAllSaleProductsLink = document.getElementById('viewAllSaleListLink');
viewAllSaleProductsLink.addEventListener('click', (event) => {
    event.preventDefault();
    window.location.href = `${window.location.origin}/ecommerce-fe/pages/sale-products.html`;
});


