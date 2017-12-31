package org.laxio.piston.protocol.v340.packet.play.client;

import org.laxio.piston.piston.protocol.stream.PistonOutput;
import org.laxio.piston.protocol.v340.packet.ProtocolPacket;

import java.io.IOException;

public class ServerConfirmTransactionPacket extends ProtocolPacket {

    private byte windowId;
    private short actionId;
    private boolean accepted;

    public ServerConfirmTransactionPacket() {
        // required empty constructor
    }

    public ServerConfirmTransactionPacket(byte windowId, short actionId, boolean accepted) {
        this.windowId = windowId;
        this.actionId = actionId;
        this.accepted = accepted;
    }

    @Override
    public void onWrite(PistonOutput output) throws IOException {
        output.writeByte(windowId);
        output.writeShort(actionId);
        output.writeBoolean(accepted);
    }

    public byte getWindowId() {
        return windowId;
    }

    public short getActionId() {
        return actionId;
    }

    public boolean isAccepted() {
        return accepted;
    }

    @Override
    public String toString() {
        return "ServerConfirmTransactionPacket{" +
                "windowId=" + windowId +
                ", actionId=" + actionId +
                ", accepted=" + accepted +
                '}';
    }

}
