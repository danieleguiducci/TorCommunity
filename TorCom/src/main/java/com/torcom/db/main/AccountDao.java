package com.torcom.db.main;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;

/**
 * Created by daniele on 17/12/2015.
 */
@Service
public class AccountDao {

    private static Logger logger = LogManager.getLogger(AccountDao.class);

    private RowMapper rowMapper = (ResultSet rs, int rowNum) ->{
        AccountRow book= new AccountRow();
        //book.setIsbn(rs.getString("isbn"));
        //book.setAutore(rs.getString("autore"));
        //book.setTitolo(rs.getString("titolo"));
        return book;
    };
    @Autowired
    private JdbcTemplate db;

    public void insert(AccountRow com) {
        db.update("INSERT INTO community (publicdomain, signature, rawdata, privatekey) VALUES(?,?,?,?)",
                com.getPublicDomain(),com.getSignature(),com.getRawData(),com.getPrivateKey());
    }



}
