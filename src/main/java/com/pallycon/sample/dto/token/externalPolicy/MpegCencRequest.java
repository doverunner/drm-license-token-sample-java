package com.pallycon.sample.dto.token.externalPolicy;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Created By NY on 2020-01-14.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MpegCencRequest {
    @JsonProperty("key_id")
    private String keyId;
    @JsonProperty("key")
    private String key;
    @JsonProperty("iv")
    private String iv;

    public MpegCencRequest(String keyId, String key, String iv) {
        this.keyId = keyId;
        this.key = key;
        this.iv = iv;
    }
}
