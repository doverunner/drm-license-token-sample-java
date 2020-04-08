package com.pallycon.sample.enumTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pallycon.sample.test.NcgControlHdcp;
import com.pallycon.sample.test.security.playready.UnCompressedDigitalAudioProtection;
import com.pallycon.sample.test.security.widevine.WidevineSecurityLevel;
import com.pallycon.sample.token.policy.PlaybackPolicy;
import com.pallycon.sample.token.policy.SecurityPolicyWidevine;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.*;

/**
 * Created By NY on 2020-04-06.
 */
public class EnumTest {

    private static Logger logger = LoggerFactory.getLogger(EnumTest.class);

    @Test
    public void getEnumTest() {
        WidevineSecurityLevel level = WidevineSecurityLevel.HW_SECURE_CRYPTO;
        assertEquals(3, level.getValue());
        assertNotSame(2, level.getValue());
    }

    @Test(expected = IllegalArgumentException.class)
    public void getSecurityLevel() {
        WidevineSecurityLevel level = WidevineSecurityLevel.HW_SECURE_DECODE ;
        WidevineSecurityLevel.getValue(103);
    }

    @Test
    public void unComp() {
        assertEquals(250, UnCompressedDigitalAudioProtection.LEVEL_250.getValue());
    }

    @Test
    public void test() {
//        CompressedDigitalAudioProtectionLevel a = CompressedDigitalAudioProtectionLevel.LEVEL_150 ;
//        logger.info(a + ""); //LEVEL_150 로 나옴,,,,
//        logger.info(a.getValue() + "");
//
//        logger.info(WidevineSecurityLevel.HW_SECURE_DECODE.toString());
        NcgControlHdcp controlHdcp = NcgControlHdcp.HDCP_NONE;
        logger.info("test? " + controlHdcp.toString()); //상수명이 나옴
        logger.info("get value " + controlHdcp.getValue()); //value
        logger.info("get type " + controlHdcp.getType()); //type
    }

    @Test
    public void playbackPolicyTest() throws JsonProcessingException {
        PlaybackPolicy request = new PlaybackPolicy()
//                .persistent(false)
//                .allowedTrackTypes(AllowedTrackTypes.SD_UHD1)
//                .expireDate("2020-04-30T12:00:05Z")
//                .licenseDuration(20)
                ;

        ObjectMapper objectMapper = new ObjectMapper();
        logger.debug(objectMapper.writeValueAsString(request));
        logger.debug(String.valueOf(request.getLicenseDuration()));
    }

    @Test
    public void widevineTest() throws JsonProcessingException {
        SecurityPolicyWidevine widevine = new SecurityPolicyWidevine()
                .disableAnalogOutput(true)
                .securityLevel(WidevineSecurityLevel.HW_SECURE_ALL)
                ;
        ObjectMapper objectMapper = new ObjectMapper();
        logger.debug(objectMapper.writeValueAsString(widevine));
    }


}
