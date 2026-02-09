import { fetchWishlist } from "../api/wishlist-api.js";
import { handleSidebarProfile } from "../components/sidebar-profile.js";
import { renderProductCard } from "../components/simple-product.js";

// call functions
handleSidebarProfile();
handleWishListView();


async function handleWishListView(){
    const wishList = document.getElementById('wishList');

    try {
        const response = await fetchWishlist();

        if (!response.data || response.data.length === 0){
            wishList.innerHTML = '<p class="text-danger"><strong>Không có sản phẩm yêu thích.</strong></p>';
            return;
        } 
        renderProductCard(response.data, wishList);


    } catch (error) {
        console.log(error);
        wishList.innerHTML = '<p class="text-danger"><strong>Không thể tải lên dữ liệu.</strong></p>';
    }
}

