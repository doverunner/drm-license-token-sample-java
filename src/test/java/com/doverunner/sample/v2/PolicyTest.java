package com.doverunner.sample.v2;

import com.doverunner.sample.token.policy.securityPolicy.ncg.NcgControlHdcp;
import com.doverunner.sample.token.policy.securityPolicy.wiseplay.OutputControl;
import com.doverunner.sample.token.policy.securityPolicy.wiseplay.WiseplaySecurityLevel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.doverunner.sample.token.policy.playbackPolicy.AllowedTrackTypes;
import com.doverunner.sample.token.policy.securityPolicy.fairplay.FairplayHdcpEnforcement;
import com.doverunner.sample.token.policy.securityPolicy.playready.DigitalVideoProtection;
import com.doverunner.sample.token.policy.securityPolicy.playready.PlayreadySecurityLevel;
import com.doverunner.sample.token.policy.securityPolicy.widevine.HdcpSrmRule;
import com.doverunner.sample.token.policy.securityPolicy.widevine.RequiredHdcpVersion;
import com.doverunner.sample.token.policy.securityPolicy.widevine.WidevineSecurityLevel;
import com.doverunner.sample.exception.DoverunnerTokenException;
import com.doverunner.sample.token.policy.common.TrackType;
import com.doverunner.sample.token.DoverunnerDrmTokenPolicy;
import com.doverunner.sample.token.policy.*;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public class PolicyTest {

    private static Logger logger = LoggerFactory.getLogger(PolicyTest.class);

    @Test
    public void makePolicy() {
        ObjectMapper objectMapper = new ObjectMapper();
        PlaybackPolicy playbackPolicy = new PlaybackPolicy()
                .persistent(false)
                .licenseDuration(60)
                .allowedTrackTypes(AllowedTrackTypes.ALL);
        SecurityPolicy securityPolicy = new SecurityPolicy()
                .trackType(TrackType.ALL)
                .widevine(new SecurityPolicyWidevine()
                        .securityLevel(WidevineSecurityLevel.SW_SECURE_CRYPTO)
                        .requiredHdcpVersion(RequiredHdcpVersion.HDCP_NONE)
                        .hdcpSrmRule(HdcpSrmRule.HDCP_SRM_RULE_NONE))
                .playready(new SecurityPolicyPlayready()
                        .securityLevel(PlayreadySecurityLevel.LEVEL_150)
                        .digitalVideoProtection(DigitalVideoProtection.LEVEL_100))
                .fairplay(new SecurityPolicyFairplay()
                        .hdcpEnforcement(FairplayHdcpEnforcement.HDCP_NONE)
                        .allowAirplay(true)
                        .allowAvAdapter(true))
                .ncg(new SecurityPolicyNcg()
                        .allowMobileAbnormalDevice(true)
                        .allowExternalDisplay(true)
                        .controlHdcp(NcgControlHdcp.HDCP_NONE))
                .wiseplay(new SecurityPolicyWiseplay()
                        .securityLevel(WiseplaySecurityLevel.SW_LEVEL_1)
                        .outputControl(OutputControl.HDCP_NONE));
        String securityStr = "" +
                "{" +
                "\"track_type\":\"ALL\"," +
                    "\"widevine\":{" +
                    "\"security_level\":1," +
                    "\"required_hdcp_version\":\"HDCP_NONE\"," +
                    "\"hdcp_srm_rule\":\"HDCP_SRM_RULE_NONE\"," +
                    "\"override_device_revocation\":false," +
                    "\"enable_license_cipher\":false," +
                    "\"allow_test_device\":true" +
                    "}," +
                    "\"playready\":{" +
                    "\"security_level\":150," +
                    "\"digital_video_protection_level\":100," +
                    "\"require_hdcp_type_1\":false," +
                    "\"enable_license_cipher\":false" +
                    "}," +
                    "\"fairplay\":{" +
                    "\"hdcp_enforcement\":-1,\"allow_airplay\":true,\"allow_av_adapter\":true}"+
                    ",\"ncg\":{" +
                    "\"allow_mobile_abnormal_device\":true," +
                    "\"allow_external_display\":true," +
                    "\"control_hdcp\":0" +
                "}," +
                    "\"wiseplay\":{" +
                    "\"security_level\":1," +
                    "\"output_control\":0" +
                "}" +
                "}";

        String policyStr = "" +
                "{" +
                    "\"policy_version\":2," +
                    "\"playback_policy\":{" +
                        "\"persistent\":false,\"license_duration\":60," +
                        "\"allowed_track_types\":\"ALL\"" +
                    "}," +
                    "\"security_policy\":[" +
                        securityStr +
                    "]" +
                "}";

        try {
            logger.info("--------------check--------------");
            logger.debug(objectMapper.writeValueAsString(playbackPolicy));
            logger.debug(objectMapper.writeValueAsString(securityPolicy));
            Assert.assertFalse(playbackPolicy.getPersistent());
            Assert.assertSame(60, playbackPolicy.getLicenseDuration());
            Assert.assertNull(playbackPolicy.getExpireDate());
            Assert.assertEquals("ALL", playbackPolicy.getAllowedTrackTypes());
            Assert.assertEquals(AllowedTrackTypes.ALL.getValue(), playbackPolicy.getAllowedTrackTypes());


            JsonNode experimentNode = objectMapper.readTree(securityStr);
            JsonNode actualNode = objectMapper.readTree(objectMapper.writeValueAsString(securityPolicy));
            Assert.assertEquals(experimentNode, actualNode);

            DoverunnerDrmTokenPolicy policy = new DoverunnerDrmTokenPolicy
                    .PolicyBuilder()
                    .playbackPolicy(playbackPolicy)
                    .securityPolicy(securityPolicy)
                    .build();
            logger.info("--------------policy--------------");
            Assert.assertEquals(policyStr, policy.toJsonString());

        } catch (JsonProcessingException | DoverunnerTokenException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void externalKeyListTest() throws DoverunnerTokenException, JsonProcessingException {
        ExternalKeyPolicyMpegCenc mpegCenc = new ExternalKeyPolicyMpegCenc(
                TrackType.ALL_VIDEO,
                "1111aaaa1111aaaa1111aaaa1111aaaa",
                "1111aaaa1111aaaa1111aaaa1111aaaa"
        );
        ExternalKeyPolicyMpegCenc mpegCenc2 = new ExternalKeyPolicyMpegCenc(
                TrackType.HD,
                "2a2a2b2b2a2a2b2b2a2a2b2b2a2a2b2b",
                "2a2a2b2b2a2a2b2b2a2a2b2b2a2a2b2b",
                "2a2a2b2b2a2a2b2b2a2a2b2b2a2a2b2b"
        );
        ExternalKeyPolicyMpegCenc mpegCenc3 = new ExternalKeyPolicyMpegCenc(
                TrackType.UHD2,
                "c3c3c3c3c3c3c3c3c3c3c3c3c3c3c3c3",
                "c3c3c3c3c3c3c3c3c3c3c3c3c3c3c3c3"
        );
        ExternalKeyPolicy externalKeyPolicyWithAdd = new ExternalKeyPolicy()
                .mpegCenc(mpegCenc)
                .mpegCenc(mpegCenc2)
                .mpegCenc(mpegCenc3);
        DoverunnerDrmTokenPolicy policyWithAdd = new DoverunnerDrmTokenPolicy
                .PolicyBuilder()
                .externalKey(externalKeyPolicyWithAdd)
                .build();

        ExternalKeyPolicy externalKeyPolicyWithList = new ExternalKeyPolicy()
                .mpegCenc(Arrays.asList(mpegCenc, mpegCenc2, mpegCenc3));
        DoverunnerDrmTokenPolicy policyWithList = new DoverunnerDrmTokenPolicy
                .PolicyBuilder()
                .externalKey(externalKeyPolicyWithList)
                .build();

        Assert.assertEquals("2a2a2b2b2a2a2b2b2a2a2b2b2a2a2b2b", mpegCenc2.getIv());
        Assert.assertEquals(policyWithAdd.toJsonString(), policyWithList.toJsonString());

    }

    @Test
    public void hlsList() throws JsonProcessingException {

        ExternalKeyPolicy hlsList = new ExternalKeyPolicy()
                .hlsAes(
                    Arrays.asList(
                        new ExternalKeyPolicyHlsAes(TrackType.SD,
                                "5eee5eee5eee5eee5eee5eee5eee5eee",
                                "5eee5eee5eee5eee5eee5eee5eee5eee",
                                "5eee5eee5eee5eee5eee5eee5eee5eee"),
                        new ExternalKeyPolicyHlsAes(TrackType.HD,
                                "c3c3c3c3c3c3c3c3c3c3c3c3c3c3c3c3",
                                "5eee5eee5eee5eee5eee5eee5eee5eee",
                                "5eee5eee5eee5eee5eee5eee5eee5eee"),
                        new ExternalKeyPolicyHlsAes(TrackType.UHD1,
                                "5eee5eee5eee5eee5eee5eee5eee5eee",
                                "2a2a2b2b2a2a2b2b2a2a2b2b2a2a2b2b",
                                "2a2a2b2b2a2a2b2b2a2a2b2b2a2a2b2b")
                    )
                );

        String hlsStr = "" +
            "{\"hls_aes\":[" +
                "{\"track_type\":\"SD\",\"key\":\"5eee5eee5eee5eee5eee5eee5eee5eee\"," +
                    "\"iv\":\"5eee5eee5eee5eee5eee5eee5eee5eee\",\"key_id\":\"5eee5eee5eee5eee5eee5eee5eee5eee\"}," +
                "{\"track_type\":\"HD\",\"key\":\"c3c3c3c3c3c3c3c3c3c3c3c3c3c3c3c3\"," +
                    "\"iv\":\"5eee5eee5eee5eee5eee5eee5eee5eee\",\"key_id\":\"5eee5eee5eee5eee5eee5eee5eee5eee\"}," +
                "{\"track_type\":\"UHD1\",\"key\":\"5eee5eee5eee5eee5eee5eee5eee5eee\"," +
                    "\"iv\":\"2a2a2b2b2a2a2b2b2a2a2b2b2a2a2b2b\",\"key_id\":\"2a2a2b2b2a2a2b2b2a2a2b2b2a2a2b2b\"}" +
            "]}";

        ObjectMapper objectMapper = new ObjectMapper();
        Assert.assertEquals(hlsStr, objectMapper.writeValueAsString(hlsList));

        Assert.assertEquals("5eee5eee5eee5eee5eee5eee5eee5eee", hlsList.getHlsAesList().get(0).getKey());
        Assert.assertEquals("5eee5eee5eee5eee5eee5eee5eee5eee", hlsList.getHlsAesList().get(0).getIv());

        Assert.assertEquals("c3c3c3c3c3c3c3c3c3c3c3c3c3c3c3c3", hlsList.getHlsAesList().get(1).getKey());
        Assert.assertEquals("5eee5eee5eee5eee5eee5eee5eee5eee", hlsList.getHlsAesList().get(1).getIv());

        Assert.assertEquals("5eee5eee5eee5eee5eee5eee5eee5eee", hlsList.getHlsAesList().get(2).getKey());
        Assert.assertEquals("2a2a2b2b2a2a2b2b2a2a2b2b2a2a2b2b", hlsList.getHlsAesList().get(2).getIv());
    }

    @Test
    public void ncg() throws JsonProcessingException {
        ExternalKeyPolicyNcg ncg = new ExternalKeyPolicyNcg(
                "c3c3c3c3c3c3c3c3c3c3c3c3c3c3c3c35eee5eee5eee5eee5eee5eee5eee5eee");
        ExternalKeyPolicy externalKeyPolicy = new ExternalKeyPolicy().ncg(ncg);

        ObjectMapper objectMapper = new ObjectMapper();
        Assert.assertEquals("c3c3c3c3c3c3c3c3c3c3c3c3c3c3c3c35eee5eee5eee5eee5eee5eee5eee5eee"
                , externalKeyPolicy.getNcg().getCek());

        String ncgStr = "{\"cek\":\"c3c3c3c3c3c3c3c3c3c3c3c3c3c3c3c35eee5eee5eee5eee5eee5eee5eee5eee\"}";
        Assert.assertEquals(ncgStr, objectMapper.writeValueAsString(ncg));
    }
}
