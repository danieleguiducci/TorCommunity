package com.torcom;

import com.torcom.service.serialization.JsonObjectMapper;
import io.vertx.core.Vertx;
import liquibase.integration.spring.SpringLiquibase;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
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
@ComponentScan(basePackages = "com.torcom",excludeFilters =  @ComponentScan.Filter(pattern = "com.torcom.community.*", type = FilterType.REGEX))

public class TorcomApp implements InitializingBean{
    protected static Logger logger = LoggerFactory.getLogger(Boot.class);
    @Value("${port}")
    private int port;
    @Autowired
    private ProxyConfigurer proxyServer;
    @Autowired
    private DataSource dataSource;
    @Autowired
    private SpringLiquibase liquid;
    @Autowired
    private JsonObjectMapper om;

    public TorcomApp() {
        logger.info("Torcom is starting. Please wait...");
        Security.addProvider(new BouncyCastleProvider());
    }

    @Bean
    public Clock clock() {
        return Clock.systemUTC();
    }

    @Bean
    @ConfigurationProperties("liquibase")
    public SpringLiquibase liquibase() {
        SpringLiquibase sl=new SpringLiquibase();
        sl.setChangeLog("classpath:db-changes.xml");
        sl.setDataSource(dataSource);
        sl.setContexts("production");
        sl.setShouldRun(false);
        return sl;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Vertx.vertx().deployVerticle(proxyServer);
    }
}
