package org.laxio.piston.protocol.v340.packet.login.client;

        /*-
         * #%L
         * Protocol
         * %%
         * Copyright (C) 2017 - 2018 Laxio
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

import org.laxio.piston.piston.protocol.stream.PistonOutput;
import org.laxio.piston.protocol.v340.packet.ProtocolPacket;

import java.io.IOException;
import java.security.PublicKey;
import java.util.Arrays;

public class EncryptionRequestPacket extends ProtocolPacket {

    private String serverName;
    private PublicKey key;
    private byte[] verifyToken;

    public EncryptionRequestPacket() {
        // required empty constructor
    }

    public EncryptionRequestPacket(String serverName, PublicKey key, byte[] verifyToken) {
        this.serverName = serverName;
        this.key = key;
        this.verifyToken = verifyToken;
    }

    public String getServerName() {
        return serverName;
    }

    public PublicKey getKey() {
        return key;
    }

    public byte[] getVerifyToken() {
        return verifyToken;
    }

    @Override
    public void onWrite(PistonOutput output) throws IOException {
        output.writeString(serverName);
        output.writeBytes(key.getEncoded());
        output.writeBytes(verifyToken);
    }

    @Override
    public String toString() {
        return "EncryptionRequestPacket{" +
                "serverName='" + serverName + '\'' +
                ", key=" + key +
                ", verifyToken=" + Arrays.toString(verifyToken) +
                '}';
    }

}
