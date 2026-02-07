const userInfoLink = document.getElementById('infoLink');
const myOrdersLink = document.getElementById('myOrdersLink')
const wishListLink = document.getElementById('wishListLink');
const loginLink = document.getElementById('loginLink');
const logoutLink = document.getElementById('logoutLink');

// call function
handleUserIcon();


function handleUserIcon() {
    const isLoggedIn = !!localStorage.getItem('access_token');

    // toggle view
    if (isLoggedIn) {
        loginLink.classList.add('d-none');
        logoutLink.classList.remove('d-none');
    } else {
        loginLink.classList.remove('d-none');
        logoutLink.classList.add('d-none');
    }

    // profile click event
    userInfoLink.addEventListener('click', (event) => {
        if (!isLoggedIn) {
            event.preventDefault();
            alert('Vui lòng đăng nhập để truy cập trang này.');
        } else {
            window.location.href = window.location.origin + '/ecommerce-fe/pages/profile.html';
        }
    });

    // my orders click event
    myOrdersLink.addEventListener('click', (event) => {
        if (!isLoggedIn) {
            event.preventDefault();
            alert('Vui lòng đăng nhập để truy cập trang này.');
        } else {
            window.location.href = window.location.origin + '/ecommerce-fe/pages/my-orders.html';
        }
    });

    // wishlist click event
    wishListLink.addEventListener('click', (event) => {
        if (!isLoggedIn) {
            event.preventDefault();
            alert('Vui lòng đăng nhập để truy cập trang này.');
        } else {
            window.location.href = window.location.origin + '/ecommerce-fe/pages/wishlist.html';
        }
    });

    // login click event
    loginLink.addEventListener('click', (event) => {
        event.preventDefault();
        window.location.href = window.location.origin + '/ecommerce-fe/pages/login.html';
    });

    // logout click event
    logoutLink.addEventListener('click', (event) => {
        event.preventDefault();
        sessionStorage.removeItem('access_token');
        window.location.href = window.location.origin + '/ecommerce-fe/pages/index.html';
    });
}

