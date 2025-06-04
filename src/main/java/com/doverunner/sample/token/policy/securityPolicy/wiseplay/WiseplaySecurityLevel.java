package com.doverunner.sample.token.policy.securityPolicy.wiseplay;

import java.util.Arrays;

public enum WiseplaySecurityLevel {
    SW_LEVEL_1(1),
    HW_LEVEL_2(2),
    ENHANCED_HW_LEVEL_3(3);

    private int value;

    WiseplaySecurityLevel(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public static int getValue(int value) {
        if (value == 0) {
            throw new IllegalArgumentException("Value cannot be null or empty!");
        }

        return Arrays.stream(WiseplaySecurityLevel.values())
                .filter(level -> level.getValue() == value)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Cannot create enum from " + value + " value!"))
                .getValue();

    }

    @Override
    public String toString() {
        return String.valueOf(this.value);
    }
}
