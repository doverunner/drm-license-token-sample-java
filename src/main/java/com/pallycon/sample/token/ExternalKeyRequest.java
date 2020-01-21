package com.pallycon.sample.token;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.pallycon.sample.exception.PallyConTokenException;

import java.util.regex.Pattern;

/**
 * Created By NY on 2020-01-21.
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExternalKeyRequest {

    @JsonProperty("mpeg_cenc")
    private MpegCencRequest mpegCenc;
    @JsonProperty("hls_aes")
    private HlsAesRequest hlsAes;
    @JsonProperty("ncg")
    private NcgRequest ncg;

    //constructor
    public ExternalKeyRequest() {
    }

    public ExternalKeyRequest(MpegCencRequest mpegCenc) {
        this.mpegCenc = mpegCenc;
    }
    public ExternalKeyRequest(MpegCencRequest mpegCenc, HlsAesRequest hlsAes) {
        this.mpegCenc = mpegCenc;
        this.hlsAes = hlsAes;
    }
    public ExternalKeyRequest(MpegCencRequest mpegCenc, HlsAesRequest hlsAes, NcgRequest ncg) {
        this.mpegCenc = mpegCenc;
        this.hlsAes = hlsAes;
        this.ncg = ncg;
    }

    //setter
    public void setMpegCenc(MpegCencRequest mpegCenc) {
        this.mpegCenc = mpegCenc;
    }

    public void setHlsAes(HlsAesRequest hlsAes) {
        this.hlsAes = hlsAes;
    }

    public void setNcg(NcgRequest ncg) {
        this.ncg = ncg;
    }

    //getter
    public MpegCencRequest getMpegCenc() {
        return mpegCenc;
    }
    public HlsAesRequest getHlsAes() {
        return hlsAes;
    }
    public NcgRequest getNcg() {
        return ncg;
    }

    public void check() throws PallyConTokenException {
        if (null != this.mpegCenc ) {
            checkMpegCenc();
        }
        if (null != this.hlsAes) {
            checkHlsAes();
        }
        if (null != this.ncg) {
            checkNcg();
        }
    }

    //check each fields
    private void checkMpegCenc() throws PallyConTokenException {
        if (!checkHex16(this.mpegCenc.getKeyId())) {
            throw new PallyConTokenException("1019");
        }
        if (!checkHex16(this.mpegCenc.getKey())) {
            throw new PallyConTokenException("1020");
        }
        if (!checkHex16(this.mpegCenc.getIv())) {
            throw new PallyConTokenException("1021");
        }
    }
    private void checkHlsAes() throws PallyConTokenException{
        if (!checkHex16(this.hlsAes.getKey())) {
            throw new PallyConTokenException("1017");
        }
        if (!checkHex16(this.hlsAes.getIv())) {
            throw new PallyConTokenException("1018");
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

