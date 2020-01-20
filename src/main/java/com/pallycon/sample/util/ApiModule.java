package com.pallycon.sample.util;

/**
 * Created By NY on 2020-01-14.
 */
public class ApiModule {

    private static final String AES_IV = "0123456789abcdef";

    //hash
    //generated base64 sha256
    //site accessKey + drmType + siteId + userId + cId + token + timestamp
    public static String makeHash( String accessKey,
            String drmType, String siteId, String userId, String cId, String encToken, String currentTime
    ) {
        StringBuffer bf = new StringBuffer();
        bf.append(accessKey);
        bf.append(drmType);
        bf.append(siteId);
        bf.append(userId);
        bf.append(cId);
        bf.append(encToken);
        bf.append(currentTime);

        return SHAUtil.encrypt(bf.toString());
    }

    //token //base64 aes256
    public static String makeEncryptPolicy(String token, String aesKey) throws Exception {
        String encToken;
        StringEncrypter encrypter = new StringEncrypter(aesKey, AES_IV);
        encToken = encrypter.encrypt(token);
        return encToken;
    }
}
