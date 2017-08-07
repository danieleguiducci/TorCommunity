package com.torcom;

import com.torcom.second.Config;
import com.typesafe.config.ConfigFactory;
import org.ethereum.config.SystemProperties;
import org.ethereum.facade.Ethereum;
import org.ethereum.facade.EthereumFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Controller;

@Controller
public class Blockchain implements InitializingBean {

    Ethereum ethereum;

    @Override
    public void afterPropertiesSet() throws Exception {
        //SystemProperties.getDefault().overrideParams("database.incompatibleDatabaseBehavior", "RESET");
        SystemProperties prop = SystemProperties.getDefault();
        prop.overrideParams(ConfigFactory.load("eth.properties"));
        ethereum = EthereumFactory.createEthereum(Config.class);
    }

    public static void main(String[] args) {
        SystemProperties prop = SystemProperties.getDefault();
        prop.overrideParams(ConfigFactory.load("eth.properties"));
        Ethereum ethereum = EthereumFactory.createEthereum(Config.class);
    }


}
