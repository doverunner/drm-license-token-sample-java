package com.doverunner.sample.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class DoveRunnerTokenException extends Exception{
    private static Logger logger = LoggerFactory.getLogger(DoveRunnerTokenException.class);
    private ErrorCode errorCode;
    private String message;

    public DoveRunnerTokenException(String code) {
        this.errorCode = new ErrorCode(code);
        this.message = objectToJson(this.errorCode);
        logger.error(this.message);
    }

    public String getMessage() {
        return this.message;
    }

    public ErrorCode getErrorCode() {
        return this.errorCode;
    }

    private static String objectToJson(Object object) {
        String errorString = "";

        try {
            ObjectMapper mapper = new ObjectMapper();
            errorString = mapper.writeValueAsString(object);
        } catch (IOException var3) {
            var3.printStackTrace();
        }

        return errorString;
    }
}
