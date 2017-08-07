package com.torcom.second;

import org.ethereum.samples.BasicSample;
import org.springframework.context.annotation.Bean;

public class Config {
    @Bean
    public BasicSample basicSample() {
        return new BasicSample();
    }
}
