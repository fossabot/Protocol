package org.laxio.piston.protocol.v340.packet.play.client;

import org.laxio.piston.piston.protocol.stream.PistonOutput;
import org.laxio.piston.protocol.v340.packet.ProtocolPacket;

import java.io.IOException;

public class WindowPropertyPacket extends ProtocolPacket {

    private byte windowId;
    private short property;
    private short value;

    public WindowPropertyPacket() {
        // required empty constructor
    }

    public WindowPropertyPacket(byte windowId, short property, short value) {
        this.windowId = windowId;
        this.property = property;
        this.value = value;
    }

    public byte getWindowId() {
        return windowId;
    }

    public short getProperty() {
        return property;
    }

    public short getValue() {
        return value;
    }

    @Override
    public void onWrite(PistonOutput output) throws IOException {
        output.writeByte(windowId);
        output.writeShort(property);
        output.writeShort(value);
    }

    @Override
    public String toString() {
        return "WindowPropertyPacket{" +
                "windowId=" + windowId +
                ", property=" + property +
                ", value=" + value +
                '}';
    }

}
