/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.torcommunity.core.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author Daniele
 */
@Entity
@Table(uniqueConstraints=@UniqueConstraint(columnNames = {"hash"}))
public class Community implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    @JoinColumn(nullable=false)
    private Account administrator;
    private byte[] hash;
    @Lob
    private byte[] communityManifest;
    @Lob
    private byte[] signature;

    public Account getAdministrator() {
        return administrator;
    }

    public void setAdministrator(Account administrator) {
        this.administrator = administrator;
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

    public byte[] getCommunityManifest() {
        return communityManifest;
    }

    public void setCommunityManifest(byte[] communityManifest) {
        this.communityManifest = communityManifest;
    }

    public byte[] getSignature() {
        return signature;
    }

    public void setSignature(byte[] signature) {
        this.signature = signature;
    }
    
}
