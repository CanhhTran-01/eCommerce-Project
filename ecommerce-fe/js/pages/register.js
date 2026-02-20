let generatedOtp = null;

// ===== cache DOM =====
const steps = document.querySelectorAll(".step");

const emailInput = document.getElementById("email");
const usernameInput = document.getElementById("username");
const passwordInput = document.getElementById("password");
const otpInput = document.getElementById("otp");

const btnNextEmail = document.getElementById("btn-next-email");
const btnSendOtp = document.getElementById("btn-send-otp");
const btnVerifyOtp = document.getElementById("btn-verify-otp");


// ===== step control =====
function nextStep(step) {
    steps.forEach(s => s.classList.add("d-none"));
    document.getElementById("step" + step).classList.remove("d-none");
}


// ===== business logic =====
function handleNextEmail() {
    const email = emailInput.value.trim();

    if (!email) {
        alert("Nhập email");
        return;
    }

    nextStep(2);
}

function handleSendOtp() {
    const username = usernameInput.value.trim();
    const password = passwordInput.value.trim();

    if (!username || !password) {
        alert("Nhập đủ username + password");
        return;
    }

    generatedOtp = Math.floor(100000 + Math.random() * 900000);
    console.log("OTP:", generatedOtp);

    alert("OTP đã gửi (mock). Check console");

    nextStep(3);
}

function handleVerifyOtp() {
    const otp = otpInput.value;

    if (otp == generatedOtp) {
        alert("Đăng ký thành công");
        window.location.href = "../pages/login.html";
    } else {
        alert("OTP sai");
    }
}


// ===== event binding =====
btnNextEmail.addEventListener("click", handleNextEmail);
btnSendOtp.addEventListener("click", handleSendOtp);
btnVerifyOtp.addEventListener("click", handleVerifyOtp);