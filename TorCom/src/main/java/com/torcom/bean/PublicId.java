package com.torcom.bean;

import org.apache.commons.codec.binary.Base32;

import java.util.Arrays;

/**
 * Created by daniele on 21/12/2015.
 */
public class PublicId {

    private static final Base32 base32 = new Base32();

    private byte[] publicId;

    private PublicId(byte[] data) {
        this.publicId = data;
    }

    public static PublicId create(byte[] publicId) {
        return new PublicId(publicId);
    }

    public static PublicId create(String publicId) {
        return create(base32.decode(publicId));
    }

    public byte[] getBytes() {
        return Arrays.copyOf(publicId, publicId.length);
    }

    public String getPublicId() {
        return base32.encodeAsString(publicId);
    }

    @Override
    public String toString() {
        return getPublicId();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PublicId)) return false;
        PublicId that = (PublicId) o;
        return Arrays.equals(publicId, that.publicId);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(publicId);
    }
}
