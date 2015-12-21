package com.torcom.service.crypto;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;

import com.torcom.bean.PublicDomain;
import com.torcom.bean.PublicSid;
import org.apache.commons.codec.binary.Base32;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

/**
 * Created by daniele on 16/12/2015.
 */
@Service
public class CryptoUtil implements InitializingBean {

    private static final  Base32 base32 = new Base32();
    private static final  int urlLength = 15; //byte length for the domain url
    private static final BigInteger p = new BigInteger("358021667241052329260477917967335643");
    private static final  BigInteger g=new BigInteger("41490204059424128096426867114102659947841876679151357761978796873944950447065614456380480581212536433064216635941802041452828290049538333005093884826376264772329615288079904057520058063070530292089866748064521794003036684496365588213955135836794623103193842561629667766496440882516264767818073403794920049640119708063472189782392119310784931349474834475487043709649127057616104124225925729896565185037810505186125906457648308209342274928188263782216405511572747230778359569534740627");

    public String generatePrivateDomain(byte[] digestPrivateKey, byte msgHash[]) {
        if(digestPrivateKey.length!=20 || msgHash.length!=20) {
            throw new IllegalArgumentException("Size of digestPrivKey and msghash must be 20byte");
        }
        BigInteger s=new BigInteger(digestPrivateKey);
        BigInteger msgHashInt = new BigInteger(msgHash);
        BigInteger privateDomain=s.modPow(msgHashInt, p);
        return base32.encodeAsString(zeroPad(privateDomain.toByteArray(), urlLength));
    }
    public PublicSid generatePublicSid(byte[] digestPrivateKey) {
        if(digestPrivateKey.length!=20) {
            throw new IllegalArgumentException("Size of digest must be 20byte");
        }
        BigInteger s=new BigInteger(digestPrivateKey);
        BigInteger publicSidInt=s.modPow(g, p);
        return PublicSid.create(zeroPad(publicSidInt.toByteArray(), urlLength));
    }
    public PublicDomain privateDomain2publicDomain(String base32Domain) {
        BigInteger privateDomainInt=new BigInteger(base32.decode(base32Domain));
        BigInteger publicDomainInt=privateDomainInt.modPow(g, p);
        return PublicDomain.create(zeroPad(publicDomainInt.toByteArray(), urlLength));
    }

    public PublicDomain domainSid2publicDomain(PublicSid domainSid, byte msgHash[]) {

        BigInteger domainSidInt = domainSid.getAsBigint();
        if(domainSidInt.signum()!=1 && domainSidInt.equals(BigInteger.ONE)) {
            throw new IllegalArgumentException("Illegal domainSid param");
        }
        BigInteger msgHashInt = new BigInteger(msgHash);
        BigInteger publicDomainInt=domainSidInt.modPow(msgHashInt, p);
        return PublicDomain.create(zeroPad(publicDomainInt.toByteArray(), urlLength)) ;
    }

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
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        MessageDigest.getInstance("sha1"); //If sha1 is not present go out @ the init process
        Cipher.getInstance("AES/CBC/PKCS5Padding");
    }

    private static byte[] zeroPad(byte[] data, int minSize) {
        int toPad = minSize - data.length;
        if (toPad < 0) {
            throw new IllegalArgumentException("minSize " + minSize + " lunghezza:" + data.length);
        }
        if (toPad == 0) {
            return data;
        }
        byte[] newData = new byte[minSize];
        int i = 0;
        for (; i < toPad; i++) {
            newData[i] = 0;
        }
        for (int j = 0; j < data.length; j++) {
            newData[i + j] = data[j];
        }
        return newData;
    }
}
