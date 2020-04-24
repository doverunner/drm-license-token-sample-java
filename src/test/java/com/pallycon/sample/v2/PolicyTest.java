package com.pallycon.sample.v2;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pallycon.sample.config.AllowedTrackTypes;
import com.pallycon.sample.exception.PallyConTokenException;
import com.pallycon.sample.config.TrackType;
import com.pallycon.sample.token.PallyConDrmTokenPolicy;
import com.pallycon.sample.token.policy.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.omg.CORBA.PUBLIC_MEMBER;
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
        String securityStr = "" +
                "{" +
        "\"track_type\":\"ALL\"," +
                "\"widevine\":{" +
                "\"security_level\":1," +
                "\"required_hdcp_version\":\"HDCP_NONE\"," +
                "\"required_cgms_flags\":\"CGMS_NONE\"," +
                "\"disable_analog_output\":false," +
                "\"hdcp_srm_rule\":\"HDCP_SRM_RULE_NONE\"" +
                "}," +
                "\"playready\":{" +
                "\"security_level\":150," +
                "\"digital_video_protection_level\":100," +
                "\"analog_video_protection_level\":100," +
                "\"compressed_digital_audio_protection_level\":100," +
                "\"uncompressed_digital_audio_protection_level\":100," +
                "\"require_hdcp_type_1\":false" +
                "}," +
                "\"fairplay\":{" +
                "\"hdcp_enforcement\":-1,\"allow_airplay\":true,\"allow_av_adapter\":true}," +
                "\"ncg\":{" +
                "\"allow_mobile_abnormal_device\":false," +
                "\"allow_external_display\":false,\"control_hdcp\":0}" +
                "}";

        String policyStr = "" +
                "{" +
                    "\"policy_version\":2," +
                    "\"playback_policy\":{" +
                        "\"persistent\":false,\"license_duration\":0," +
                        "\"expire_date\":\"\",\"allowed_track_types\":\"ALL\"" +
                    "}," +
                    "\"security_policy\":[" +
                        securityStr +
                    "]" +
                "}";

        try {
            logger.info("--------------check--------------");
            Assert.assertFalse(playbackPolicy.getPersistent());
            Assert.assertSame(0, playbackPolicy.getLicenseDuration());
            Assert.assertEquals("", playbackPolicy.getExpireDate());
            Assert.assertEquals("ALL", playbackPolicy.getAllowedTrackTypes());
            Assert.assertEquals(AllowedTrackTypes.ALL.getValue(), playbackPolicy.getAllowedTrackTypes());


            Assert.assertEquals(securityStr, objectMapper.writeValueAsString(securityPolicy));

            PallyConDrmTokenPolicy policy = new PallyConDrmTokenPolicy
                    .PolicyBuilder()
                    .playbackPolicy(playbackPolicy)
                    .securityPolicy(securityPolicy)
                    .build();
            logger.info("--------------policy--------------");
            Assert.assertEquals(policyStr, policy.toJsonString());

        } catch (JsonProcessingException | PallyConTokenException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void externalKeyListTest() throws PallyConTokenException, JsonProcessingException {
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
                                "5eee5eee5eee5eee5eee5eee5eee5eee"),
                        new ExternalKeyPolicyHlsAes(TrackType.HD,
                                "c3c3c3c3c3c3c3c3c3c3c3c3c3c3c3c3",
                                "5eee5eee5eee5eee5eee5eee5eee5eee"),
                        new ExternalKeyPolicyHlsAes(TrackType.UHD1,
                                "5eee5eee5eee5eee5eee5eee5eee5eee",
                                "2a2a2b2b2a2a2b2b2a2a2b2b2a2a2b2b")
                    )
                );

        String hlsStr = "" +
            "{\"hls_aes\":[" +
                "{\"track_type\":\"SD\",\"key\":\"5eee5eee5eee5eee5eee5eee5eee5eee\"," +
                    "\"iv\":\"5eee5eee5eee5eee5eee5eee5eee5eee\"}," +
                "{\"track_type\":\"HD\",\"key\":\"c3c3c3c3c3c3c3c3c3c3c3c3c3c3c3c3\"," +
                    "\"iv\":\"5eee5eee5eee5eee5eee5eee5eee5eee\"}," +
                "{\"track_type\":\"UHD1\",\"key\":\"5eee5eee5eee5eee5eee5eee5eee5eee\"," +
                    "\"iv\":\"2a2a2b2b2a2a2b2b2a2a2b2b2a2a2b2b\"}" +
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
