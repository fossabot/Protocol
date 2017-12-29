package org.laxio.piston.protocol.v340.packet.status.client;

import org.json.JSONArray;
import org.json.JSONObject;
import org.laxio.piston.piston.chat.ChatColor;
import org.laxio.piston.piston.chat.MessageBuilder;
import org.laxio.piston.piston.protocol.stream.PistonOutput;
import org.laxio.piston.protocol.v340.packet.ProtocolPacket;

import java.io.IOException;

public class ResponsePacket extends ProtocolPacket {

    @Override
    public void onWrite(PistonOutput output) throws IOException {
        // TODO: fill out with meaningful data
        JSONObject json = new JSONObject();

        JSONObject version = new JSONObject();
        version.put("name", "Piston");
        version.put("protocol", 340);
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

        MessageBuilder builder = MessageBuilder.builder();
        builder.message(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "L A X I O" + ChatColor.GRAY + " - TEST SERVER\n" + ChatColor.YELLOW + "New line support?");

        JSONObject description = builder.build().toJSON();
        json.put("description", description);

        output.writeString(json.toString());
    }

    @Override
    public String toString() {
        return "ResponsePacket{}";
    }

}
