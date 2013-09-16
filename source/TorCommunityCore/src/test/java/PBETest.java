
import java.security.Key;
import java.security.SecureRandom;
import java.security.Security;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Daniele
 */
public class PBETest
{
    public static void main(String[] args)
        throws Exception
    {
        Security.addProvider(new BouncyCastleProvider());
        String algo = "PBEWithSHA1andDESede";
        System.out.println("====== " + algo + " ======");

        char[] password = "password".toCharArray();
        SecureRandom rand = new SecureRandom();
        byte[] salt = new byte[32];
        rand.nextBytes(salt);
        int iterationCount = rand.nextInt(2048);

        //encryption key
        PBEKeySpec          encPBESpec = new PBEKeySpec(password, salt, iterationCount);
        SecretKeyFactory    encKeyFact = SecretKeyFactory.getInstance(algo);
        Key encKey = encKeyFact.generateSecret(encPBESpec);
        System.out.println("encryptioin iteration: " + iterationCount);

        //decryption key
        rand.nextBytes(salt);
        iterationCount = rand.nextInt(2048);
        PBEKeySpec          decPBESpec = new PBEKeySpec(password, salt, iterationCount);
        SecretKeyFactory    decKeyFact = SecretKeyFactory.getInstance(algo);
        Key decKey = decKeyFact.generateSecret(decPBESpec);
        System.out.println("decryptioin iteration: " + iterationCount);

        System.out.println("encryption key is same as decryption key? " + encKey.equals(decKey));

    }

}
