package org.laxio.piston.protocol.v340.packet.play.client;

import org.laxio.piston.piston.chat.MessageComponent;
import org.laxio.piston.piston.inventory.InventoryType;
import org.laxio.piston.piston.protocol.stream.PistonOutput;
import org.laxio.piston.protocol.v340.packet.ProtocolPacket;

import java.io.IOException;

public class OpenWindowPacket extends ProtocolPacket {

    private byte windowId;
    private InventoryType type;
    private MessageComponent title;
    private byte slots;
    private int entityId;

    public OpenWindowPacket() {
        // required empty constructor
    }

    public OpenWindowPacket(byte windowId, InventoryType type, MessageComponent title, byte slots) {
        this(windowId, type, title, slots, -1);
    }

    public OpenWindowPacket(byte windowId, InventoryType type, MessageComponent title, byte slots, int entityId) {
        this.windowId = windowId;
        this.type = type;
        this.title = title;
        this.slots = slots;
        this.entityId = entityId;
    }

    public byte getWindowId() {
        return windowId;
    }

    public InventoryType getType() {
        return type;
    }

    public MessageComponent getTitle() {
        return title;
    }

    public byte getSlots() {
        return slots;
    }

    public int getEntityId() {
        return entityId;
    }

    @Override
    public void onWrite(PistonOutput output) throws IOException {
        output.writeByte(windowId);
        output.writeString(type.getName());
        output.writeString(title.toJSON().toString());
        output.writeByte(slots);

        if (type == InventoryType.HORSE) {
            output.writeInt(entityId);
        }
    }

    @Override
    public String toString() {
        return "OpenWindowPacket{" +
                "windowId=" + windowId +
                ", type=" + type +
                ", title=" + title +
                ", slots=" + slots +
                ", entityId=" + entityId +
                '}';
    }
}
