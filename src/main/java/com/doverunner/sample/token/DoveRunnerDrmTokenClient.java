package com.doverunner.sample.token;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.doverunner.sample.config.Config;
import com.doverunner.sample.exception.DoveRunnerTokenException;
import com.doverunner.sample.token.policy.common.ResponseFormat;
import com.doverunner.sample.util.StringEncrypter;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.TimeZone;

/**
 * constructs token for license server
 */

@JsonPropertyOrder({
        "drm_type", "site_id", "user_id", "cid", "policy", "response_format", "key_rotation", "timestamp", "hash"
})
public class DoveRunnerDrmTokenClient implements DoveRunnerDrmToken {

    /**
     *  To make DoveRunnerToken, REQUIREMENTS are below .
     *  @param drmType
     *  @param siteId
     *  @param userId
     *  @param cId
     *  @param policy
     *  @param responseFormat
     *  @param timestamp
     *  @param hash BEFORE SET hash, other fields are prerequisite.
     * */

    @JsonProperty("drm_type")
    private String drmType;
    @JsonProperty("site_id")
    private String siteId;
    @JsonProperty("user_id")
    private String userId;
    @JsonProperty("cid")
    private String cId;
    @JsonProperty("policy")
    private String encPolicy;
    @JsonProperty("response_format")
    private String responseFormat;
    @JsonProperty("key_rotation")
    private Boolean keyRotation;
    @JsonProperty("timestamp")
    private String timestamp;
    @JsonProperty("hash")
    private String hash;

    @JsonIgnore
    private String siteKey;
    @JsonIgnore
    private String accessKey;
    @JsonIgnore
    private String policy;
    @JsonIgnore
    private static final String AES_IV = "0123456789abcdef";

    // default : TODO SET CONFIG FIELDS.
    public DoveRunnerDrmTokenClient() {
        this.playready()
            .siteId(Config.SITE_ID)
            .siteKey(Config.SITE_KEY)
            .accessKey(Config.ACCESS_KEY)
            .userId(Config.USER_ID)
            .cId(Config.C_ID)
            .responseFormat(ResponseFormat.ORIGINAL)
            .keyRotation(false);
    }

    public DoveRunnerDrmTokenClient siteKey(String siteKey) {
        this.siteKey = siteKey;
        return this;
    }

    public DoveRunnerDrmTokenClient accessKey(String accessKey) {
        this.accessKey = accessKey;
        return this;
    }

    public DoveRunnerDrmTokenClient playready() {
        this.drmType = "PlayReady";
        return this;
    }

    public DoveRunnerDrmTokenClient widevine() {
        this.drmType = "Widevine";
        return this;
    }

    public DoveRunnerDrmTokenClient fairplay() {
        this.drmType = "FairPlay";
        return this;
    }

    public DoveRunnerDrmTokenClient ncg() {
        this.drmType = "NCG";
        return this;
    }

    public DoveRunnerDrmTokenClient wiseplay() {
        this.drmType = "Wiseplay";
        return this;
    }

    public DoveRunnerDrmTokenClient siteId(String siteId) {
        this.siteId = siteId;
        return this;
    }

    public DoveRunnerDrmTokenClient userId(String userId) {
        this.userId = userId;
        return this;
    }

    public DoveRunnerDrmTokenClient cId(String cId) {
        this.cId = cId;
        return this;
    }

    public DoveRunnerDrmTokenClient policy(DoveRunnerDrmTokenPolicy policyRequest) throws DoveRunnerTokenException, Exception {
        this.policy = policyRequest.toJsonString();
        StringEncrypter stringEncrypter = new StringEncrypter(this.siteKey, AES_IV);
        this.encPolicy = stringEncrypter.encrypt(this.policy);
        return this;
    }

    public DoveRunnerDrmTokenClient responseFormat(ResponseFormat responseFormat) {
        this.responseFormat = responseFormat.getValue();
        return this;
    }

    public DoveRunnerDrmTokenClient keyRotation(Boolean keyRotation) {
        this.keyRotation = keyRotation;
        return this;
    }

    private DoveRunnerDrmTokenClient timestamp(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        format.setTimeZone(TimeZone.getTimeZone("GMT"));
        this.timestamp = format.format(new Date());
        return this;
    }

    private DoveRunnerDrmTokenClient hash() throws NoSuchAlgorithmException {
        StringBuffer bf = new StringBuffer();
        bf.append(this.accessKey);
        bf.append(this.drmType);
        bf.append(this.siteId);
        bf.append(this.userId);
        bf.append(this.cId);
        bf.append(this.encPolicy);
        bf.append(this.timestamp);
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] bytes = digest.digest(bf.toString().getBytes(StandardCharsets.UTF_8));
        this.hash = Base64.getEncoder().encodeToString(bytes);
        return this;
    }

    public String execute() {
        String result = "";

        try {
            validateSampleObject();
            timestamp();
            hash();
            result = Base64.getEncoder().encodeToString(this.toJsonString().getBytes());
        } catch (DoveRunnerTokenException e) {
            result = e.getMessage();
        } catch (Exception e) {
            result = e.getMessage();
        }

        return result;
    }

    private void validateSampleObject() throws DoveRunnerTokenException {
        if (null == this.userId) {
            throw new DoveRunnerTokenException("1000");
        }
        if (null == this.cId) {
            throw new DoveRunnerTokenException("1001");
        }
        if (null == this.siteId) {
            throw new DoveRunnerTokenException("1002");
        }
        if (null == this.accessKey) {
            throw new DoveRunnerTokenException("1003");
        }
        if (null == this.siteKey) {
            throw new DoveRunnerTokenException("1004");
        }
        if (null == this.policy) {
            throw new DoveRunnerTokenException("1005");
        }
    }

    @Override
    public String toJsonString() throws DoveRunnerTokenException {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new DoveRunnerTokenException("2001");
        }
    }

    @Override
    public String getDrmType() {
        return this.drmType;
    }

    @Override
    public String getSiteId() {
        return this.siteId;
    }

    @Override
    public String getUserId() {
        return this.userId;
    }

    @Override
    public String getCId() {
        return this.cId;
    }

    @Override
    public String getPolicy() {
        return this.policy;
    }

    @Override
    public String getSiteKey() {
        return this.siteKey;
    }

    @Override
    public String getAccessKey() {
        return this.accessKey;
    }

}
