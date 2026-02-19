import { fetchProductByFilter } from "../api/product-api.js";
import { renderProductCard } from "../components/simple-product.js";


// call functions
handleCategorySidebarView();
handleProductList();
handlePriceFilter();
handleSort();


// FILTER REQUEST
function buildFilterRequest() {
    const params = new URLSearchParams(window.location.search);

    return {
        searchText: params.get('searchText'),
        categoryId: params.getAll('categoryId').map(Number),
        minPrice: params.get('minPrice') ? Number(params.get('minPrice')) : null,
        maxPrice: params.get('maxPrice') ? Number(params.get('maxPrice')) : null,
        sort: params.get('sort')
    };
}


// product listing by filter
async function handleProductList() {
    const productViewList = document.getElementById('productViewList');

    try {
        const response = await fetchProductByFilter(buildFilterRequest());
        sessionStorage.setItem('filter_product', JSON.stringify(response.data));

        // Clear existing products
        productViewList.innerHTML = '';

        // Handle case when no products are found
        if (!response || response.data.length === 0) {
            productViewList.innerHTML = '<p class="text-danger"><strong>Không có sản phẩm.</strong></p>';
            return;
        }

        // Render products for the selected category
        renderProductCard(response.data, productViewList);

    } catch (error) {
        console.error('Error loading products by category:', error);
        productViewList.innerHTML = '<p class="text-danger"><strong>Không thể tải sản phẩm cho danh mục này.</strong></p>';
    }
}


// sidebar categories
function handleCategorySidebarView() {
    const params = new URLSearchParams(window.location.search);
    const selectedCategoryIds = params.getAll('categoryId').map(Number);
    const categoriesObj = JSON.parse(sessionStorage.getItem('category_list')) || [];
    const categorySidebar = document.getElementById('categorySidebar');

    categorySidebar.innerHTML = categoriesObj.data.map(category => {
        const isChecked = (selectedCategoryIds.includes(category.id)) ? 'checked' : '';
        return ` 
            <div class="category-item" data-category-id="${category.id}">
                <input type="checkbox" name="categoryId" value="${category.id}" 
                            id="cat-${category.id}" class="category-checkbox" ${isChecked}>
                <label for="cat-${category.id}">${category.categoryName}</label>
            </div>
    `}).join('');

    document.querySelectorAll('.category-checkbox').forEach(checkbox => {
        checkbox.addEventListener('change', () => {

            const params = new URLSearchParams(window.location.search);

            // Clear old param 
            params.delete('searchText');
            params.delete('categoryId');

            document.querySelectorAll('.category-checkbox').forEach(cb => {
                if (cb.checked) {
                    params.append('categoryId', cb.value);
                }
            });

            const newUrl = `${window.location.pathname}?${params.toString()}`;
            window.history.pushState({}, '', newUrl);

            handleProductList();
            window.scrollTo({
                top: 0,
                behavior: "smooth"
            });
        });
    });

}


// filter with min and max price
function handlePriceFilter() {

    const minInput = document.getElementById("priceMin");
    const maxInput = document.getElementById("priceMax");
    const applyBtn = document.getElementById("applySearchByPriceBtn");

    applyBtn.addEventListener("click", () => {

        const minPrice = minInput.value ? Number(minInput.value) : null;
        const maxPrice = maxInput.value ? Number(maxInput.value) : null;

        const params = new URLSearchParams(window.location.search);

        // clear old price params
        params.delete("minPrice");
        params.delete("maxPrice");

        if (minPrice !== null && !isNaN(minPrice)) {
            params.append("minPrice", minPrice);
        }

        if (maxPrice !== null && !isNaN(maxPrice)) {
            params.append("maxPrice", maxPrice);
        }

        const newUrl = `${window.location.pathname}?${params.toString()}`;

        window.history.pushState({}, '', newUrl);
        handleProductList();
        window.scrollTo({
            top: 0,
            behavior: "smooth"
        });
    });
}


// sort by criteria
function handleSort() {

    const sortSelect = document.getElementById("sortSelect");

    sortSelect.addEventListener("change", () => {

        const sortValue = sortSelect.value;

        const params = new URLSearchParams(window.location.search);

        params.delete("sort");

        if (sortValue) {
            params.append("sort", sortValue);
        }

        const newUrl = `${window.location.pathname}?${params.toString()}`;

        window.history.pushState({}, '', newUrl);

        handleProductList();
        window.scrollTo({
            top: 0,
            behavior: "smooth"
        });
    });
}



