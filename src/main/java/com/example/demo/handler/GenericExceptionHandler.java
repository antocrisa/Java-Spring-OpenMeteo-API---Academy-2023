package com.example.demo.handler;

import com.example.demo.dto.ErrorResponseDto;
import com.example.demo.enumeration.ErrorCode;
import com.example.demo.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
public class GenericExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleDataNotFoundException(DataNotFoundException dataNotFoundException) {
        log.error("Data not found exception", dataNotFoundException);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponseDto.builder()
                .errorCode(ErrorCode.DATA_NOT_FOUND)
                .errorMessage(dataNotFoundException.getMessage())
                .build());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleEntityNotFoundException(EntityNotFoundException entityNotFoundException) {
        log.error("Entity not found exception", entityNotFoundException);
        return handleCustomErrorException(entityNotFoundException, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InternalErrorException.class)
    public ResponseEntity<ErrorResponseDto> handleInternalErrorException(InternalErrorException internalErrorException) {
        log.error("Internal Error Exception", internalErrorException);
        return handleCustomErrorException(internalErrorException, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(DataNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handleDataNotValidException(DataNotValidException dataNotValidException) {
        log.error("Data not valid exception", dataNotValidException);
        return handleCustomErrorException(dataNotValidException, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<ErrorResponseDto> handleCustomErrorException(CustomErrorException customErrorException, HttpStatus httpStatus) {
        log.error("Managed Exception", customErrorException);

        return ResponseEntity.status(httpStatus).body(
                ErrorResponseDto.builder()
                        .errorCode(customErrorException.getErrorCode())
                        .errorMessage(customErrorException.getMessage())
                        .build());
    }

}

