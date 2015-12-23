package com.torcom.db.dao.imp;

import com.torcom.db.dao.CommunityDao;
import com.torcom.db.entity.CommunityRow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by daniele on 17/12/2015.
 */
@Service
public class CommunityDaoImp implements CommunityDao{

    protected static Logger logger = LoggerFactory.getLogger(CommunityDaoImp.class);
    private RowMapper rowMapper = (ResultSet rs, int rowNum) ->{
        CommunityRow book= new CommunityRow();
        //book.setIsbn(rs.getString("isbn"));
        //book.setAutore(rs.getString("autore"));
        //book.setTitolo(rs.getString("titolo"));
        return book;
    };
    @Autowired
    private JdbcTemplate db;


    @Override
    public void insert(CommunityRow com) {
        db.update("INSERT INTO community (publicdomain,signature,rawdata,privatekey) VALUES(?,?,?,?)",
                com.getPublicDomain(),com.getSignature(),com.getRawData(),com.getPrivateKey());
    }



}
