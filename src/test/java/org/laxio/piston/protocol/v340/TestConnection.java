package org.laxio.piston.protocol.v340;

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
