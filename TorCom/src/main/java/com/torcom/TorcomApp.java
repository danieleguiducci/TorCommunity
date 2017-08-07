package com.torcom;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.torcom.service.serialization.JsonObjectMapper;
import io.vertx.core.Vertx;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.ethereum.config.SystemProperties;
import org.ethereum.facade.Ethereum;
import org.ethereum.facade.EthereumFactory;
import org.ethereum.samples.BasicSample;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.security.Security;
import java.time.Clock;

/**
 * Created by daniele on 21/12/2015.
 */
@SpringBootApplication
@EnableTransactionManagement
@ComponentScan(basePackages = "com.torcom", excludeFilters = @ComponentScan.Filter(pattern = "com.torcom.second.*", type = FilterType.REGEX))

public class TorcomApp implements InitializingBean {

    private final static Logger log = LogManager.getLogger(TorcomApp.class);

    @Value("${port}")
    private int port;
    @Autowired
    private ProxyConfigurer proxyServer;
    @Autowired
    private DataSource dataSource;
    @Autowired
    private JsonObjectMapper om;
    private Ethereum ethereum;

    public TorcomApp() {
        log.info("Torcom is starting. Please wait...");
        Security.addProvider(new BouncyCastleProvider());
    }


    public static void main(String[] args) throws JsonProcessingException {
        SpringApplication.run(TorcomApp.class, args);
    }

    @Bean
    public Clock clock() {
        return Clock.systemUTC();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Vertx.vertx().deployVerticle(proxyServer);
    }
}
