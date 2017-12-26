package org.laxio.piston.protocol.v340.packet.login.client;

import org.json.JSONObject;
import org.laxio.piston.piston.protocol.stream.PistonOutput;
import org.laxio.piston.protocol.v340.packet.ProtocolPacket;

import java.io.IOException;

public class DisconnectPacket extends ProtocolPacket {

    private JSONObject chat;

    public DisconnectPacket(JSONObject chat) {
        this.chat = chat;
    }

    public JSONObject getChat() {
        return chat;
    }

    @Override
    public void onWrite(PistonOutput output) throws IOException {
        output.writeString(chat.toString());
    }

}
