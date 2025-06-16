package com.doverunner.sample.token.policy;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.doverunner.sample.token.policy.securityPolicy.playready.*;

/**
 * @related security_policy
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder(
        {"security_level", "digital_video_protection_level", "analog_video_protection_level",
        "digital_audio_protection_level", "require_hdcp_type_1", "enable_license_cipher"})
public class SecurityPolicyPlayready {

    @JsonProperty("security_level")
    private Integer securityLevel;

    @JsonProperty("digital_video_protection_level")
    private Integer digitalVideoProtection;

    @JsonProperty("analog_video_protection_level")
    private Integer analogVideoProtection;

    @JsonProperty("digital_audio_protection_level")
    private Integer digitalAudioProtection;

    @JsonProperty("require_hdcp_type_1")
    private Boolean requireHdcpType1 = false;

    @JsonProperty("enable_license_cipher")
    private Boolean enableLicenseCipher = false;

    public SecurityPolicyPlayready() {
    }

    public SecurityPolicyPlayready securityLevel(PlayreadySecurityLevel securityLevel) {
        this.securityLevel = securityLevel.getValue();
        return this;
    }

    public SecurityPolicyPlayready digitalVideoProtection(DigitalVideoProtection digitalVideoProtection) {
        this.digitalVideoProtection = digitalVideoProtection.getValue();
        return this;
    }

    public SecurityPolicyPlayready analogVideoProtection(AnalogVideoProtection analogVideoProtection) {
        this.analogVideoProtection = analogVideoProtection.getValue();
        return this;
    }

    public SecurityPolicyPlayready digitalAudioProtection(DigitalAudioProtection digitalAudioProtection) {
        this.digitalAudioProtection = digitalAudioProtection.getValue();
        return this;
    }

    public SecurityPolicyPlayready requireHdcpType1(boolean requireHdcpType1) {
        this.requireHdcpType1 = requireHdcpType1;
        return this;
    }

    public SecurityPolicyPlayready enableLicenseCipher(boolean enableLicenseCipher) {
        this.enableLicenseCipher = enableLicenseCipher;
        return this;
    }

    public Integer getSecurityLevel() {
        return securityLevel;
    }

    public Integer getDigitalVideoProtection() {
        return digitalVideoProtection;
    }

    public Integer getAnalogVideoProtection() {
        return analogVideoProtection;
    }

    public Boolean getRequireHdcpType1() {
        return requireHdcpType1;
    }

    public Integer getDigitalAudioProtection() {
        return digitalAudioProtection;
    }

    public Boolean getEnableLicenseCipher() { return enableLicenseCipher; }
}
