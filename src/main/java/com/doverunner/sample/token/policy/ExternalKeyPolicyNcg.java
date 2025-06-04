package com.doverunner.sample.token.policy;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExternalKeyPolicyNcg {
    @JsonProperty("cek")
    private String cek;

    public ExternalKeyPolicyNcg(String cek) {
        this.cek = cek;
    }

    public String getCek() {
        return cek;
    }
}
