package com.myproject.ecommerce.service;

import com.myproject.ecommerce.dto.request.GenerateOtpRequest;
import com.myproject.ecommerce.dto.request.VerifyOtpRequest;
import com.myproject.ecommerce.enums.OtpType;
import com.myproject.ecommerce.exception.BaseException;
import com.myproject.ecommerce.exception.ErrorCode;
import java.time.Duration;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class OtpService {
    private final StringRedisTemplate stringRedisTemplate;
    private final MailService mailService;

    private static final long OTP_TTL = 120; // seconds

    // generate and send OTP
    public void generateOtp(GenerateOtpRequest request) {

        // rate limiting (max 5 click/h)
        String rateLimitKey = buildRateLimitKey(request.getEmail(), request.getOtpType());
        Long requestCount = stringRedisTemplate.opsForValue().increment(rateLimitKey);

        if (requestCount != null && requestCount == 1) {
            stringRedisTemplate.expire(rateLimitKey, 1, TimeUnit.HOURS);
        }

        if (requestCount != null && requestCount > 5) {
            throw new BaseException(ErrorCode.TOO_MANY_REQUESTS);
        }

        // redis key
        String key = buildKey(request.getEmail(), request.getOtpType());

        // deduplication check (waiting after 2 minutes)
        if (stringRedisTemplate.opsForValue().get(key) != null) {
            throw new BaseException(ErrorCode.OTP_ALREADY_SENT);
        }

        // gen OTP & Save in Redis
        String otp = String.valueOf(ThreadLocalRandom.current().nextInt(100000, 1000000));
        stringRedisTemplate.opsForValue().set(key, otp, OTP_TTL, TimeUnit.SECONDS);

        // send email
        mailService.sendOtp(request.getEmail(), otp);
    }

    // verify OTP
    public void verifyOtp(VerifyOtpRequest request) {

        // get key for checking
        String key = buildKey(request.getEmail(), request.getOtpType());
        String attemptKey = buildAttemptKey(request.getEmail(), request.getOtpType());
        String savedOtp = stringRedisTemplate.opsForValue().get(key);

        if (savedOtp == null) {
            throw new BaseException(ErrorCode.OTP_EXPIRED); // expired or not found
        }

        // incorrect
        if (!savedOtp.equals(request.getOtp())) {
            Long requestCount = stringRedisTemplate.opsForValue().increment(attemptKey);

            if (requestCount != null && requestCount == 1) {
                stringRedisTemplate.expire(attemptKey, OTP_TTL, TimeUnit.SECONDS); // TTL = OTP TTL
            }

            if (requestCount != null && requestCount > 3) {
                stringRedisTemplate.delete(key);
                throw new BaseException(ErrorCode.EXCEED_INPUT_LIMIT);
            }

            throw new BaseException(ErrorCode.OTP_INVALID); // otp incorrect
        }

        // correct
        stringRedisTemplate.delete(key); // avoid reusing old key
        stringRedisTemplate.delete(attemptKey);

        // create verified flag
        String verifiedKey = buildVerifiedKey(request.getEmail(), request.getOtpType());
        stringRedisTemplate.opsForValue().set(verifiedKey, "true", Duration.ofMinutes(5));
    }

    // check verify for OTP
    public void ensureOtpVerified(String email, OtpType type) {
        String verifiedKey = buildVerifiedKey(email, type);
        if (stringRedisTemplate.opsForValue().get(verifiedKey) == null) {
            throw new BaseException(ErrorCode.OTP_NOT_VERIFIED);
        }
    }

    // clear verify after completing
    public void clearVerify(String email, OtpType type) {
        String verifiedKey = buildVerifiedKey(email, type);
        stringRedisTemplate.delete(verifiedKey);
    }

    private String buildKey(String email, OtpType type) {
        return "otp:" + type.name().toLowerCase() + ":" + email; // otp:{type}:{email}
    }

    private String buildVerifiedKey(String email, OtpType type) {
        return "otp:verified:" + type.name().toLowerCase() + ":" + email; // otp:verified:{type}:{email}
    }

    private String buildRateLimitKey(String email, OtpType type) {
        return "otp_rate:" + type.name().toLowerCase() + ":" + email;
    }

    private String buildAttemptKey(String email, OtpType type) {
        return "otp_attempt:" + type.name().toLowerCase() + ":" + email;
    }
}
