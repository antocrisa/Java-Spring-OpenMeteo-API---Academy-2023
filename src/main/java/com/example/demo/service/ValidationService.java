package com.example.demo.service;

import com.example.demo.enumeration.ErrorCode;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.DataNotValidException;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import java.time.LocalDate;
import java.util.Set;

@Service
public class ValidationService {


    private final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();

    public void doValidate(Object input) {
        Set<ConstraintViolation<Object>> constraintViolations = validatorFactory.getValidator().validate(input);
        if (!constraintViolations.isEmpty()) {
            throw new DataNotValidException(ErrorCode.DATA_NOT_VALID, constraintViolations);
        }
    }

    public void doValidateDays(Integer days) {
        if(days < 1 || days > 92) {
            throw new BadRequestException("Day number must be less than or equal to 92 (3 months)", ErrorCode.DATA_NOT_VALID);
        }
    }

    public void doValidateDate(LocalDate date) {

        if(LocalDate.now().minusMonths(3).compareTo(date) > 0) {
            throw new BadRequestException("Date must be max 3 months from today", ErrorCode.DATA_NOT_VALID);
        }
    }



}

