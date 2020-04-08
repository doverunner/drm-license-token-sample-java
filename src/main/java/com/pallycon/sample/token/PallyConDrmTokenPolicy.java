package com.pallycon.sample.token;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pallycon.sample.exception.PallyConTokenException;
import com.pallycon.sample.token.policy.ExternalKeyPolicy;
import com.pallycon.sample.token.policy.PlaybackPolicy;
import com.pallycon.sample.token.policy.SecurityPolicy;

/**
 * Create policy data for @ PallyConDrmToken
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"policy_version", "playback_policy", "security_policy", "external_key"})
public class PallyConDrmTokenPolicy {

    @JsonProperty("policy_version")
    private final int POLICY_VERSION = 2;

    @JsonProperty("playback_policy")
    private PlaybackPolicy playbackPolicy;

    @JsonProperty("security_policy")
    private SecurityPolicy securityPolicy;

    @JsonProperty("external_key")
    private ExternalKeyPolicy externalKey;


    private PallyConDrmTokenPolicy(PolicyBuilder policyBuilder) {
        this.playbackPolicy = policyBuilder.playbackPolicy;
        this.securityPolicy = policyBuilder.securityPolicy;
        this.externalKey = policyBuilder.externalKey;
    }

    //----------------------------- start of builder pattern
    public static class PolicyBuilder {
        private PlaybackPolicy playbackPolicy;
        private SecurityPolicy securityPolicy;
        private ExternalKeyPolicy externalKey;

        /**
         * create constructor as you want
         */
        public PolicyBuilder playbackPolicy(PlaybackPolicy playbackPolicy) {
            this.playbackPolicy = playbackPolicy;
            return this;
        }

        public PolicyBuilder securityPolicy(SecurityPolicy securityPolicy) {
            this.securityPolicy = securityPolicy;
            return this;
        }

        public PolicyBuilder externalKey(ExternalKeyPolicy externalKey) {
            this.externalKey = externalKey;
            return this;
        }

        public PallyConDrmTokenPolicy build() throws PallyConTokenException {
            PallyConDrmTokenPolicy token = new PallyConDrmTokenPolicy(this);
            validateTokenObject(token);
            return token;
        }

        private void validateTokenObject(PallyConDrmTokenPolicy token) throws PallyConTokenException {
            if (null != token.playbackPolicy) {
                token.playbackPolicy.check();
            }

//            if (null != token.securityPolicy) {
//                token.securityPolicy.check();
//            }

            if (null != token.externalKey) {
                token.externalKey.check();
            }
        }
    }
    //----------------------------- end of builder pattern

    public String toJsonString() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(this);
    }

    public PlaybackPolicy getPlaybackPolicy() {
        return playbackPolicy;
    }

    public SecurityPolicy getSecurityPolicy() {
        return securityPolicy;
    }

    public ExternalKeyPolicy getExternalKey() {
        return externalKey;
    }
}

