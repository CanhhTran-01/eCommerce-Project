package com.myproject.ecommerce.service;

import com.myproject.ecommerce.dto.request.GenerateOtpRequest;
import com.myproject.ecommerce.dto.request.VerifyOtpRequest;
import com.myproject.ecommerce.enums.ErrorCode;
import com.myproject.ecommerce.enums.OtpType;
import com.myproject.ecommerce.exception.BaseException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class OtpService {
    private final StringRedisTemplate stringRedisTemplate;
    private final MailService mailService;

    private static final long OTP_TTL = 120; // seconds


    // generate and send OTP
    public void generateOtp(GenerateOtpRequest request){
        // gen otp
        String otp = String.valueOf(ThreadLocalRandom.current().nextInt(100000, 1000000));

        // redis key
        String key = buildKey(request.getEmail(), request.getOtpType());

        // fix user spam
        if (stringRedisTemplate.opsForValue().get(key) != null) {
            throw new BaseException(ErrorCode.OTP_ALREADY_SENT);
        }

        // save redis
        stringRedisTemplate.opsForValue().set(key, otp, OTP_TTL, TimeUnit.SECONDS);

        // send email
        mailService.sendOtp(request.getEmail(), otp);
    }


    // verify OTP
    public void verifyOtp(VerifyOtpRequest request){
        // get key for checking
        String key = buildKey(request.getEmail(), request.getOtpType());
        String savedOtp = stringRedisTemplate.opsForValue().get(key);

        if (savedOtp == null) {
            throw new BaseException(ErrorCode.OTP_EXPIRED); // expired or not found
        }

        if (!savedOtp.equals(request.getOtp())) {
            throw new BaseException(ErrorCode.OTP_INVALID); // false otp
        }

        stringRedisTemplate.delete(key); // avoid reusing

        // create verified flag
        String verifiedKey = buildVerifiedKey(request.getEmail(), request.getOtpType());
        stringRedisTemplate.opsForValue().set(verifiedKey, "true", Duration.ofMinutes(5));
    }


    // check verify for OTP
    public void ensureOtpVerified(String email, OtpType type){
        String verifiedKey = buildVerifiedKey(email, type);
        if (stringRedisTemplate.opsForValue().get(verifiedKey) == null) {
            throw new BaseException(ErrorCode.OTP_NOT_VERIFIED);
        }
    }


    // clear verify after completing
    public void clearVerify(String email, OtpType type){
        String verifiedKey = buildVerifiedKey(email, type);
        stringRedisTemplate.delete(verifiedKey);
    }


    private String buildKey(String email, OtpType type) {
        return "otp:" + type.name().toLowerCase() + ":" + email;  // otp:{type}:{email}
    }
    private String buildVerifiedKey(String email, OtpType type){
        return "otp:verified:" + type.name().toLowerCase() + ":" + email; // otp:verified:{type}:{email}
    }
}
