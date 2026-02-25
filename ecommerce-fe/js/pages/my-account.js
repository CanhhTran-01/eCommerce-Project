import { fetchAccountInfo } from "../api/my-account.js";
import { formatDateTime } from "../utils/format.js";

const username = document.getElementById('username');
const email = document.getElementById('email');
const createdAt = document.getElementById('createdAt');

// call functions
handleMyAccountInfo();

async function handleMyAccountInfo(){
    try {
        const response = await fetchAccountInfo();

        username.innerText = response.data.username;
        email.innerText = response.data.email;
        createdAt.innerText = formatDateTime(response.data.createdAt);

    } catch (error){
        alert(error.message);
        window.location.href = window.location.origin + "/ecommerce-fe/pages/index.html";
    }
}