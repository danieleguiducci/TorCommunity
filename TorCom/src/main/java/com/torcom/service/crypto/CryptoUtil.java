package com.torcom.service.crypto;

import com.torcom.Const;
import com.torcom.bean.Domain;
import com.torcom.bean.PublicId;
import com.torcom.bean.PublicSid;
import com.torcom.db.main.AccountRow;
import org.apache.commons.codec.binary.Base32;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;

/**
 * Created by daniele on 16/12/2015.
 */
@Service
public class CryptoUtil {

    private static final Base32 base32 = new Base32();
    private static final int urlLength = Const.PUBLIC_DOMAIN_LENGTH; //byte length for the domain url
    private static final BigInteger p = new BigInteger("730750568290937136971131182712229683171787815699");
    private static final BigInteger g = new BigInteger("41490204059424128096426867114102659947841876679151357761978796873944950447065614456380480581212536433064216635941802041452828290049538333005093884826376264772329615288079904057520058063070530292089866748064521794003036684496365588213955135836794623103193842561629667766496440882516264767818073403794920049640119708063472189782392119310784931349474834475487043709649127057616104124225925729896565185037810505186125906457648308209342274928188263782216405511572747230778359569534740627");

    public Domain generatePrivateDomain(AccountRow accountRow) throws NoSuchAlgorithmException {
        byte[] hash = MessageDigest.getInstance(Const.DIGEST_FUNCTION).digest(accountRow.getRawData());
        byte[] privHash = MessageDigest.getInstance(Const.DIGEST_FUNCTION).digest(accountRow.getPrivateKey());
        BigInteger s = new BigInteger(privHash);
        BigInteger msgHashInt = new BigInteger(hash);
        BigInteger privateDomain = s.modPow(msgHashInt, p);
        if(privateDomain.toByteArray().length != urlLength) {
            throw new IllegalStateException("Domain size is wrong "+privateDomain.toByteArray().length );
        }
        return new Domain(privateDomain.toByteArray());
    }

    public PublicSid generatePublicSid(PrivateKey privateKey) throws NoSuchAlgorithmException {
        byte[] digestPrivateKey = MessageDigest.getInstance(Const.DIGEST_FUNCTION).digest(privateKey.getEncoded());
        BigInteger s = new BigInteger(digestPrivateKey);
        BigInteger publicSidInt = s.modPow(g, p);
        if(publicSidInt.toByteArray().length != urlLength) {
            throw new IllegalStateException("PublicSid size is wrong "+publicSidInt.toByteArray().length );
        }
        return PublicSid.create(publicSidInt.toByteArray());
    }

    public PublicId privateDomain2publicDomain(Domain domain) {
        BigInteger privateDomainInt = new BigInteger(domain.getDomain());
        BigInteger publicDomainInt = privateDomainInt.modPow(g, p);
        return PublicId.create(zeroPad(publicDomainInt.toByteArray(), urlLength));
    }

    public PublicId domainSid2publicDomain(PublicSid domainSid, AccountRow accountRow) throws NoSuchAlgorithmException {
        byte[] hash = MessageDigest.getInstance(Const.DIGEST_FUNCTION).digest(accountRow.getRawData());
        BigInteger domainSidInt = domainSid.getAsBigint();
        if (domainSidInt.signum() != 1 && domainSidInt.toByteArray().length != urlLength) {
            throw new IllegalArgumentException("Illegal domainSid param");
        }
        BigInteger msgHashInt = new BigInteger(hash);
        BigInteger publicDomainInt = domainSidInt.modPow(msgHashInt, p);
        return PublicId.create(zeroPad(publicDomainInt.toByteArray(), urlLength));
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
