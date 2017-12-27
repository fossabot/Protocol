package org.laxio.piston.protocol.v340.packet.status.server;

import org.laxio.piston.piston.protocol.stream.PistonInput;
import org.laxio.piston.protocol.v340.packet.ProtocolPacket;

import java.io.IOException;

public class PingPacket extends ProtocolPacket {

    private long payload;

    public long getPayload() {
        return payload;
    }

    @Override
    public void onRead(PistonInput input) throws IOException {
        this.payload = input.readLong();
    }

    @Override
    public String toString() {
        return "PingPacket{" +
                "payload=" + payload +
                '}';
    }

}
