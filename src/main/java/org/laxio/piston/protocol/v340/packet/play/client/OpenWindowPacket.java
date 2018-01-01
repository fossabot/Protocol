package org.laxio.piston.protocol.v340.packet.play.client;

        /*-
         * #%L
         * Protocol
         * %%
         * Copyright (C) 2017 - 2018 Laxio
         * %%
         * This file is part of Piston, licensed under the MIT License (MIT).
         *
         * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
         *
         * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
         *
         * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
         * #L%
         */

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
