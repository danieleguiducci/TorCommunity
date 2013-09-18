/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.torcommunity.web.controller;

import javax.servlet.http.HttpServletRequest;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Daniele
 */
@Controller
public class Index implements InitializingBean{
    protected static org.slf4j.Logger logger = LoggerFactory.getLogger(Index.class);
    @RequestMapping(value = "/")
    public ModelAndView getHomepage(HttpServletRequest req) {
        ModelAndView vista=new ModelAndView("index");
        vista.addObject("dominio",req.getServerName());
        return vista;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        logger.info("Controller MVC web inizializzato");
    }
}
