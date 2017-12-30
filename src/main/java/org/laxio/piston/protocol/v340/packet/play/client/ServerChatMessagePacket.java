package org.laxio.piston.protocol.v340.packet.play.client;

import org.laxio.piston.piston.chat.ChatPosition;
import org.laxio.piston.piston.chat.MessageComponent;
import org.laxio.piston.piston.protocol.stream.PistonOutput;
import org.laxio.piston.protocol.v340.packet.ProtocolPacket;

import java.io.IOException;

public class ServerChatMessagePacket extends ProtocolPacket {

    private MessageComponent message;
    private ChatPosition position;

    public ServerChatMessagePacket() {
        // required empty constructor
    }

    public ServerChatMessagePacket(MessageComponent message, ChatPosition position) {
        this.message = message;
        this.position = position;
    }

    public MessageComponent getMessage() {
        return message;
    }

    public ChatPosition getPosition() {
        return position;
    }

    @Override
    public void onWrite(PistonOutput output) throws IOException {
        output.writeString(message.toJSON().toString());
        output.writeByte(position.getPosition());
    }

    @Override
    public String toString() {
        return "ServerChatMessagePacket{" +
                "message=" + message +
                ", position=" + position +
                '}';
    }

}
