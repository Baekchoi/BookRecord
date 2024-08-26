package com.example.bookrecord2.exception.impl;

import com.example.bookrecord2.exception.AbstractException;
import org.springframework.http.HttpStatus;

public class UnauthorizedException extends AbstractException {

    @Override
    public int getStatusCode() {
        return HttpStatus.BAD_REQUEST.value();
    }

    @Override
    public String getMessage() {
        return "본인이 작성한 글만 삭제할 수 있습니다.";
    }
}
