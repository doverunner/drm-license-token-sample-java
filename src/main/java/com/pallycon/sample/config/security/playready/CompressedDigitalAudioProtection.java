package com.pallycon.sample.config.security.playready;

/**
 * for @security_policy @playready @security_level @analog_video_protection_level
 */
@Deprecated
public enum CompressedDigitalAudioProtection {
    LEVEL_100(100),
    LEVEL_301(301);

    private int value;

    CompressedDigitalAudioProtection(int value) {
        this.value = value;
    }
    
    public int getValue() {
        return this.value;
    }
}
