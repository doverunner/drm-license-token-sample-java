package com.pallycon.sample.token.policy;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.pallycon.sample.exception.PallyConTokenException;
import com.pallycon.sample.test.AllowedTrackTypes;

import java.util.regex.Pattern;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"persistent", "license_duration", "expire_date", "allowed_track_types"})
public class PlaybackPolicy {

    @JsonProperty("persistent")
    private boolean persistent;

    @JsonProperty("license_duration")
    private int licenseDuration;

    @JsonProperty("expire_date")
    private String expireDate;

    @JsonProperty("allowed_track_types")
    private AllowedTrackTypes allowedTrackTypes;

    public PlaybackPolicy() {
        this.persistent = false;
        this.licenseDuration = 0;
        this.expireDate = "";
        this.allowedTrackTypes = AllowedTrackTypes.SD_HD;
    }


    /**
     * BUILDER PATTERN VER 2.0
     * */
    public PlaybackPolicy persistent(boolean persistent) {
        this.persistent = persistent;
        return this;
    }

    public PlaybackPolicy licenseDuration(int licenseDuration) {
        this.licenseDuration = licenseDuration;
        return this;
    }

    public PlaybackPolicy expireDate(String expireDate) {
        this.expireDate = expireDate;
        return this;
    }

    public PlaybackPolicy allowedTrackTypes(AllowedTrackTypes allowedTrackTypes) {
        this.allowedTrackTypes = allowedTrackTypes;
        return this;
    }

    public void check() throws PallyConTokenException {
        if (!"".equals(this.expireDate) && !checkDates(this.expireDate)) {
            throw new PallyConTokenException("1009");
        }
    }

    private boolean checkDates(String expireDate) {
        Pattern pattern = Pattern.compile("^[0-9]{4}-[0,1][0-9]-[0-5][0-9]T[0-2][0-3]:[0-5][0-9]:[0-5][0-9]Z$");
        return pattern.matcher(expireDate).matches();
    }


    /**
     * getter
     * */
    public boolean getPersistent() {
        return persistent;
    }

    public int getLicenseDuration() {
        return licenseDuration;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public AllowedTrackTypes getAllowedTrackTypes() {
        return allowedTrackTypes;
    }
}
