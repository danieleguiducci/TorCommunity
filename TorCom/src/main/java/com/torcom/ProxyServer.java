/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.torcom;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.torcom.web.admin.AdminCtrl;
import com.torcom.web.api.CommunityCtrl;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.net.NetSocket;
import io.vertx.core.streams.Pump;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.StaticHandler;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;

import io.vertx.ext.web.handler.VirtualHostHandler;
import org.msgpack.jackson.dataformat.MessagePackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * @author daniele
 */
@Component
public class ProxyServer extends AbstractVerticle {
    protected static Logger logger = LoggerFactory.getLogger(ProxyServer.class);
    @Value("${localproxy.port}")
    private int localproxyPort;
    @Autowired
    private ApplicationContext context;
    @Autowired
    private AdminCtrl adminCtrl;
    @Autowired
    private CommunityCtrl communityCtrl;

    private static class Status {
        private int stato = 0;
    }

    @Override
    public void start() throws Exception {
        logger.info("Vertx starting configuration");
        Router router = Router.router(vertx);

        // Serve the static pages
        String adminDomain = "admin." + Const.TLD_SELF;
        Router adminRoute = adminCtrl.getRouter(vertx);

        StaticHandler sh = StaticHandler.create()
                .setDirectoryListing(false).setCachingEnabled(false)
                .setWebRoot("./web/admin").setIndexPage("index.html");
        router.route().path("/api/*").handler(VirtualHostHandler.create(adminDomain, routingContext -> {
            adminRoute.handleContext(routingContext);
        }));
        router.route().handler(VirtualHostHandler.create(adminDomain, sh));

        Router communityRouter=communityCtrl.getRouter(vertx);
        StaticHandler staticCommunity = StaticHandler.create()
                .setDirectoryListing(false).setCachingEnabled(false)
                .setWebRoot("./web/community").setIndexPage("index.html");
        router.route().path("/api/*").handler(VirtualHostHandler.create("*."+Const.TLD_SELF, routingContext -> {
            communityRouter.handleContext(routingContext);
        }));
        router.route().handler(VirtualHostHandler.create("*."+Const.TLD_SELF, staticCommunity));

        vertx.createHttpServer().requestHandler(router::accept).listen(8091, "localhost");
        vertx.createNetServer().connectHandler((NetSocket event) -> {
            Status status = new Status();
            event.handler((Buffer b) -> {
                if (status.stato == 0) {
                    //System.out.println("Handshake: "+bytesToHex(b.getBytes()));
                    Buffer b2 = Buffer.buffer();
                    b2.appendByte((byte) 5).appendByte((byte) 0);
                    event.write(b2);

                    status.stato = 1;
                } else if (status.stato == 1) {
                    //System.out.println("Data: "+bytesToHex(b.getBytes()));
                    int protocol = b.getUnsignedByte(0);
                    int command = b.getUnsignedByte(1);
                    int zero = b.getUnsignedByte(2);
                    int addressType = b.getUnsignedByte(3);
                    int pos = 4;
                    if (protocol != 5) throw new IllegalStateException("Protocol not supported");
                    if (command != 1) throw new IllegalStateException("Invalid command");
                    if (zero != 0) throw new IllegalStateException("Invalid format. third char must be zero");

                    InetSocketAddress destination;
                    if (addressType == 1) {
                        try {
                            InetAddress add = Inet4Address.getByAddress(b.getBytes(pos, 8));
                            pos += 4;
                            int port = b.getShort(pos) & 0xffff;
                            destination = new InetSocketAddress(add, port);
                        } catch (UnknownHostException ex) {
                            throw new IllegalStateException(ex);
                        }

                    } else if (addressType == 3) {
                        int size = b.getByte(pos) & 0xff;
                        pos++;
                        String domain = b.getString(pos, pos + size);
                        pos += size;
                        int port = b.getShort(pos) & 0xffff;

                        if (domain.toLowerCase().endsWith("." + Const.TLD_SELF)) {
                            domain = "localhost";
                            port = 8091;
                        }
                        destination = InetSocketAddress.createUnresolved(domain.toLowerCase(), port);
                    } else {
                        throw new IllegalStateException("Not supported IPv6, yet");
                    }

                    System.out.println("destinazione " + destination);


                    vertx.createNetClient().connect(destination.getPort(), destination.getHostName(), (evento2) -> {
                        if (evento2.succeeded()) {
                            System.out.println("Successo " + destination.getHostString());
                            Buffer b2 = Buffer.buffer();
                            b2.appendUnsignedByte((byte) 5);
                            b2.appendUnsignedByte((byte) 0)
                                    .appendUnsignedByte((byte) 0)
                                    .appendUnsignedByte((byte) 3)
                                    .appendUnsignedByte((short) destination.getHostString().length())
                                    .appendString(destination.getHostString())
                                    .appendUnsignedShort(destination.getPort());
                            event.write(b2);
                            NetSocket ee2 = evento2.result();
                            Pump.pump(event, ee2).start();
                            Pump.pump(ee2, event).start();

                        } else {
                            System.out.println("fallimento");
                        }

                    });
                    status.stato = 2;
                } else if (status.stato == 2) {
                    System.out.println("RICEVO DATI:" + new String(b.getBytes()));
                }

            });
        }).listen(localproxyPort);
    }

    final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();

    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }
}
