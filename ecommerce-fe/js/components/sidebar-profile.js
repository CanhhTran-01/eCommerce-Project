const nickNameHTML = document.getElementById('nickName');
const avtHTML = document.getElementById('avatar');
const userInfoObj = JSON.parse(sessionStorage.getItem('user_info'));


export function handleSidebarProfile() {

    avtHTML.innerHTML = `<img src="${userInfoObj.data.avatarUrl}" alt="profile-avatar">`;

    nickNameHTML.innerHTML = `<h5>${userInfoObj.data.nickName}</h5>` || '...';
    
}