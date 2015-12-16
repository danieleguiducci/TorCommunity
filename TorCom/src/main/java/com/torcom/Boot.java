package com.torcom;

import io.vertx.core.Vertx;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Boot {
    @Value("${port}")
    private int port;
    @Autowired
    private ProxyServer proxyServer;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(Boot.class, args);
    }

    @PostConstruct
    public void deployVerticle() {
       // Vertx.vertx().deployVerticle(staticServer);
       Vertx.vertx().deployVerticle(proxyServer);
    }
}
