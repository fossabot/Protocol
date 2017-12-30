package org.laxio.piston.protocol.v340.packet.play.client;

import org.laxio.piston.piston.entity.Entity;
import org.laxio.piston.piston.entity.type.ExperienceOrbEntity;
import org.laxio.piston.piston.protocol.stream.PistonOutput;
import org.laxio.piston.protocol.v340.packet.ProtocolPacket;

import java.io.IOException;

public class SpawnExperienceOrbPacket extends ProtocolPacket {

    private ExperienceOrbEntity entity;

    public SpawnExperienceOrbPacket() {
        // required empty constructor
    }

    public SpawnExperienceOrbPacket(ExperienceOrbEntity entity) {
        this.entity = entity;
    }

    public Entity getEntity() {
        return entity;
    }

    @Override
    public void onWrite(PistonOutput output) throws IOException {
        output.writeVarInt(entity.getEntityId());
        output.writeLocation(entity.getLocation(), false);
        output.writeShort(entity.getExperience());
    }

    @Override
    public String toString() {
        return "SpawnExperienceOrbPacket{" +
                "entity=" + entity +
                '}';
    }

}
