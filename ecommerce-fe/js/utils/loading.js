const uploadBtn = document.getElementById("uploadBtn");
const avatarSpinner = document.getElementById('avatarSpinner');

export function showLoading() {
    uploadBtn.style.display = "none";
    avatarSpinner.style.display = "block";
}

export function hideLoading() {
    uploadBtn.style.display = "inline-block";
    avatarSpinner.style.display = "none";
}