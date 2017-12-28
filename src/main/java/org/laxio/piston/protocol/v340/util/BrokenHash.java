package org.laxio.piston.protocol.v340.util;

import org.laxio.piston.piston.exception.PistonRuntimeException;
import org.laxio.piston.piston.exception.protocol.auth.SessionAuthenticationException;

import javax.crypto.SecretKey;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;

/**
 * Generates a broken Minecraft-style twos-complement signed
 * hex digest. Tested and confirmed to match vanilla.
 * <p>
 * See <a href="https://gist.github.com/unascribed/70e830d471d6a3272e3f">here</a>
 */
public class BrokenHash {

    public static String hash(String str) {
        try {
            byte[] digest = digest(str, "SHA-1");
            return new BigInteger(digest).toString(16);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static String hash(String serverName, PublicKey key, SecretKey secret) {
        try {
            byte[] ascii = serverName.getBytes("ISO_8859_1");
            test("ascii", "secret", "public");
            byte[] digest = digest("SHA-1", ascii, secret.getEncoded(), key.getEncoded());
            return new BigInteger(digest).toString(16);
        } catch (Exception ex) {
            throw new PistonRuntimeException(new SessionAuthenticationException("Unable to digest", ex));
        }
    }

    private static byte[] digest(String algorithm, byte[] serverName, byte[] secretKey, byte[] publicKey) throws NoSuchAlgorithmException {
        try {
            MessageDigest digest = MessageDigest.getInstance(algorithm);
            digest.update(serverName);
            digest.update(secretKey);
            digest.update(publicKey);

            return digest.digest();
        } catch (Exception ex) {
            throw new PistonRuntimeException(new SessionAuthenticationException("Unable to digest", ex));
        }
    }

    private static byte[] digest(String str, String algorithm) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance(algorithm);
        byte[] strBytes = str.getBytes(StandardCharsets.UTF_8);
        return md.digest(strBytes);
    }

    private static void test(String... digest) {
        String[] var3 = digest;
        int var4 = digest.length;

        for (int var5 = 0; var5 < var4; ++var5) {
            String var6 = var3[var5];
            System.out.print(var6 + ".");
        }

        System.out.println();
    }

}
