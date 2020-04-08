package com.pallycon.sample.test.security.playready;

/**
 * for @security_policy @playready @security_level @analog_video_protection_level
 */
public enum CompressedDigitalAudioProtection {
    LEVEL_100(100),
    LEVEL_150(150),
    LEVEL_200(200),
    LEVEL_201(201);

    private int value;

    CompressedDigitalAudioProtection(int value) {
        this.value = value;
    }
    
    public int getValue() {
        return this.value;
    }
}
