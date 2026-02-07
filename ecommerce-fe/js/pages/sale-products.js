import { renderProductCard } from "../components/simple-product.js";

// call functions
handleSaleProductsPage();


// handle sale products page view
function handleSaleProductsPage() {
    const saleProductList = document.getElementById('saleProductList');

    const saleProductsDataObj = JSON.parse(sessionStorage.getItem('sale_list'));
    renderProductCard(saleProductsDataObj.data, saleProductList);
}


