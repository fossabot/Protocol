package org.laxio.piston.protocol.v340.packet.handshake.client;

import org.laxio.piston.piston.protocol.stream.PistonOutput;
import org.laxio.piston.protocol.v340.packet.ProtocolPacket;

import java.io.IOException;

/**
 * See <a href="https://gist.github.com/4poc/6281388">this Gist</a> for an example from the client-side
 * other references: <a href="http://wiki.vg/Server_List_Ping#1.6">wiki.vg#1.6</a>
 */
public class LegacyServerListResponsePacket extends ProtocolPacket {

    @Override
    public void onWrite(PistonOutput output) throws IOException {
        // TODO: complete packet
    }

    @Override
    public String toString() {
        return "LegacyServerListResponsePacket{}";
    }

}
