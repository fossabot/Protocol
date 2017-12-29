package org.laxio.piston.protocol.v340.packet.play.client;

import org.laxio.piston.piston.entity.Entity;
import org.laxio.piston.piston.entity.type.PaintingEntity;
import org.laxio.piston.piston.protocol.stream.PistonOutput;
import org.laxio.piston.protocol.v340.packet.ProtocolPacket;

import java.io.IOException;

public class SpawnPaintingPacket extends ProtocolPacket {

    private PaintingEntity entity;

    public SpawnPaintingPacket() {
        // required empty packet
    }

    public SpawnPaintingPacket(PaintingEntity entity) {
        this.entity = entity;
    }

    public Entity getEntity() {
        return entity;
    }

    @Override
    public void onWrite(PistonOutput output) throws IOException {
        output.writeVarInt(entity.getEntityId());
        output.writeUUID(entity.getUUID());
        output.writeString(entity.getTitle());
        output.writePosition(entity.getLocation());
        output.writeByte(entity.getDirection().getPaintingId());
    }

    @Override
    public String toString() {
        return "SpawnPaintingPacket{" +
                "entity=" + entity +
                '}';
    }

}
