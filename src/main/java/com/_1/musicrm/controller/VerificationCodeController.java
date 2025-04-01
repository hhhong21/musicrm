package com._1.musicrm.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com._1.musicrm.requset.EmailRequest;
import com._1.musicrm.requset.VerificationRequest;
import com._1.musicrm.service.VerificationCodeService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/verification")
@Validated  // 让 @Valid 生效
public class VerificationCodeController {

    private final VerificationCodeService verificationCodeService;

    public VerificationCodeController(VerificationCodeService verificationCodeService) {
        this.verificationCodeService = verificationCodeService;
    }

    // 发送验证码
    @PostMapping("/send")
    public ResponseEntity<Map<String, String>> sendVerificationCode(@Valid @RequestBody EmailRequest request) {
        boolean success = verificationCodeService.sendVerificationCode(request.getEmail());
        return success
                ? ResponseEntity.ok(Map.of("message", "The verification code has been sent!"))
                : ResponseEntity.badRequest().body(Map.of("error", "Please try again later."));
    }
    

    // 验证验证码
    @PostMapping("/verify")
    public ResponseEntity<Map<String, String>> verifyCode(@Valid @RequestBody VerificationRequest request) {
        boolean valid = verificationCodeService.verifyCode(request.getEmail(), request.getVerificationCode());
        return valid
                ? ResponseEntity.ok(Map.of("message", "The verification code is correct!"))
                : ResponseEntity.badRequest().body(Map.of("error", "The verification code is incorrect or has expired."));
    }
}