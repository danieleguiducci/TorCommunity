/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.danieleguiducci.torcommunity.core.dao.imp;

import com.danieleguiducci.torcommunity.core.dao.AccountDao;
import com.danieleguiducci.torcommunity.core.entity.Account;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Daniele
 */
@Service
public class AccountDaoImp implements AccountDao {

    protected static Logger logger = LoggerFactory.getLogger(AccountDaoImp.class);
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    @Transactional
    public void insert(Account account) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(account);
    }

    @Override
    @Transactional
    public Account find(byte[] hash) {
        Session session = sessionFactory.getCurrentSession();
        Query q = session.createQuery("FROM Account account WHERE account.hash=:hash");
        q.setBinary("hash", hash);
        return (Account) q.uniqueResult();
    }

    @Override
    public Account findException(byte[] hash) throws AccountNotFoundException {
        Account account=find(hash);
        if(account==null) throw new AccountNotFoundException();
        return account;
    }

    @Override
    @Transactional
    public void delete(Account account) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(account);
    }

    @Override
    @Transactional
    public Account findBySecretId(byte[] secretId) {
        Session session = sessionFactory.getCurrentSession();
        Query q = session.createQuery("FROM Account account WHERE account.accountSecretId=:hash");
        q.setBinary("hash", secretId);
        return (Account) q.uniqueResult();
    }
}
