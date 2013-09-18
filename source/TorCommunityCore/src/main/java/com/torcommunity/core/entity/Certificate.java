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
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

/**
 *
 * @author Daniele
 */
@Entity
public class Certificate implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    private Account account;
    @ManyToOne
    private Account certificatore;
    @Lob
    byte[] certificateContent;
    @Lob
    private byte[] signature;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Account getCertificatore() {
        return certificatore;
    }

    public void setCertificatore(Account certificatore) {
        this.certificatore = certificatore;
    }

    public byte[] getCertificateContent() {
        return certificateContent;
    }

    public void setCertificateContent(byte[] certificateContent) {
        this.certificateContent = certificateContent;
    }

    public byte[] getSignature() {
        return signature;
    }

    public void setSignature(byte[] signature) {
        this.signature = signature;
    }
    
}
