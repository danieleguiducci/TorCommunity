package com.torcom;

import org.apache.commons.codec.binary.Base32;
import org.junit.Test;

import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * Created by daniele on 21/12/2016.
 */
public class GenerateG {

    @Test
    public void generateG() {
        Base32 base32 = new Base32();
        SecureRandom sr = new SecureRandom();
        BigInteger bigger = null;
        for(int i =0; i< 100000; i++) {
            BigInteger integer = BigInteger.probablePrime(Const.PUBLIC_DOMAIN_LENGTH*8-1, sr);
            if(integer.toByteArray().length == Const.PUBLIC_DOMAIN_LENGTH) {
                if (bigger == null) {
                    bigger = integer;
                } else if (integer.compareTo(bigger) == 1) {
                    bigger = integer;
                    System.out.println(bigger);
                    System.out.println(base32.encodeAsString(bigger.toByteArray()));
                }
            }

        }

    }

    private static byte[] zeroPad(byte[] data, int minSize) {
        int toPad = minSize - data.length;
        if (toPad < 0) {
            throw new IllegalArgumentException("minSize " + minSize + " lunghezza:" + data.length);
        }
        if (toPad == 0) {
            return data;
        }
        byte[] newData = new byte[minSize];
        int i = 0;
        for (; i < toPad; i++) {
            newData[i] = 0;
        }
        for (int j = 0; j < data.length; j++) {
            newData[i + j] = data[j];
        }
        return newData;
    }
}
