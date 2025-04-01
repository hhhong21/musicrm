package com._1.musicrm.controller;

import com._1.musicrm.model.User;
import com._1.musicrm.requset.VerificationRequest;
import com._1.musicrm.service.UserService;
import com._1.musicrm.service.VerificationCodeService;
import com._1.musicrm.exception.DuplicateEmailException;
import com._1.musicrm.util.ResponseUtils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class UserRegLogApiController {

    private final UserService userService;
    private final VerificationCodeService verificationCodeService;

    public UserRegLogApiController(UserService userService, VerificationCodeService verificationCodeService) {
        this.userService = userService;
        this.verificationCodeService = verificationCodeService;
    }

    // 用户注册
    @PostMapping("/userRegister")
    public ResponseEntity<?> registerUser(@Valid @RequestBody VerificationRequest request, BindingResult result) {
        // 1️⃣ 校验请求数据
        if (result.hasErrors()) {
            Map<String, String> errors = result.getFieldErrors().stream()
                    .collect(Collectors.toMap(
                            FieldError::getField,
                            error -> error.getDefaultMessage() != null ? error.getDefaultMessage() : "Invalid value."
                    ));
            return ResponseEntity.badRequest().body(errors);
        }

        // 2️⃣ 验证验证码是否正确
        if (!verificationCodeService.verifyCode(request.getEmail(), request.getVerificationCode())) {
            return ResponseEntity.badRequest()
                    .body(ResponseUtils.createResponse("error", "Invalid or expired verification code."));
        }

        // 3️⃣ 创建用户对象
        try {
            User user = new User();
            user.setEmail(request.getEmail());
            user.setPlainTextPassword(request.getPassword()); // 设置明文密码
            user.setUsername(request.getUsername()); // 可能的字段

            // 4️⃣ 调用注册逻辑
            User registeredUser = userService.registerUser(user);

            // 5️⃣ 返回成功信息
            Map<String, Object> response = ResponseUtils.createResponse("message", "Registered successfully!");
            response.put("email", registeredUser.getEmail());
            return ResponseEntity.ok(response);
        } catch (DuplicateEmailException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(ResponseUtils.createResponse("error", ex.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseUtils.createResponse("error", "Failed to register, please try again later."));
        }
    }

    //上传头像
    @PostMapping("/upload-avatar")
    public ResponseEntity<?> uploadAvatar(@RequestParam("avatar") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body(ResponseUtils.createResponse("error", "Please upload a valid avatar."));
        }

        try {
            String fileUrl = userService.uploadAvatar(file); // 调用 Service 层处理头像上传
            return ResponseEntity.ok(ResponseUtils.createResponse("message", "Avatar uploaded successfully!", "url", fileUrl));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseUtils.createResponse("error", "Failed to upload avatar, please try again later."));
        }
    }

    //更新个人信息
    @PutMapping("/update-profile")
    public ResponseEntity<?> updateProfile(@RequestBody User user) {
        try {
            User updatedUser = userService.updateUserProfile(user);
            return ResponseEntity.ok(ResponseUtils.createResponse("message", "Personal information updated successfully!", "user", updatedUser));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseUtils.createResponse("error", "Failed to update personal information, please try again later."));
        }
    }

    //更改密码
    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(
        @RequestParam("email") String email,  // 需要提供 email 参数
        @RequestParam("currentPassword") String currentPassword,
        @RequestParam("newPassword") String newPassword) {
        try {
            userService.changePassword(email, currentPassword, newPassword);
            return ResponseEntity.ok(ResponseUtils.createResponse("message", "Password changed successfully!"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ResponseUtils.createResponse("error", "Parameter error：" + e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseUtils.createResponse("error", "Failed to change password, please try again later."));
        }
    }
}