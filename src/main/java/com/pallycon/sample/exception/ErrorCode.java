package com.pallycon.sample.exception;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created By NY on 2020-01-15.
 */
public class ErrorCode {
    @JsonProperty("error_code")
    private String errorCode;
    @JsonProperty("error_message")
    private String errorMessage;

    public ErrorCode() {
    }

    public ErrorCode(String errorCode) {
        this.errorCode = errorCode;
        this.setErrorMessage(errorCode);
    }

    // TODO switch case 로 바꾸기
    private void setErrorMessage(String errorCode) {
        if ("1000".equals(errorCode)) {
            this.errorMessage = "Token err : The userId is Required";
        }

        if ("1001".equals(errorCode)) {
            this.errorMessage = "Token err : The cId is Required";
        }

        if ("1002".equals(errorCode)) {
            this.errorMessage = "Token err : The siteId is Required";
        }

        if ("1003".equals(errorCode)) {
            this.errorMessage = "Token err : The accessKey is Required";
        }

        if ("1004".equals(errorCode)) {
            this.errorMessage = "Token err : The siteKey is Required";
        }

        if ("1005".equals(errorCode)) {
            this.errorMessage = "Token err : The policy is Required";
        }

        if ("1009".equals(errorCode)) {
            this.errorMessage = "PlaybackPolicy : The expireDate time format should be 'YYYY-MM-DD'T'HH:mm:ss'Z'";
        }

        if ("1010".equals(errorCode)) {
            this.errorMessage = "PlaybackPolicy : The limit value should be true when setting duration or expireDate";
        }

        if ("1014".equals(errorCode)) {
            this.errorMessage = "SecurityPolicy : The playreadySecurityLevel should be in 150 or more";
        }

        if ("1017".equals(errorCode)) {
            this.errorMessage = "HlsAes : The Key should be 16byte hex String";
        }

        if ("1018".equals(errorCode)) {
            this.errorMessage = "HlsAes : The Iv should be 16byte hex String";
        }

        if ("1019".equals(errorCode)) {
            this.errorMessage = "MpegCenc : The KeyId should be 16byte hex String";
        }

        if ("1020".equals(errorCode)) {
            this.errorMessage = "MpegCenc : The Key should be 16byte hex String";
        }

        if ("1021".equals(errorCode)) {
            this.errorMessage = "MpegCenc : The Iv should be 16byte hex String";
        }

        if ("1022".equals(errorCode)) {
            this.errorMessage = "Ncg : The Cek should be 32byte hex String";
        }

    }

    public String getErrorCode() {
        return this.errorCode;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

}
