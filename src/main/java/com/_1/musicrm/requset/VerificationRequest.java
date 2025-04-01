package com._1.musicrm.requset;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class VerificationRequest {
    @NotBlank(message = "The email can't be empty!")
    @Email(message = "Invalid email format!")
    private String email;

    @NotBlank(message = "The password can't be empty!")
    private String password;

    @NotBlank(message = "Verification code can't be empty!")
    private String verificationCode;

    private String username; // 用户名（可选）

    // Getter 和 Setter 方法
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}