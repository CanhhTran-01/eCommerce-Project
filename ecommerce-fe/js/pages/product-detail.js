import { fetchProductDetail } from "../api/product-api.js";
import { formatVND } from "../utils/format.js";

const productName = document.getElementById('productName');
const productPrice = document.getElementById('productPrice');
const productStatus = document.getElementById('productStatus');
const productInfo = document.getElementById('productInfo');
const productDesc = document.getElementById('description');


// call functions
handleProductDetailPage();


async function handleProductDetailPage() {
    // get productId from url
    const productId = Number(new URLSearchParams(window.location.search).get('productId'));

    try {
        const response = await fetchProductDetail(productId);

        productName.innerText = response.data.productName || '...';


        const hasDiscount = response.data.discountPrice != null && response.data.discountPrice < response.data.price;
        const discountBadgeHTML = hasDiscount
            ? `<span class="badge bg-danger"> -${Math.round((1 - response.data.discountPrice / response.data.price) * 100)}%</span>`
            : '';
        const priceHTML = hasDiscount
            ? `<div class="mb-2">
                    <span class="text-muted text-decoration-line-through">${formatVND(response.data.price)}</span>
               </div>
               <div class="h3 text-danger fw-bold">${formatVND(response.data.discountPrice)}</div>`
            :
            `<div class="h3 text-danger fw-bold">${formatVND(response.data.price)}</div>`;
        productPrice.innerHTML = `
                    ${priceHTML}
                    ${discountBadgeHTML}
                `


        const stockBadgeHTML = (response.data.stockQuantity > 0)
            ? `<span class="badge bg-success">Còn hàng</span><br>
                <small class="text-success">${response.data.stockQuantity} sản phẩm sẵn có</small>`
            : `<span class="badge bg-danger">Hết hàng</span>`;
        productStatus.innerHTML = stockBadgeHTML;

        productInfo.innerHTML = `
                    <li class="mb-2"><strong>Giới thiệu:</strong> ${response.data.shortDescription || '...'}</li>
                    <li class="mb-2"><strong>Brand:</strong> ${response.data.brand || '...'}</li>
                    <li class="mb-2"><strong>Color:</strong> ${response.data.color || 'Ngẫu nhiên'}</li>
                    <li class="mb-2"><strong>Made in:</strong> ${response.data.madeIn || 'Không rõ'}</li>                   
                `


        productDesc.innerHTML = `
                    <p>${response.data.shortDescription}.</p>
                    <p>${response.data.description}.</p>
                `

    } catch (error) {
        console.log(error);
        alert('Có lỗi xảy ra');
    }
}


function renderProductImage() { }

