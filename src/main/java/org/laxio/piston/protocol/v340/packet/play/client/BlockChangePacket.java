package org.laxio.piston.protocol.v340.packet.play.client;

import org.laxio.piston.piston.protocol.stream.PistonOutput;
import org.laxio.piston.protocol.v340.data.BlockChange;
import org.laxio.piston.protocol.v340.packet.ProtocolPacket;

import java.io.IOException;

public class BlockChangePacket extends ProtocolPacket {

    private BlockChange change;

    public BlockChangePacket() {
        // required empty constructor
    }

    public BlockChangePacket(BlockChange change) {
        this.change = change;
    }

    public BlockChange getChange() {
        return change;
    }

    @Override
    public void onWrite(PistonOutput output) throws IOException {
        output.writePosition(change.getLocation());
        output.writeVarInt(change.asVarInt());
    }

    @Override
    public String toString() {
        return "BlockChangePacket{" +
                "change=" + change +
                '}';
    }

}
