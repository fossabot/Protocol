package org.laxio.piston.protocol.v340.packet.play.client;

import org.laxio.piston.piston.entity.Entity;
import org.laxio.piston.piston.entity.GlobalEntityType;
import org.laxio.piston.piston.entity.type.ExperienceOrbEntity;
import org.laxio.piston.piston.entity.type.GlobalEntity;
import org.laxio.piston.piston.protocol.stream.PistonOutput;
import org.laxio.piston.piston.world.Location;
import org.laxio.piston.protocol.v340.packet.ProtocolPacket;

import java.io.IOException;

public class SpawnGlobalEntityPacket extends ProtocolPacket {

    private GlobalEntity entity;

    public SpawnGlobalEntityPacket() {
        // required empty packet
    }

    public SpawnGlobalEntityPacket(GlobalEntity entity) {
        this.entity = entity;
    }

    @Override
    public void onWrite(PistonOutput output) throws IOException {
        output.writeVarInt(entity.getEntityId());
        output.writeByte(entity.getGlobalType().getType());
        output.writeLocation(entity.getLocation(), false);
    }

    @Override
    public String toString() {
        return "SpawnGlobalEntityPacket{" +
                "entity=" + entity +
                '}';
    }

}
