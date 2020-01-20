package com.pallycon.sample.exception.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created By NY on 2020-01-15.
 */
public class ErrorDto {
    // json type 으로 error dto 만들기
    @JsonProperty("error_code")
    String errorCode;
    @JsonProperty("message")
    String message;
    @JsonIgnore
    String body;

    public ErrorDto() { super(); }

    public ErrorDto(String errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
