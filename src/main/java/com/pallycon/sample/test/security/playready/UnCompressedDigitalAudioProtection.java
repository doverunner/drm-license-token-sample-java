package com.pallycon.sample.test.security.playready;

/**
 * for @security_policy @playready @uncompressed_digital_audio_protection_level
 */
public enum UnCompressedDigitalAudioProtection {

    LEVEL_100(100),
    LEVEL_250(250),
    LEVEL_300(300),
    LEVEL_301(301)
    ;

    private int value;

    UnCompressedDigitalAudioProtection(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }
}
