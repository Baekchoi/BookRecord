package com.example.bookrecord2.service;

import com.example.bookrecord2.dto.auth;
import com.example.bookrecord2.security.TokenProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;
    private final TokenProvider tokenProvider;

    public AuthService(UserDetailsService userDetailsService, AuthenticationManager authenticationManager, TokenProvider tokenProvider) {
        this.userDetailsService = userDetailsService;
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
    }

    public String authenticateUser(auth.SignIn signIn) {
        try {
            userDetailsService.loadUserByUsername(signIn.getEmail());
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            signIn.getEmail(),
                            signIn.getPassword()
                )
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return tokenProvider.generateToken(authentication);

        } catch (UsernameNotFoundException e) {
            throw new IllegalArgumentException("존재하지 않는 이메일입니다.");
        } catch (BadCredentialsException e) {
            throw new IllegalArgumentException("잘못된 비밀번호 입니다.");
        } catch (AuthenticationException e) {
            throw new RuntimeException("인증 과정에서 오류가 발생했습니다.");
        }
    }
}
