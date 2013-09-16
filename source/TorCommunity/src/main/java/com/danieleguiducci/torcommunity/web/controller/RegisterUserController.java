/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.danieleguiducci.torcommunity.web.controller;

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
    
    @RequestMapping(value = "/makeaccount", method = RequestMethod.GET)
    public ModelAndView registerUser() {
        return  new ModelAndView("makeaccount");
    }
}
