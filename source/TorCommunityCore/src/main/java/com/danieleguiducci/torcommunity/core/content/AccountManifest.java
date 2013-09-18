/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.danieleguiducci.torcommunity.core.content;

import java.io.Serializable;

/**
 *
 * @author Daniele
 */
public class AccountManifest implements Serializable{
    private String userName;
    private long createdTime;
    private byte[] publicKey;
    private byte[] password;
    private byte[] encPrivateKey;
    private byte[] accountSecretId;
    private byte[] domainSid;

    public byte[] getDomainSid() {
        return domainSid;
    }

    public void setDomainSid(byte[] domainSid) {
        this.domainSid = domainSid;
    }
    
    public byte[] getAccountSecretId() {
        return accountSecretId;
    }

    public void setAccountSecretId(byte[] accountSecretId) {
        this.accountSecretId = accountSecretId;
    }
    
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }



    public long getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(long createdTime) {
        this.createdTime = createdTime;
    }

    public byte[] getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(byte[] publicKey) {
        this.publicKey = publicKey;
    }

    public byte[] getPassword() {
        return password;
    }

    public void setPassword(byte[] password) {
        this.password = password;
    }

    public byte[] getEncPrivateKey() {
        return encPrivateKey;
    }

    public void setEncPrivateKey(byte[] encPrivateKey) {
        this.encPrivateKey = encPrivateKey;
    }
    
    
}
