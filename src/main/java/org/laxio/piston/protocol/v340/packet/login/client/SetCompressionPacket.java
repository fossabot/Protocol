package org.laxio.piston.protocol.v340.packet.login.client;

import org.laxio.piston.piston.protocol.stream.PistonOutput;
import org.laxio.piston.protocol.v340.packet.ProtocolPacket;

import java.io.IOException;

public class SetCompressionPacket extends ProtocolPacket {

    private int threshold;

    public SetCompressionPacket() {
        // required empty packet
    }

    public SetCompressionPacket(int threshold) {
        this.threshold = threshold;
    }

    public int getThreshold() {
        return threshold;
    }

    @Override
    public void onWrite(PistonOutput output) throws IOException {
        output.writeVarInt(threshold);
    }

}
