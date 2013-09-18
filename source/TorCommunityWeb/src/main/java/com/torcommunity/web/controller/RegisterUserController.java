/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.torcommunity.web.controller;

import com.torcommunity.core.service.AccountService;
import com.torcommunity.core.service.CreationAccountException;
import com.torcommunity.web.controller.pojo.MakeAccountRequest;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Daniele
 */
@Controller
public class RegisterUserController {
    protected static org.slf4j.Logger logger = LoggerFactory.getLogger(RegisterUserController.class);
    @Autowired
    private AccountService accountService;
    @RequestMapping(value = "/makeaccount", method = RequestMethod.GET)
    public ModelAndView registerUser() {
        return  new ModelAndView("makeaccount");
    }
    @RequestMapping(value = "/makeaccountdo", method = RequestMethod.POST)
    public ModelAndView registerUserDo(MakeAccountRequest richiesta) throws CreationAccountException {
        long start=System.currentTimeMillis();
        accountService.createNewAccount(richiesta.getUsername(), richiesta.getPassword1()+richiesta.getPassword2()+richiesta.getPassword3()+richiesta.getPassword4());
        logger.info("Account maded time:"+(System.currentTimeMillis()-start));
        return  new ModelAndView("ok");
    }
}
