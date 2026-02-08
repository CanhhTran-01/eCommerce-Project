const nickNameHTML = document.getElementById('nickName');
const avtHTML = document.getElementById('avatar');
const userInfoObj = JSON.parse(sessionStorage.getItem('user_info'));


export function handleSidebarProfile() {

    avtHTML.innerHTML = `<img src="${userInfoObj.data.avatarUrl ?? 
        '/ecommerce-fe/assets/icons/default-avt.jpg'}" alt="profile-avatar">`;

    nickNameHTML.innerHTML = `<h5>${userInfoObj.data.nickName}</h5>` || '...';
    
}