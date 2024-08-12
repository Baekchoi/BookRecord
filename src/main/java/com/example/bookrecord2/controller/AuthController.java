package com.example.bookrecord2.controller;

import com.example.bookrecord2.dto.JwtResponse;
import com.example.bookrecord2.dto.auth;
import com.example.bookrecord2.dto.constant.Authority;
import com.example.bookrecord2.entity.User;
import com.example.bookrecord2.repository.UserRepository;
import com.example.bookrecord2.security.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenProvider tokenProvider;

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody auth.SignUp signUp) {
        if (userRepository.existsByEmail(signUp.getEmail())) {
            return ResponseEntity.badRequest().body("이미 사용된 이메일입니다.");
        }

        if (userRepository.existsByNickname(signUp.getNickname())) {
            return ResponseEntity.badRequest().body("이미 사용된 닉네임입니다.");
        }

        User user = new User();
        user.setEmail(signUp.getEmail());
        user.setPassword(passwordEncoder.encode(signUp.getPassword()));
        user.setNickname(signUp.getNickname());
        user.setRoles(String.valueOf(Authority.ROLE_USER));

        userRepository.save(user);

        return ResponseEntity.ok("로그인에 성공했습니다.");
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody auth.SignIn signIn) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        signIn.getEmail(),
                        signIn.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.generateToken(authentication);

        return ResponseEntity.ok(new JwtResponse(jwt));
    }
}
