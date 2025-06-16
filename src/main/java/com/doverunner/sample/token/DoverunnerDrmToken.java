package com.doverunner.sample.token;

import com.doverunner.sample.exception.DoverunnerTokenException;

public interface DoverunnerDrmToken {

    String getDrmType();

    String getSiteId();

    String getUserId();

    String getCId();

    String getPolicy();

    String getSiteKey();

    String getAccessKey();

    String toJsonString() throws DoverunnerTokenException;

}
