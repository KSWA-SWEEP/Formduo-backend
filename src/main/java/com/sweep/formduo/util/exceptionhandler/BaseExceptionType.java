package com.sweep.formduo.util.exceptionhandler;

import org.springframework.http.HttpStatus;

public interface BaseExceptionType {
    String getErrorCode();
    String getMessage();
    HttpStatus getHttpStatus();
}
