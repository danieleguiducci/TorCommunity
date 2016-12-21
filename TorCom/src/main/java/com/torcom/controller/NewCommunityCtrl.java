package com.torcom.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.torcom.CommunitySet;
import com.torcom.bean.Domain;
import com.torcom.bean.PublicId;
import com.torcom.bean.PublicSid;
import com.torcom.db.main.AccountDao;
import com.torcom.db.main.AccountIos;
import com.torcom.db.main.AccountRow;
import com.torcom.service.crypto.CryptoUtil;
import com.torcom.service.serialization.MsgPackMapper;
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
    private MsgPackMapper mapper;

    @Autowired
    private AccountDao accountDao;

    public void newCommunity() throws NoSuchAlgorithmException, InvalidKeyException, JsonProcessingException, SignatureException {
        log.info("Creation of a new community....");
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("ECDSA");
        keyGen.initialize(256);
        KeyPair pair = keyGen.generateKeyPair();
        PrivateKey priv = pair.getPrivate();
        PublicKey pub = pair.getPublic();
        AccountIos accountIos = new AccountIos();
        accountIos.setPublicKey(pub.getEncoded());
        accountIos.setCreationDate(Instant.now());
        PublicSid publicSid = cryptoUtil.generatePublicSid(priv);
        accountIos.setPublicSid(publicSid.toByteArray());

        AccountRow accountRow = new AccountRow();
        accountRow.setRawData(mapper.writeValueAsBytes(accountIos));
        accountRow.setPrivateKey(priv.getEncoded());

        Signature sign = Signature.getInstance("ECDSA");
        sign.initSign(priv);
        sign.update(accountRow.getRawData());
        byte[] signature = sign.sign();
        accountRow.setSignature(signature);
        sign.initVerify(pub);
        sign.update(accountRow.getRawData());
        sign.verify(accountRow.getSignature());

        Domain domain =  cryptoUtil.generatePrivateDomain(accountRow);

        PublicId publicId1 = cryptoUtil.privateDomain2publicDomain(domain);
        PublicId publicId2 = cryptoUtil.domainSid2publicDomain(publicSid, accountRow);
        if(!publicId2.equals(publicId1)) {
            throw new IllegalStateException("publicId must be the same");
        }

        //comCtrl.getOrStart(com.getPublicDomain());
        log.info("Community created and started. RawElementSize:" + accountRow.getRawData().length);
        log.info("Domain "+domain);
        return;
    }

}
