/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.danieleguiducci.torcommunity.core.service;

import com.danieleguiducci.torcommunity.core.entity.Account;

/**
 *
 * @author Daniele
 */
public interface AccountService {
    public void addAccount(Account account) throws AccountNotValidException;
    public Account createNewAccount(String username, String password) throws CreationAccountException;
}
