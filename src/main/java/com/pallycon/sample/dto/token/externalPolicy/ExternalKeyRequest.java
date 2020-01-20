package com.pallycon.sample.dto.token.externalPolicy;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.pallycon.sample.exception.PallyConTokenException;

import java.util.regex.Pattern;

/**
 * Created By NY on 2020-01-13.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExternalKeyRequest {

    @JsonProperty("mpeg_cenc")
    private MpegCencRequest mpegCenc;
    @JsonProperty("hls_aes")
    private HlsAesRequest hlsAes;
    @JsonProperty("ncg")
    private NcgRequest ncg;

    //TODO jsonObject 변환해야 함. **Jackson object mapper + each field need to be set

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
            throw new PallyConTokenException("mpegCenc: keyId not match hex16");
        }
        if (!checkHex16(this.mpegCenc.getKey())) {
            throw new PallyConTokenException("mpegCenc: key not match hex16");
        }
        if (!checkHex16(this.mpegCenc.getIv())) {
            throw new PallyConTokenException("mpegCenc: iv not match hex16");
        }
    }
    private void checkHlsAes() throws PallyConTokenException{
        if (!checkHex16(this.hlsAes.getKey())) {
            throw new PallyConTokenException("hlsAes: key not match hex16");
        }
        if (!checkHex16(this.hlsAes.getIv())) {
            throw new PallyConTokenException("hlsAes: iv not match hex16");
        }
    }
    private void checkNcg() throws PallyConTokenException{
        if (!checkHex32(this.ncg.getCek())) {
            throw new PallyConTokenException("ncg: cek not match hex32");
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
