package com.pallycon.sample.v2;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pallycon.sample.exception.PallyConTokenException;
import com.pallycon.sample.config.TrackType;
import com.pallycon.sample.token.PallyConDrmTokenPolicy;
import com.pallycon.sample.token.policy.ExternalKeyPolicy;
import com.pallycon.sample.token.policy.ExternalKeyPolicyMpegCenc;
import com.pallycon.sample.token.policy.PlaybackPolicy;
import com.pallycon.sample.token.policy.SecurityPolicy;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

/**
 * Created By NY on 2020-04-08.
 */
public class PolicyTest {

    private static Logger logger = LoggerFactory.getLogger(PolicyTest.class);

    @Test
    public void makePolicy() {
        ObjectMapper objectMapper = new ObjectMapper();
        PlaybackPolicy playbackPolicy = new PlaybackPolicy();
        SecurityPolicy securityPolicy = new SecurityPolicy();

        try {
            logger.info("--------------check--------------");
            logger.debug(objectMapper.writeValueAsString(playbackPolicy));
            logger.debug(objectMapper.writeValueAsString(securityPolicy));

            PallyConDrmTokenPolicy policy = new PallyConDrmTokenPolicy
                    .PolicyBuilder()
                    .playbackPolicy(playbackPolicy)
                    .securityPolicy(securityPolicy)
                    .build();
            logger.info("--------------policy--------------");
            logger.debug(policy.toJsonString());

        } catch (JsonProcessingException | PallyConTokenException e) {
            e.printStackTrace();
        }
    }



    @Test
    public void externalKeyListTest() throws PallyConTokenException, JsonProcessingException {
        ExternalKeyPolicyMpegCenc mpegCenc = new ExternalKeyPolicyMpegCenc(
                TrackType.ALL_VIDEO,
                "d5f1a1aa55546666d5f1a1aa55546666",
                "11b11af515c10000fff111a1aef51510"
        );
        ExternalKeyPolicyMpegCenc mpegCenc2 = new ExternalKeyPolicyMpegCenc(
                TrackType.HD,
                "d5f1a1aa55546666d5f1a1aa55546666",
                "d5f1a1aa55546666d5f1a1aa55546666",
                "11b11af515c10000fff111a1aef51510"
        );
        ExternalKeyPolicyMpegCenc mpegCenc3 = new ExternalKeyPolicyMpegCenc(
                TrackType.UHD2,
                "d5f1a1aa55546666d5f1a1aa55546666",
                "11b11af515c10000fff111a1aef51510"
        );
        ExternalKeyPolicy externalKeyPolicyWithAdd = new ExternalKeyPolicy()
                .mpegCenc(mpegCenc)
                .mpegCenc(mpegCenc2)
                .mpegCenc(mpegCenc3);
        PallyConDrmTokenPolicy policyWithAdd = new PallyConDrmTokenPolicy
                .PolicyBuilder()
                .externalKey(externalKeyPolicyWithAdd)
                .build();

        ExternalKeyPolicy externalKeyPolicyWithList = new ExternalKeyPolicy()
                .mpegCenc(Arrays.asList(mpegCenc, mpegCenc2, mpegCenc3));
        PallyConDrmTokenPolicy policyWithList = new PallyConDrmTokenPolicy
                .PolicyBuilder()
                .externalKey(externalKeyPolicyWithList)
                .build();

        logger.debug(mpegCenc.getIv());

        logger.info("compare the overloaded method || addOneToList or addAllByList");
        logger.debug(policyWithAdd.toJsonString());
        logger.debug(policyWithList.toJsonString());

    }
}
