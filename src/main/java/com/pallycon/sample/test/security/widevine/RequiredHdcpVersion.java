package com.pallycon.sample.test.security.widevine;

/**
 * for @security_policy @widevine @required_hdcp_version
 */
public enum RequiredHdcpVersion {

    HDCP_NONE("HDCP_NONE"),
    HDCP_V1("HDCP_V1"),
    HDCP_V2("HDCP_V2"),
    HDCP_V2_1("HDCP_V2.1"),
    HDCP_V2_2("HDCP_V2.2"),
    HDCP_NO_DIGITAL_OUTPUT("HDCP_NO_DIGITAL_OUTPUT");

    private String value;

    RequiredHdcpVersion(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
