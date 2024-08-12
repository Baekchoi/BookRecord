package com.example.bookrecord2.dto;

import lombok.Data;

public class auth {

    @Data
    public static class SignUp {
        private String email;
        private String password;
        private String nickname;
    }

    @Data
    public static class SignIn {
        private String email;
        private String password;
    }
}
