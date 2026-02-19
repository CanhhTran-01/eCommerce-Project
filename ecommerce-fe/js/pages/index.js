import { fetchCategories } from "../api/category-api.js";
import { fetchSaleProducts } from "../api/product-api.js";
import { renderProductCard } from "../components/simple-product.js";

// call functions
handleCategoryMenu();
handleSaleProducts();


// handle category menu for home page
async function handleCategoryMenu() {
    const categoryHomeView = document.getElementById('categoryHomeView');

    try {
        const response = await fetchCategories();

        // store in session storage
        sessionStorage.setItem('category_list', JSON.stringify(response));

        // render Category Menu Home Page HTML
        categoryHomeView.innerHTML = response.data.map(category => `
                <div class="category-item-wrapper" data-category-id="${category.id}">
                    <div class="category-item-circle">
                        <img src="${category.imageUrl}" alt="${category.categoryName}">
                    </div>
                    <span style="white-space: nowrap; 
                                    overflow: hidden; 
                                    text-overflow: ellipsis;" 
                        class="category-item-text">${category.categoryName}</span>
                </div>
            `).join('');

        // handle category click events
        categoryHomeView.addEventListener('click', (event) => {
            const target = event.target.closest('.category-item-wrapper');
            if (target) {
                const categoryId = target.dataset.categoryId;
                if (categoryId) {
                    window.location.href =
                        `${window.location.origin}/ecommerce-fe/pages/product-list.html?categoryId=${categoryId}`;
                }
            }
        });

    } catch (error) {
        console.error('Error generating category links:', error);
        categoryHomeView.innerHTML = `<div class="text-danger"><strong>Không thể tải lên danh mục.</strong></div>`;
    }
}


// handle sale products for home page
async function handleSaleProducts() {
    const saleProductHomeView = document.getElementById('saleProductHomeView');
    const viewAllLink = document.querySelector('.view-all-link');

    try {
        const response = await fetchSaleProducts();
        sessionStorage.setItem('sale_list', JSON.stringify(response));

        // render 6 products
        renderProductCard(response.data.slice(0, 6), saleProductHomeView);

        // show "View All" link if more than 6 products available
        if (response.data.length > 6) {
            viewAllLink.classList.remove('d-none');
        }

        // handle view all click events
        viewAllLink.addEventListener('click', () => {
            window.location.href = window.location.origin + '/ecommerce-fe/pages/sale-products.html';
        });


    } catch (error) {
        console.error('Error fetching sale products:', error);
        saleProductHomeView.innerHTML = `<div class="text-danger"><strong>Không thể tải lên sản phẩm.</strong></div>`;
    }
}

