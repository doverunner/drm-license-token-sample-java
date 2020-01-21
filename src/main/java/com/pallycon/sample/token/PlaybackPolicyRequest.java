package com.pallycon.sample.token;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.pallycon.sample.exception.PallyConTokenException;
import lombok.Data;

import java.util.regex.Pattern;

/**
 * Created By NY on 2020-01-13.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PlaybackPolicyRequest {
    private boolean limit = false;
    private boolean persistent = false;
    private int duration;
    @JsonProperty("expire_date")
    private String expireDate;

    public PlaybackPolicyRequest() {
    }

    public PlaybackPolicyRequest(boolean limit) {
        this.limit = limit;
    }

    public PlaybackPolicyRequest(boolean limit, boolean persistent) {
        this.limit = limit;
        this.persistent = persistent;
    }

    public PlaybackPolicyRequest(boolean limit, boolean persistent, int duration) {
        this.limit = limit;
        this.persistent = persistent;
        this.duration = duration;
    }

    public PlaybackPolicyRequest(boolean limit, boolean persistent, String expireDate) {
        this.limit = limit;
        this.persistent = persistent;
        this.expireDate = expireDate;
    }

    public void check() throws PallyConTokenException {
        if (null != this.expireDate && !checkDates(this.expireDate)) {
            throw new PallyConTokenException("1009");
        }
        if (false == this.limit && (0 != this.duration || null != this.expireDate)) {
            throw new PallyConTokenException("1010");
        }
    }


    private boolean checkDates(String expireDate) {
        Pattern pattern = Pattern.compile("^[0-9]{4}-[0,1][0-9]-[0-5][0-9]T[0-2][0-3]:[0-5][0-9]:[0-5][0-9]Z$");
        return pattern.matcher(expireDate).matches();
    }
}
