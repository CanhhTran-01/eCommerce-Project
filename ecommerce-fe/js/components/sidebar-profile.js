const nickNameHTML = document.getElementById('nickName');
const avtHTML = document.getElementById('avatar');

export function handleSidebarProfile(avtUrl, nickName){
    avtHTML.innerHTML = `<img src="${avtUrl}" alt="profile-avatar">`;
    nickNameHTML.innerHTML = `<h5>${nickName}</h5>` || '...';
}