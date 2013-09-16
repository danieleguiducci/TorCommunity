package com.danieleguiducci.torcommunity;

import org.slf4j.LoggerFactory;

/**
 * Hello world!
 *
 */
public class TorCommunity {
    protected static org.slf4j.Logger logger = LoggerFactory.getLogger(TorCommunity.class);
   //private Server server ;
    WebServer webserver;
    public TorCommunity()  {
       webserver=new WebServer(4321);
     /* server = new Server(4321);
        ServletHandler handler = new ServletHandler();
        DispatcherServlet dispatcherServlet=new DispatcherServlet();
        ServletHolder servHolder = new ServletHolder("web", dispatcherServlet);

        servHolder.setInitParameter("contextConfigLocation", "webapp.xml");
        handler.addServletWithMapping(servHolder, "/web/*");
         URL ciao=this.getClass().getClassLoader().getResource("web");
         String warUrlString=ciao.toExternalForm();
         logger.info("WebApp context warFile: "+warUrlString);
        WebAppContext contextHandler = new WebAppContext(warUrlString,"/");
        contextHandler.addEventListener(new org.springframework.web.context.ContextLoaderListener());
        contextHandler.setInitParameter("contextConfigLocation", "applicationContext.xml");
        contextHandler.setServletHandler(handler);
        contextHandler.setContextPath("/");
        
        contextHandler.setExtractWAR(false);
        
        server.setHandler(contextHandler);*/
    }
    public void start() throws Exception {
     /*   
        server.start();*/
        logger.info("Start of server");
        webserver.start();
    }
    public void stop() throws Exception {
         logger.info("Stopping server");
       /* logger.info("Stopping server");
        server.stop();
        server.join();
        logger.info("Stoped server");*/
        webserver.stop();
    }

}
