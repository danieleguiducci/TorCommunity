package com.torcom.controller;

import com.torcom.Const;
import com.torcom.TorcomApp;
import com.torcom.bean.Community;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;

/**
 * Created by daniele on 21/12/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(value = {"server.port=0", "management.port=0"}, classes = TorcomApp.class)
public class NewComCtrlIntegrationTest {
    protected static Logger log = LoggerFactory.getLogger(NewComCtrlIntegrationTest.class);

    @Autowired
    private NewCommunityCtrl newcomCtrl;

    @Test
    public void createNewCommunity()  throws Exception{
        Community com=newcomCtrl.newCommunity();
        assertEquals(24,com.getPrivateDomain().length());
        assertEquals(com.getPublicDomain().getRaw().length, Const.PUBLIC_DOMAIN_LENGTH);
        log.info("Generated PrivateDomain:"+com.getPrivateDomain());
    }
}
