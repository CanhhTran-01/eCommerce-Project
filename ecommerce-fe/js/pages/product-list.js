import { fetchProductByCategoryId } from "../api/product-api.js";
import { renderProductCard } from "../components/simple-product.js";
import { renderCategoryLinks } from "../components/category.js";

// call functions
handleCategorySidebarView();
handleProductByCategoryView();

// handle product listing by category view
async function handleProductByCategoryView() {
    const categoryId = Number(new URLSearchParams(window.location.search).get('categoryId'));
    const productViewList = document.getElementById('productViewList');

    try {
        const response = await fetchProductByCategoryId(categoryId);

        // Clear existing products
        productViewList.innerHTML = '';

        // Handle case when no products are found
        if (!response || response.data.length === 0) {
            productViewList.innerHTML = '<p class="text-danger"><strong>Không có sản phẩm nào trong danh mục này.</strong></p>';
            return;
        }

        // Render products for the selected category
        renderProductCard(response.data, productViewList);

    } catch (error) {
        console.error('Error loading products by category:', error);
        productViewList.innerHTML = '<p class="text-danger"><strong>Không thể tải sản phẩm cho danh mục này.</strong></p>';
    }
}

// generate category view for sidebar
function handleCategorySidebarView() {
    const categorySidebar = document.getElementById('categorySidebar');
    const categoriesObj = JSON.parse(sessionStorage.getItem('category_list')) || [];

    renderCategoryLinks(categoriesObj.data, categorySidebar);
}



