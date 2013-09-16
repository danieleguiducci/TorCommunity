/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.danieleguiducci.torcommunity.core.dao;

import com.danieleguiducci.torcommunity.core.entity.Account;

/**
 *
 * @author Daniele
 */
public interface AccountDao {
    public void insert(Account account);
  
    public Account find(byte[] hash);
    public Account findBySecretId(byte[] hash);
    public Account findException(byte[] hash)  throws AccountNotFoundException;
    public void delete(Account account);
    public static class AccountNotFoundException extends Exception{
        
    }
}
