package org.laxio.piston.protocol.v340.packet.play.client;

import org.laxio.piston.piston.item.Slot;
import org.laxio.piston.piston.list.LockableLinkedList;
import org.laxio.piston.piston.protocol.stream.PistonOutput;
import org.laxio.piston.protocol.v340.packet.ProtocolPacket;

import java.io.IOException;
import java.util.List;

public class WindowItemsPacket extends ProtocolPacket {

    private byte windowId;
    private List<Slot> slots;

    public WindowItemsPacket() {
        // required empty constructor
    }

    public WindowItemsPacket(byte windowId, List<Slot> slots) {
        this.windowId = windowId;

        LockableLinkedList<Slot> list = new LockableLinkedList<>(slots);
        list.setLocked(true);

        this.slots = list;
    }

    public byte getWindowId() {
        return windowId;
    }

    public List<Slot> getSlots() {
        return slots;
    }

    @Override
    public void onWrite(PistonOutput output) throws IOException {
        output.writeByte(windowId);

        output.writeShort((short) slots.size());
        // TODO: Write implementation for slot read/write
        throw new UnsupportedOperationException("Slots are not currently supported");

        /*
        for (Slot slot : slots) {
        }
        */
    }

    @Override
    public String toString() {
        return "WindowItemsPacket{" +
                "windowId=" + windowId +
                ", slots=" + slots +
                '}';
    }

}
