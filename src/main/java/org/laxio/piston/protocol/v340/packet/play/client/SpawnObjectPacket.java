package org.laxio.piston.protocol.v340.packet.play.client;

import org.laxio.piston.piston.entity.Entity;
import org.laxio.piston.piston.protocol.stream.PistonOutput;
import org.laxio.piston.protocol.v340.packet.ProtocolPacket;

import java.io.IOException;

public class SpawnObjectPacket extends ProtocolPacket {

    private Entity entity;

    public SpawnObjectPacket() {
        // required empty packet
    }

    public SpawnObjectPacket(Entity entity) {
        this.entity = entity;
    }

    public Entity getEntity() {
        return entity;
    }

    @Override
    public void onWrite(PistonOutput output) throws IOException {
        output.writeVarInt(entity.getEntityId());
        output.writeUUID(entity.getUUID());
        output.writeByte(entity.getType().getId());
        output.writeLocation(entity.getLocation(), true);
        output.writeInt(entity.getData());
        output.writeVelocity(entity.getVelocity());
    }

    @Override
    public String toString() {
        return "SpawnObjectPacket{" +
                "entity=" + entity +
                '}';
    }

}
