package com.torcom.service.serialization;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.stereotype.Service;

/**
 * Created by daniele on 17/12/2015.
 */
@Service
public class JsonObjectMapper extends ObjectMapper{
    public JsonObjectMapper() {
        this.registerModule(new JavaTimeModule());
    }

}
