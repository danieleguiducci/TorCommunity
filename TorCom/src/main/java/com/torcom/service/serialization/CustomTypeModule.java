package com.torcom.service.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.torcom.bean.PublicDomain;
import com.torcom.bean.PublicSid;

import java.io.IOException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * Created by daniele on 23/12/2015.
 */
public class CustomTypeModule extends SimpleModule {
    public CustomTypeModule() throws NoSuchAlgorithmException {
        super("CustomTypeModule", new Version(1, 0, 0, null));
        this.addSerializer(PublicSid.class, new JsonSerializer<PublicSid>() {
            @Override
            public void serialize(PublicSid value, JsonGenerator gen, SerializerProvider serializers)
                    throws IOException, JsonProcessingException {
                gen.writeBinary(value.getPublicSid());
            }
        });
        this.addDeserializer(PublicSid.class, new JsonDeserializer<PublicSid>() {
            @Override
            public PublicSid deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
                return PublicSid.create(p.getBinaryValue());
            }
        });
        this.addSerializer(PublicDomain.class, new JsonSerializer<PublicDomain>() {
            @Override
            public void serialize(PublicDomain value, JsonGenerator gen, SerializerProvider serializers) throws IOException, JsonProcessingException {
                gen.writeBinary(value.getRaw());
            }
        });
        this.addDeserializer(PublicDomain.class, new JsonDeserializer<PublicDomain>() {
            @Override
            public PublicDomain deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
                return PublicDomain.create(p.getBinaryValue());
            }
        });
    }
}
