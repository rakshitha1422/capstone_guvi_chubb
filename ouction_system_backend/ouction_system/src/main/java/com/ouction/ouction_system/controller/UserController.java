package com.ouction.ouction_system.controller;

import com.ouction.ouction_system.model.User;
import com.ouction.ouction_system.service.EmailService;
import com.ouction.ouction_system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController

@RequestMapping("/api")
public class UserController {

    @Autowired
    UserService service;

    private String generatedUniqueCode;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    EmailService emailService;

    @PostMapping("/get-unique-code")
    public ResponseEntity<Map<String, String>> getUniqueCode(@RequestBody Map<String, String> requestBody) {
        String email = requestBody.get("email"); // Get the user's email from the frontend
        generatedUniqueCode = UUID.randomUUID().toString().substring(0, 6).toUpperCase();

        // Send email with the unique code
            emailService.sendEmail(email,"Verification code",generatedUniqueCode);
            Map<String, String> response = new HashMap<>();
            response.put("uniqueCode", generatedUniqueCode);
            return ResponseEntity.ok(response);

    }

    @PostMapping("/verify-unique-code")
    public ResponseEntity<Map<String, Boolean>> verifyUniqueCode(@RequestBody Map<String, String> requestBody) {
        String enteredCode = requestBody.get("enteredUniqueCode");
        Map<String, Boolean> response = new HashMap<>();
        response.put("verified", generatedUniqueCode != null && generatedUniqueCode.equals(enteredCode));
        return ResponseEntity.ok(response);
    }

    @PostMapping("/usersignup/save")
    public String registerUser(@RequestBody User user) {
        String name = service.addUser(user);
        return name;
    }

    @GetMapping("/check-username/{username}")
    public ResponseEntity<Map<String, Boolean>> checkUsername(@PathVariable String username) {
        boolean isAvailable = !service.existsByUsername(username);
        Map<String, Boolean> response = new HashMap<>();
        response.put("available", isAvailable);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody Map<String, String> loginData) {
        String username = loginData.get("username");
        String password = loginData.get("password");

        User user = service.findByUsername(username);

        Map<String, String> response = new HashMap<>();

        if (user == null) {
            response.put("message", "Username does not exist.");
            return ResponseEntity.badRequest().body(response);
        }

        if (!passwordEncoder.matches(password, user.getUserpassword())) {
            response.put("message", "Incorrect password.");
            return ResponseEntity.badRequest().body(response);
        }

        response.put("message", "Login successful.");
        response.put("role", user.getUserrole());
        return ResponseEntity.ok(response);
    }






}
