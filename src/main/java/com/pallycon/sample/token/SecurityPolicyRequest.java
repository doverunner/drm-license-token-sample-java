package com.pallycon.sample.token;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.pallycon.sample.exception.PallyConTokenException;

/**
 * Created By NY on 2020-01-13.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SecurityPolicyRequest {

    @JsonProperty("hardware_drm")
    private boolean hardwareDrm = false; //default: false
    @JsonProperty("output_protect")
    private OutputProtectRequest outputProtect = new OutputProtectRequest();
    @JsonProperty("allow_mobile_abnormal_device")
    private boolean allowMobileAbnormalDevice = false; //default: false
    @JsonProperty("playready_security_level")
    private int playreadySecurityLevel = 150; //default : 150

    public SecurityPolicyRequest() {
    }

    public SecurityPolicyRequest(boolean hardwareDrm) {
        this.hardwareDrm = hardwareDrm;
    }

    public SecurityPolicyRequest(boolean hardwareDrm, OutputProtectRequest outputProtect) {
        this.hardwareDrm = hardwareDrm;
        this.outputProtect = outputProtect;
    }

    public SecurityPolicyRequest(boolean hardwareDrm, OutputProtectRequest outputProtect, boolean allowMobileAbnormalDevice) {
        this.hardwareDrm = hardwareDrm;
        this.outputProtect = outputProtect;
        this.allowMobileAbnormalDevice = allowMobileAbnormalDevice;
    }

    public SecurityPolicyRequest(boolean hardwareDrm, OutputProtectRequest outputProtect
            , boolean allowMobileAbnormalDevice, int playreadySecurityLevel) {
        this.hardwareDrm = hardwareDrm;
        this.outputProtect = outputProtect;
        this.allowMobileAbnormalDevice = allowMobileAbnormalDevice;
        this.playreadySecurityLevel = playreadySecurityLevel;
    }

    public boolean isHardwareDrm() {
        return hardwareDrm;
    }

    public void setHardwareDrm(boolean hardwareDrm) {
        this.hardwareDrm = hardwareDrm;
    }

    public OutputProtectRequest getOutputProtect() {
        return outputProtect;
    }

    public void setOutputProtect(OutputProtectRequest outputProtect) {
        this.outputProtect = outputProtect;
    }

    public boolean isAllowMobileAbnormalDevice() {
        return allowMobileAbnormalDevice;
    }

    public void setAllowMobileAbnormalDevice(boolean allowMobileAbnormalDevice) {
        this.allowMobileAbnormalDevice = allowMobileAbnormalDevice;
    }

    public int getPlayreadySecurityLevel() {
        return playreadySecurityLevel;
    }

    public void setPlayreadySecurityLevel(int playreadySecurityLevel) {
        this.playreadySecurityLevel = playreadySecurityLevel;
    }

    public void check() throws PallyConTokenException {
        if (150 > this.playreadySecurityLevel) {
            throw new PallyConTokenException("1014");
        }
    }
}
