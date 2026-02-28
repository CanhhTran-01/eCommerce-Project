package com.myproject.ecommerce.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender mailSender;

    // send OTP
    public void sendOtp(String to, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("OTP Verification for Ecommerce");
        message.setText("Your OTP is: " + otp);

        mailSender.send(message);
    }

    // send new password
    public void sendNewPassword(String to, String username, String newPassword) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Xin Chào, " + username + " !");
        message.setText("Mật khẩu mới của bạn là: " + newPassword + ", Xin vui lòng không chia sẻ mật khẩu này với "
                + "bất kì ai.");

        mailSender.send(message);
    }
}
