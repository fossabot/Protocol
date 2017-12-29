package org.laxio.piston.protocol.v340.packet.play.client;

import org.laxio.piston.piston.entity.player.Player;
import org.laxio.piston.piston.protocol.stream.PistonOutput;
import org.laxio.piston.protocol.v340.packet.ProtocolPacket;

import java.io.IOException;

public class SpawnPlayerPacket extends ProtocolPacket {

    private Player entity;

    public SpawnPlayerPacket() {
        // required empty packet
    }

    public SpawnPlayerPacket(Player entity) {
        this.entity = entity;
    }

    public Player getEntity() {
        return entity;
    }

    @Override
    public void onWrite(PistonOutput output) throws IOException {
        output.writeVarInt(entity.getEntityId());
        output.writeUUID(entity.getUUID());
        output.writeLocation(entity.getLocation(), true);
        output.writeMetadata(entity.getMetadata());
    }

    @Override
    public String toString() {
        return "SpawnPlayerPacket{" +
                "entity=" + entity +
                '}';
    }

}
