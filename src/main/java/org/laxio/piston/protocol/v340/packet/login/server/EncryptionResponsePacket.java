package org.laxio.piston.protocol.v340.packet.login.server;

        /*-
         * #%L
         * Protocol
         * %%
         * Copyright (C) 2017 Laxio
         * %%
         * This file is part of Piston, licensed under the MIT License (MIT).
         *
         * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
         *
         * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
         *
         * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
         * #L%
         */

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
