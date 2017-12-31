package org.laxio.piston.protocol.v340.packet.status.server;

import org.laxio.piston.piston.protocol.stream.PistonInput;
import org.laxio.piston.protocol.v340.packet.ProtocolPacket;

public class RequestPacket extends ProtocolPacket {

    @Override
    public void onRead(PistonInput input) {
        // empty packet
    }

    @Override
    public String toString() {
        return "RequestPacket{}";
    }

}
