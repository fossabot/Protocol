package org.laxio.piston.protocol.v340.packet.play.client;

import org.laxio.piston.piston.protocol.stream.PistonOutput;
import org.laxio.piston.piston.world.Location;
import org.laxio.piston.protocol.v340.packet.ProtocolPacket;

import java.io.IOException;

public class UpdateBlockEntityPacket extends ProtocolPacket {

    private Location block;
    private BlockEntityAction action;
    // private NBTData data;

    public UpdateBlockEntityPacket() {
        // required empty packet
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
