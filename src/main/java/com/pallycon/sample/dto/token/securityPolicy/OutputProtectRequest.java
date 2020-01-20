package com.pallycon.sample.dto.token.securityPolicy;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Created By NY on 2020-01-14.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OutputProtectRequest {
    @JsonProperty("allow_external_display")
    private boolean allowExternalDisplay = false;
    @JsonProperty("control_hdcp")
    private int controlHdcp = 0;

    public OutputProtectRequest() {
    }

    public OutputProtectRequest(boolean allowExternalDisplay) {
        this.allowExternalDisplay = allowExternalDisplay;
    }

    public OutputProtectRequest(int controlHdcp) {
        this.controlHdcp = controlHdcp;
    }

    public OutputProtectRequest(boolean allowExternalDisplay, int controlHdcp) {
        this.allowExternalDisplay = allowExternalDisplay;
        this.controlHdcp = controlHdcp;
    }

    public boolean isAllowExternalDisplay() {
        return allowExternalDisplay;
    }

    public void setAllowExternalDisplay(boolean allowExternalDisplay) {
        this.allowExternalDisplay = allowExternalDisplay;
    }

    public int getControlHdcp() {
        return controlHdcp;
    }

    public void setControlHdcp(int controlHdcp) {
        this.controlHdcp = controlHdcp;
    }
}
