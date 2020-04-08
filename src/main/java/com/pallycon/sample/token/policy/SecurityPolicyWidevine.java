package com.pallycon.sample.token.policy;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.pallycon.sample.test.security.widevine.HdcpSrmRule;
import com.pallycon.sample.test.security.widevine.RequiredCgmsFlags;
import com.pallycon.sample.test.security.widevine.RequiredHdcpVersion;
import com.pallycon.sample.test.security.widevine.WidevineSecurityLevel;

/**
 * @related security_policy
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"security_level", "required_hdcp_version", "required_cgms_flags",
        "disable_analog_output", "hdcp_srm_rule"})

public class SecurityPolicyWidevine {

    @JsonProperty("security_level")
    private int securityLevel;
    @JsonProperty("required_hdcp_version")
    private String requiredHdcpVersion;
    @JsonProperty("required_cgms_flags")
    private String requiredCgmsFlags;
    @JsonProperty("disable_analog_output")
    private boolean disableAnalogOutput;
    @JsonProperty("hdcp_srm_rule")
    private String hdcpSrmRule;

    public SecurityPolicyWidevine() {
        this.securityLevel = WidevineSecurityLevel.SW_SECURE_CRYPTO.getValue();
        this.requiredHdcpVersion = RequiredHdcpVersion.HDCP_NONE.getValue();
        this.requiredCgmsFlags = RequiredCgmsFlags.CGMS_NONE.getValue();
        this.hdcpSrmRule = HdcpSrmRule.HDCP_SRM_RULE_NONE.getValue();
    }

    public SecurityPolicyWidevine securityLevel(WidevineSecurityLevel securityLevel) {
        this.securityLevel = securityLevel.getValue();
        return this;
    }

    public SecurityPolicyWidevine requiredHdcpVersion(RequiredHdcpVersion requiredHdcpVersion) {
        this.requiredHdcpVersion = requiredHdcpVersion.getValue();
        return this;
    }

    public SecurityPolicyWidevine requiredCgmsFlags(RequiredCgmsFlags requiredCgmsFlags) {
        this.requiredCgmsFlags = requiredCgmsFlags.getValue();
        return this;
    }

    public SecurityPolicyWidevine disableAnalogOutput(boolean disableAnalogOutput) {
        this.disableAnalogOutput=disableAnalogOutput;
        return this;
    }

    public SecurityPolicyWidevine hdcpSrmRule(HdcpSrmRule hdcpSrmRule) {
        this.hdcpSrmRule = hdcpSrmRule.getValue();
        return this;
    }

    public int getSecurityLevel() {
        return securityLevel;
    }

    public String getRequiredHdcpVersion() {
        return requiredHdcpVersion;
    }

    public String getRequiredCgmsFlags() {
        return requiredCgmsFlags;
    }

    public boolean getDisableAnalogOutput() {
        return disableAnalogOutput;
    }

    public String getHdcpSrmRule() {
        return hdcpSrmRule;
    }
}
