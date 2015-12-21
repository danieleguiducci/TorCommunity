package com.torcom.controller;

import com.torcom.Const;
import com.torcom.bean.Community;
import com.torcom.bean.PublicDomain;
import com.torcom.service.crypto.CryptoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.security.*;
import java.time.Clock;
import java.time.Instant;

/**
 * Created by daniele on 19/12/2015.
 */
@Controller
public class NewCommunityCtrl {
    protected static Logger log = LoggerFactory.getLogger(NewCommunityCtrl.class);

    @Autowired
    private CryptoUtil cryptoUtil;
    @Autowired
    private Clock clock;

    public Community newCommunity() throws NoSuchAlgorithmException {


        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("EC");
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        keyGen.initialize(256, random);
        KeyPair pair = keyGen.generateKeyPair();
        PrivateKey priv = pair.getPrivate();
        PublicKey pub = pair.getPublic();
        MessageDigest md=MessageDigest.getInstance("sha1");
        byte[] privKeySha= md.digest(priv.getEncoded());

        String privDomain=cryptoUtil.generatePrivateDomain("Hash of the account ".getBytes(),privKeySha)+"."+ Const.TLD_SELF;
        byte[] pubSid=cryptoUtil.generatePublicSid(privKeySha);
        PublicDomain pubicDomain=cryptoUtil.privateDomain2publicDomain(privDomain);
        return Community.create(pub,priv, Instant.now(clock),pubSid,privDomain,pubicDomain);
    }

}
