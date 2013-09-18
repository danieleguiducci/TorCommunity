package com.torcommunity.core.service;

import com.torcommunity.core.service.imp.CryptoUtil;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.bouncycastle.util.Arrays;

/**
 * Unit test for simple App.
 */
public class CryptoUtilTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public CryptoUtilTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( CryptoUtilTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp() throws Exception
    {
        CryptoUtil ci=new CryptoUtil();
        ci.afterPropertiesSet();
       byte[] msgDigest=ci.getSha1().digest("Hash of the account owner".getBytes());
       byte[] privaKeyDigest=ci.getSha1().digest("Its my privateKey".getBytes());
       String privateDomain=ci.generatePrivateDomain(privaKeyDigest,msgDigest);
       System.out.println("Public domain will be "+privateDomain.toLowerCase());
       byte[] domainSid=ci.generatePublicSid(privaKeyDigest);
       byte[] publicDomain=ci.privateDomain2publicDomain(privateDomain);
       byte[] publicDomainByUser=ci.domainSid2publicDomain(domainSid, msgDigest);
       assertTrue(Arrays.areEqual(publicDomainByUser, publicDomain));
    }
}
