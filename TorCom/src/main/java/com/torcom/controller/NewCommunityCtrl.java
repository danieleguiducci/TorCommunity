package com.torcom.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.torcom.CommunitySet;
import com.torcom.bean.Community;
import com.torcom.bean.PublicDomain;
import com.torcom.bean.PublicSid;
import com.torcom.db.main.CommunityDao;
import com.torcom.db.main.CommunityRow;
import com.torcom.service.crypto.CryptoUtil;
import com.torcom.service.serialization.JsonObjectMapper;
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

    @Autowired
    private CommunitySet comCtrl;

    @Autowired
    private JsonObjectMapper jsonObjectMapper;

    @Autowired
    private CommunityDao communityDao;

    public Community newCommunity() throws NoSuchAlgorithmException, InvalidKeyException, JsonProcessingException, SignatureException {
        log.info("Creation of a new community....");
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("EC");
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        keyGen.initialize(256, random);
        KeyPair pair = keyGen.generateKeyPair();
        PrivateKey priv = pair.getPrivate();
        PublicKey pub = pair.getPublic();
        MessageDigest md=MessageDigest.getInstance("sha1");
        byte[] privKeySha= md.digest(priv.getEncoded());

        String privDomain=cryptoUtil.generatePrivateDomain(privKeySha,privKeySha);
        PublicSid pubSid=cryptoUtil.generatePublicSid(privKeySha);
        PublicDomain pubicDomain=cryptoUtil.privateDomain2publicDomain(privDomain);
        Community com= Community.create(pub,priv, Instant.now(clock),pubSid,privDomain,pubicDomain);

        byte[] rawElement=jsonObjectMapper.writeValueAsBytes(com);

        Signature sign=Signature.getInstance("ECDSA");
        sign.initSign(com.getPrivKey());
        sign.update(rawElement);
        byte[] signature=sign.sign();

        CommunityRow comRow=new CommunityRow();
        comRow.setPrivateKey(com.getPrivKey().getEncoded());
        comRow.setPublicDomain(com.getPublicDomain().getDomain());
        comRow.setSignature(signature);
        comRow.setRawData(rawElement);

        communityDao.insert(comRow);

        comCtrl.getOrStart(com.getPublicDomain());
        log.info("Community created and started. RawElementSize:"+rawElement.length);
        return com;
    }

}
