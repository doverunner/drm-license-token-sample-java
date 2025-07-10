package com.doverunner.sample.token;

import com.doverunner.sample.exception.DoveRunnerTokenException;

public interface DoveRunnerDrmToken {

    String getDrmType();

    String getSiteId();

    String getUserId();

    String getCId();

    String getPolicy();

    String getSiteKey();

    String getAccessKey();

    String toJsonString() throws DoveRunnerTokenException;

}
