package org.laxio.piston.protocol.v340.packet.status.client;

import org.json.JSONArray;
import org.json.JSONObject;
import org.laxio.piston.piston.chat.MessageComponent;
import org.laxio.piston.piston.protocol.stream.PistonOutput;
import org.laxio.piston.protocol.v340.packet.ProtocolPacket;

import java.io.IOException;

public class ResponsePacket extends ProtocolPacket {

    private MessageComponent motd;

    public ResponsePacket() {
        // required empty
    }

    public ResponsePacket(MessageComponent motd) {
        this.motd = motd;
    }

    public MessageComponent getMotd() {
        return motd;
    }

    @Override
    public void onWrite(PistonOutput output) throws IOException {
        // TODO: fill out with meaningful data
        JSONObject json = new JSONObject();

        JSONObject version = new JSONObject();
        version.put("name", "Piston");
        version.put("protocol", getServer().getProtocol().getVersion());
        json.put("version", version);

        JSONObject players = new JSONObject();
        players.put("max", 0);
        players.put("online", 1);

        JSONArray sample = new JSONArray();
        JSONObject para = new JSONObject();
        para.put("name", "ParaPiston");
        para.put("id", "44df6b20-168f-4389-b7ac-434518207c2f");
        sample.put(para);
        players.put("sample", sample);
        json.put("players", players);

        JSONObject description = motd.toJSON();
        json.put("description", description);

        output.writeString(json.toString());
    }

    @Override
    public String toString() {
        return "ResponsePacket{}";
    }

}
