package com.torcom.contrib;

import java.time.Instant;

/**
 * Created by daniele on 17/12/2015.
 */
abstract public class Contrib {
    private byte[] sign;
    private Instant creationTime;

    public byte[] getSign() {
        return sign;
    }

    public void setSign(byte[] sign) {
        this.sign = sign;
    }

    public Instant getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Instant creationTime) {
        this.creationTime = creationTime;
    }
}
