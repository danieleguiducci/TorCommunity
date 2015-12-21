package com.torcom;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.torcom.service.serialization.JsonObjectMapper;
import io.vertx.core.Vertx;
import javax.sql.DataSource;

import liquibase.integration.spring.SpringLiquibase;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.security.Security;
import java.time.Clock;

@SpringBootApplication
@EnableTransactionManagement
@ComponentScan(basePackages = "com.torcom",excludeFilters =  @ComponentScan.Filter(pattern = "com.torcom.community.*", type = FilterType.REGEX))
public class Boot implements InitializingBean{
    protected static Logger logger = LoggerFactory.getLogger(Boot.class);
    @Value("${port}")
    private int port;
    @Autowired
    private ProxyServer proxyServer;
    @Autowired
    private DataSource dataSource;
    @Autowired
    private SpringLiquibase liquid;
    @Autowired
    private JsonObjectMapper om;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws JsonProcessingException {

        Security.addProvider(new BouncyCastleProvider());
        SpringApplication.run(Boot.class, args);
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
        return sl;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Vertx.vertx().deployVerticle(proxyServer);
    }


}
