package com.example.bookrecord2.service;

import com.example.bookrecord2.dto.auth;
import com.example.bookrecord2.entity.User;
import com.example.bookrecord2.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // 추가한 부분
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("해당 이메일의 사용자를 찾을 수 없습니다."));
    }

    @Transactional
    public String registerUser(auth.SignUp signUp) {
        if (userRepository.existsByEmail(signUp.getEmail())) {
            throw new IllegalArgumentException("이미 사용된 이메일입니다.");
        }

        if (userRepository.existsByNickname(signUp.getNickname())) {
            throw new IllegalArgumentException("이미 사용된 닉네임입니다.");
        }

        User user = User.builder()
                .email(signUp.getEmail())
                .password(passwordEncoder.encode(signUp.getPassword()))
                .nickname(signUp.getNickname())
                .roles("ROLE_USER")
                .build();

        userRepository.save(user);

        return "회원가입에 성공했습니다.";
    }

    public String getNicknameByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("해당 이메일의 사용자를 찾을 수 없습니다."));

        return user.getNickname();
    }
}
