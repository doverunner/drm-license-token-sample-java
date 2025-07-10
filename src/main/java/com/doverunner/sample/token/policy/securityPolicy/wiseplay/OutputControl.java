package com.doverunner.sample.token.policy.securityPolicy.wiseplay;

import java.util.Arrays;

public enum OutputControl {
    HDCP_NONE(0),
    HDCP_V1_4(1),
    HDCP_V2_2(2),
    HDCP_RESTRICTED(3);

    private int value;

    OutputControl(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public static int getValue(int value) {

       return Arrays.stream(OutputControl.values())
                .filter(control -> control.getValue() == value)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Cannot create enum from " + value + " value!"))
                .getValue();
    }

    @Override
    public String toString() {
        return String.valueOf(this.value);
    }
}
