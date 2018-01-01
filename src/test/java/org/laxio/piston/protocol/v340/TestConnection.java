package org.laxio.piston.protocol.v340;

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

import org.laxio.piston.piston.protocol.Connection;
import org.laxio.piston.piston.protocol.Packet;
import org.laxio.piston.piston.protocol.ProtocolState;

import java.net.InetSocketAddress;

public class TestConnection implements Connection {

    @Override
    public ProtocolState getState() {
        throw new UnsupportedOperationException("Unavailable in test phase");
    }

    @Override
    public void setState(ProtocolState state) {
        throw new UnsupportedOperationException("Unavailable in test phase");
    }

    @Override
    public void sendPacket(Packet packet) {
        throw new UnsupportedOperationException("Unavailable in test phase");
    }

    @Override
    public boolean isEncrypted() {
        throw new UnsupportedOperationException("Unavailable in test phase");
    }

    @Override
    public InetSocketAddress getAddress() {
        throw new UnsupportedOperationException("Unavailable in test phase");
    }

    @Override
    public int getProtocolVersion() {
        throw new UnsupportedOperationException("Unavailable in test phase");
    }

}
