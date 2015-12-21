package com.torcom.bean;

import java.math.BigInteger;
import java.util.Objects;

/**
 * Created by daniele on 21/12/2015.
 */
public class PublicSid {
    private byte[] publicSid;
    private BigInteger publicSidInt=null;
    private PublicSid(byte[] publicSid) {
        Objects.requireNonNull(publicSid,"PublicSid can't be null");
        this.publicSid=publicSid;
    }

    public static PublicSid create(byte[] data) {
        return new PublicSid(data);
    }

    public BigInteger getAsBigint() {
        if(publicSidInt==null) {
            synchronized (this) {
                if (publicSidInt != null) {
                    return publicSidInt;
                }
                publicSidInt = new BigInteger(publicSid);
            }
        }
        return publicSidInt;
    }
}
