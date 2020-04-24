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
    public void makeToken() {
        
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
        SecurityPolicy securityPolicy = new SecurityPolicy();
        ExternalKeyPolicy externalKeyPolicy = new ExternalKeyPolicy();
        
        //setup playbackPolicy
        playbackPolicy
                .allowedTrackTypes(AllowedTrackTypes.SD_UHD1)
            	.licenseDuration(60);

        /** setup SecurityPolicy
            - creates subpolicies named*/
        SecurityPolicyWidevine widevineForSD 
            = new SecurityPolicyWidevine()
                .securityLevel(WidevineSecurityLevel.SW_SECURE_CRYPTO)
                .requiredHdcpVersion(RequiredHdcpVersion.HDCP_V2_1);
        SecurityPolicyFairplay fairplayForSD 
            = new SecurityPolicyFairplay()
                .allowAirplay(false);
        SecurityPolicyPlayready playreadyForSD 
            = new SecurityPolicyPlayready()
                .compressedDigitalAudioProtection(
        		    CompressedDigitalAudioProtection.LEVEL_301)
                .analogVideoProtection(AnalogVideoProtection.LEVEL_150)
                .uncompressedDigitalAudioProtection(
            		UnCompressedDigitalAudioProtection.LEVEL_100);
        //constructs subpolicies for SecurityPolicy
        securityPolicy
                .widevine(widevineForSD)
                .fairplay(fairplayForSD)
                .playready(playreadyForSD)
                .trackType(TrackType.SD);
     
        // setup ExternalKeyPolicy
        ExternalKeyPolicyMpegCenc mpegCenc = new ExternalKeyPolicyMpegCenc(
                TrackType.ALL_VIDEO, "<Key ID>", "<Key>");
        externalKeyPolicy.mpegCenc(Arrays.asList(mpegCenc, mpegCenc, mpegCenc));

        /**
         * 2. build policy
         * */
        try {
            policy = new PallyConDrmTokenPolicy
                    .PolicyBuilder()
                    .playbackPolicy(playbackPolicy)
                    .externalKey(externalKeyPolicy)
                    .securityPolicy(securityPolicyForSD)
                    .securityPolicy(securityPolicyForSD)
                    .build();
            logger.info("---------------policyJson---------------");
            logger.debug(policy.toJsonString());

            /**
             * 3. create token
             * */
            token = new PallyConDrmTokenClient()
                	.widevine()
                    .siteId("<Site ID>")
                    .siteKey("<Site Key>")
                    .accessKey("<Access Key>")
                    .userId("<tester-user>")
                	.cId("<Content ID>")
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
```

After run the mothod, you can get the TOKEN you intended below the '--result--' string.  if there are minor mistakes when created, `result` will return error messages we already made on  `ErrorCode` or you can see the messages below. Follow the comment and fix the bugs. 

For example, 

```json
{
    "error_code": "1000",
    "error_message": "Token err : The userId is Required"
}
```

If you want to see All the error codes and error messages you would get, see the `pallycon\excepion\ErrorCode.java` . 





We hope this guide would be helpful to generate DRM License Token to request PallyCon Multi-DRM Cloud Server where get the License issued from.







### Error Messagges

| Error Code | Error Messages                                               |
| ---------- | ------------------------------------------------------------ |
| 1000       | Token err : The userId is Required                           |
| 1001       | Token err : The cId is Required                              |
| 1002       | Token err : The siteId is Required                           |
| 1003       | Token err : The accessKey is Required                        |
| 1004       | Token err : The siteKey is Required                          |
| 1005       | Token err : The policy is Required                           |
| 1011 | PlaybackPolicy : The expireDate time format should be `YYYY-MM-DD'T'HH:mm:ss'Z` |
| 1018 | ExternalKey : ExternalKey should be filled with MpegCenc, HlsAes or Ncg if called |
| 1040 | MpegCenc : The KeyId should be 16byte hex String |
| 1041 | MpegCenc : The Key should be 16byte hex String |
| 1042 | MpegCenc : The Iv should be 16byte hex String |
| 1044 | HlsAes : The Key should be 16byte hex String |
| 1045 | HlsAes : The Iv should be 16byte hex String |
| 1047 | Ncg : The Cek should be 32byte hex String |
| 2001 | json parser error |
