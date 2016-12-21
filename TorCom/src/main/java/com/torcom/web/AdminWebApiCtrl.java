package com.torcom.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.torcom.CommunitySet;
import com.torcom.bean.Community;
import com.torcom.controller.NewCommunityCtrl;
import com.torcom.service.serialization.JsonObjectMapper;
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
public class AdminWebApiCtrl {

    protected static Logger logger = LoggerFactory.getLogger(AdminWebApiCtrl.class);

    @Autowired
    private JsonObjectMapper mapper;

    @Autowired
    private NewCommunityCtrl newComCtrl;

    @Autowired
    private CommunitySet comCtrl;

    public void getName(RoutingContext ctx) {
        ctx.response().end("GET NAME CHIAMAT");
    }

    public void createcommunty(RoutingContext ctx) {
        HttpServerResponse resp = ctx.response();
        try {

            resp.putHeader("content-type", "application/json");

            Community com = newComCtrl.newCommunity();

            CreateCommunityResp respObj = new CreateCommunityResp();

            resp.end(mapper.writeValueAsString(respObj));
        } catch (NoSuchAlgorithmException | JsonProcessingException | SecurityException | SignatureException | InvalidKeyException e) {
            logger.error("Error during creation of key", e);
            resp.setStatusCode(500).end();
        }

    }

    public static class CreateCommunityResp {
        public String url;
    }

    public Router getRouter(Vertx vertx) {
        Router router = Router.router(vertx);
        router.route("/getname").handler(this::getName);
        router.route(HttpMethod.POST, "/createcommunity").consumes("application/json").handler(this::createcommunty);
        return router;
    }
}
