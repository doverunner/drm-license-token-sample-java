package com.pallycon.sample;

import com.pallycon.sample.config.*;
import com.pallycon.sample.token.policy.common.ResponseFormat;
import com.pallycon.sample.token.policy.common.TrackType;
import com.pallycon.sample.token.policy.playbackPolicy.AllowedTrackTypes;
import com.pallycon.sample.token.policy.securityPolicy.fairplay.FairplayHdcpEnforcement;
import com.pallycon.sample.token.policy.securityPolicy.ncg.NcgControlHdcp;
import com.pallycon.sample.token.policy.securityPolicy.playready.DigitalAudioProtection;
import com.pallycon.sample.exception.PallyConTokenException;
import com.pallycon.sample.token.policy.securityPolicy.playready.AnalogVideoProtection;
import com.pallycon.sample.token.policy.securityPolicy.playready.DigitalVideoProtection;
import com.pallycon.sample.token.policy.securityPolicy.playready.PlayreadySecurityLevel;
import com.pallycon.sample.token.policy.securityPolicy.widevine.RequiredCgmsFlags;
import com.pallycon.sample.token.policy.securityPolicy.widevine.RequiredHdcpVersion;
import com.pallycon.sample.token.policy.securityPolicy.widevine.WidevineSecurityLevel;
import com.pallycon.sample.token.PallyConDrmTokenClient;
import com.pallycon.sample.token.PallyConDrmTokenPolicy;
import com.pallycon.sample.token.policy.*;
import com.pallycon.sample.v2.PolicyTest;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

/**
 * for creating token eventually.
 */
public class PallyConDrmTokenSampleTest {
    private static Logger logger = LoggerFactory.getLogger(PolicyTest.class);

    /** STEPS TO GET TOKEN
     *
     * 1. set up policies you want
     * 2. build policy
     * 3. create token
     * */

    @Test
    public void makeToken() {

        /**
         * @param licenseToken creates a token for Multi Drm license request.
         *                     if not follow the token or policy rule of pallyCon,
         *                     you would get the error codes and messages and fix to issue the token you intended to.
         * */

        String licenseToken = "";
        PallyConDrmTokenPolicy policy = null;
        PallyConDrmTokenClient token = null;

        /**
         * 1. set up policies you want
         * @param playback_policy
         * @param security_policy
         * @param external_key
         * */
        PlaybackPolicy playbackPolicy = new PlaybackPolicy();
        SecurityPolicy securityPolicyForAll = new SecurityPolicy();
        ExternalKeyPolicy externalKeyPolicy = new ExternalKeyPolicy();

        // setup ExternalKeyPolicy
        ExternalKeyPolicyMpegCenc mpegCenc = new ExternalKeyPolicyMpegCenc(
                TrackType.ALL_VIDEO,
                "<Key ID>",
                "<Key>"
        );

        // setup SecurityPolicy
        /** creates ALL track for securityPolicy */
        SecurityPolicyWidevine widevineForAll = new SecurityPolicyWidevine()
                .securityLevel(WidevineSecurityLevel.SW_SECURE_CRYPTO)
                .requiredHdcpVersion(RequiredHdcpVersion.HDCP_NONE)
                .requiredCgmsFlags(RequiredCgmsFlags.CGMS_NONE)
                .overrideDeviceRevocation(true);
        SecurityPolicyFairplay fairplayForAll = new SecurityPolicyFairplay()
                .hdcpEnforcement(FairplayHdcpEnforcement.HDCP_NONE)
                .allowAvAdapter(true)
                .allowAirplay(true);
        SecurityPolicyPlayready playreadyForAll = new SecurityPolicyPlayready()
                .securityLevel(PlayreadySecurityLevel.LEVEL_150)
                .analogVideoProtection(AnalogVideoProtection.LEVEL_100)
                .digitalVideoProtection(DigitalVideoProtection.LEVEL_100)
                .digitalAudioProtection(DigitalAudioProtection.LEVEL_100)
                .requireHdcpType1(false);

        playbackPolicy
                .allowedTrackTypes(AllowedTrackTypes.SD_HD)
                .persistent(false);
        securityPolicyForAll
                .widevine(widevineForAll)
                .fairplay(fairplayForAll)
                .playready(playreadyForAll)
                .trackType(TrackType.ALL);
        externalKeyPolicy.mpegCenc(Arrays.asList(mpegCenc, mpegCenc, mpegCenc));


        try {
            /**
             * 2. build policy
             * */
            policy = new PallyConDrmTokenPolicy
                    .PolicyBuilder()
                    .playbackPolicy(playbackPolicy)
                    .externalKey(externalKeyPolicy)
                    .securityPolicy(securityPolicyForAll)
                    .build();
            logger.info("---------------policyJson---------------");
            logger.debug(policy.toJsonString());

            /**
             * 3. create token
             * */
            token = new PallyConDrmTokenClient()
                    .playready()
                    .siteId(Config.SITE_ID)
                    .siteKey(Config.SITE_KEY)
                    .accessKey(Config.ACCESS_KEY)
                    .userId(Config.USER_ID)
                    .cId(Config.C_ID)
                    .policy(policy)
                    .responseFormat(ResponseFormat.ORIGINAL);
            logger.info("---------------tokenJson---------------");
            licenseToken = token.execute();
            logger.debug(token.toJsonString());

        } catch (PallyConTokenException e) {
            licenseToken = e.getMessage();
        } catch (Exception e) {
            licenseToken = "unexpected Exception || " + e.getMessage();
        }
        logger.info("---------------result---------------");
        logger.debug(licenseToken);
    }
}
