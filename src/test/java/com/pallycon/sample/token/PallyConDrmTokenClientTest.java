package com.pallycon.sample.token;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.pallycon.sample.config.Config;
import com.pallycon.sample.exception.PallyConTokenException;
import com.pallycon.sample.token.policy.ExternalKeyPolicy;
import com.pallycon.sample.token.policy.ExternalKeyPolicyHlsAes;
import com.pallycon.sample.token.policy.ExternalKeyPolicyMpegCenc;
import com.pallycon.sample.token.policy.PlaybackPolicy;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.*;
/**
 * @Deprecated
 * version 1 Test code.
 */
@Deprecated
public class PallyConDrmTokenClientTest {

    private PallyConDrmTokenPolicy policy;
    private PallyConDrmTokenClient tokenClient;
    private static Logger logger = LoggerFactory.getLogger(PallyConDrmTokenClientTest.class);

    /**
     * CHECK
     * USE builder pattern : PallyConDrmTokenClient, TokenRequest
     */

    @Before
    public void setUpPolicy() throws PallyConTokenException, JsonProcessingException {
        logger.info("start to set up the policy");

        PlaybackPolicy playback = new PlaybackPolicy();
        SecurityPolicyRequest security = new SecurityPolicyRequest();
        ExternalKeyPolicy externalKey = new ExternalKeyPolicy();

        //set security
        security.setPlayreadySecurityLevel(2000);
        security.setAllowMobileAbnormalDevice(true);
        security.getOutputProtect().setControlHdcp(2);

        //set external key
        externalKey.setHlsAes(new ExternalKeyPolicyHlsAes(
                "1111aaef51510000ffff1111aaef5151",
                "11115555444477776666000033332222"
        ));
        externalKey.setMpegCenc(new ExternalKeyPolicyMpegCenc(
                "d5f1a1aa55546666d5f1a1aa55546666",
                "11b11af515c10000fff111a1aef51510",
                "11b11af515c10000fff111a1aef51510"
        ));

        //set playback
//        playback.setLimit(true);
//        playback.setLicenseDuration(6000);
//        playback.setPersistent(false);

        this.policy = new PallyConDrmTokenPolicy.PolicyBuilder()
                .playbackPolicy(playback)
//                .securityPolicy(security)
                .externalKey(externalKey)
                .build();
        logger.debug(policy.toJsonString());
        logger.info("end of set up the policy");
    }

    @Test
    public void makeError() throws PallyConTokenException, JsonProcessingException, Exception {
        setUpPolicy();
        String errorCode = "";
        PallyConDrmTokenClient client = new PallyConDrmTokenClient();

        try {
            errorCode = client.execute();
        } catch (PallyConTokenException e) {
            assertEquals("1000", e.getErrorCode().getErrorCode());
        }
        logger.debug(errorCode);

        try {
            errorCode = client.userId("user").execute();
        } catch (PallyConTokenException e) {
            assertEquals("1001", e.getErrorCode().getErrorCode());
        }
        logger.debug(errorCode);

        try {
            errorCode = client.cId("pixar-animation").execute();
        } catch (PallyConTokenException e) {
            assertEquals("1002", e.getErrorCode().getErrorCode());
        }
        logger.debug(errorCode);

        try {
            errorCode = client.siteId("USER").execute();
        } catch (PallyConTokenException e) {
            assertEquals("1003", e.getErrorCode().getErrorCode());
        }
        logger.debug(errorCode);
        try {
            errorCode = client.accessKey(Config.ACCESS_KEY).execute();
        } catch (PallyConTokenException e) {
            assertEquals("1004", e.getErrorCode().getErrorCode());
        }
        logger.debug(errorCode);
        try {
            errorCode = client.siteKey(Config.SITE_KEY).execute();
        } catch (PallyConTokenException e) {
            assertEquals("1005", e.getErrorCode().getErrorCode());
        }
        logger.debug(errorCode);
        try {
            errorCode = client.policy(policy).execute();
        } catch (PallyConTokenException e) {
            assertEquals("1005", e.getErrorCode().getErrorCode());
        }
        logger.debug(errorCode);
        logger.debug(client.toJsonString());
    }

}