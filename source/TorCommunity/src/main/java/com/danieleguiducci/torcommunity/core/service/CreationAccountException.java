/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.danieleguiducci.torcommunity.core.service;

/**
 *
 * @author Daniele
 */
public class CreationAccountException extends Exception {

    /**
     * Creates a new instance of
     * <code>CreationAccountException</code> without detail message.
     */
    public CreationAccountException() {
    }

    /**
     * Constructs an instance of
     * <code>CreationAccountException</code> with the specified detail message.
     *
     * @param msg the detail message.
     */
    public CreationAccountException(String msg) {
        super(msg);
    }

    public CreationAccountException(Throwable cause) {
        super(cause);
    }
    
}
