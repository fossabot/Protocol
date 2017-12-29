package org.laxio.piston.protocol.v340.packet.login.client;

import org.laxio.piston.piston.chat.MessageBuilder;
import org.laxio.piston.piston.chat.MessageComponent;
import org.laxio.piston.piston.protocol.stream.PistonOutput;
import org.laxio.piston.protocol.v340.packet.ProtocolPacket;

import java.io.IOException;

public class DisconnectPacket extends ProtocolPacket {

    private MessageComponent message;

    public DisconnectPacket() {
        // required empty packet
    }

    @Deprecated
    public DisconnectPacket(String message) {
        this.message = MessageBuilder.builder().message(message).build();
    }

    public MessageComponent getMessage() {
        return message;
    }

    @Override
    public void onWrite(PistonOutput output) throws IOException {
        output.writeString(this.message.toJSON().toString());
    }

    @Override
    public String toString() {
        return "DisconnectPacket{" +
                "message=" + message +
                '}';
    }

}
