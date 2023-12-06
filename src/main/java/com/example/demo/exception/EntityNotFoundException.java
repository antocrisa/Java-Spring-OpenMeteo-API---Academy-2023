package com.example.demo.exception;

import com.example.demo.enumeration.ErrorCode;

public class EntityNotFoundException extends CustomErrorException {

    public EntityNotFoundException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }
}
