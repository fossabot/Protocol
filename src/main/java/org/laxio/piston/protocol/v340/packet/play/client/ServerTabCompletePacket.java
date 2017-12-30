package org.laxio.piston.protocol.v340.packet.play.client;

import org.laxio.piston.piston.list.LockableLinkedList;
import org.laxio.piston.piston.protocol.stream.PistonOutput;
import org.laxio.piston.protocol.v340.packet.ProtocolPacket;

import java.io.IOException;
import java.util.List;

public class ServerTabCompletePacket extends ProtocolPacket {

    private List<String> matches;

    public ServerTabCompletePacket() {
        // required empty constructor
    }

    public ServerTabCompletePacket(List<String> matches) {
        LockableLinkedList<String> list = new LockableLinkedList<>(matches);
        list.setLocked(true);

        this.matches = list;
    }

    public List<String> getMatches() {
        return matches;
    }

    @Override
    public void onWrite(PistonOutput output) throws IOException {
        output.writeVarInt(matches.size());

        for (String match : matches) {
            output.writeString(match);
        }
    }

    @Override
    public String toString() {
        return "ServerTabCompletePacket{" +
                "matches=" + matches +
                '}';
    }

}
