/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.torcommunity.core.service.imp;

import com.torcommunity.core.content.AccountManifest;
import com.torcommunity.core.content.builder.AccountManifestBuilder;
import com.torcommunity.core.dao.AccountDao;
import com.torcommunity.core.entity.Account;
import com.torcommunity.core.service.AccountNotValidException;
import com.torcommunity.core.service.AccountService;
import com.torcommunity.core.service.CreationAccountException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Daniele
 */
@Service
public class AccountServiceImp implements AccountService {

    protected static Logger logger = LoggerFactory.getLogger(AccountServiceImp.class);
    private AccountManifestBuilder manifestBuilder = new AccountManifestBuilder();
    @Autowired
    private AccountDao accountDao;
    @Autowired
    private CryptoUtil digestUtil;

    @Override
    public Account addAccount(byte[] manifestRaw,byte[] signature,byte[] hash) throws AccountNotValidException {
        try {
            if(!Arrays.equals(digestUtil.getSha1().digest(manifestRaw),hash)) {
                throw new IllegalArgumentException("Invalid initial integrity");
            }
            AccountManifest manifest = manifestBuilder.buildFromRaw(manifestRaw);
            X509EncodedKeySpec bobPubKeySpec = new X509EncodedKeySpec(manifest.getPublicKey());
            KeyFactory keyFactory = KeyFactory.getInstance("ECDSA");
            PublicKey publicKey=keyFactory.generatePublic(bobPubKeySpec);
            Signature sig = Signature.getInstance("SHA1withECDSA");
            sig.initVerify(publicKey);
            sig.update(manifestRaw);
            if(!sig.verify(signature)) {
                throw new IllegalAccessException("Signature is not valid");
            }
            if(manifest.getCreatedTime()==0) {
                throw new IllegalAccessException("Date can't be zero");
            }
            
            MessageDigest digestAccSecId=digestUtil.getSha1();
            digestAccSecId.update(manifest.getAccountPartialSecretId());
            digestAccSecId.update(manifest.getUserName().getBytes());
            Account account=new Account();
            account.setHash(hash.clone());
            account.setAccountManifest(manifestRaw);
            account.setSignature(signature);
            account.setPublicDomain(digestUtil.domainSid2publicDomain(manifest.getDomainSid(), hash));
            account.setAccountSecretId(digestAccSecId.digest());
            accountDao.insert(account);
            return account;
        } catch (Exception e) {
            throw new AccountNotValidException(e);
        }
    }


    @Override
    public Account createNewAccount(String username, String userPassword) throws CreationAccountException {
        try {
    
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("EC");
            keyGen.initialize(224);
            KeyPair paio = keyGen.genKeyPair();

            AccountManifest manifest = new AccountManifest();
            manifest.setUserName(username);
            manifest.setCreatedTime(System.currentTimeMillis());
            manifest.setEncPrivateKey(cripta(paio.getPrivate(), manifest.getCreatedTime(), userPassword));
            manifest.setPublicKey(paio.getPublic().getEncoded());
            manifest.setUserName(username);
            manifest.setAccountPartialSecretId(generateSecretId((username+"CONCAT"+userPassword).getBytes()));
            manifest.setDomainSid(digestUtil.generatePublicSid(generateSecretId(paio.getPrivate().getEncoded())));
            byte[] rawManifest = manifestBuilder.serializa(manifest);

            Signature dsa = Signature.getInstance("SHA1withECDSA");
            dsa.initSign(paio.getPrivate());
            dsa.update(rawManifest);
            byte[] signature=dsa.sign();
            byte[] msgHash=digestUtil.getSha1().digest(rawManifest);
            return addAccount(rawManifest,signature,msgHash);
        } catch (Exception e) {
            throw new CreationAccountException(e);
        }
    }

    private byte[] cripta(PrivateKey privKey, long createdTime, String encPassword) throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");

        KeySpec spec = new PBEKeySpec(encPassword.toCharArray(), (createdTime + "#").getBytes(), 100000, 256);
        SecretKey tmp = factory.generateSecret(spec);

        Cipher cipher = digestUtil.getAesCbcPkcs5Padding();
        cipher.init(Cipher.ENCRYPT_MODE, tmp);
        return cipher.doFinal(privKey.getEncoded());
    }

    private byte[] generateSecretId(byte[] item) {
        MessageDigest digest = digestUtil.getSha1();
        for (int i = 0; i < 100000; i++) {
            
            byte[] tmp = digest.digest(item);
            digest.update(tmp);
            digest.update(("SALTO"+i).getBytes());
        }
        byte[] encPassword = digest.digest();
        return encPassword;
    }
}
