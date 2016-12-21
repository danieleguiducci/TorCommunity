package com.torcom.bean;

import org.apache.commons.codec.binary.Base32;

/**
 * Created by daniele on 21/12/2016.
 */
public class Domain {

    private static final Base32 base32 = new Base32();

    private byte[] domain;

    public Domain(byte[] domain) {
        this.domain = domain;
    }

    public Domain(String domain) {
        this(base32.decode(domain));
    }

    public byte[] getDomain() {
        return domain;
    }

    public String toString() {
        return base32.encodeAsString(domain);
    }
}
