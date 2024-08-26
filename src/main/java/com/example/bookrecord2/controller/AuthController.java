package com.example.bookrecord2.controller;

import com.example.bookrecord2.dto.JwtResponse;
import com.example.bookrecord2.dto.auth;
import com.example.bookrecord2.service.AuthService;
import com.example.bookrecord2.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody auth.SignUp signUp) {
        try {
            String result = userService.registerUser(signUp);
            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody auth.SignIn signIn) {
        String jwt = authService.authenticateUser(signIn);
        return ResponseEntity.ok(new JwtResponse(jwt));
    }
}
