package com.torcom.service.serialization;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

/**
 * Created by daniele on 23/12/2015.
 */
abstract public class BaseObjectMapper extends ObjectMapper {
    public BaseObjectMapper() throws Exception{
        registerModules();
    }

    public BaseObjectMapper(JsonFactory jf) throws Exception{
        super(jf);
        registerModules();
    }
    private void registerModules() throws Exception {
        this.registerModule(new JavaTimeModule());
        this.registerModule(new CryptoModule());
        this.registerModule(new CustomTypeModule());
    }
}
