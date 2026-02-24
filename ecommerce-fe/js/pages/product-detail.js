import { fetchProductDetail, fetchRelatedProducts } from "../api/product-api.js";
import { formatVND, formatDateTime } from "../utils/format.js";
import { fetchProductReviews } from "../api/product-api.js";
import { addProductToWishList } from "../api/wishlist-api.js";
import { renderProductCard } from "../components/simple-product.js";

const productId = Number(new URLSearchParams(window.location.search).get('productId'));
const productName = document.getElementById('productName');
const productPrice = document.getElementById('productPrice');
const productStatus = document.getElementById('productStatus');
const productInfo = document.getElementById('productInfo');
const productDesc = document.getElementById('description');
const addToWishListBtn = document.getElementById('addToWishlistBtn');
const addToCartBtn = document.getElementById('addToCartBtn');
const relatedProducts = document.getElementById('relatedProducts');

// call functions
handleProductDetail();
handleRelatedProducts();


async function handleProductDetail() {
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
                    <li class="mb-2"> ${response.data.shortDescription || '...'} </li>
                    <li class="mb-2"><strong>Brand:</strong> ${response.data.brand || '...'}</li>
                    <li class="mb-2"><strong>Color:</strong> ${response.data.color || 'Ngẫu nhiên'}</li>
                    <li class="mb-2"><strong>Made in:</strong> ${response.data.madeIn || 'Không rõ'}</li>                   
                `

        productDesc.innerHTML = `
                    <p>${response.data.shortDescription}</p>
                    <p>${response.data.description}</p>
                `

    } catch (error) {
        console.log(error);
        alert('Không lấy được dữ liệu sản phẩm');
    }
}


document.getElementById('reviews-tab').addEventListener('click', async (event) => {
    event.preventDefault();

    const productId = Number(new URLSearchParams(window.location.search).get('productId'));
    const productReviews = document.getElementById('reviews');
    try {
        const response = await fetchProductReviews(productId);

        if (!response.data || response.data.length == 0) {
            productReviews.innerHTML = '<p class="text-danger"><strong>Không có bình luận cho sản phẩm này.</strong></p>';
            return;
        }

        productReviews.innerHTML = response.data.map(item =>
            `
                <div class="review mb-4">
                    <div class="d-flex justify-content-between align-items-start">
                        <div>
                            <h6 class="mb-1">${item.nickName} </h6>
                            <span class="text-muted small">- ${item.title}</span>
                            <div class="stars">
                                <div class="stars-filled" style="width: ${(item.rating / 5) * 100}%"></div>
                            </div>
                        </div>
                        <small class="text-muted"> ${formatDateTime(item.updatedAt)}</small>
                        </div>
                        <p class="mt-2">
                            ${item.comment}
                        </p>
                </div>
            `
        ).join('');


    } catch (error) {
        console.log(error);
        productReviews.innerHTML = '<p class="text-danger"><strong>Có lỗi xảy ra.</strong></p>';
    }
});


async function  handleRelatedProducts() {
    try {
        const response = await fetchRelatedProducts(productId);

        if (!response.data || response.data.length == 0){
            relatedProducts.innerHTML = `<div class="text-danger"><strong>Không có sản phẩm.</strong></div>`;
            return;
        }
        renderProductCard(response.data.slice(0, 12), relatedProducts);

    } catch (error) {
        console.log(error);
        relatedProducts.innerHTML = `<div class="text-danger"><strong>Không thể tải lên sản phẩm.</strong></div>`;
    } 
}


addToWishListBtn.addEventListener('click', (e) => {
    e.preventDefault();
    addProductToWishList(productId);
    alert('Đã thêm sản phẩm vào yêu thích');
    document.getElementById('addToWishlistBtn').classList.add('d-none');
});


addToCartBtn.addEventListener('click', (e) => { 
    e.preventDefault();


});


