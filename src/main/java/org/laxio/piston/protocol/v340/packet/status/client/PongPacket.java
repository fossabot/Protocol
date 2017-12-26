package org.laxio.piston.protocol.v340.packet.status.client;

import org.laxio.piston.piston.protocol.stream.PistonOutput;
import org.laxio.piston.protocol.v340.packet.ProtocolPacket;

import java.io.IOException;

public class PongPacket extends ProtocolPacket {

    private long payload;

    public PongPacket() {
        // required empty packet
    }

    public PongPacket(long payload) {
        this.payload = payload;
    }

    public long getPayload() {
        return payload;
    }

    @Override
    public void onWrite(PistonOutput output) throws IOException {
        output.writeLong(payload);
    }

    @Override
    public String toString() {
        return "PongPacket{" +
                "payload=" + payload +
                '}';
    }

}
