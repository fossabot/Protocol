package org.laxio.piston.protocol.v340.packet.play.client;

import org.laxio.piston.piston.boss.BossBar;
import org.laxio.piston.piston.protocol.stream.PistonOutput;
import org.laxio.piston.protocol.v340.data.boss.BarAction;
import org.laxio.piston.protocol.v340.packet.ProtocolPacket;

import java.io.IOException;

public class BossBarPacket extends ProtocolPacket {

    private BarAction action;

    public BossBarPacket() {
        // required empty constructor
    }

    public BossBarPacket(BarAction action) {
        this.action = action;
    }

    public BossBar getBar() {
        return action.getBar();
    }

    public BarAction getAction() {
        return action;
    }

    @Override
    public void onWrite(PistonOutput output) throws IOException {
        output.writeUUID(getBar().getUniqueId());
        output.writeVarInt(action.getId());
        action.write(output);
    }

    @Override
    public String toString() {
        return "BossBarPacket{" +
                "action=" + action +
                '}';
    }

}
