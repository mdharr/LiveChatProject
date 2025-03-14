package com.mdharr.livechat.controllers;

import com.mdharr.livechat.dtos.LoginRequest;
import com.mdharr.livechat.entities.User;
import com.mdharr.livechat.services.CaptchaService;
import com.mdharr.livechat.services.UserService;
import com.mdharr.livechat.security.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenUtil tokenUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CaptchaService captchaService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User registeredUser = userService.register(user);
        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        if (!captchaService.verifyCaptcha(loginRequest.getCaptcha())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Captcha validation failed.");
        }
        User user = userService.findByUsername(loginRequest.getUsername())
                .orElse(null);
        if(user == null || !passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            return ResponseEntity.status(401).body("Invalid username or password");
        }
        String token = tokenUtil.generateToken(user.getUsername());
        return ResponseEntity.ok(token);
    }
}
