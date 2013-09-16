/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.danieleguiducci.torcommunity.core.service.imp;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

/**
 *
 * @author Daniele
 */
@Service
public class CryptoUtil implements InitializingBean{
    public MessageDigest getSha1() {
        try {
            return MessageDigest.getInstance("sha1");
        } catch (NoSuchAlgorithmException ex) {
            throw new RuntimeException(ex);
        }
    }
    public Cipher getAesCbcPkcs5Padding() {
        try {
            return Cipher.getInstance("AES/CBC/PKCS5Padding");
        } catch(NoSuchAlgorithmException | NoSuchPaddingException e){
            throw new RuntimeException(e);
        }
    }
    @Override
    public void afterPropertiesSet() throws Exception {
        MessageDigest.getInstance("sha1"); //If sha1 is not present go out @ the init process
        Cipher.getInstance("AES/CBC/PKCS5Padding");
    }
}
