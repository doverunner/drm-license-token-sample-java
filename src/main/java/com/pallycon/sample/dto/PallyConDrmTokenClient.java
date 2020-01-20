package com.pallycon.sample.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pallycon.sample.dto.token.PolicyRequest;
import com.pallycon.sample.exception.PallyConTokenException;
import com.pallycon.sample.util.ApiModule;
import jdk.nashorn.internal.ir.ThrowNode;
import jdk.nashorn.internal.ir.TryNode;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created By NY on 2020-01-14.
 */
public class PallyConDrmTokenClient implements PallyConDrmToken{

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
    private String drmType;
    @JsonProperty("site_id")
    private String siteId;
    @JsonProperty("user_id")
    private String userId;
    @JsonProperty("cid")
    private String cId;
    @JsonProperty("policy")
    private String policy;
    @JsonProperty("timestamp")
    private String timestamp;
    @JsonProperty("hash")
    private String hash;

    @JsonIgnore
    private String siteKey;
    @JsonIgnore
    private String accessKey;

    private PallyConDrmTokenClient() {
    }

    private PallyConDrmTokenClient(PallyConDrmTokenClientBuilder pallyConDrmTokenClientBuilder) {
        this.drmType = pallyConDrmTokenClientBuilder.drmType;
        this.siteId = pallyConDrmTokenClientBuilder.siteId;
        this.userId = pallyConDrmTokenClientBuilder.userId;
        this.cId = pallyConDrmTokenClientBuilder.cId;
        this.policy = pallyConDrmTokenClientBuilder.policy;
        this.timestamp = pallyConDrmTokenClientBuilder.timestamp;
        this.hash = pallyConDrmTokenClientBuilder.hash;
        this.siteKey = pallyConDrmTokenClientBuilder.siteKey;
        this.accessKey = pallyConDrmTokenClientBuilder.accessKey;
    }

    public static class PallyConDrmTokenClientBuilder {

        private String drmType = "fairplay";
        private String siteId;
        private String userId;
        private String cId;
        private String policy;
        private String timestamp;
        private String hash;
        private String siteKey;
        private String accessKey;

        /**
         * siteKey, accessKey set first
         */
        public PallyConDrmTokenClientBuilder siteKey(String siteKey) {
            this.siteKey = siteKey;
            return this;
        }

        public PallyConDrmTokenClientBuilder accessKey(String accessKey) {
            this.accessKey = accessKey;
            return this;
        }

        public PallyConDrmTokenClientBuilder playready() {
            this.drmType = "playready";
            return this;
        }

        public PallyConDrmTokenClientBuilder widevine() {
            this.drmType = "widevine";
            return this;
        }

        public PallyConDrmTokenClientBuilder fairplay() {
            this.drmType = "fairplay";
            return this;
        }

        public PallyConDrmTokenClientBuilder siteId(String siteId) {
            this.siteId = siteId;
            return this;
        }

        public PallyConDrmTokenClientBuilder userId(String userId) {
            this.userId = userId;
            return this;
        }

        public PallyConDrmTokenClientBuilder cId(String cId) {
            this.cId = cId;
            return this;
        }

        public PallyConDrmTokenClientBuilder policy(PolicyRequest policyRequest) throws Exception {
            this.policy = ApiModule.makeEncryptPolicy(policyRequest.toJsonString(), this.siteKey);
            return this;
        }

        private PallyConDrmTokenClientBuilder timestamp(){
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
            format.setTimeZone(TimeZone.getTimeZone("GMT"));
            this.timestamp = format.format(new Date());
            return this;
        }

        private PallyConDrmTokenClientBuilder hash(){
            this.hash = ApiModule.makeHash (
                                    this.accessKey,
                                    this.drmType, this.siteId, this.userId, this.cId,
                                    this.policy, this.timestamp
                        );
            return this;
        }

        public PallyConDrmTokenClient execute() throws PallyConTokenException {
            timestamp();
            hash();

            PallyConDrmTokenClient drmTokenClient = new PallyConDrmTokenClient(this);
            validateSampleObject(drmTokenClient);
            return drmTokenClient;
        }

        private void validateSampleObject(PallyConDrmTokenClient drmTokenClient) throws PallyConTokenException {
            if (null == drmTokenClient.siteKey) {
                throw new PallyConTokenException("token err : set siteKey");
            }
            if (null == drmTokenClient.accessKey) {
                throw new PallyConTokenException("token err : set accessKey");
            }
            if (null == drmTokenClient.drmType) {
                throw new PallyConTokenException("token err : make drmType");
            }
            if (null == drmTokenClient.siteId) {
                throw new PallyConTokenException("token err : make drmType");
            }
            if (null == drmTokenClient.userId) {
                throw new PallyConTokenException("token err : make userId");
            }
            if (null == drmTokenClient.cId) {
                throw new PallyConTokenException("token err : make cId");
            }
            if (null == drmTokenClient.policy) {
                throw new PallyConTokenException("token err : make token");
            }
        }
    }
    //----------------------------- end of builder pattern

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
