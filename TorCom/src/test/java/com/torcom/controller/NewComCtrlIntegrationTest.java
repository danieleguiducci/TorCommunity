package com.torcom.controller;

import com.torcom.TorcomApp;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by daniele on 21/12/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(value = {"server.port=0", "management.port=0"}, classes = TorcomApp.class)
@TestPropertySource(locations = {"classpath:/test.properties"})
public class NewComCtrlIntegrationTest {
    protected static Logger log = LoggerFactory.getLogger(NewComCtrlIntegrationTest.class);

    @Autowired
    private NewCommunityCtrl newcomCtrl;

    @Test
    public void createNewAccount() throws Exception {
        newcomCtrl.newCommunity();
        //assertEquals(24,com.getPrivateDomain().length());
        //assertEquals(com.getPublicDomain().getRaw().length, Const.PUBLIC_DOMAIN_LENGTH);
        //log.info("Generated PrivateDomain:"+com.getPrivateDomain());
    }
}
