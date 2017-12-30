package org.laxio.piston.protocol.v340.packet.play.client;

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
