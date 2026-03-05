import { formatVND } from "../utils/format.js"

const orderCode = document.getElementById('orderCode');
const totalCheckout = document.getElementById('totalCheckout');
const paymentMethod = document.getElementById('paymentMethod');
const shippingMethod = document.getElementById('shippingMethod');
const checkoutResponse = JSON.parse(sessionStorage.getItem('checkoutResponse'));

// call functions
handlePage();

function handlePage(){
    orderCode.innerText = "#" + checkoutResponse.orderCode;
    totalCheckout.innerText = formatVND(checkoutResponse.totalCheckout);
    paymentMethod.innerText = checkoutResponse.paymentMethod;
    shippingMethod.innerText = checkoutResponse.shippingMethod;
}


