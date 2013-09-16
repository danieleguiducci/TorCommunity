/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.danieleguiducci.torcommunity;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.NCSARequestLog;
import org.eclipse.jetty.server.RequestLog;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.RequestLogHandler;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.eclipse.jetty.util.thread.ThreadPool;
import org.eclipse.jetty.webapp.WebAppContext;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Daniele
 */
public class WebServer {

    protected static org.slf4j.Logger logger = LoggerFactory.getLogger(WebServer.class);
    // TODO: You should configure this appropriately for 
    // your environment
    private static final String LOG_PATH =
            "./yyyy_mm_dd.request.log";
    private static final String WEB_XML =
            "META-INF/webapp/WEB-INF/web.xml";
    private static final String CLASS_ONLY_AVAILABLE_IN_IDE =
            "com.sjl.IDE";
    private static final String PROJECT_RELATIVE_PATH_TO_WEBAPP =
            "src/main/resources/META-INF/webapp";

    public static interface WebContext {

        public File getWarPath();

        public String getContextPath();
    }
    private Server server;
    private int port;
    private String bindInterface;

    public WebServer(int aPort) {
        this(aPort, null);
    }

    public WebServer(int aPort, String aBindInterface) {
        port = aPort;
        bindInterface = aBindInterface;
    }

    public void start() throws Exception {
        logger.info("start server");
        server = new Server(port);

        // server.setThreadPool(createThreadPool());
        //server.addConnector(createConnector());
        server.setHandler(createHandlers());
        server.setStopAtShutdown(true);

        server.start();
    }

    public void join() throws InterruptedException {
        server.join();
    }

    public void stop() throws Exception {
        server.stop();
    }

    private ThreadPool createThreadPool() {
        // TODO: You should configure these appropriately
        // for your environment - this is an example only
        QueuedThreadPool _threadPool = new QueuedThreadPool();
        _threadPool.setMinThreads(10);
        _threadPool.setMaxThreads(100);
        return _threadPool;
    }

    /* private SelectChannelConnector createConnector()
     {
     SelectChannelConnector _connector = 
     new SelectChannelConnector();
     _connector.setPort(port);
     _connector.setHost(bindInterface);
     return _connector;
     }*/
    private HandlerCollection createHandlers() {
        logger.info("Creo handler");
        WebAppContext _ctx = new WebAppContext();
        _ctx.setContextPath("/");
        logger.info("WebApp context creato");
        if (false) {//isRunningInShadedJar()
            logger.info("Avvio in shaded modejar: ");
            logger.info("Shaded war url "+ getShadedWarUrl() );
            _ctx.setWar(getShadedWarUrl());
        } else {
            logger.info("Avvio in ide");
            _ctx.setWar(PROJECT_RELATIVE_PATH_TO_WEBAPP);
        }

        List<Handler> _handlers = new ArrayList<Handler>();

        _handlers.add(_ctx);

        HandlerList _contexts = new HandlerList();
        _contexts.setHandlers(_handlers.toArray(new Handler[0]));

        RequestLogHandler _log = new RequestLogHandler();
        _log.setRequestLog(createRequestLog());

        HandlerCollection _result = new HandlerCollection();
        _result.setHandlers(new Handler[]{_contexts, _log});

        return _result;
    }

    private RequestLog createRequestLog() {
        NCSARequestLog _log = new NCSARequestLog();

        File _logPath = new File(LOG_PATH);
        _logPath.getParentFile().mkdirs();

        _log.setFilename(_logPath.getPath());
        _log.setRetainDays(90);
        _log.setExtended(false);
        _log.setAppend(true);
        _log.setLogTimeZone("GMT");
        _log.setLogLatency(true);
        return _log;
    }

    private boolean isRunningInShadedJar() {
        try {
            Class.forName(CLASS_ONLY_AVAILABLE_IN_IDE);
            return false;
        } catch (ClassNotFoundException anExc) {
            return true;
        }
    }

    private URL getResource(String aResource) {
        return Thread.currentThread().
                getContextClassLoader().getResource(aResource);
    }

    private String getShadedWarUrl() {
        String _urlStr = getResource(WEB_XML).toString();
        // Strip off "WEB-INF/web.xml"
        return _urlStr.substring(0, _urlStr.length() - 15);
    }
}