package com.torcom.service.serialization;

import com.torcom.TorcomApp;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.time.Clock;
import java.time.Instant;
import static org.junit.Assert.*;
/**
 * Created by daniele on 23/12/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(TorcomApp.class)
@IntegrationTest({"server.port=0", "management.port=0"})
public class JsonObjectMapperIntegrationTest {
    protected static Logger log = LoggerFactory.getLogger(JsonObjectMapperIntegrationTest.class);

    @Autowired
    private JsonObjectMapper mapper;
    @Autowired
    private MsgPackMapper msgPackMapper;
    @Autowired
    private Clock clock;

    @Test
    public void timeSerializzationTest() throws Exception{
        log.info("Instant Serialization"+mapper.writeValueAsString(Instant.now()));
    }
    @Test
    public void serielizeKey() throws Exception{
        KeyPairGenerator kpg=KeyPairGenerator.getInstance("EC");
        kpg.initialize(256);
        KeyPair kp=kpg.generateKeyPair();
        PublicKey pk=kp.getPublic();
        PrivateKey privK=kp.getPrivate();

        TestObj testObj=new TestObj();
        testObj.pubKey=pk;
        String pkJson=mapper.writeValueAsString(testObj);
        log.info("Public Key "+pkJson);
        log.info("PublicKey Size:"+pkJson.length());
        TestObj pk2=mapper.readValue(pkJson,TestObj.class);
        assertEquals(pkJson,mapper.writeValueAsString(testObj));

        byte[] pkByte=msgPackMapper.writeValueAsBytes(testObj);
        log.info("PublicKey Size msgPack:"+pkByte.length);
        TestObj pk3=mapper.readValue(pkByte,TestObj.class);
        assertEquals(pkByte,mapper.writeValueAsBytes(pk3));


    }

    public static class TestObj {
        public PublicKey pubKey;
    }
}
