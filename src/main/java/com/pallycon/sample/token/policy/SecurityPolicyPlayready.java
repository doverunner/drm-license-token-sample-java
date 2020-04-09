package com.pallycon.sample.token.policy;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.pallycon.sample.config.security.playready.*;

/**
 * @related security_policy
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder(
        {"security_level", "digital_video_protection_level", "analog_video_protection_level",
        "compressed_digital_audio_protection_level", "uncompressed_digital_audio_protection_level",
        "require_hdcp_type_1"})
public class SecurityPolicyPlayready {

    @JsonProperty("security_level")
    private int securityLevel;

    @JsonProperty("digital_video_protection_level")
    private int digitalVideoProtection;

    @JsonProperty("analog_video_protection_level")
    private int analogVideoProtection;

    @JsonProperty("compressed_digital_audio_protection_level")
    private int compressedDigitalAudioProtection;

    @JsonProperty("uncompressed_digital_audio_protection_level")
    private int uncompressedDigitalAudioProtection;

    @JsonProperty("require_hdcp_type_1")
    private boolean requireHdcpType1;

    public SecurityPolicyPlayready() {
        this.securityLevel = PlayreadySecurityLevel.LEVEL_150.getValue();
        this.digitalVideoProtection = DigitalVideoProtection.LEVEL_100.getValue();
        this.analogVideoProtection = AnalogVideoProtection.LEVEL_100.getValue();
        this.compressedDigitalAudioProtection = CompressedDigitalAudioProtection.LEVEL_100.getValue();
        this.uncompressedDigitalAudioProtection = UnCompressedDigitalAudioProtection.LEVEL_100.getValue();
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

    public SecurityPolicyPlayready compressedDigitalAudioProtection(CompressedDigitalAudioProtection compressedDigitalAudioProtection) {
        this.compressedDigitalAudioProtection = compressedDigitalAudioProtection.getValue();
        return this;
    }

    public SecurityPolicyPlayready uncompressedDigitalAudioProtection(UnCompressedDigitalAudioProtection uncompressedDigitalAudioProtection) {
        this.uncompressedDigitalAudioProtection = uncompressedDigitalAudioProtection.getValue();
        return this;
    }

    public SecurityPolicyPlayready requireHdcpType1(boolean requireHdcpType1) {
        this.requireHdcpType1 = requireHdcpType1;
        return this;
    }

    public int getSecurityLevel() {
        return securityLevel;
    }

    public int getDigitalVideoProtection() {
        return digitalVideoProtection;
    }

    public int getAnalogVideoProtection() {
        return analogVideoProtection;
    }

    public int getCompressedDigitalAudioProtection() {
        return compressedDigitalAudioProtection;
    }

    public int getUncompressedDigitalAudioProtection() {
        return uncompressedDigitalAudioProtection;
    }

    public boolean getRequireHdcpType1() {
        return requireHdcpType1;
    }
}
