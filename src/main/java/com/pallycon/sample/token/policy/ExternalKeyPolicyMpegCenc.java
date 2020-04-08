package com.pallycon.sample.token.policy;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.pallycon.sample.test.TrackType;
import lombok.Data;

/**
 * Created By NY on 2020-01-14.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"track_type", "key_id", "key", "iv"})
public class ExternalKeyPolicyMpegCenc {

    @JsonProperty("track_type")
    private TrackType trackType;
    @JsonProperty("key_id")
    private String keyId;
    @JsonProperty("key")
    private String key;
    @JsonProperty("iv")
    private String iv;

    @Deprecated
    public ExternalKeyPolicyMpegCenc(String keyId, String key, String iv) {
        this.keyId = keyId;
        this.key = key;
        this.iv = iv;
    }


    //TODO CHANGE CONSTRUCTOR TO THIS
    public ExternalKeyPolicyMpegCenc(TrackType trackType, String keyId, String key, String iv) {
        this.trackType = trackType;
        this.keyId = keyId;
        this.key = key;
        this.iv = iv;
    }
}
