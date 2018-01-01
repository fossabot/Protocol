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

public class UpdateBlockEntityPacket extends ProtocolPacket {

    private Location block;
    private BlockEntityAction action;
    // private NBTData data;

    public UpdateBlockEntityPacket() {
        // required empty constructor
    }

    public UpdateBlockEntityPacket(Location block, BlockEntityAction action) {
        this.block = block;
        this.action = action;
    }

    public Location getBlock() {
        return block;
    }

    public BlockEntityAction getAction() {
        return action;
    }

    @Override
    public void onWrite(PistonOutput output) throws IOException {
        output.writePosition(block);
        output.writeByte(action.getAction());
        // output.writeNBT(data);
    }

    @Override
    public String toString() {
        return "UpdateBlockEntityPacket{" +
                "block=" + block +
                ", action=" + action +
                '}';
    }

    public enum BlockEntityAction {

        MOB_SPAWNER(1),
        COMMAND_BLOCK(2),
        BEACON(3),
        MOB_HEAD(4),
        FLOWER_POT(5),
        BANNER(6),
        STRUCTURE_TILE(7),
        GATEWAY(8),
        SIGN_TEXT(9),
        @Deprecated
        SHULKER_BOX(10),
        BED(11);

        private byte action;

        BlockEntityAction(int action) {
            this.action = (byte) action;
        }

        public byte getAction() {
            return action;
        }

    }

}
