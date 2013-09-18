/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.torcommunity.core.service;

/**
 *
 * @author Daniele
 */
public class AccountNotValidException extends Exception {

    /**
     * Creates a new instance of
     * <code>AccountNotValidException</code> without detail message.
     */
    public AccountNotValidException() {
    }

    /**
     * Constructs an instance of
     * <code>AccountNotValidException</code> with the specified detail message.
     *
     * @param msg the detail message.
     */
    public AccountNotValidException(String msg) {
        super(msg);
    }

    public AccountNotValidException(Throwable cause) {
        super(cause);
    }
    
}
