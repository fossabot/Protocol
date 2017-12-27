package org.laxio.piston.protocol.v340.packet.login.client;

import org.laxio.piston.piston.player.Player;
import org.laxio.piston.piston.protocol.stream.PistonOutput;
import org.laxio.piston.protocol.v340.packet.ProtocolPacket;

import java.io.IOException;

public class LoginSuccessPacket extends ProtocolPacket {

    private Player player;

    public LoginSuccessPacket() {
        // required empty packet
    }

    public LoginSuccessPacket(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    @Override
    public void onWrite(PistonOutput output) throws IOException {
        output.writeUUID(player.getUUID(), true);
        output.writeString(player.getName());
    }

    @Override
    public String toString() {
        return "LoginSuccessPacket{" +
                "player=" + player +
                '}';
    }

}
