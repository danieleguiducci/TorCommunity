/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.danieleguiducci.torcommunity.core.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import org.hibernate.annotations.Index;

/**
 *
 * @author Daniele
 */
@Entity
@Table(uniqueConstraints={@UniqueConstraint(columnNames = {"hash"})})
public class Account implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Index(name = "idxAccountHash")
    @Column(name = "hash", unique = true, nullable=false)
    
    private byte[] hash;
    @Lob
    private byte[] signature;
    @Lob
    private byte[] accountManifest;
    @Index(name = "idxAccountSecretId")
    private byte[] accountSecretId;

    @Index(name="idxAccountPublicDomain")
    private byte[] publicDomain;

    public byte[] getPublicDomain() {
        return publicDomain;
    }

    public void setPublicDomain(byte[] publicDomain) {
        this.publicDomain = publicDomain;
    }
    
    

    
    public byte[] getAccountSecretId() {
        return accountSecretId;
    }

    public void setAccountSecretId(byte[] accountSecretId) {
        this.accountSecretId = accountSecretId;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getHash() {
        return hash;
    }

    public void setHash(byte[] hash) {
        this.hash = hash;
    }

    public byte[] getSignature() {
        return signature;
    }

    public void setSignature(byte[] signature) {
        this.signature = signature;
    }

    public byte[] getAccountManifest() {
        return accountManifest;
    }

    public void setAccountManifest(byte[] accountManifest) {
        this.accountManifest = accountManifest;
    }

 
    
}
