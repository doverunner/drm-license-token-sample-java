package com.pallycon.sample.token;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.pallycon.sample.exception.PallyConTokenException;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.*;

/**
 * Created By NY on 2020-01-15.
 */
public class PolicyRequestTest {

    private PlaybackPolicyRequest playback;
    private SecurityPolicyRequest security;
    private ExternalKeyRequest external;
    private static Logger logger = LoggerFactory.getLogger(PolicyRequestTest.class);

    @Before
    public void setPlayback() {
        logger.info("start to set up the playback");

        playback = new PlaybackPolicyRequest();
        playback.setLimit(true);
        playback.setPersistent(false);
        playback.setDuration(12);

        logger.info("end of set up the playback");
    }

    @Before
    public void setSecurity() {
        logger.info("start to set up the security");

        security = new SecurityPolicyRequest();
        security.setPlayreadySecurityLevel(2000);
        security.getOutputProtect().setControlHdcp(2);

        logger.info("end of set up the security");
    }

    @Before
    public void setExternal() {
        logger.info("start to set up the external");

        external = new ExternalKeyRequest();
        external.setHlsAes(new HlsAesRequest(
                "1111aaef51510000ffff1111aaef5151",
                "11115555444477776666000033332222"
        ));
        external.setNcg(new NcgRequest("1111aaef5151001111555544447777666600003333222200ffff1111aaef5151"));

        logger.info("end of set up the external");
    }

    @Test
    public void isPolicyNull() throws PallyConTokenException, JsonProcessingException {
        setPlayback();
        PolicyRequest policyRequest = new PolicyRequest.PolicyBuilder().playbackPolicy(playback).build();
        assertNotNull(policyRequest);
        logger.debug(policyRequest.toJsonString());
    }

    @Test
    public void getPlaybackPolicy() throws PallyConTokenException, JsonProcessingException {
        setPlayback();

        PolicyRequest policyRequest = new PolicyRequest.PolicyBuilder().playbackPolicy(playback).build();
        assertEquals(12, policyRequest.getPlaybackPolicy().getDuration());
        assertEquals(true, policyRequest.getPlaybackPolicy().isLimit());
        assertEquals(false, policyRequest.getPlaybackPolicy().isPersistent());

        logger.debug(policyRequest.toJsonString());
    }

    @Test
    public void getPlaybackPolicy2() throws PallyConTokenException, JsonProcessingException {
        setPlayback();
        playback.setExpireDate("2020-01-01T23:59:59Z");
        playback.setDuration(0);

        PolicyRequest policyRequest = new PolicyRequest.PolicyBuilder().playbackPolicy(playback).build();
        assertEquals("2020-01-01T23:59:59Z", policyRequest.getPlaybackPolicy().getExpireDate());

        logger.debug("playback: " + policyRequest.getPlaybackPolicy());
        logger.debug(policyRequest.toJsonString());
    }

    @Test
    public void getSecurityPolicy() throws PallyConTokenException, JsonProcessingException {
        setSecurity();

        PolicyRequest policyRequest = new PolicyRequest.PolicyBuilder().securityPolicy(security).build();
        assertEquals(false, policyRequest.getSecurityPolicy().isHardwareDrm());
        logger.debug(policyRequest.toJsonString());

        policyRequest.getSecurityPolicy().getOutputProtect().setAllowExternalDisplay(true);
        assertEquals(2, policyRequest.getSecurityPolicy().getOutputProtect().getControlHdcp());
        assertEquals(true, policyRequest.getSecurityPolicy().getOutputProtect().isAllowExternalDisplay());
        logger.debug(policyRequest.toJsonString());
    }

    @Test
    public void getExternalKey() throws PallyConTokenException, JsonProcessingException {
        setExternal();
        PolicyRequest policyRequest = new PolicyRequest.PolicyBuilder().externalKey(external).build();
        assertEquals("11115555444477776666000033332222", policyRequest.getExternalKey().getHlsAes().getIv());

        external.setMpegCenc(new MpegCencRequest(
                "d5f1a1aa55546666d5f1a1aa55546666",
                "11b11af515c10000fff111a1aef51510",
                "11b11af515c10000fff111a1aef51510"
        ));
        System.out.println(policyRequest.toJsonString());
    }

    @Test
    public void makeSampleToken() throws PallyConTokenException, JsonProcessingException {
        PlaybackPolicyRequest playback = new PlaybackPolicyRequest(true, false, 12);

        PolicyRequest policyRequest = new PolicyRequest.PolicyBuilder()
                .playbackPolicy(playback)
                .build();

        String test = "{\"playback_policy\":{\"limit\":true,\"persistent\":false,\"duration\":12}}";
        assertEquals(test , policyRequest.toJsonString());
    }
    @Test
    public void makeSampleToken2() throws PallyConTokenException, JsonProcessingException {
        PlaybackPolicyRequest playback = new PlaybackPolicyRequest(true, false, 12);

        SecurityPolicyRequest security = new SecurityPolicyRequest();
//        security.setPlayreadySecurityLevel(1500); // error check OK
        security.setPlayreadySecurityLevel(2000);
        security.getOutputProtect().setControlHdcp(2);

        ExternalKeyRequest external = new ExternalKeyRequest();
        external.setHlsAes(new HlsAesRequest("1111aaef51510000ffff1111aaef5151", "11115555444477776666000033332222"));

        PolicyRequest policyRequest = new PolicyRequest.PolicyBuilder().playbackPolicy(playback).externalKey(external).securityPolicy(security)
                .build();
        System.out.println(policyRequest.toJsonString());
    }

    @Test
    public void isEmptyPoliciesExist() throws PallyConTokenException, JsonProcessingException {
        PlaybackPolicyRequest playback = new PlaybackPolicyRequest();
        SecurityPolicyRequest security = new SecurityPolicyRequest();

        PolicyRequest token = new PolicyRequest.PolicyBuilder().playbackPolicy(playback).securityPolicy(security).build();
        PolicyRequest token2 = new PolicyRequest.PolicyBuilder().build();

        System.out.println(token.toJsonString());
        System.out.println(token2.toJsonString());
    }

    @Test
    public void makeError() throws PallyConTokenException, JsonProcessingException {
        PlaybackPolicyRequest playback = new PlaybackPolicyRequest();
        playback.setExpireDate("2020");
        String error = "";
        PolicyRequest policy = null;
        try {
            policy = new PolicyRequest.PolicyBuilder()
                    .playbackPolicy(playback)
                    .build();
            logger.debug(policy.toJsonString());
        } catch (PallyConTokenException e) {
            error = e.getMessage();
            assertEquals("1009", e.getErrorCode().getErrorCode());
            logger.debug("error message : "+error);
        }
    }

}