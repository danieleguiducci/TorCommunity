/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.torcommunity.core.service;

import com.torcommunity.core.entity.Account;

/**
 *
 * @author Daniele
 */
public interface AccountService {
    public Account addAccount(byte[] manifestRaw,byte[] signature,byte[] hash) throws AccountNotValidException;
    public Account createNewAccount(String username, String password) throws CreationAccountException;
}
