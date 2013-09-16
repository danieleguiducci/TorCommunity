/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.danieleguiducci.torcommunity.core.service.imp;

import com.danieleguiducci.torcommunity.core.content.AccountManifest;
import com.danieleguiducci.torcommunity.core.content.builder.AccountManifestBuilder;
import com.danieleguiducci.torcommunity.core.dao.AccountDao;
import com.danieleguiducci.torcommunity.core.entity.Account;
import com.danieleguiducci.torcommunity.core.service.AccountNotValidException;
import com.danieleguiducci.torcommunity.core.service.AccountService;
import com.danieleguiducci.torcommunity.core.service.CreationAccountException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
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
    public void addAccount(Account account) throws AccountNotValidException {
        try {
            AccountManifest manifest = manifestBuilder.buildFromRaw(account.getAccountManifest());

        } catch (Exception e) {
            throw new AccountNotValidException(e);
        }
    }

    private boolean verifySignature(AccountManifest manifest, Account account) {
        return false;
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
            manifest.setAccountSecretId(generateSecretId(username,userPassword));

            byte[] rawManifest = manifestBuilder.serializa(manifest);

            Signature dsa = Signature.getInstance("SHA1withECDSA");
            dsa.initSign(paio.getPrivate());
            dsa.update(rawManifest);

            Account account = new Account();
            account.setAccountManifest(rawManifest);
            account.setSignature(dsa.sign());
            account.setHash(digestUtil.getSha1().digest(rawManifest));
            account.setAccountSecretId(manifest.getAccountSecretId());
            accountDao.insert(account);
            //manifest.set
            return account;
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

    private byte[] generateSecretId(String username, String userPassword) {
        MessageDigest digest = digestUtil.getSha1();
        for (int i = 0; i < 100000; i++) {
            byte[] tmp = digest.digest((username + "#.#" + userPassword + i).getBytes());
            digest.update(tmp);
        }
        byte[] encPassword = digest.digest();
        return encPassword;
    }
}
