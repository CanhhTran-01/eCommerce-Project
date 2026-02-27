const steps = document.querySelectorAll(".step");
const emailInput = document.getElementById("email");
const otpInput = document.getElementById("otp");
const newPasswordInput = document.getElementById('newPassword');
const oldPasswordInput = document.getElementById('oldPassword');
const confirmNewPasswordInput = document.getElementById('confirmNewPassword');
const btnSendEmail = document.getElementById("btn-send-email");
const notifyOtpSending = document.getElementById('notifyOtpSending');
const btnVerifyOtp = document.getElementById("btn-verify-otp");
const btnResetPassword = document.getElementById('btn-reset-password');
const waittingOtp = document.getElementById('waittingOtp');
const waittingSendNewPass = document.getElementById('waittingSendNewPass');

const genOtpRequest = {
    email: null,
    otpType: "UPDATE_PASSWORD"
}
const verifyOtpRequest = {
    email: null,
    otp: null,
    otpType: "UPDATE_PASSWORD"
}
const updatePassRequest = {
    oldPassword: null,
    newPassword: null,
    otpType: "UPDATE_PASSWORD"
}


// view step control
function nextStep(step) {
    steps.forEach(s => s.classList.add("d-none"));
    document.getElementById("step" + step).classList.remove("d-none");
}


// logic
function handleSendEmail() {
    const email = emailInput.value.trim();

    if (!email) {
        alert("Chưa nhập email");
        return;
    }

    const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

    if (!emailPattern.test(email)) {
        alert("Email không đúng định dạng");
        return;
    }

    genOtpRequest.email = email;
    verifyOtpRequest.email = email;
    updatePassRequest.email = email;
    nextStep(2);
}


function handleVerifyOtp() {
    const otp = otpInput.value.trim();

    genOtpRequest.otp = otp;
    verifyOtpRequest.otp = otp;

    btnVerifyOtp.classList.add('d-none');
    waittingOtp.classList.remove('d-none');
    try {
        // call http://localhost:8080/eCommerce/api/accounts/email/verify
        alert(response.message)
        nextStep(3);

    } catch (error) {
        console.log(error);
        notifyOtpSending.innerHTML = error.message;
    } finally  {
        btnVerifyOtp.classList.remove('d-none');
        waittingOtp.classList.add('d-none');
    }
}


function handleChangePassword() {
    const oldPassword = oldPasswordInput.value.trim();
    const newPassword = newPasswordInput.value.trim();
    const confirmNewPassword = confirmNewPasswordInput.value.trim();

    if (!oldPassword || !newPassword || !confirmNewPassword){
        alert('Chưa nhập đủ thông tin');
        return;
    }

    updatePassRequest.newPassword = newPassword;
    updatePassRequest.oldPassword = oldPassword;

    waittingSendNewPass.classList.remove('d-none');
    try {
        // call http://localhost:8080/eCommerce/api/accounts/reset-password

        alert(response.message);

        // logout + href
        window.location.href = "../pages/login.html";

    } catch (error) {
        console.log(error);
        alert(error.message);
    }
}


btnSendEmail.addEventListener("click", handleSendEmail);
btnVerifyOtp.addEventListener("click", handleVerifyOtp);
btnResetPassword.addEventListener("click", handleChangePassword);