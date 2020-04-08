package com.pallycon.sample;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.pallycon.sample.config.Config;
import com.pallycon.sample.exception.PallyConTokenException;
import com.pallycon.sample.test.TrackType;
import com.pallycon.sample.token.*;
import com.pallycon.sample.token.policy.ExternalKeyPolicy;
import com.pallycon.sample.token.policy.ExternalKeyPolicyHlsAes;
import com.pallycon.sample.token.policy.ExternalKeyPolicyMpegCenc;
import com.pallycon.sample.token.policy.PlaybackPolicy;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created By NY on 2020-01-21.
 */
public class SampleTest {

    private static Logger logger = LoggerFactory.getLogger(SampleTest.class);

    /** STEPS TO GET TOKEN
     *
     * 1. set up policies you want
     * 2. build policy
     * 3. create token
     * */

    @Test
    public void makeToken() throws PallyConTokenException, JsonProcessingException, Exception {
        logger.info("start to set up the policy");
        PallyConDrmTokenPolicy policy = null;
        PallyConDrmTokenClient tokenClient = null;
        String result = "";


        /**
         * 1. set up policies you want
         * */

        PlaybackPolicy playback = new PlaybackPolicy();
        SecurityPolicyRequest security = new SecurityPolicyRequest();
        ExternalKeyPolicy externalKey = new ExternalKeyPolicy();

        //set security
        security.setPlayreadySecurityLevel(2000);
        security.setAllowMobileAbnormalDevice(true);
        security.getOutputProtect().setControlHdcp(2);

        //set external key
        externalKey.hlsAes(new ExternalKeyPolicyHlsAes(
                TrackType.ALL,
                "1111aaef51510000ffff1111aaef5151",
                "11115555444477776666000033332222"
        ));
        externalKey.mpegCenc(new ExternalKeyPolicyMpegCenc(
                TrackType.ALL,
                "d5f1a1aa55546666d5f1a1aa55546666",
                "11b11af515c10000fff111a1aef51510",
                "11b11af515c10000fff111a1aef51510"
        ));

        //set playback
//        playback.setLimit(true); // error code : 1010
//        playback.setLicenseDuration(6000);
//        playback.setPersistent(false);


        /**
         * 2. build policy
         * */
        try {
            policy = new PallyConDrmTokenPolicy.PolicyBuilder()
                    .playbackPolicy(playback)
//                    .securityPolicy(security)
                    .externalKey(externalKey)
                    .build();
            logger.debug(policy.toJsonString());

        } catch (PallyConTokenException e) {
            result = e.getMessage();
            logger.debug("policy error_message : " + result);
        }

        /**
         * 3. create token
         * */
        logger.info("start to set up the token!");
        if ("".equals(result)) {
            try {
                tokenClient = new PallyConDrmTokenClient()
                        .siteKey(Config.SITE_KEY)
                        .accessKey(Config.ACCESS_KEY)
                        .widevine()
                        .siteId("TEST")
                        .cId("disney-animation")
                        .userId("test1234")
                        .policy(policy);
                result = tokenClient.execute();
                logger.debug(tokenClient.toJsonString());
            } catch (PallyConTokenException e) {
                logger.debug(e.getMessage());
            }
        }
        logger.debug(result); //result

    }
}
