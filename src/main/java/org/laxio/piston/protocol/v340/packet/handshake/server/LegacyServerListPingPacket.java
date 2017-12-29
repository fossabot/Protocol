package org.laxio.piston.protocol.v340.packet.handshake.server;

import org.laxio.piston.piston.protocol.stream.PistonInput;
import org.laxio.piston.protocol.v340.packet.ProtocolPacket;

/**
 * See <a href="https://gist.github.com/4poc/6281388">this Gist</a> for an example from the client-side
 */
public class LegacyServerListPingPacket extends ProtocolPacket {

    @Override
    public void onRead(PistonInput input) {
        // TODO: complete
    }

    @Override
    public String toString() {
        return "LegacyServerListPingPacket{}";
    }

}
