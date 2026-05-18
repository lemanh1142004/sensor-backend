package com.example.demo.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin("*")
public class AdminController {

    private final UserRepository userRepository;

    public AdminController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 1. Lấy tất cả người dùng (Trừ ADMIN)
    @GetMapping("/users")
    public List<User> getAllUsersExceptAdmin() {
        return userRepository.findByRoleNot("ADMIN");
    }

    // 2. Thay đổi trạng thái tài khoản (Khóa / Mở khóa)
    @PutMapping("/users/{id}/status")
    public Object updateUserStatus(@PathVariable Long id, @RequestBody Map<String, String> body) {
        Optional<User> userOptional = userRepository.findById(id);
        
        if (userOptional.isEmpty()) {
            return Map.of("success", false, "message", "Không tìm thấy người dùng");
        }

        User user = userOptional.get();
        String newStatus = body.get("status"); // Nhận vào "LOCKED" hoặc "ACTIVE"
        
        user.setStatus(newStatus);
        userRepository.save(user);

        return Map.of("success", true, "message", "Cập nhật trạng thái thành công", "user", user);
    }
}