package com.pallycon.sample.config;

public enum ResponseFormat {
    ORIGINAL("original"),
    CUSTOM("custom");

    private String value;

    ResponseFormat(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
