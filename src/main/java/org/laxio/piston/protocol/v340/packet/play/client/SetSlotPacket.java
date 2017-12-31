package org.laxio.piston.protocol.v340.packet.play.client;

import org.laxio.piston.piston.item.Slot;
import org.laxio.piston.piston.protocol.stream.PistonOutput;
import org.laxio.piston.protocol.v340.packet.ProtocolPacket;

import java.io.IOException;

public class SetSlotPacket extends ProtocolPacket {

    private byte windowId;
    private short slotId;
    private Slot slot;

    public SetSlotPacket() {
        // required empty constructor
    }

    public SetSlotPacket(byte windowId, short slotId, Slot slot) {
        this.windowId = windowId;
        this.slotId = slotId;
        this.slot = slot;
    }

    public byte getWindowId() {
        return windowId;
    }

    public short getSlotId() {
        return slotId;
    }

    public Slot getSlot() {
        return slot;
    }

    @Override
    public void onWrite(PistonOutput output) throws IOException {
        output.writeByte(windowId);

        output.writeShort(slotId);
        // TODO: Write implementation for slot read/write
        throw new UnsupportedOperationException("Slots are not currently supported");
    }

    @Override
    public String toString() {
        return "SetSlotPacket{" +
                "windowId=" + windowId +
                ", slotId=" + slotId +
                ", slot=" + slot +
                '}';
    }

}
