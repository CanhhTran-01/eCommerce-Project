import { forgotPass } from "../api/auth-api.js";
import { generateOtp, verifyOtp } from "../api/otp.js";

const steps = document.querySelectorAll(".step");
const emailInput = document.getElementById("email");
const otpInput = document.getElementById("otp");
const usernameInput = document.getElementById("username");

const btnSendEmail = document.getElementById("btn-send-email");
const notifyOtpSending = document.getElementById('notifyOtpSending');

const btnRequireOtp = document.getElementById("btn-require-otp");
const btnVerifyOtp = document.getElementById("btn-verify-otp");
const waittingProcessOtp = document.getElementById('waittingProcessOtp');

const waittingSendNewPass = document.getElementById('waittingSendNewPass');
const btnResetPassword = document.getElementById('btn-reset-password');

const genOtpRequest = {
    email: null,
    otpType: "FORGOT_PASSWORD"
}
const verifyOtpRequest = {
    email: null,
    otp: null,
    otpType: "FORGOT_PASSWORD"
}
const forgotPassRequest = {
    email: null,
    username: null,
    otpType: "FORGOT_PASSWORD"
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
    forgotPassRequest.email = email;
    nextStep(2);
}


async function handleRequireOtp() {

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

        nextStep(3);

    } catch (error) {
        console.log(error);
        notifyOtpSending.innerText = error.message;
    } finally {
        btnVerifyOtp.classList.remove('d-none');
        waittingProcessOtp.classList.add('d-none');
    }
}


async function handleUsernameEnter() {
    const username = usernameInput.value.trim();

    forgotPassRequest.username = username;

    btnResetPassword.classList.add('d-none');
    waittingSendNewPass.classList.remove('d-none');
    try {
        const response = await forgotPass(forgotPassRequest);

        alert(response.message);
        window.location.href = "../pages/login.html";

    } catch (error) {
        console.log(error);
        alert(error.message);
        window.location.href = "../pages/forgot-password.html";
    }
}


btnSendEmail.addEventListener("click", handleSendEmail);
btnRequireOtp.addEventListener("click", handleRequireOtp);
btnVerifyOtp.addEventListener('click', handleVerifyOtp);
btnResetPassword.addEventListener("click", handleUsernameEnter);