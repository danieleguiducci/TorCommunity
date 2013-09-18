/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.torcommunity.core.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import org.hibernate.annotations.Index;

/**
 *
 * @author Daniele
 */

@Entity
@org.hibernate.annotations.Table(appliesTo = "Contribution",
    indexes = {
            @Index(name = "IDX_XDN_DFN",
                    columnNames = {"createDate","type"}
            )
    }
)
@Table(name="Contribution",uniqueConstraints=@UniqueConstraint(columnNames = {"hash"}))
public class Contribution implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    @JoinColumn(nullable=false)
    private Account author;
    @Index(name = "idxContribHash")
    @Column(name = "hash", unique = true, nullable=false)
    private byte[] hash;
    @Lob
    private byte[] content;
    @Lob
    private byte[] signature;
     @Index(name = "idxContribCreateDate")
    private long createDate;
     @Index(name = "idxContribType")
    private String type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Account getAuthor() {
        return author;
    }

    public void setAuthor(Account author) {
        this.author = author;
    }

    public byte[] getHash() {
        return hash;
    }

    public void setHash(byte[] hash) {
        this.hash = hash;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public byte[] getSignature() {
        return signature;
    }

    public void setSignature(byte[] signature) {
        this.signature = signature;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
     
}
