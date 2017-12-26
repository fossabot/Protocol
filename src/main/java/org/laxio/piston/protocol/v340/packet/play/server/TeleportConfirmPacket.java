package org.laxio.piston.protocol.v340.packet.play.server;

import org.laxio.piston.piston.protocol.stream.PistonInput;
import org.laxio.piston.protocol.v340.packet.ProtocolPacket;

import java.io.IOException;

public class TeleportConfirmPacket extends ProtocolPacket {

    private int teleportId;

    public int getTeleportId() {
        return teleportId;
    }

    @Override
    public void onRead(PistonInput input) throws IOException {
        this.teleportId = input.readVarInt();
    }

}
