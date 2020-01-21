package com.pallycon.sample.token;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pallycon.sample.exception.PallyConTokenException;
import com.pallycon.sample.util.StringEncrypter;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created By NY on 2020-01-14.
 */
public class PallyConDrmTokenClient implements PallyConDrmToken {

    /**
     *  To make PallyConToken, REQUIREMENTS are below .
     *  @param drmType
     *  @param siteId
     *  @param userId
     *  @param cId
     *  @param policy
     *  @param timestamp
     *  @param hash BEFORE SET hash, other fields are prerequisite.
     * */

    @JsonProperty("drm_type")
    private String drmType = "PlayReady";
    @JsonProperty("site_id")
    private String siteId;
    @JsonProperty("user_id")
    private String userId;
    @JsonProperty("cid")
    private String cId;
    @JsonProperty("policy")
    private String encPolicy;
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

    public PallyConDrmTokenClient() {

    }
    public PallyConDrmTokenClient siteKey(String siteKey) {
        this.siteKey = siteKey;
        return this;
    }

    public PallyConDrmTokenClient accessKey(String accessKey) {
        this.accessKey = accessKey;
        return this;
    }

    public PallyConDrmTokenClient playready() {
        this.drmType = "PlayReady";
        return this;
    }

    public PallyConDrmTokenClient widevine() {
        this.drmType = "Widevine";
        return this;
    }

    public PallyConDrmTokenClient fairplay() {
        this.drmType = "FairPlay";
        return this;
    }

    public PallyConDrmTokenClient siteId(String siteId) {
        this.siteId = siteId;
        return this;
    }

    public PallyConDrmTokenClient userId(String userId) {
        this.userId = userId;
        return this;
    }

    public PallyConDrmTokenClient cId(String cId) {
        this.cId = cId;
        return this;
    }

    public PallyConDrmTokenClient policy(PolicyRequest policyRequest) throws Exception {
        this.policy = policyRequest.toJsonString();
        StringEncrypter stringEncrypter = new StringEncrypter(this.siteKey, AES_IV);
        this.encPolicy = stringEncrypter.encrypt(policyRequest.toJsonString());
        return this;
    }

    private PallyConDrmTokenClient timestamp(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
        format.setTimeZone(TimeZone.getTimeZone("GMT"));
        this.timestamp = format.format(new Date());
        return this;
    }

    private PallyConDrmTokenClient hash() throws Exception {
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

    public String execute() throws PallyConTokenException, JsonProcessingException, Exception {
        String result = "";

        try {
            validateSampleObject();
            timestamp();
            hash();
            result = Base64.getEncoder().encodeToString(this.toJsonString().getBytes());
        } catch (PallyConTokenException e) {
            result = e.getMessage();
        }

        return result;
    }

    private void validateSampleObject() throws PallyConTokenException {
        if (null == this.userId) {
            throw new PallyConTokenException("1000");
        }
        if (null == this.cId) {
            throw new PallyConTokenException("1001");
        }
        if (null == this.siteId) {
            throw new PallyConTokenException("1002");
        }
        if (null == this.accessKey) {
            throw new PallyConTokenException("1003");
        }
        if (null == this.siteKey) {
            throw new PallyConTokenException("1004");
        }
        if (null == this.policy) {
            throw new PallyConTokenException("1005");
        }
    }




    @Override
    public String toJsonString() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(this);
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
