package org.laxio.piston.protocol.v340.data;

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

