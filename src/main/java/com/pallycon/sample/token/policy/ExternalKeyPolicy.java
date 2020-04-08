package com.pallycon.sample.token.policy;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.pallycon.sample.exception.PallyConTokenException;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created By NY on 2020-01-21.
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
// public class ExternalKeyRequest
// public class ExternalKeyMpegCenc
// public class AmazonSQSAsyncClientBuilder
public class ExternalKeyPolicy {

    @JsonProperty("mpeg_cenc")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private List<ExternalKeyPolicyMpegCenc> mpegCencList;

    @JsonProperty("hls_aes")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private List<ExternalKeyPolicyHlsAes> hlsAesList;

    @JsonProperty("ncg")
    private ExternalKeyPolicyNcg ncg;

    @Deprecated
    @JsonIgnore
    @JsonProperty("mpeg_cenc")
    private ExternalKeyPolicyMpegCenc mpegCenc;

    @Deprecated
    @JsonIgnore
    @JsonProperty("hls_aes")
    private ExternalKeyPolicyHlsAes hlsAes;


    //constructor
    public ExternalKeyPolicy() {
        this.mpegCencList = new ArrayList();
        this.hlsAesList = new ArrayList();
    }

    //setter
    /**
     * constructs a mpegCenc list at once using a list of mpegCenc.
     * */
    public ExternalKeyPolicy mpegCenc(List<ExternalKeyPolicyMpegCenc> mpegCencList) {
        this.mpegCencList = mpegCencList;
        return this;
    }

    /**
     * constructs a mpegCenc list
     * adding a mpegCenc to existing mpegCenc list step-by-step.
     * */
    public ExternalKeyPolicy mpegCenc(ExternalKeyPolicyMpegCenc mpegCenc) {
        this.mpegCencList.add(mpegCenc);
        return this;
    }

    /**
     * constructs a hlsAes list at once using a list of hlsAes.
     * */
    public ExternalKeyPolicy hlsAes(List<ExternalKeyPolicyHlsAes> hlsAesList) {
        this.hlsAesList = hlsAesList;
        return this;
    }

    /**
     * constructs a hlsAes list
     * adding a hlsAes to existing hlsAes list step-by-step.
     * */
    public ExternalKeyPolicy hlsAes(ExternalKeyPolicyHlsAes hlsAes) {
        this.hlsAesList.add(hlsAes);
        return this;
    }

    public ExternalKeyPolicy ncg(ExternalKeyPolicyNcg ncg) {
        this.ncg = ncg;
        return this;
    }

    //setter -> changed to BUILDER PATTERN
    @Deprecated
    public void setMpegCenc(ExternalKeyPolicyMpegCenc mpegCenc) {
        this.mpegCenc = mpegCenc;
    }

    @Deprecated
    public void setHlsAes(ExternalKeyPolicyHlsAes hlsAes) {
        this.hlsAes = hlsAes;
    }

    @Deprecated
    public void setNcg(ExternalKeyPolicyNcg ncg) {
        this.ncg = ncg;
    }

    //getter
    @Deprecated
    public ExternalKeyPolicyMpegCenc getMpegCenc() {
        return mpegCenc;
    }

    @Deprecated
    public ExternalKeyPolicyHlsAes getHlsAes() {
        return hlsAes;
    }

    public ExternalKeyPolicyNcg getNcg() {
        return ncg;
    }

    public List<ExternalKeyPolicyMpegCenc> getMpegCencList() {
        return mpegCencList;
    }

    public List<ExternalKeyPolicyHlsAes> getHlsAesList() {
        return hlsAesList;
    }

    public void check() throws PallyConTokenException {
        if (null != this.mpegCencList ) {
            checkMpegCenc();
        }
        if (null != this.hlsAesList) {
            checkHlsAes();
        }
        if (null != this.ncg) {
            checkNcg();
        }
    }

    //check each fields
    private void checkMpegCenc() throws PallyConTokenException{
        for (ExternalKeyPolicyMpegCenc mpegCenc : this.mpegCencList) {
            if (!checkHex16(mpegCenc.getKeyId())) {
                throw new PallyConTokenException("1019");
            }
            if (!checkHex16(mpegCenc.getKey())) {
                throw new PallyConTokenException("1020");
            }
            if (!checkHex16(mpegCenc.getIv())) {
                throw new PallyConTokenException("1021");
            }
        }
    }

    private void checkHlsAes() throws PallyConTokenException {
        for (ExternalKeyPolicyHlsAes hlsAes : this.hlsAesList) {
            if (!checkHex32(hlsAes.getKey())) {
                throw new PallyConTokenException("1017");
            }
            if (!checkHex32(hlsAes.getIv())) {
                throw new PallyConTokenException("1018");
            }
        }
    }

    private void checkNcg() throws PallyConTokenException{
        if (!checkHex32(this.ncg.getCek())) {
            throw new PallyConTokenException("1022");
        }
    }

    // hex 16 byte || 32 byte check
    private boolean checkHex32(String words){
        Pattern pattern = Pattern.compile("^[0-9a-f]{64}$");
        return pattern.matcher(words).matches();
    }
    private boolean checkHex16(String words){
        Pattern pattern = Pattern.compile("^[0-9a-f]{32}$");
        return pattern.matcher(words).matches();
    }

}

