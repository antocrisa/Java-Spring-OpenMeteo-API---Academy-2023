package com.example.demo.enumeration;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(enumAsRef = true, description = "Error codes: \n" +
        "* `INTERNAL_ERROR` - Something went wrong\n" +
        "* `DATA_NOT_FOUND` - No data found\n" +
        "* `DATA_NOT_VALID` - Invalid data provided\n" +
        "* `ISTAT_ALREADY_EXIST` - Istat code already in use\n" +
        "* `ERROR_READING_FILE` - Error reading file\n" +
        "")
public enum ErrorCode {

    INTERNAL_ERROR, DATA_NOT_VALID, DATA_NOT_FOUND, ISTAT_ALREADY_EXIST, ERROR_READING_FILE
}
