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

    private void setErrorMessage(String errorCode) {

        switch (errorCode) {
            case "1000":
                this.errorMessage = "Token err : The userId is Required";
                break;
            case "1001":
                this.errorMessage = "Token err : The cId is Required";
                break;
            case "1002":
                this.errorMessage = "Token err : The siteId is Required";
                break;
            case "1003":
                this.errorMessage = "Token err : The accessKey is Required";
                break;
            case "1004":
                this.errorMessage = "Token err : The siteKey is Required";
                break;
            case "1005":
                this.errorMessage = "Token err : The policy is Required";
                break;
            case "1009":
                this.errorMessage = "PlaybackPolicy : The expireDate time format should be 'YYYY-MM-DD'T'HH:mm:ss'Z'";
                break;
            case "1010":
                this.errorMessage = "PlaybackPolicy : The limit value should be true when setting duration or expireDate";
                break;
            case "1014":
                this.errorMessage = "SecurityPolicy : The playreadySecurityLevel should be in 150 or more";
                break;
            case "1017":
                this.errorMessage = "HlsAes : The Key should be 16byte hex String";
                break;
            case "1018":
                this.errorMessage = "HlsAes : The Iv should be 16byte hex String";
                break;
            case "1019":
                this.errorMessage = "MpegCenc : The KeyId should be 16byte hex String";
                break;
            case "1020":
                this.errorMessage = "MpegCenc : The Key should be 16byte hex String";
                break;
            case "1021":
                this.errorMessage = "MpegCenc : The Iv should be 16byte hex String";
                break;
            case "1022":
                this.errorMessage = "Ncg : The Cek should be 32byte hex String";
                break;
        }
    }

    public String getErrorCode() {
        return this.errorCode;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

}
