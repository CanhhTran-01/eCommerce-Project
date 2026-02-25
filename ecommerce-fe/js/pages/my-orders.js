import { handleSidebarProfile } from "../components/sidebar-profile.js";
import { fetchActiveOrderItemsByUser } from "../api/order-item-api.js";
import { renderOrderItem } from "../components/order-item.js";
import { handleLogout } from "../pages/logout.js";


// call functions
handleSidebarProfile();
handleMyOrder();


async function handleMyOrder(){
    const ordersList = document.getElementById('ordersList');

    try {
        const response = await fetchActiveOrderItemsByUser();
        sessionStorage.setItem = ('order_items', JSON.stringify(response));

        renderOrderItem(response.data, ordersList);

    } catch (error){
        console.log(error);
        ordersList.innerHTML = '<p class="text-danger"><strong>Không thể tải lên dữ liệu.</strong></p>';
    }
}


// handle log out click event
document.getElementById('logoutLinkInProfile').addEventListener('click', (event) => {
    event.preventDefault();
    handleLogout();
});
