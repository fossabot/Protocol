package org.laxio.piston.protocol.v340.packet.play.client;

import org.laxio.piston.piston.entity.player.Animation;
import org.laxio.piston.piston.entity.player.Player;
import org.laxio.piston.piston.protocol.stream.PistonOutput;
import org.laxio.piston.protocol.v340.packet.ProtocolPacket;

import java.io.IOException;

public class ServerAnimationPacket extends ProtocolPacket {

    private Player player;
    private Animation animation;

    public ServerAnimationPacket() {
        // required empty packet
    }

    public ServerAnimationPacket(Player player, Animation animation) {
        this.player = player;
        this.animation = animation;
    }

    @Override
    public void onWrite(PistonOutput output) throws IOException {
        output.writeVarInt(player.getEntityId());
        output.writeByte(animation.getId());
    }

    @Override
    public String toString() {
        return "ServerAnimationPacket{" +
                "player=" + player +
                ", animation=" + animation +
                '}';
    }

}
