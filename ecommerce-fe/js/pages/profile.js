import { fetchProfile } from "../api/profile-api.js";
import { updateProfileRequest } from "../api/profile-api.js";
import { formatDateVN } from "../utils/format.js";
import { fetchOrderItemsHistory } from "../api/order-item-api.js";
import { renderOrderItem } from "../components/order-item.js";
import { handleLogout } from "../api/logout.js";
import { uploadAvatar } from "../api/upload-file-api.js";
import { hideLoading, showLoading } from "../utils/loading.js";

const profileBox = document.getElementById('profileBox');
const nickNameHTML = document.getElementById('nickName');
const avtHTML = document.getElementById('avatar');
const avatarInput = document.getElementById("avatarInput");
const uploadBtn = document.getElementById("uploadBtn");
const avatarImg = document.getElementById("avatarImg");

// call functions
handleProfile();


async function handleProfile() {
    try {
        const response = await fetchProfile();
        sessionStorage.setItem('user_info', JSON.stringify(response));

        // display nick name and avt
        avtHTML.innerHTML = `<img src="${response.data.avatarUrl}" alt="profile-avatar">`;
        nickNameHTML.innerHTML = `<h5>${response.data.nickName}</h5>` || '...';
        avatarImg.src = response.data.avatarUrl;

        // display profile
        profileBox.innerHTML = `
                <div class="user-info-section">
                    <div class="row mb-3 align-items-center">
                        <div class="col-auto">
                            <i class="fas fa-id-badge text-primary" style="width: 20px;"></i>
                        </div>
                        <div class="col">
                            <strong>Mã người dùng:</strong> 
                            <span class="text-secondary ms-1">${response.data.userCode || '...'}</span>
                        </div>
                    </div>

                    <div class="row mb-3 align-items-center">
                        <div class="col-auto">
                            <i class="fas fa-user text-primary" style="width: 20px;"></i>
                        </div>
                        <div class="col">
                            <strong>Họ và tên:</strong> 
                            <span class="text-secondary ms-1">${response.data.fullName || '...'}</span>
                        </div>
                    </div>

                    <div class="row mb-3 align-items-center">
                        <div class="col-auto">
                            <i class="fas fa-calendar-alt text-primary" style="width: 20px;"></i>
                        </div>
                        <div class="col">
                            <strong>Ngày sinh:</strong> 
                            <span class="text-secondary ms-1">${formatDateVN(response.data.dateOfBirth)}</span>
                        </div>
                    </div>

                    <div class="row mb-3 align-items-center">
                        <div class="col-auto">
                            <i class="fas fa-venus-mars text-primary" style="width: 20px;"></i>
                        </div>
                        <div class="col">
                            <strong>Giới tính:</strong> 
                            <span class="text-secondary ms-1">
                                ${response.data.gender === 'MALE' ? 'Nam' : response.data.gender === 'FEMALE' ? 'Nữ' : 'Khác'}
                            </span>
                        </div>
                    </div>

                    <div class="row mb-3 align-items-center">
                        <div class="col-auto">
                            <i class="fas fa-phone text-primary" style="width: 20px;"></i>
                        </div>
                        <div class="col">
                            <strong>Số điện thoại:</strong> 
                            <span class="text-secondary ms-1">${response.data.phoneNumber || '...'}</span>
                        </div>
                    </div>

                    <div class="row mb-3 align-items-center">
                        <div class="col-auto">
                            <i class="fas fa-star text-warning" style="width: 20px;"></i>
                        </div>
                        <div class="col">
                            <strong>Điểm tích lũy:</strong> 
                            <span class="badge bg-warning text-dark ms-1">${response.data.personalPoints?.toLocaleString() || 0}</span>
                        </div>
                    </div>

                    <div class="row mb-3 align-items-center">
                        <div class="col-auto">
                            <i class="fas fa-user-tag text-primary" style="width: 20px;"></i>
                        </div>
                        <div class="col"> 
                            <strong>Vai trò:</strong>
                            <span class="badge bg-primary ms-1">${response.data.role || 'USER'} </span>
                        </div>
                    </div>

                    <div class="profile-actions">
                        <button id="editProfileBtn" class="btn-edit-profile"> Cập nhật </button>
                    </div>
                </div>
            `;

        // handle update click event
        document.getElementById('editProfileBtn').addEventListener('click', (event) => {
            event.preventDefault();
            const userInfoObj = JSON.parse(sessionStorage.getItem('user_info'));

            profileBox.innerHTML = '';
            profileBox.innerHTML = `
        <div class="card shadow-sm border-0">
            <div class="card-body p-4">
                <h5 class="card-title mb-4 text-primary">--- Cập nhật hồ sơ --- </h5>
        
                <form id="editProfileForm" data-user-id="${userInfoObj.data.id}">
                    <div class="mb-3">
                        <label class="form-label fw-bold">Nickname</label>
                        <input type="text" class="form-control shadow-none" name="nickName" value="${userInfoObj.data.nickName}">
                    </div>

                    <div class="mb-3">
                        <label class="form-label fw-bold">Họ và tên</label>
                        <input type="text" class="form-control shadow-none" name="fullName" value="${userInfoObj.data.fullName ?? ''}">
                    </div>

                    <div class="mb-3">
                        <label class="form-label fw-bold">Số điện thoại</label>
                        <input type="text" class="form-control shadow-none" name="phoneNumber" value="${userInfoObj.data.phoneNumber ?? ''}">
                    </div>

                    <div class="row">
                        <div class="col-md-6 mb-3">
                            <label class="form-label fw-bold">Giới tính</label>
                            <select class="form-select shadow-none" name="gender">
                                <option value="">-- Chọn --</option>
                                <option value="MALE" ${userInfoObj.data.gender === 'MALE' ? 'selected' : ''}>Nam</option>
                                <option value="FEMALE" ${userInfoObj.data.gender === 'FEMALE' ? 'selected' : ''}>Nữ</option>
                                <option value="OTHER" ${userInfoObj.data.gender === 'OTHER' ? 'selected' : ''}>Khác</option>
                            </select>
                        </div>

                        <div class="col-md-6 mb-3">
                            <label class="form-label fw-bold">Ngày sinh</label>
                            <input type="date" class="form-control shadow-none" name="dateOfBirth" value="${userInfoObj.data.dateOfBirth ?? ''}">
                        </div>
                    </div>

                    <div class="d-flex gap-2 pt-2">
                        <button type="submit" class="btn btn-primary px-4 shadow-sm">Lưu thay đổi</button>
                        <button type="button" id="cancelEditBtn" class="btn btn-light px-4 border">Hủy</button>
                    </div>
                </form>
            </div>
        </div>
            `;

            // save changes click event
            document.getElementById('editProfileForm').addEventListener('submit', async (event) => {
                event.preventDefault();
                const userId = event.target.dataset.userId;  // get userId from form
                const formData = new FormData(event.target); // get data from form
                const dataRequest = Object.fromEntries(formData.entries()); // convert into Obj

                try {
                    const response = await updateProfileRequest(userId, dataRequest);

                    // in case of coming to here , it means no error
                    window.alert('Cập nhật thành công !');
                    sessionStorage.setItem('user_info', JSON.stringify(response));
                    window.location.href = window.location.origin + '/ecommerce-fe/pages/profile.html';

                } catch (error) {
                    console.log(error);
                    alert('Có lỗi xảy ra');
                    window.location.href = window.location.origin + '/ecommerce-fe/pages/profile.html';
                }

            });

            // cancel click event
            document.getElementById('cancelEditBtn').addEventListener('click', () => {
                if (confirm('Bạn có chắc muốn hủy các thay đổi ?')) {
                    window.location.href = window.location.origin + '/ecommerce-fe/pages/profile.html';
                }
            });

        });

    } catch (error) {
        profileBox.innerHTML = `<p class="text-danger">Không thể tải thông tin người dùng...</p>`;
        console.error('Error loading user info:', error);
    }

}


