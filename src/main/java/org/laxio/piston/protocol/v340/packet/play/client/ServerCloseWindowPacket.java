package org.laxio.piston.protocol.v340.packet.play.client;

import org.laxio.piston.piston.protocol.stream.PistonOutput;
import org.laxio.piston.protocol.v340.packet.ProtocolPacket;

import java.io.IOException;

public class ServerCloseWindowPacket extends ProtocolPacket {

    private byte windowId;

    public ServerCloseWindowPacket() {
        // required empty constructor
    }

    public byte getWindowId() {
        return windowId;
    }

    @Override
    public void onWrite(PistonOutput output) throws IOException {
        output.writeByte(windowId);
    }

    @Override
    public String toString() {
        return "ServerCloseWindowPacket{" +
                "windowId=" + windowId +
                '}';
    }

}
