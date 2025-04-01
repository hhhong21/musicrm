package com._1.musicrm.service;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;

import java.util.concurrent.TimeUnit;
import java.util.Random;

@Service
public class VerificationCodeService {

    private static final long CODE_EXPIRE_MINUTES = 5; // 验证码有效期5分钟
    private static final long RESEND_INTERVAL_SECONDS = 60; // 重新发送间隔60秒

    private final StringRedisTemplate redisTemplate;
    private final EmailService emailService;
    private final Random random = new Random();

    public VerificationCodeService(StringRedisTemplate redisTemplate, EmailService emailService) {
        this.redisTemplate = redisTemplate;
        this.emailService = emailService;
    }

    public String generateVerificationCode() {
        return String.format("%06d", random.nextInt(1000000)); // 生成6位随机验证码
    }

    public boolean sendVerificationCode(String email) {
        if (email == null || email.isEmpty()) {
            return false; // 确保 email 非空
        }

        String redisKey = "verification_code:" + email;
        String lastSentTimeKey = "verification_last_sent:" + email;

        // 先检查键是否存在
        Boolean exists = redisTemplate.hasKey(lastSentTimeKey);
        if (Boolean.TRUE.equals(exists)) {
            Long lastSentTime = redisTemplate.getExpire(lastSentTimeKey, TimeUnit.SECONDS);
            if (lastSentTime != null && lastSentTime > 0) {
                return false; // 仍在冷却时间内
            }
        }

        // 生成验证码
        String code = generateVerificationCode();
        redisTemplate.opsForValue().set(redisKey, code, CODE_EXPIRE_MINUTES, TimeUnit.MINUTES);
        redisTemplate.opsForValue().set(lastSentTimeKey, "1", RESEND_INTERVAL_SECONDS, TimeUnit.SECONDS);

        // 构建邮件内容
        String subject = "MusicRM Account Verification Code";
        String content = "<div style='font-family: Arial, sans-serif; line-height: 1.6;'>"
                + "<h2 style='color: #333;'>Dear user,</h2>"
                + "<p>Your verification code is: <strong style='font-size: 18px; color: #d9534f;'>" + code + "</strong></p>"
                + "<p>Please use this verification code within 5 minutes to complete your registration.</p>"
                + "<p>Thank you for using <strong>MusicRM</strong>!</p>"
                + "<br><p>Best regards,<br><strong>MusicRM Team</strong></p>"
                + "</div>";

        try {
            emailService.sendHtmlEmail(email, subject, content);
        } catch (MessagingException e) {
            e.printStackTrace();
            // 发送失败时，删除 Redis 记录，允许用户重新请求
            redisTemplate.delete(redisKey);
            redisTemplate.delete(lastSentTimeKey);
            return false;
        }
        return true;
    }

    public boolean verifyCode(String email, String code) {
        if (email == null || email.isEmpty() || code == null || code.isEmpty()) {
            return false; // 确保 email 和 code 非空
        }

        String redisKey = "verification_code:" + email;
        String storedCode = redisTemplate.opsForValue().get(redisKey);

        if (storedCode != null && storedCode.equals(code)) {
            redisTemplate.delete(redisKey); // 验证成功后删除验证码
            return true;
        }
        return false;
    }
}