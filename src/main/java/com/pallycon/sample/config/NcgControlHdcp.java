package com.pallycon.sample.config;

/**
 * for @security_policy @control_hdcp
 */
public enum NcgControlHdcp {

    HDCP_NONE("HDCP_NONE", 0),
    HDCP_ANY("HDCP_ANY", 1),
    HDCP_OVER_VER_2_2("HDCP_OVER_VER_2_2", 2);

    private String type;
    private int value;

    NcgControlHdcp(String type, int value) {
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
