
import { getInfo } from "../api/userInfoApi.js";

handleAccountInfoPage();

export function handleAccountInfoPage() {

    const nickName = document.getElementById('nickName');
    const profileBox = document.getElementById('profileBox');

    loadUserInfo(nickName, profileBox);
}

async function loadUserInfo(nickName, profileBox) {
    try {
        const userInfo = await getInfo();

        nickName.textContent = userInfo.data.fullName || '...';

        profileBox.innerHTML = `
                <p><strong>Họ và tên:</strong> ${userInfo.data.fullName || '...'}</p>
                <p><strong>Mã người dùng:</strong> ${userInfo.data.userCode || '...'}</p>
                <p><strong>Số điện thoại:</strong> ${userInfo.data.phoneNumber || '...'}</p>
                <p><strong>Giới tính:</strong> ${userInfo.data.gender || '...'}</p>
                <p><strong>Ngày sinh:</strong> ${userInfo.data.dateOfBirth || '...'}</p>
                <p><strong>Ảnh đại diện:</strong> ${userInfo.data.avatarUrl || '...'}</p>
                <p><strong>Điểm tích lũy:</strong> ${userInfo.data.personalPoints || 0}</p>
            `;

    } catch (error) {
        profileBox.innerHTML = `<p class="text-danger">Không thể tải thông tin người dùng...</p>`;
        console.error('Error loading user info:', error);
    }
}