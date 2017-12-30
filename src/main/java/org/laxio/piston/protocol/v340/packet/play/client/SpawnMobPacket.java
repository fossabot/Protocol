package org.laxio.piston.protocol.v340.packet.play.client;

import org.laxio.piston.piston.entity.Entity;
import org.laxio.piston.piston.entity.type.LivingEntity;
import org.laxio.piston.piston.protocol.stream.PistonOutput;
import org.laxio.piston.protocol.v340.packet.ProtocolPacket;

import java.io.IOException;

public class SpawnMobPacket extends ProtocolPacket {

    private LivingEntity entity;

    public SpawnMobPacket() {
        // required empty constructor
    }

    public SpawnMobPacket(LivingEntity entity) {
        this.entity = entity;
    }

    public Entity getEntity() {
        return entity;
    }

    @Override
    public void onWrite(PistonOutput output) throws IOException {
        output.writeVarInt(entity.getEntityId());
        output.writeUUID(entity.getUUID());
        output.writeVarInt(entity.getType().getType());
        output.writeLocation(entity.getLocation(), true);
        output.writeInt(entity.getData());
        output.writeVelocity(entity.getVelocity());
        output.writeMetadata(entity.getMetadata());
    }

    @Override
    public String toString() {
        return "SpawnMobPacket{" +
                "entity=" + entity +
                '}';
    }

}
