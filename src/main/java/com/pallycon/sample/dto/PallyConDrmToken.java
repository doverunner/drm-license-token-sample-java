package com.pallycon.sample.dto;

import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * Created By NY on 2020-01-16.
 */
public interface PallyConDrmToken {

    String toJsonString() throws JsonProcessingException;

    String getDrmType();

    String getSiteId();

    String getUserId();

    String getCId();

    String getPolicy();

    String getSiteKey();

    String getAccessKey();

}
