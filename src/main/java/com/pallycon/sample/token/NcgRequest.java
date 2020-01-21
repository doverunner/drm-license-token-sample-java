package com.pallycon.sample.token;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Created By NY on 2020-01-14.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NcgRequest {
    @JsonProperty("cek")
    private String cek;

    public NcgRequest(String cek) {
        this.cek = cek;
    }
}