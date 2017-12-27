package org.laxio.piston.protocol.v340.packet.login.server;

import org.laxio.piston.piston.protocol.stream.PistonInput;
import org.laxio.piston.protocol.v340.packet.ProtocolPacket;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.util.Arrays;

public class EncryptionResponsePacket extends ProtocolPacket {

    private byte[] sharedSecret;
    private byte[] verifyToken;

    public byte[] getSharedSecret() {
        return sharedSecret;
    }

    public byte[] getVerifyToken() {
        return verifyToken;
    }

    @Override
    public void onRead(PistonInput input) throws IOException {
        sharedSecret = input.readBytes();
        verifyToken = input.readBytes();
    }

    public SecretKey getSecretKey(PrivateKey key) {
        return construct(key, sharedSecret);
    }

    @Override
    public String toString() {
        return "EncryptionResponsePacket{" +
                "sharedSecret=" + Arrays.toString(sharedSecret) +
                ", verifyToken=" + Arrays.toString(verifyToken) +
                '}';
    }

    private static SecretKey construct(PrivateKey key, byte[] out) {
        return new SecretKeySpec(encrypt(key, out), "AES");
    }

    private static byte[] encrypt(Key key, byte[] out) {
        return encrypt(2, key, out);
    }

    private static byte[] encrypt(int opmode, Key key, byte[] out) {
        try {
            return cipher(opmode, key.getAlgorithm(), key).doFinal(out);
        } catch (IllegalBlockSizeException | BadPaddingException ex) {
            ex.printStackTrace();
        }

        return null;
    }

    private static Cipher cipher(int opmode, String algorithm, Key key) {
        try {
            Cipher var3 = Cipher.getInstance(algorithm);
            var3.init(opmode, key);
            return var3;
        } catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException ex) {
            ex.printStackTrace();
        }

        return null;
    }

}
