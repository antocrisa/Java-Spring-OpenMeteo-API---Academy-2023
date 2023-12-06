package com.example.demo.exception;

import com.example.demo.enumeration.ErrorCode;

public class BadRequestException extends CustomErrorException {

    public BadRequestException(String message, ErrorCode errorCode) {
        super(message, errorCode);

    }

    public BadRequestException(String message, Throwable cause, ErrorCode errorCode) {
        super(message,cause,errorCode);

    }

}
