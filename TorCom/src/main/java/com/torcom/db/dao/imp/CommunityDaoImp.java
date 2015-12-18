package com.torcom.db.dao.imp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

/**
 * Created by daniele on 17/12/2015.
 */
@Service
public class CommunityDaoImp {
    protected static Logger logger = LoggerFactory.getLogger(CommunityDaoImp.class);
    @Autowired
    private JdbcTemplate db;


}
