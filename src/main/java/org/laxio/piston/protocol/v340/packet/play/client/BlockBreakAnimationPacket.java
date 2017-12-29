package org.laxio.piston.protocol.v340.packet.play.client;

import org.laxio.piston.piston.entity.Entity;
import org.laxio.piston.piston.protocol.stream.PistonOutput;
import org.laxio.piston.piston.world.Location;
import org.laxio.piston.protocol.v340.packet.ProtocolPacket;

import java.io.IOException;

public class BlockBreakAnimationPacket extends ProtocolPacket {

    private int entityId;
    private Location block;
    private byte stage;

    public BlockBreakAnimationPacket() {
        // required empty packet
    }

    public BlockBreakAnimationPacket(int entityId, Location block, byte stage) {
        this.entityId = entityId;
        this.block = block;
        this.stage = stage;
    }

    public BlockBreakAnimationPacket(Entity entity, Location block, byte stage) {
        this.entityId = entity.getEntityId();
        this.block = block;
        this.stage = stage;
    }

    public int getEntityId() {
        return entityId;
    }

    public Location getBlock() {
        return block;
    }

    public byte getStage() {
        return stage;
    }

    @Override
    public void onWrite(PistonOutput output) throws IOException {
        output.writeVarInt(entityId);
        output.writePosition(block);
        output.writeByte(stage);
    }

    @Override
    public String toString() {
        return "BlockBreakAnimationPacket{" +
                "entityId=" + entityId +
                ", block=" + block +
                ", stage=" + stage +
                '}';
    }

}
