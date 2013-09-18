/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.torcommunity.core.content.builder;

import com.torcommunity.core.content.AccountManifest;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 *
 * @author Daniele
 */
public class AccountManifestBuilder {

    private ObjectMapper mapper = new ObjectMapper();

    public AccountManifest buildFromRaw(byte[] rawData) throws AccManifestReadingException {
        try {
            ByteArrayInputStream bis=new ByteArrayInputStream(rawData);
            GZIPInputStream gzipInput=new  GZIPInputStream(bis);
            return mapper.readValue(gzipInput, AccountManifest.class);
        } catch (IOException e) {
            throw new AccManifestReadingException(e);
        }
    }
    public byte[] serializa(AccountManifest manifest) {
        
        try {
            ByteArrayOutputStream bos=new  ByteArrayOutputStream();
            GZIPOutputStream gzipOut = new GZIPOutputStream(bos);
            mapper.writeValue(gzipOut, manifest);
            gzipOut.flush();
            gzipOut.finish();
            return bos.toByteArray();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        } finally {
            
        }
       
    }
    private static class AccManifestReadingException extends Exception {

        public AccManifestReadingException(Throwable cause) {
            super(cause);
        }
    }
}
