package com.example.demo.controller;

import java.util.Map;
import java.util.Optional;

import org.springframework.web.bind.annotation.*;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin("*")
public class AuthController {

    private final UserRepository repo;

    public AuthController(UserRepository repo) {
        this.repo = repo;
    }

    @PostMapping("/register")
    public Object register(@RequestBody User user) {

        Optional<User> existing = repo.findByUsername(user.getUsername());

        if (existing.isPresent()) {
            return Map.of("message", "Tài khoản đã tồn tại");
        }

        if (user.getRole() == null) {
            user.setRole("USER");
        }

        return repo.save(user);
    }

    @PostMapping("/login")
    public Object login(@RequestBody Map<String, String> body) {

        Optional<User> user = repo.findByUsername(body.get("username"));

        if (user.isEmpty()) {
            return Map.of("message", "Sai tài khoản");
        }

        if (!user.get().getPassword().equals(body.get("password"))) {
            return Map.of("message", "Sai mật khẩu");
        }

        return user.get();
    }
}