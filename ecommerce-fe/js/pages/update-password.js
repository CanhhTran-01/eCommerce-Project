import { resetPass } from "../api/auth-api.js";
import { generateOtp, verifyOtp } from "../api/otp.js";

const emailData = sessionStorage.getItem('email_account');
document.getElementById('emailData').innerText = 'Xác minh email: ' + emailData;

const steps = document.querySelectorAll(".step");
const otpInput = document.getElementById("otp");
const newPasswordInput = document.getElementById('newPassword');
const oldPasswordInput = document.getElementById('oldPassword');
const confirmNewPasswordInput = document.getElementById('confirmNewPassword');
const notifyOtpSending = document.getElementById('notifyOtpSending');
const btnRequireOtp = document.getElementById("btn-require-otp");
const btnVerifyOtp = document.getElementById("btn-verify-otp");
const waittingProcessOtp = document.getElementById('waittingProcessOtp');
const waittingSendNewPass = document.getElementById('waittingSendNewPass');
const btnResetPassword = document.getElementById('btn-reset-password');

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
async function handleRequireOtp() {
    genOtpRequest.email = emailData;
    verifyOtpRequest.email = emailData;

    btnRequireOtp.classList.add('d-none');
    waittingProcessOtp.classList.remove('d-none');

    try {
        const response = await generateOtp(genOtpRequest);  // gen OTP

        waittingProcessOtp.classList.add('d-none');
        btnVerifyOtp.classList.remove('d-none');

        notifyOtpSending.innerText = response.message;

    } catch (error) {
        console.log(error);
        notifyOtpSending.innerText = error.message;
        btnRequireOtp.classList.remove('d-none');
        waittingProcessOtp.classList.add('d-none');
    }
}

async function handleVerifyOtp() {
    const otp = otpInput.value.trim();
    
    verifyOtpRequest.otp = otp;

    btnVerifyOtp.classList.add('d-none');
    waittingProcessOtp.classList.remove('d-none');
    try {
        const response = await verifyOtp(verifyOtpRequest);
        alert(response.message);

        nextStep(2);

    } catch (error) {
        console.log(error);
        notifyOtpSending.innerText = error.message;
    } finally {
        btnVerifyOtp.classList.remove('d-none');
        waittingProcessOtp.classList.add('d-none');
    }
}

async function handleChangePassword() {
    const oldPassword = oldPasswordInput.value.trim();
    const newPassword = newPasswordInput.value.trim();
    const confirmNewPassword = confirmNewPasswordInput.value.trim();

    if (!oldPassword || !newPassword || !confirmNewPassword){
        alert('Chưa nhập đủ thông tin');
        return;
    }

    updatePassRequest.newPassword = newPassword;
    updatePassRequest.oldPassword = oldPassword;

    btnResetPassword.classList.add('d-none');
    waittingSendNewPass.classList.remove('d-none');

    try {
        const response = await resetPass(updatePassRequest);
        alert(response.message);

        window.location.href = "../pages/index.html";

    } catch (error) {
        console.log(error);
        alert(error.message);
    }

}


btnRequireOtp.addEventListener("click", handleRequireOtp);
btnVerifyOtp.addEventListener('click', handleVerifyOtp);
btnResetPassword.addEventListener("click", handleChangePassword);