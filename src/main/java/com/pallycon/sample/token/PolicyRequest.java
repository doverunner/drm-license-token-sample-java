package com.pallycon.sample.token;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pallycon.sample.exception.PallyConTokenException;

/**
 * Created By NY on 2020-01-13.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PolicyRequest {
    @JsonProperty("playback_policy")
    private PlaybackPolicyRequest playbackPolicy;
    @JsonProperty("security_policy")
    private SecurityPolicyRequest securityPolicy;
    @JsonProperty("external_key")
    private ExternalKeyRequest externalKey;

    public PlaybackPolicyRequest getPlaybackPolicy() {
        return playbackPolicy;
    }

    public SecurityPolicyRequest getSecurityPolicy() {
        return securityPolicy;
    }

    public ExternalKeyRequest getExternalKey() {
        return externalKey;
    }

    //builder pattern
    private PolicyRequest(PolicyBuilder policyBuilder) {
        this.playbackPolicy = policyBuilder.playbackPolicy;
        this.securityPolicy = policyBuilder.securityPolicy;
        this.externalKey = policyBuilder.externalKey;
    }

    public static class PolicyBuilder {
        private PlaybackPolicyRequest playbackPolicy;
        private SecurityPolicyRequest securityPolicy;
        private ExternalKeyRequest externalKey;

        /**
         * create constructor as you want
         */
        public PolicyBuilder playbackPolicy(PlaybackPolicyRequest playbackPolicy) {
            this.playbackPolicy = playbackPolicy;
            return this;
        }

        public PolicyBuilder securityPolicy(SecurityPolicyRequest securityPolicy) {
            this.securityPolicy = securityPolicy;
            return this;
        }

        public PolicyBuilder externalKey(ExternalKeyRequest externalKey) {
            this.externalKey = externalKey;
            return this;
        }

        public PolicyRequest build() throws PallyConTokenException {
            PolicyRequest token = new PolicyRequest(this);
            validateTokenObject(token);
            return token;
        }

        private void validateTokenObject(PolicyRequest token) throws PallyConTokenException {
            if (null != token.playbackPolicy) {
                token.playbackPolicy.check();
            }

            if (null != token.securityPolicy) {
                token.securityPolicy.check();
            }

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
}

