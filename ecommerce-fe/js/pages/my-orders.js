import { handleSidebarProfile } from "../components/sidebar-profile.js";
import { fetchOrderItemsByUser } from "../api/order-item.js";
import { renderOrderItem } from "../components/order-item.js";

// call functions
handleSidebarProfile();
handleMyOrder();


async function handleMyOrder(){
    const ordersList = document.getElementById('ordersList');

    try {
        const response = await fetchOrderItemsByUser();
        sessionStorage.setItem = ('order_items', JSON.stringify(response));

        renderOrderItem(response.data, ordersList);

    } catch (error){
        console.log(error);
        ordersList.innerHTML = '<p class="text-danger"><strong>Không thể tải lên dữ liệu.</strong></p>';
    }
}



