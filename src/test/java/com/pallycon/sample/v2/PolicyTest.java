package com.pallycon.sample.v2;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pallycon.sample.exception.PallyConTokenException;
import com.pallycon.sample.test.TrackType;
import com.pallycon.sample.token.PallyConDrmTokenPolicy;
import com.pallycon.sample.token.policy.ExternalKeyPolicy;
import com.pallycon.sample.token.policy.ExternalKeyPolicyMpegCenc;
import com.pallycon.sample.token.policy.PlaybackPolicy;
import com.pallycon.sample.token.policy.SecurityPolicy;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.function.LongBinaryOperator;

/**
 * Created By NY on 2020-04-08.
 */
public class PolicyTest {

    private static Logger logger = LoggerFactory.getLogger(PolicyTest.class);

    @Test
    public void policyEncTest() throws PallyConTokenException, JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        PlaybackPolicy playbackPolicy = new PlaybackPolicy();
        SecurityPolicy securityPolicy = new SecurityPolicy();

        logger.info("--------------test--------------");
        logger.debug(objectMapper.writeValueAsString(playbackPolicy));
        logger.debug(objectMapper.writeValueAsString(securityPolicy));

        PallyConDrmTokenPolicy policy = new PallyConDrmTokenPolicy.PolicyBuilder()
                .playbackPolicy(playbackPolicy)
                .securityPolicy(securityPolicy)
                .build();

        logger.info("--------------policy--------------");
        logger.debug(policy.toString());
        logger.debug(policy.toJsonString());
    }

    @Test
    public void listTest() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        ExternalKeyPolicy externalKey = new ExternalKeyPolicy();
        ExternalKeyPolicyMpegCenc mpegCenc = new ExternalKeyPolicyMpegCenc(
                TrackType.UHD1,
                "d5f1a1aa55546666d5f1a1aa55546666",
                "11b11af515c10000fff111a1aef51510",
                "11b11af515c10000fff111a1aef51510"
        );
        logger.info("--------------before--------------");
        logger.debug(objectMapper.writeValueAsString(mpegCenc));
        logger.debug(objectMapper.writeValueAsString(Optional.ofNullable(externalKey).orElse(null)));

        logger.info("--------------after--------------");
        externalKey.mpegCenc(mpegCenc);
        externalKey.mpegCenc(mpegCenc);
        externalKey.mpegCenc(mpegCenc);
        logger.debug(objectMapper.writeValueAsString(externalKey));
    }

    @Test
    public void policyBuildTest() throws PallyConTokenException, JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();

        ExternalKeyPolicyMpegCenc mpegCencFalse = new ExternalKeyPolicyMpegCenc(
                TrackType.UHD1,
                "d5f1a1aa55546666d5f1a1aa55546666",
                "51510",
                "11b11af515c10000fff111a1aef51510"
        );
        ExternalKeyPolicyMpegCenc mpegCencTrue = new ExternalKeyPolicyMpegCenc(
                TrackType.ALL_VIDEO,
                "d5f1a1aa55546666d5f1a1aa55546666",
                "11b11af515c10000fff111a1aef51510",
                "11b11af515c10000fff111a1aef51510"
        );

        ExternalKeyPolicy externalKeyTrue = new ExternalKeyPolicy();
        externalKeyTrue.mpegCenc(mpegCencTrue);

        ExternalKeyPolicy externalKeyFalse = new ExternalKeyPolicy()
                .mpegCenc(mpegCencFalse);


//        logger.info("--------------empty check--------------");
//        PallyConDrmTokenPolicy2 policy = new PallyConDrmTokenPolicy2
//                .PolicyBuilder()
//                .build();
//        logger.debug(objectMapper.writeValueAsString(policy));

        logger.info("--------------exception check--------------");
        PallyConDrmTokenPolicy policy3 = new PallyConDrmTokenPolicy
                .PolicyBuilder()
                .externalKey(externalKeyTrue)
                .build();
        logger.debug(objectMapper.writeValueAsString(policy3));

        logger.info("--------------exception check--------------");
        PallyConDrmTokenPolicy policy2 = new PallyConDrmTokenPolicy
                .PolicyBuilder()
                .externalKey(externalKeyFalse)
                .build();
        logger.debug(objectMapper.writeValueAsString(policy2));

    }

    @Test
    public void externalKeyTest() throws PallyConTokenException, JsonProcessingException {
        // mpegCenc iv 값 은 default 가 아님,, 체크하는지 안하는지 확인해야 됨ㅎ
        ExternalKeyPolicyMpegCenc mpegCenc = new ExternalKeyPolicyMpegCenc(
                TrackType.ALL_VIDEO,
                "d5f1a1aa55546666d5f1a1aa55546666",
                "11b11af515c10000fff111a1aef51510"
        );
        ExternalKeyPolicy externalKeyPolicy = new ExternalKeyPolicy()
                .mpegCenc(mpegCenc);
        PallyConDrmTokenPolicy policy = new PallyConDrmTokenPolicy
                .PolicyBuilder()
                .externalKey(externalKeyPolicy)
                .build();

        logger.debug(mpegCenc.getIv());
        logger.debug(policy.toJsonString());

    }
}
