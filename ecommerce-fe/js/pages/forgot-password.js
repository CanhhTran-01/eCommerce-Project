// ===== state =====
let generatedOtp = null;

// ===== cache DOM =====
const steps = document.querySelectorAll(".step");

const emailInput = document.getElementById("email");
const otpInput = document.getElementById("otp");
const newPasswordInput = document.getElementById("newPassword");
const confirmPasswordInput = document.getElementById("confirmPassword");

const btnSendEmail = document.getElementById("btn-send-email");
const btnVerifyOtp = document.getElementById("btn-verify-otp");
const btnResetPassword = document.getElementById("btn-reset-password");


// ===== step control =====
function nextStep(step) {
    steps.forEach(s => s.classList.add("d-none"));
    document.getElementById("step" + step).classList.remove("d-none");
}


// ===== handlers =====
function handleSendEmail() {
    const email = emailInput.value.trim();

    if (!email) {
        alert("Nhập email");
        return;
    }

    // mock OTP
    generatedOtp = Math.floor(100000 + Math.random() * 900000);
    console.log("OTP:", generatedOtp);

    alert("OTP đã gửi (mock)");

    nextStep(2);
}

function handleVerifyOtp() {
    if (otpInput.value == generatedOtp) {
        nextStep(3);
    } else {
        alert("OTP sai");
    }
}

function handleResetPassword() {
    const newPass = newPasswordInput.value.trim();
    const confirmPass = confirmPasswordInput.value.trim();

    if (!newPass || !confirmPass) {
        alert("Nhập đủ mật khẩu");
        return;
    }

    if (newPass !== confirmPass) {
        alert("Mật khẩu không khớp");
        return;
    }

    alert("Đổi mật khẩu thành công");

    window.location.href = "../pages/login.html";
}


// ===== bind =====
btnSendEmail.addEventListener("click", handleSendEmail);
btnVerifyOtp.addEventListener("click", handleVerifyOtp);
btnResetPassword.addEventListener("click", handleResetPassword);