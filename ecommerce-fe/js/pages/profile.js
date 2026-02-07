import { fetchProfile } from "../api/profile-api.js";

// call functions
handleProfile();


async function handleProfile() {
    const nickName = document.getElementById('nickName');
    const profileBox = document.getElementById('profileBox');

    try {
        const response = await fetchProfile();

        nickName.textContent = response.data.fullName || '...';

        profileBox.innerHTML = `
                <p><strong>Họ và tên:</strong> ${response.data.fullName || '...'}</p>
                <p><strong>Mã người dùng:</strong> ${response.data.userCode || '...'}</p>
                <p><strong>Số điện thoại:</strong> ${response.data.phoneNumber || '...'}</p>
                <p><strong>Giới tính:</strong> ${response.data.gender || '...'}</p>
                <p><strong>Ngày sinh:</strong> ${response.data.dateOfBirth || '...'}</p>
                <p><strong>Ảnh đại diện:</strong> ${response.data.avatarUrl || '...'}</p>
                <p><strong>Điểm tích lũy:</strong> ${response.data.personalPoints || 0}</p>
            `;

    } catch (error) {
        profileBox.innerHTML = `<p class="text-danger">Không thể tải thông tin người dùng...</p>`;
        console.error('Error loading user info:', error);
    }

}
