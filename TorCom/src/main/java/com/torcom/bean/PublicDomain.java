package com.torcom.bean;

import com.torcom.Const;
import org.apache.commons.codec.binary.Base32;
import org.bouncycastle.util.Arrays;

/**
 * Created by daniele on 21/12/2015.
 */
public class PublicDomain {

    private static final Base32 base32 = new Base32();

    private byte[] publicDomain;
    private String publicDomainString;

    private PublicDomain(byte[] data) {
        if(data.length!= Const.PUBLIC_DOMAIN_LENGTH) {
            throw new IllegalArgumentException("Public domain langth must be "+Const.PUBLIC_DOMAIN_LENGTH);
        }
        this.publicDomain=data;
        publicDomainString=base32.encodeAsString(data);
    }

    public static PublicDomain create(byte[] publicDomain) {
        return new PublicDomain(publicDomain);
    }
    public static PublicDomain create(String publicDomain) {
        return create(base32.decode(publicDomain));
    }
    public byte[] getRaw() {
        return Arrays.clone(publicDomain);
    }
    public String getDomain() {
        return publicDomainString;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PublicDomain)) return false;

        PublicDomain that = (PublicDomain) o;

        return publicDomainString.equals(that.publicDomainString);

    }

    @Override
    public int hashCode() {
        return publicDomainString.hashCode();
    }
}
