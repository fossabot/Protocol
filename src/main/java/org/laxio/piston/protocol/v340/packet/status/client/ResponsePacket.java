/*-
 * ========================LICENSE_START========================
 * Protocol
 * %%
 * Copyright (C) 2017 - 2018 Laxio
 * %%
 * This file is part of Piston, licensed under the MIT License (MIT).
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 * ========================LICENSE_END========================
 */
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
