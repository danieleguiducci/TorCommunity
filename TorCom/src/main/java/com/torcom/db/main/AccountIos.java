package com.torcom.db.main;

import java.time.Instant;

/**
 * Account ISO = Immutable Object Serializable
 * Created by daniele on 21/12/2016.
 */
public class AccountIos {
    private byte[] publicKey;
    private byte[] publicSid;
    private Instant creationDate;

    public byte[] getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(byte[] publicKey) {
        this.publicKey = publicKey;
    }

    public byte[] getPublicSid() {
        return publicSid;
    }

    public void setPublicSid(byte[] publicSid) {
        this.publicSid = publicSid;
    }

    public Instant getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Instant creationDate) {
        this.creationDate = creationDate;
    }
}
