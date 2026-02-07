
// for home page category menu rendering
export function renderCategoryMenu(data, container) {
    container.innerHTML = data.map(category => `
                <div class="category-item-wrapper" data-category-id="${category.id}">
                    <div class="category-item-circle">
                        <img src="${category.imageUrl}" alt="${category.categoryName}">
                    </div>
                    <span class="category-item-text">${category.categoryName}</span>
                </div>
    `).join('');
}


// for side bar category filter rendering
export function renderCategoryLinks(categoriesData, categorySidebar) {
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

