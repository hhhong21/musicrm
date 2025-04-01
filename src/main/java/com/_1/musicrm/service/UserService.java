package com._1.musicrm.service;

import com._1.musicrm.exception.DuplicateEmailException;
import com._1.musicrm.exception.InvalidPasswordException;
import com._1.musicrm.model.User;
import com._1.musicrm.repository.UserRepository;

import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private static final String UPLOAD_DIR = "uploads/"; // 本地存储路径
    
    // 通过构造函数注入 passwordEncoder
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public User registerUser(User user) {
        // 1. 检查邮箱是否已注册
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new DuplicateEmailException("The email has been registered!");
        }

        // 2. 确保前端传递的密码字段有效
        String rawPassword = user.getPlainTextPassword(); // 获取明文密码
        if (rawPassword == null || rawPassword.trim().isEmpty()) {
            throw new IllegalArgumentException("The password can't be empty!");
        }

        // 3. 加密密码并存储
        String encodePassword = passwordEncoder.encode(rawPassword);
        user.setPassword(encodePassword);

        // 4. 保存用户到数据库
        return userRepository.save(user);
    }

    //上传头像
    public String uploadAvatar(MultipartFile file) {
        try {
            // 目标路径
            Path uploadPath = Paths.get(UPLOAD_DIR);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // 保存文件
            Path filePath = uploadPath.resolve(file.getOriginalFilename());
            Files.write(filePath, file.getBytes());

            return "/uploads/" + file.getOriginalFilename(); // 返回存储路径
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload:" + e.getMessage());
        }
    }

    //更新用户信息
    public User updateUserProfile(User user) {
        User existingUser = userRepository.findByEmail(user.getEmail()).orElseThrow(
            () -> new RuntimeException("The user does not exist.")
        );

        // 更新字段
        existingUser.setUsername(user.getUsername());

        return userRepository.save(existingUser);
    }

    //修改密码
    public void changePassword(String email, String currentPassword, String newPassword) {
        User user = userRepository.findByEmail(email).orElseThrow(
            () -> new RuntimeException("The user does not exist.")
        );

        // 校验当前密码
        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            throw new InvalidPasswordException("Current password is incorrect.");
        }

        // 更新密码（加密存储）
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }
}