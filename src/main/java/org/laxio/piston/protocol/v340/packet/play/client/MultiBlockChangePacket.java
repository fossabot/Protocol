package org.laxio.piston.protocol.v340.packet.play.client;

        /*-
         * #%L
         * Protocol
         * %%
         * Copyright (C) 2017 Laxio
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

import org.laxio.piston.piston.list.LockableLinkedList;
import org.laxio.piston.piston.protocol.stream.PistonOutput;
import org.laxio.piston.piston.world.Block;
import org.laxio.piston.piston.world.Chunk;
import org.laxio.piston.protocol.v340.data.BlockChange;
import org.laxio.piston.protocol.v340.packet.ProtocolPacket;

import java.io.IOException;
import java.util.List;

public class MultiBlockChangePacket extends ProtocolPacket {

    private Chunk chunk;
    private List<BlockChange> changes;

    public MultiBlockChangePacket() {
        // required empty constructor
    }

    public MultiBlockChangePacket(Chunk chunk, List<BlockChange> changes) {
        this.chunk = chunk;

        LockableLinkedList<BlockChange> list = new LockableLinkedList<>(changes);
        list.setLocked(true);

        this.changes = list;
    }

    @Override
    public void onWrite(PistonOutput output) throws IOException {
        output.writeInt(chunk.getX());
        output.writeInt(chunk.getZ());
        output.writeVarInt(changes.size());

        for (BlockChange change : changes) {
            Block block = change.getBlock();
            int pos = ((block.getChunkX() & 0xF0) << 4) & (block.getChunkZ() & 0x0F);
            output.writeByte((byte) pos);
            output.writeVarInt(change.asVarInt());
        }
    }

    @Override
    public String toString() {
        return "MultiBlockChangePacket{" +
                "chunk=" + chunk +
                ", changes=" + changes +
                '}';
    }

}
