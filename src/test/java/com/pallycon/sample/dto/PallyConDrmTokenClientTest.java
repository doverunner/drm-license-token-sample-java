package com.pallycon.sample.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.pallycon.sample.config.Config;
import com.pallycon.sample.dto.token.PolicyRequest;
import com.pallycon.sample.dto.token.externalPolicy.ExternalKeyRequest;
import com.pallycon.sample.dto.token.externalPolicy.HlsAesRequest;
import com.pallycon.sample.dto.token.externalPolicy.MpegCencRequest;
import com.pallycon.sample.dto.token.playbackPolicy.PlaybackPolicyRequest;
import com.pallycon.sample.dto.token.securityPolicy.SecurityPolicyRequest;
import com.pallycon.sample.exception.PallyConTokenException;
import com.pallycon.sample.util.ApiModule;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.*;
/**
 * Created By NY on 2020-01-16.
 */
//@EnableAutoConfiguration
//@ComponentScan
//@PropertySource("application.properties")
//@EnableConfigurationProperties
class PallyConDrmTokenClientTest {

    private PolicyRequest policy;
    private static Logger logger = LoggerFactory.getLogger(PallyConDrmTokenClientTest.class);

    /**
     * CHECK
     * USE builder pattern : PallyConDrmTokenClient, TokenRequest
     */

    @Before
    void setUpPolicy() throws PallyConTokenException, JsonProcessingException {
        logger.info("start to set up the policy");

        PlaybackPolicyRequest playback = new PlaybackPolicyRequest();
        SecurityPolicyRequest security = new SecurityPolicyRequest();
        ExternalKeyRequest externalKey = new ExternalKeyRequest();

        //set security
        security.setPlayreadySecurityLevel(2000);
        security.setAllowMobileAbnormalDevice(true);
        security.getOutputProtect().setControlHdcp(2); //security에선 우회가능.

        //set external key
        externalKey.setHlsAes(new HlsAesRequest(
                "1111aaef51510000ffff1111aaef5151",
                "11115555444477776666000033332222"
        ));
        externalKey.setMpegCenc(new MpegCencRequest(
                "d5f1a1aa55546666d5f1a1aa55546666",
                "11b11af515c10000fff111a1aef51510",
                "11b11af515c10000fff111a1aef51510"
        ));

        //set playback
        playback.setLimit(true);
        playback.setDuration(6000);
        playback.setPersistent(false);

        logger.info("start to build up the policy");
        this.policy = new PolicyRequest.PolicyBuilder()
                .playbackPolicy(playback)
                .securityPolicy(security)
                .externalKey(externalKey)
                .build();
        logger.debug(policy.toJsonString());
        logger.info("end of set up the policy");
    }

    @Test
    void getSiteId() throws JsonProcessingException, PallyConTokenException {
        PallyConDrmTokenClient client = new PallyConDrmTokenClient.PallyConDrmTokenClientBuilder()
                .siteId(Config.siteId)
                .execute();
        assertEquals("DEMO", client.getSiteId());

        logger.debug("result", client.toJsonString());
    }

    @Test
    void setPrerequisite() throws PallyConTokenException, JsonProcessingException, Exception {
        logger.info("start the test setPrerequisite");
        PallyConDrmTokenClient client = new PallyConDrmTokenClient.PallyConDrmTokenClientBuilder()
                .siteId(Config.siteId)
                .playready()
                .userId(Config.userId)
                .cId(Config.cId)
                .execute();
        assertEquals("playready", client.getDrmType());
        assertEquals("inkadev", client.getUserId());
        assertEquals("big-buck-bunny_trailer", client.getCId());

        logger.debug("result", client.toJsonString());
    }

    @Test
    void makeToken() throws PallyConTokenException, JsonProcessingException, Exception {
        setUpPolicy();

        logger.info("start to set token");
        PallyConDrmTokenClient tokenClient = new PallyConDrmTokenClient.PallyConDrmTokenClientBuilder()
                .siteId(Config.siteId)
                .siteKey(Config.siteKey)
                .accessKey(Config.accesskey)
                .widevine()
                .userId(Config.userId)
                .cId(Config.cId)
                .policy(policy)
                .execute();

        assertEquals("widevine", tokenClient.getDrmType());
        assertEquals("DEMO", tokenClient.getSiteId());
        assertEquals("inkadev", tokenClient.getUserId());
        assertEquals("big-buck-bunny_trailer", tokenClient.getCId());
        assertEquals(ApiModule.makeEncryptPolicy(policy.toJsonString(), Config.siteKey), tokenClient.getPolicy());

        logger.info("execute the token");
        logger.debug(tokenClient.toJsonString());
    }

    @Test
    void getSiteKey() throws PallyConTokenException, JsonProcessingException, Exception {
        setUpPolicy();
        logger.info("start to set token");
        PallyConDrmTokenClient tokenClient = new PallyConDrmTokenClient.PallyConDrmTokenClientBuilder()
                .siteKey(Config.siteKey)
                .accessKey(Config.accesskey)
                .widevine()
                .siteId(Config.siteId)
                .userId(Config.userId)
                .cId(Config.cId)
                .policy(policy)
                .execute();

        assertEquals(Config.siteKey, tokenClient.getSiteKey());
        logger.debug(tokenClient.toJsonString());
    }

    @Test
    void getAccessKey() throws PallyConTokenException, JsonProcessingException, Exception {
        setUpPolicy();
        logger.info("start to set token");
        PallyConDrmTokenClient tokenClient = new PallyConDrmTokenClient.PallyConDrmTokenClientBuilder()
                .siteKey(Config.siteKey)
                .accessKey(Config.accesskey)
                .widevine()
                .siteId(Config.siteId)
                .userId(Config.userId)
                .cId(Config.cId)
                .policy(policy)
                .execute();

        assertEquals(Config.accesskey, tokenClient.getAccessKey());
        logger.info("access key: " + tokenClient.getAccessKey());
        logger.debug(tokenClient.toJsonString());
    }
}