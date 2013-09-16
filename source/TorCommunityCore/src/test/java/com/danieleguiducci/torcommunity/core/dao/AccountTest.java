/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.danieleguiducci.torcommunity.core.dao;

import com.danieleguiducci.torcommunity.core.service.CreationAccountException;
import junit.framework.TestCase;
import org.springframework.context.ApplicationContext;

/**
 *
 * @author Daniele
 */
public class AccountTest extends TestCase {
ApplicationContext context ;
    public AccountTest() {
    }

    public AccountTest(String name) {
        super(name);
      // context =  new ClassPathXmlApplicationContext(new String[] {"applicationContext.xml"});
    }
    public void testUniqueInsert() throws CreationAccountException {
        //AccountService accountDao=(AccountService)context.getBean(AccountService.class);
      //  accountDao.createNewAccount("daniele", "Filippo");
    
    }
}
