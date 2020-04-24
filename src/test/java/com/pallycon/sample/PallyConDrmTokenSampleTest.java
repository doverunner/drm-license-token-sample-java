package com.pallycon.sample;

import com.pallycon.sample.config.*;
import com.pallycon.sample.exception.PallyConTokenException;
import com.pallycon.sample.config.security.playready.AnalogVideoProtection;
import com.pallycon.sample.config.security.playready.CompressedDigitalAudioProtection;
import com.pallycon.sample.config.security.playready.UnCompressedDigitalAudioProtection;
import com.pallycon.sample.config.security.widevine.RequiredCgmsFlags;
import com.pallycon.sample.config.security.widevine.RequiredHdcpVersion;
import com.pallycon.sample.config.security.widevine.WidevineSecurityLevel;
import com.pallycon.sample.token.PallyConDrmTokenClient;
import com.pallycon.sample.token.PallyConDrmTokenPolicy;
import com.pallycon.sample.token.policy.*;
import com.pallycon.sample.v2.PolicyTest;
import org.junit.Assert;
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
        SecurityPolicy securityPolicyForSD = new SecurityPolicy();
        SecurityPolicy securityPolicyForHD = new SecurityPolicy();
        ExternalKeyPolicy externalKeyPolicy = new ExternalKeyPolicy();

        // setup ExternalKeyPolicy
        ExternalKeyPolicyMpegCenc mpegCenc = new ExternalKeyPolicyMpegCenc(
                TrackType.ALL_VIDEO,
                "<Key ID>",
                "<Key>"
        );

        // setup SecurityPolicy
        /** creates SD track for securityPolicy */
        SecurityPolicyWidevine widevineForSD = new SecurityPolicyWidevine()
                .securityLevel(WidevineSecurityLevel.SW_SECURE_CRYPTO)
                .requiredHdcpVersion(RequiredHdcpVersion.HDCP_V2_1);
        SecurityPolicyFairplay fairplayForSD = new SecurityPolicyFairplay()
                .allowAirplay(false);
        SecurityPolicyPlayready playreadyForSD = new SecurityPolicyPlayready()
                .compressedDigitalAudioProtection(CompressedDigitalAudioProtection.LEVEL_301)
                .analogVideoProtection(AnalogVideoProtection.LEVEL_150)
                .uncompressedDigitalAudioProtection(UnCompressedDigitalAudioProtection.LEVEL_100);


        /** creates HD track for securityPolicy */
        SecurityPolicyWidevine widevineForHD = new SecurityPolicyWidevine()
                .securityLevel(WidevineSecurityLevel.HW_SECURE_DECODE)
                .requiredCgmsFlags(RequiredCgmsFlags.COPY_FREE)
                .disableAnalogOutput(true);
        SecurityPolicyFairplay fairplayForHD = new SecurityPolicyFairplay();
        SecurityPolicyNcg ncgForHD = new SecurityPolicyNcg()
                .allowExternalDisplay(true)
                .controlHdcp(NcgControlHdcp.HDCP_V2_2);

        playbackPolicy
                .allowedTrackTypes(AllowedTrackTypes.SD_UHD1)
                .licenseDuration(60);
        securityPolicyForSD
                .widevine(widevineForSD)
                .fairplay(fairplayForSD)
                .playready(playreadyForSD)
                .trackType(TrackType.SD);
        securityPolicyForHD
                .widevine(widevineForHD)
                .fairplay(fairplayForHD)
                .ncg(ncgForHD)
                .trackType(TrackType.HD);
        externalKeyPolicy.mpegCenc(Arrays.asList(mpegCenc, mpegCenc, mpegCenc));


        try {
            /**
             * 2. build policy
             * */
            policy = new PallyConDrmTokenPolicy
                    .PolicyBuilder()
                    .externalKey(externalKeyPolicy)
                    .securityPolicy(securityPolicyForSD)
                    .securityPolicy(securityPolicyForHD)
                    .build();
            logger.info("---------------policyJson---------------");
            logger.debug(policy.toJsonString());

            /**
             * 3. create token
             * */
            token = new PallyConDrmTokenClient()
                    .siteKey(Config.SITE_KEY)
                    .accessKey(Config.ACCESS_KEY)
                    .widevine()
                    .siteId(Config.SITE_ID)
                    .cId(Config.C_ID)
                    .userId(Config.USER_ID)
                    .policy(policy)
                    .responseFormat(ResponseFormat.CUSTOM);
            logger.info("---------------tokenJson---------------");
            logger.debug(token.toJsonString());
            licenseToken = token.execute();

        } catch (PallyConTokenException e) {
            licenseToken = e.getMessage();
        } catch (Exception e) {
            licenseToken = "unexpected Exception || " + e.getMessage();
        }
        logger.info("---------------result---------------");
        logger.debug(licenseToken);
    }
}
