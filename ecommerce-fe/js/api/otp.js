import { httpClient } from "../common/httpClient.js";

export async function generateOtp(genOtpRequest) {

    let type;
    switch (genOtpRequest.otpType) {
        case 'UPDATE_PASSWORD':
            type = 'update-pass';
            break;

        case 'FORGOT_PASSWORD':
            type = 'forgot-pass';
            break;

        default:
            type = 'register';
    }

    return httpClient(`/accounts/${type}/email/otp`, {
        method: 'POST',
        body: JSON.stringify(genOtpRequest)
    });
}


export async function verifyOtp(verifyOtpRequest) {
    return httpClient('/accounts/email/verify', {
        method: 'POST',
        body: JSON.stringify(verifyOtpRequest)
    });
}