// handle purchase history click event
document.getElementById('purchaseHistoryLink').addEventListener('click', async (event) => {
    event.preventDefault();

    const purchaseHistory = document.getElementById('puschaseHistory');
    try {
        const response = await fetchOrderItemsHistory();

        renderOrderItem(response.data, purchaseHistory);

    } catch (error) {
        console.log(error);
        purchaseHistory.innerHTML = '<p class="text-danger"><strong>Không thể tải lên dữ liệu.</strong></p>';
    }
});


// handle log out click event
document.getElementById('logoutLinkInProfile').addEventListener('click', (event) => {
    event.preventDefault();
    handleLogout();
});


document.addEventListener("DOMContentLoaded", function () {

    uploadBtn.addEventListener("click", () => {
        avatarInput.click();
    });

    avatarInput.addEventListener("change", async () => {

        const file = avatarInput.files[0];
        if (!file) return;

        if (!file.type.startsWith("image/")) {
            alert("File phải là ảnh");
            return;
        }

        const reader = new FileReader();
        reader.onload = e => {
            avatarImg.src = e.target.result;
        };
        reader.readAsDataURL(file);

        const formData = new FormData();
        formData.append("image", file);

        try {
            showLoading(); 
            const response = await uploadAvatar(formData);
            
            avatarImg.src = response.data;

            hideLoading(); 
            alert("Upload thành công!");

        } catch (error) {
            hideLoading();
            console.log(error);
            alert("Upload thất bại!");
        }

    });

});



