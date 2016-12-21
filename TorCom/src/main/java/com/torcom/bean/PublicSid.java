package com.torcom.bean;

import org.bouncycastle.util.Arrays;

import java.math.BigInteger;
import java.util.Objects;

/**
 * Created by daniele on 21/12/2015.
 */
public class PublicSid {
    private byte[] publicSid;

    private PublicSid(byte[] publicSid) {
        Objects.requireNonNull(publicSid, "PublicSid can't be null");
        this.publicSid = publicSid;
    }

    public static PublicSid create(byte[] data) {
        return new PublicSid(data);
    }

    public byte[] toByteArray() {
        return Arrays.clone(publicSid);
    }

    public BigInteger getAsBigint() {
        return new BigInteger(publicSid);
    }
}
