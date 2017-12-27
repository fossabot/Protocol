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
        return construct(key);
    }

    @Override
    public String toString() {
        return "EncryptionResponsePacket{" +
                "sharedSecret=" + Arrays.toString(sharedSecret) +
                ", verifyToken=" + Arrays.toString(verifyToken) +
                '}';
    }

    private SecretKey construct(PrivateKey key) {
        return new SecretKeySpec(decipher(key, sharedSecret), "AES");
    }

    public byte[] decipher(Key key, byte[] out) {
        return decipher(2, key, out);
    }

    private byte[] decipher(int opmode, Key key, byte[] out) {
        try {
            return cipher(opmode, key.getAlgorithm(), key).doFinal(out);
        } catch (IllegalBlockSizeException | BadPaddingException ex) {
            ex.printStackTrace();
        }

        return null;
    }

    private Cipher cipher(int opmode, String algorithm, Key key) {
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
