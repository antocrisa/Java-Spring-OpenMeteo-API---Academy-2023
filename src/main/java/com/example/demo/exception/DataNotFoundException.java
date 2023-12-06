package com.example.demo.exception;

import com.example.demo.enumeration.ErrorCode;

public class DataNotFoundException extends CustomErrorException {

    public DataNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
    public DataNotFoundException(String message, ErrorCode errorCode) {
        super(message,errorCode);
    }
}
