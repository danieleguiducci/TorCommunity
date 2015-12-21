package com.torcom.community;

import com.torcom.db.dao.CommunityDao;
import com.torcom.service.crypto.CryptoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created by daniele on 21/12/2015.
 */
@Service
public class CommunityStatus implements InitializingBean{

    protected static Logger log = LoggerFactory.getLogger(CommunityStatus.class);

    @Value("#{environment.publicDomain}")
    private String publicDomain;

    @Autowired
    private CryptoUtil communityDao;

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("Community PublicDomain:"+publicDomain+" started!");
    }
}
