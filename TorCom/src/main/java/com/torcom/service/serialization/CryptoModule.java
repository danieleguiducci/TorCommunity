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

import java.io.IOException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * Created by daniele on 23/12/2015.
 */
public class CryptoModule extends SimpleModule {
    private final KeyFactory fact;
    public CryptoModule() throws NoSuchAlgorithmException {
        super("MyModule", new Version(1, 0, 0, null));
        fact=KeyFactory.getInstance("ECDSA");
        this.addSerializer(PublicKey.class,new PublicKeySerializer());
        this.addDeserializer(PublicKey.class,new PublicKeyDeserializer());
        this.addSerializer(PrivateKey.class,new PrivateKeySerializer());
        this.addDeserializer(PrivateKey.class,new PrivateKeyDeserializer());
    }

    private class PublicKeySerializer extends JsonSerializer<PublicKey> {

        @Override
        public void serialize(PublicKey value, JsonGenerator gen, SerializerProvider serializers)
                throws IOException, JsonProcessingException {
            gen.writeBinary(value.getEncoded());
        }
    }

    private class PublicKeyDeserializer extends JsonDeserializer<PublicKey> {

        @Override
        public PublicKey deserialize(JsonParser p, DeserializationContext ctxt)
                throws IOException, JsonProcessingException {

            try {
                return fact.generatePublic(new X509EncodedKeySpec(p.getBinaryValue()));
            } catch (InvalidKeySpecException e) {
                throw new IOException(e);
            }
        }
    }
    private class PrivateKeySerializer extends JsonSerializer<PrivateKey> {

        @Override
        public void serialize(PrivateKey value, JsonGenerator gen, SerializerProvider serializers)
                throws IOException, JsonProcessingException {
            gen.writeBinary(value.getEncoded());
        }
    }

    private class PrivateKeyDeserializer extends JsonDeserializer<PrivateKey> {

        @Override
        public PrivateKey deserialize(JsonParser p, DeserializationContext ctxt)
                throws IOException, JsonProcessingException {

            try {
                return fact.generatePrivate(new PKCS8EncodedKeySpec(p.getBinaryValue()));
            } catch (InvalidKeySpecException e) {
                throw new IOException(e);
            }
        }
    }
}
