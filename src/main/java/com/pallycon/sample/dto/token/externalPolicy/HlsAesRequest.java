package com.pallycon.sample.dto.token.externalPolicy;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Created By NY on 2020-01-14.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HlsAesRequest {
    @JsonProperty("key")
    private String key;
    @JsonProperty("iv")
    private String iv;

    public HlsAesRequest(String key, String iv) {
        this.key = key;
        this.iv = iv;
    }
}
