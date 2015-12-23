package com.torcom.service.serialization;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;

/**
 * Created by daniele on 17/12/2015.
 */
@Service
public class JsonObjectMapper extends BaseObjectMapper{
    public JsonObjectMapper() throws Exception {
    }
}
