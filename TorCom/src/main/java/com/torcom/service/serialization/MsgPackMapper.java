package com.torcom.service.serialization;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.msgpack.jackson.dataformat.MessagePackFactory;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;

/**
 * Created by daniele on 17/12/2015.
 */
@Service
public class MsgPackMapper extends BaseObjectMapper {
    public MsgPackMapper() throws Exception {
        super(new MessagePackFactory());

    }
}
