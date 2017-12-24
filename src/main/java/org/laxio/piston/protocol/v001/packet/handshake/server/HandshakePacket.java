package org.laxio.piston.protocol.v001.packet.handshake.server;

import org.laxio.piston.piston.protocol.stream.PistonInput;
import org.laxio.piston.piston.protocol.stream.PistonOutput;
import org.laxio.piston.protocol.v001.packet.ProtocolPacket;
import org.laxio.piston.protocol.v001.packet.ProtocolState;

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
    public void read(PistonInput input) throws IOException {
        this.protocolVersion = input.readVarInt();
        this.address = input.readString();
        this.port = input.readUnsignedShort();
        this.nextState = ProtocolState.getById(input.readVarInt());
    }

    @Override
    public void write(PistonOutput output) {
        throw new UnsupportedOperationException("Handshake Packet cannot be written by the server");
    }

}
