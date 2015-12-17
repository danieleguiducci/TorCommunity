package com.torcom.admin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.torcom.Const;
import com.torcom.crypto.CryptoUtil;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.security.*;

/**
 * Created by daniele on 16/12/2015.
 */
@Controller
public class AdminCtrl {
    protected static Logger logger = LoggerFactory.getLogger(AdminCtrl.class);
    @Autowired
    private CryptoUtil cryptoUtil;

    private ObjectMapper mapper=new ObjectMapper();
    public void getName(RoutingContext ctx) {
        ctx.response().end("GET NAME CHIAMAT");
    }
    public void createcommunty(RoutingContext ctx) {
        HttpServerResponse resp=ctx.response();
        try {

            resp.putHeader("content-type","application/json");
            KeyPairGenerator keyGen = null;
            keyGen = KeyPairGenerator.getInstance("EC");
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");

            keyGen.initialize(256, random);

            KeyPair pair = keyGen.generateKeyPair();
            PrivateKey priv = pair.getPrivate();
            PublicKey pub = pair.getPublic();
            MessageDigest md=MessageDigest.getInstance("sha1");
            byte[] privKeySha= md.digest(priv.getEncoded());
            CreateCommunityResp respObj=new CreateCommunityResp();
            respObj.url=cryptoUtil.generatePrivateDomain("Hash of the account ".getBytes(),privKeySha)+"."+ Const.TLD_SELF;

            resp.end(mapper.writeValueAsString(respObj));
        } catch (NoSuchAlgorithmException| JsonProcessingException e) {
            logger.error("Error during creation of key",e);
            resp.setStatusCode(500).end();
        }

    }
    public static class CreateCommunityResp {
        public String url;
    }
    public Router getRouter(Vertx vertx) {
        Router router = Router.router(vertx);
        router.route("/getname").handler(this::getName);
        router.route(HttpMethod.POST,"/createcommunity").consumes("application/json").handler(this::createcommunty);
        return router;
    }
}
