# :bulb: PallyCon Token JAVA Sample



### 환경

- JAVA JDK 1.8 이상



#### logback 설정 관련

- `logbackProperties.properties` 에서 저장할 장소 세팅
- `logback.xml`





### Quick Test Example

```java
public class SampleTest {

    private static Logger logger = LoggerFactory.getLogger(SampleTest.class);

    /** STEPS TO GET TOKEN
     * 
     * 1. set up policies you want
     * 2. build policy
     * 3. create token
     * */

    @Test
    public void makeToken() 
        throws PallyConTokenException, JsonProcessingException, Exception {
        
        logger.info("start to set up the policy");
        PolicyRequest policy = null;
        PallyConDrmTokenClient tokenClient = null;
        String result = "";


        /**
         * 1. set up policies you want
         * */

        PlaybackPolicyRequest playback = new PlaybackPolicyRequest();
        SecurityPolicyRequest security = new SecurityPolicyRequest();
        ExternalKeyRequest externalKey = new ExternalKeyRequest();

        //set security
        security.setPlayreadySecurityLevel(2000);
        security.setAllowMobileAbnormalDevice(true);
        security.getOutputProtect().setControlHdcp(2);

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
        playback.setLimit(true); // error code : 1010
        playback.setDuration(6000);
        playback.setPersistent(false);


        /**
         * 2. build policy
         * */
        try {
            policy = new PolicyRequest.PolicyBuilder()
                    .playbackPolicy(playback)
                    .securityPolicy(security)
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
                        .siteKey(Config.siteKey)
                        .accessKey(Config.accesskey)
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
        
        logger.debug(result); //token
    }
}
```









### Error Messagges

| Error Code | Error Messages                                               |
| ---------- | ------------------------------------------------------------ |
| 1000       | Token err : The userId is Required                           |
| 1001       | Token err : The cId is Required                              |
| 1002       | Token err : The siteId is Required                           |
| 1003       | Token err : The accessKey is Required                        |
| 1004       | Token err : The siteKey is Required                          |
| 1005       | Token err : The policy is Required                           |
| 1006       | PlaybackPolicy : The limit should be Boolean                 |
| 1007       | PlaybackPolicy : The persistent should be Boolean            |
| 1008       | PlaybackPolicy : The duration should be Integer              |
| 1009       | PlaybackPolicy : The expireDate time format should be `YYYY-MM-DD'T'HH:mm:ss'Z` |
| 1010       | PlaybackPolicy : The limit value should be true when setting duration or expireDate |
| 1011       | SecurityPolicy : The hardwareDrm must be Boolean             |
| 1012       | SecurityPolicy : The allowMobileAbnormalDevice should be Boolean |
| 1013       | SecurityPolicy : The playreadySecurityLevel should be Integer |
| 1014       | SecurityPolicy : The playreadySecurityLevel should be in 150 or more |
| 1015       | SecurityPolicy : The allowExternalDisplay should be Boolean  |
| 1016       | SecurityPolicy : The controlHdcp should be Integer           |
| 1017       | HlsAes : The Key should be 16byte hex String                 |
| 1018       | HlsAes : The Iv should be 16byte hex String                  |
| 1019       | MpegCenc : The KeyId should be 16byte hex String             |
| 1020       | MpegCenc : The Key should be 16byte hex String               |
| 1021       | MpegCenc : The Iv should be 16byte hex String                |
| 1022       | Ncg : The Cek should be 32byte hex String                    |