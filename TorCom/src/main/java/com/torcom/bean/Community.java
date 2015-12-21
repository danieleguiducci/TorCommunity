package com.torcom.bean;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.time.Instant;

/**
 * Created by daniele on 19/12/2015.
 */
public class Community {
    private PublicKey pubKey;
    private PrivateKey privKey;
    private Instant creationDate;
    private byte[]  publicSid;
    private String privateDomain;
    private PublicDomain publicDomain;

    public Community(PublicKey pubKey, PrivateKey privKey, Instant creationDate, byte[] publicSid, String privateDomain,PublicDomain publicDomain) {
        this.pubKey = pubKey;
        this.privKey = privKey;
        this.creationDate = creationDate;
        this.publicSid = publicSid;
        this.privateDomain = privateDomain;
        this.publicDomain=publicDomain;
    }

    public static Community create(PublicKey pubKey, PrivateKey privKey, Instant creationDate, byte[] publicSid, String privateDomain,PublicDomain publicDomain) {
        return new Community(pubKey,privKey,creationDate,publicSid,privateDomain,publicDomain);
    }

    public PublicKey getPubKey() {
        return pubKey;
    }

    public PrivateKey getPrivKey() {
        return privKey;
    }

    public Instant getCreationDate() {
        return creationDate;
    }

    public byte[] getPublicSid() {
        return publicSid;
    }

    public String getPrivateDomain() {
        return privateDomain;
    }

    public PublicDomain getPublicDomain() {
        return publicDomain;
    }

}
