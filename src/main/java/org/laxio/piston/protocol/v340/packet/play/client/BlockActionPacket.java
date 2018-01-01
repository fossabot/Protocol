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

import org.laxio.piston.piston.protocol.stream.PistonOutput;
import org.laxio.piston.piston.world.Location;
import org.laxio.piston.protocol.v340.packet.ProtocolPacket;

import java.io.IOException;

public class BlockActionPacket extends ProtocolPacket {

    private Location block;
    private byte actionId;
    private byte param;
    private int blockType;

    public BlockActionPacket() {
        // required empty constructor
    }

    public BlockActionPacket(Location block, byte actionId, byte param, int blockType) {
        this.block = block;
        this.actionId = actionId;
        this.param = param;
        this.blockType = blockType;
    }

    public Location getBlock() {
        return block;
    }

    public byte getActionId() {
        return actionId;
    }

    public byte getParam() {
        return param;
    }

    public int getBlockType() {
        return blockType;
    }

    @Override
    public void onWrite(PistonOutput output) throws IOException {
        output.writePosition(block);
        output.writeByte(actionId);
        output.writeByte(param);
        output.writeVarInt(blockType);
    }

    @Override
    public String toString() {
        return "BlockActionPacket{" +
                "block=" + block +
                ", actionId=" + actionId +
                ", param=" + param +
                ", blockType=" + blockType +
                '}';
    }
}
