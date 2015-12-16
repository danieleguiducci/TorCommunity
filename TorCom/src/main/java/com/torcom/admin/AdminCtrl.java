package com.torcom.admin;

import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import org.springframework.stereotype.Controller;

/**
 * Created by daniele on 16/12/2015.
 */
@Controller
public class AdminCtrl {

    public void getName(RoutingContext ctx) {
        ctx.response().end("GET NAME CHIAMAT");
    }

    public Router getRouter(Vertx vertx) {
        Router router = Router.router(vertx);
        router.route("/getname").handler(this::getName);
        return router;
    }
}
