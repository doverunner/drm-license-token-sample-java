package com.pallycon.sample.test;

/**
 * for @security_policy @fairplay
 */
public enum FairplayHdcpEnforcement {

    HDCP_NONE("HDCP_NONE", -1),
    HDCP_ANY("HDCP_ANY", 0),
    HDCP_OVER_VER_2_2("HDCP_OVER_VER_2_2", 1);

    private String type;
    private int value;

    FairplayHdcpEnforcement(String type, int value) {
        this.type = type;
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public int getValue() {
        return value;
    }
}
