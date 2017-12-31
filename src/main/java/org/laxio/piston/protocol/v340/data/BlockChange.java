package org.laxio.piston.protocol.v340.data;

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

import org.laxio.piston.piston.world.Block;
import org.laxio.piston.piston.world.Location;

/**
 * Used to represent a change of a block for packets
 */
public class BlockChange {

    private final Block block;
    private final int id;
    private final short data;

    public BlockChange(Block block, int id, short data) {
        this.block = block;
        this.id = id;
        this.data = data;
    }

    /**
     * Gets the Block which is changing
     *
     * @return The block to change
     */
    public Block getBlock() {
        return block;
    }

    /**
     * Gets the location of the block that is being changed
     *
     * @return The location of the block to change
     */
    public Location getLocation() {
        return block.getLocation();
    }

    /**
     * The id of the block
     *
     * @return The id of the block
     */
    public int getId() {
        return id;
    }

    /**
     * The block metadata, used for blocks such as colored wool
     *
     * @return The block metadata
     */
    public short getData() {
        return data;
    }

    /**
     * Packs the block id and data into a single int
     *
     * @return The packed int
     */
    public int asVarInt() {
        return (id << 4) | data;
    }

    @Override
    public String toString() {
        return "BlockChange{" +
                "block=" + block +
                ", id=" + id +
                ", data=" + data +
                '}';
    }

}

