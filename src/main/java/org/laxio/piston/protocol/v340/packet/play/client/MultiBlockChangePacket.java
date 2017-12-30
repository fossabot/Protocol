package org.laxio.piston.protocol.v340.packet.play.client;

import org.laxio.piston.piston.list.LockableLinkedList;
import org.laxio.piston.piston.protocol.stream.PistonOutput;
import org.laxio.piston.piston.world.Block;
import org.laxio.piston.piston.world.Chunk;
import org.laxio.piston.protocol.v340.data.BlockChange;
import org.laxio.piston.protocol.v340.packet.ProtocolPacket;

import java.io.IOException;
import java.util.List;

public class MultiBlockChangePacket extends ProtocolPacket {

    private Chunk chunk;
    private List<BlockChange> changes;

    public MultiBlockChangePacket() {
        // required empty constructor
    }

    public MultiBlockChangePacket(Chunk chunk, List<BlockChange> changes) {
        this.chunk = chunk;

        LockableLinkedList<BlockChange> list = new LockableLinkedList<>(changes);
        list.setLocked(true);

        this.changes = list;
    }

    @Override
    public void onWrite(PistonOutput output) throws IOException {
        output.writeInt(chunk.getX());
        output.writeInt(chunk.getZ());
        output.writeVarInt(changes.size());

        for (BlockChange change : changes) {
            Block block = change.getBlock();
            int pos = ((block.getChunkX() & 0xF0) << 4) & (block.getChunkZ() & 0x0F);
            output.writeByte((byte) pos);
            output.writeVarInt(change.asVarInt());
        }
    }

    @Override
    public String toString() {
        return "MultiBlockChangePacket{" +
                "chunk=" + chunk +
                ", changes=" + changes +
                '}';
    }

}
