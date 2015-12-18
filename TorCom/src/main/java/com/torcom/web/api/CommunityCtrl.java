package com.torcom.web.api;

import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import org.springframework.stereotype.Controller;

/**
 * Created by daniele on 16/12/2015.
 */
@Controller
public class CommunityCtrl {

    public Router getRouter(Vertx vertx) {
        Router router=Router.router(vertx);
        return router;
    }
}
