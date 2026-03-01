import { register } from "../api/auth-api.js";
import { generateOtp, verifyOtp } from "../api/otp.js";

const steps = document.querySelectorAll(".step");
const emailInput = document.getElementById("email");
const usernameInput = document.getElementById("username");
const passwordInput = document.getElementById("password");
const confirmPasswordInput = document.getElementById("confirmPassword");
const otpInput = document.getElementById("otp");
const btnNextEmail = document.getElementById("btn-next-email");
const btnSendOtp = document.getElementById("btn-send-otp");
const btnVerifyOtp = document.getElementById("btn-verify-otp");
const waittingSendOtp = document.getElementById('waittingSendOtp');
const waittingCreateNewAccount = document.getElementById('waittingCreateNewAccount');

const genOtpRequest = {
    email: null,
    otpType: "REGISTER"
}
const verifyOtpRequest = {
    email: null,
    otp: null,
    otpType: "REGISTER"
}
const registerRequest = {
    email: null,
    username: null,
    password: null
}


// view step control
function nextStep(step) {
    steps.forEach(s => s.classList.add("d-none"));
    document.getElementById("step" + step).classList.remove("d-none");
}


// logic
function handleNextEmail() {
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
    registerRequest.email = email;
    nextStep(2);
}


async function handleSendOtp() {
    const username = usernameInput.value.trim();
    const password = passwordInput.value.trim();
    const confirmPassword = confirmPasswordInput.value.trim();

    if (!username || !password || !confirmPassword) {
        alert("Nhập đủ trường thông tin");
        return;
    }

    if (password.length < 8) {
        alert("Mật khẩu tối thiểu 8 ký tự");
        return;
    }

    if (confirmPassword != password) {
        alert("Mật khẩu xác nhận không khớp");
        return;
    }

    registerRequest.username = username;
    registerRequest.password = password;

    btnSendOtp.classList.add('d-none');
    waittingSendOtp.classList.remove('d-none');
    try {
        await generateOtp(genOtpRequest);
        nextStep(3);

    } catch (error) {
        alert(error.message);
    } finally {
        btnSendOtp.classList.remove('d-none');
        waittingSendOtp.classList.add('d-none');
    }

}


async function handleVerifyOtp() {
    const otp = otpInput.value.trim();

    verifyOtpRequest.otp = otp;
    try {
        btnVerifyOtp.classList.add('d-none');
        waittingCreateNewAccount.classList.remove('d-none');
        await verifyOtp(verifyOtpRequest);

        const response = await register(registerRequest);

        alert(response.message);
        window.location.href = "../pages/login.html";

    } catch (error) {
        alert(error.message);
    } finally {
        waittingCreateNewAccount.classList.add('d-none');
        btnVerifyOtp.classList.remove('d-none');
    }
}


btnNextEmail.addEventListener("click", handleNextEmail);
btnSendOtp.addEventListener("click", handleSendOtp);
btnVerifyOtp.addEventListener("click", handleVerifyOtp);