package org.laxio.piston.protocol.v340.packet.play.client;

import org.laxio.piston.piston.protocol.stream.PistonOutput;
import org.laxio.piston.piston.world.Location;
import org.laxio.piston.protocol.v340.packet.ProtocolPacket;

import java.io.IOException;

public class BlockChangePacket extends ProtocolPacket {

    private Location block;
    private int type;
    private int meta;

    public BlockChangePacket() {
        // required empty packet
    }

    public BlockChangePacket(Location block, int type, int meta) {
        this.block = block;
        this.type = type;
        this.meta = meta;
    }

    public Location getBlock() {
        return block;
    }

    public int getType() {
        return type;
    }

    public int getMeta() {
        return meta;
    }

    @Override
    public void onWrite(PistonOutput output) throws IOException {
        output.writePosition(block);
        output.writeVarInt(type << 4 | (meta & 15));
    }

    @Override
    public String toString() {
        return "BlockChangePacket{" +
                "block=" + block +
                ", type=" + type +
                ", meta=" + meta +
                '}';
    }

}
