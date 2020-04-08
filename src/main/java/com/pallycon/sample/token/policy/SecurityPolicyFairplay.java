package com.pallycon.sample.token.policy;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.pallycon.sample.test.FairplayHdcpEnforcement;

/**
 * @related security_policy
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder
        ({"hdcp_enforcement", "allow_airplay", "allow_av_adapter"})
public class SecurityPolicyFairplay {

    @JsonProperty("hdcp_enforcement")
    private int hdcpEnforcement;

    @JsonProperty("allow_airplay")
    private boolean allowAirplay;

    @JsonProperty("allow_av_adapter")
    private boolean allowAvAdapter;

    public SecurityPolicyFairplay() {
        this.hdcpEnforcement = FairplayHdcpEnforcement.HDCP_NONE.getValue();
        this.allowAirplay = true;
        this.allowAvAdapter = true;
    }

    public SecurityPolicyFairplay hdcpEnforcement(FairplayHdcpEnforcement hdcpEnforcement) {
        this.hdcpEnforcement = hdcpEnforcement.getValue();
        return this;
    }
    public SecurityPolicyFairplay allowAirplay(boolean allowAirplay) {
        this.allowAirplay = allowAirplay;
        return this;
    }
    public SecurityPolicyFairplay allowAvAdapter(boolean allowAvAdapter) {
        this.allowAvAdapter = allowAvAdapter;
        return this;
    }

    public int getHdcpEnforcement() {
        return hdcpEnforcement;
    }

    public boolean getAllowAirplay() {
        return allowAirplay;
    }

    public boolean getAllowAvAdapter() {
        return allowAvAdapter;
    }
}
