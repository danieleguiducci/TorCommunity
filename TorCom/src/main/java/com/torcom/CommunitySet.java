package com.torcom;

import com.torcom.bean.PublicId;
import com.torcom.community.CommunityStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by daniele on 19/12/2015.
 */
@Controller
public class CommunitySet {
    @Autowired
    private ConfigurableApplicationContext context;

    private Map<PublicId,ConfigurableApplicationContext> contexts=new ConcurrentHashMap<>();




    public CommunityStatus getOrStart(PublicId publicDomain) {
        ConfigurableApplicationContext con=startCommunity(publicDomain);
        return con.getBean(CommunityStatus.class);
    }
    private synchronized void stopCommunity(PublicId publicDomain) {
        if(!contexts.containsKey(publicDomain)) {
            return;
        }
        ConfigurableApplicationContext val=contexts.get(publicDomain);
        if(val==null) return;
        val.close();
        contexts.remove(publicDomain);
    }
    private ConfigurableApplicationContext startCommunity(PublicId publicDomain) {
        ConfigurableApplicationContext val=contexts.get(publicDomain);
        if(val!=null) {
            return val;
        }
        synchronized (this) {
            if(contexts.containsKey(publicDomain)) {
                return contexts.get(publicDomain);
            }
            AnnotationConfigApplicationContext con=new AnnotationConfigApplicationContext();

            ConfigurableEnvironment ce= con.getEnvironment();
            MutablePropertySources mps=ce.getPropertySources();
            Map myMap = new HashMap();
            myMap.put("publicDomain", publicDomain.getPublicId());
            mps.addLast(new MapPropertySource("initData", myMap));

            con.setParent(context);
            con.scan(CommunityStatus.class.getPackage().getName());

            con.refresh();
            contexts.put(publicDomain,con);
            return con;
        }

    }
}
