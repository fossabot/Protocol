package org.laxio.piston.protocol.v340.packet.handshake.server;

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

import org.laxio.piston.piston.protocol.ProtocolState;
import org.laxio.piston.piston.protocol.stream.PistonInput;
import org.laxio.piston.protocol.v340.packet.ProtocolPacket;

import java.io.IOException;

public class HandshakePacket extends ProtocolPacket {

    private int protocolVersion;
    private String address;
    private int port;
    private ProtocolState nextState;

    public int getProtocolVersion() {
        return protocolVersion;
    }

    public String getAddress() {
        return address;
    }

    public int getPort() {
        return port;
    }

    public ProtocolState getNextState() {
        return nextState;
    }

    @Override
    public void onRead(PistonInput input) throws IOException {
        this.protocolVersion = input.readVarInt();
        this.address = input.readString();
        this.port = input.readUnsignedShort();
        this.nextState = ProtocolState.getById(input.readVarInt());
    }

    @Override
    public String toString() {
        return "HandshakePacket{" +
                "protocolVersion=" + protocolVersion +
                ", address='" + address + '\'' +
                ", port=" + port +
                ", nextState=" + nextState +
                '}';
    }

}
