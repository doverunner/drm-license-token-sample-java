package com.pallycon.sample.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pallycon.sample.exception.dto.ErrorDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created By NY on 2020-01-13.
 */
public class PallyConTokenException extends Exception{
    private static Logger logger = LoggerFactory.getLogger(PallyConTokenException.class);

    private ErrorDto errorDto;
    private String message;

    public PallyConTokenException(String message) {
        this.message = message;
        logger.error("{\"message\":\"" + message + "\"");
    }

    //TODO errorCode + ErrorDTO 로 변경해야 함 !!
    public PallyConTokenException(String message, ErrorDto errorDto) {
        this.errorDto = errorDto;
        this.message = message;

        logger.error("{\"message\":\"" + message + "\""
                + ",\"ERRORDTO\": " + objectToJson(errorDto) + "}" );

        makeErrorBody(errorDto);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }

    public ErrorDto getErrorDto() {
        return errorDto;
    }

    public void setErrorDto(ErrorDto errorDto) {
        this.errorDto = errorDto;
    }

    public void makeErrorBody(ErrorDto errorDto) {
        errorDto.setBody(objectToJson(errorDto));
    }


    private static String objectToJson(Object object){
        String errorString = "";
        try {
            ObjectMapper mapper = new ObjectMapper();
            errorString = mapper.writeValueAsString(object);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return errorString;
    }
}
