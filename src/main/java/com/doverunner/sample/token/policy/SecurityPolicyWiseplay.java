package com.doverunner.sample.token.policy;

import com.doverunner.sample.token.policy.securityPolicy.wiseplay.OutputControl;
import com.doverunner.sample.token.policy.securityPolicy.wiseplay.WiseplaySecurityLevel;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * @related security_policy
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonSerialize()
@JsonPropertyOrder({"security_level", "output_control"})

public class SecurityPolicyWiseplay {

    @JsonProperty("security_level")
    private Integer securityLevel;
    @JsonProperty("output_control")
    private Integer outputControl;

    public SecurityPolicyWiseplay() {
    }

    public SecurityPolicyWiseplay securityLevel(WiseplaySecurityLevel securityLevel) {
        this.securityLevel = securityLevel.getValue();
        return this;
    }

    public SecurityPolicyWiseplay outputControl(OutputControl outputControl) {
        this.outputControl = outputControl.getValue();
        return this;
    }

    public Integer getSecurityLevel() {
        return securityLevel;
    }
    public Integer getOutputControl() { return outputControl; }
}
